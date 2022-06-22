package handler;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisAnalyticsInputPreprocessingResponse;
import com.amazonaws.services.lambda.runtime.events.KinesisAnalyticsInputPreprocessingResponse.Result;
import com.amazonaws.services.lambda.runtime.events.KinesisFirehoseEvent;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import config.S3Config;
import model.BillingValue;
import model.Cricket;
import service.TransformKafkaDataService;

public class TransformKafkaDataHandler
		implements RequestHandler<KinesisFirehoseEvent, KinesisAnalyticsInputPreprocessingResponse> {

	private TransformKafkaDataService service = new TransformKafkaDataService();
	private S3Config config = new S3Config();
	private String s3BucketName = "kafka-to-firehose-data";

	public String handleRequest2(Map<Object, Object> event, Context context) {

		System.out.println("Inside Lambda");
		System.out.println("Input Class: " + event.getClass());
		System.out.println("Input " + event);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String eventInfo = gson.toJson(event);

		/* 1. Map the event info to S3EventNotification and get the File Name */

		S3EventNotification s3Object = S3EventNotification.parseJson(eventInfo);
		String s3FileName = s3Object.getRecords().get(0).getS3().getObject().getKey();
		System.out.println("S3 Object Name: " + s3Object.getRecords().get(0).getS3().getObject().getKey());

		/* 2. From the file Name, get the InputStream */

		InputStream stream = service.getStreamFromKey(s3BucketName, s3FileName, config.getS3Client());

		/* 3. Read the data from the stream and store it in the String */
		String s3FileData = null;

		if (stream != null)
			s3FileData = service.stringifyStreamData(stream);

		/*
		 * 4. Read the data from the String and store the data within open and close
		 * curly braces in a list of String Builder objects
		 */
		List<StringBuilder> matchListInString = null;
		if (!s3FileData.isEmpty())
			matchListInString = service.stringifyList(s3FileData);

		/*
		 * 5. For each String objects in the list, store that in a list of Cricket
		 * Objects
		 */

		List<Cricket> cricketList = null;
		if (matchListInString != null)
			cricketList = service.getCricketList(matchListInString);

		/*
		 * 6. For each Cricket Objects in the list, write that data in a CSV file and
		 * store it in tmp folder of Lambda
		 */
		String fileName = "/tmp/cricket-results.csv";
		if (cricketList != null)
			service.storeCsvFileInTempFolderOfLambda(fileName, cricketList);

		/*
		 * 7. Finally Get the CSV file from the tmp folder and store it in the S3 bucket
		 * and delete the corresponding file
		 */
		if (service.storeCsvFileInS3Bucket(s3BucketName, s3FileName, config.getS3Client()))
			service.deleteOldFile(s3BucketName, s3FileName, config.getS3Client());

		return null;
	}

	@Override
	public KinesisAnalyticsInputPreprocessingResponse handleRequest(KinesisFirehoseEvent kinesisFirehoseEvent,
			Context context) {

		int recordSize = kinesisFirehoseEvent.getRecords().size();
		System.out.println("No. Of Records: " + recordSize);

		/*
		 * 1. Get Data from KinesisFirehose and store it in a String Object
		 */

		List<KinesisAnalyticsInputPreprocessingResponse.Record> records = new ArrayList<KinesisAnalyticsInputPreprocessingResponse.Record>();

		kinesisFirehoseEvent.getRecords().forEach(record -> {
			String data = StandardCharsets.UTF_8.decode(record.getData()).toString();
			System.out.println("Data: " + data);

			/*
			 * 2. Read the data from the String and store the data within open and close
			 * curly braces in a list of String Builder objects
			 */
			List<StringBuilder> billingValueListInString = null;
			if (!data.isEmpty())
				billingValueListInString = service.stringifyList(data);

			billingValueListInString.remove(0);
			billingValueListInString.remove(billingValueListInString.size() - 1);
			
			
			
			
			
			/*
			 * 3. For each String objects in the list, store that in a list of Billing Value Objects
			 */

			List<BillingValue> billingValueList = null;
			if (billingValueListInString != null)
				billingValueList = service.getbillingValueList(billingValueListInString);

			/*
			 * 4. For each Billing Value Objects in the list, write that data in a CSV file and
			 * store it in tmp folder of Lambda
			 */
			String fileName = "/tmp/ls_billing_nf-value.csv";
			byte[] billingValueByteArray = null;
			if (billingValueList != null)
				billingValueByteArray = service.getByteArrayOfCsvFile(fileName, billingValueList, recordSize);

			KinesisAnalyticsInputPreprocessingResponse.Record transformedRecord = new KinesisAnalyticsInputPreprocessingResponse.Record();
			transformedRecord.setRecordId(record.getRecordId());
			transformedRecord.setResult(Result.Ok);
			transformedRecord.setData(ByteBuffer.wrap(billingValueByteArray));

			records.add(transformedRecord);

		});

		KinesisAnalyticsInputPreprocessingResponse response = new KinesisAnalyticsInputPreprocessingResponse();
		response.setRecords(records);

		return response;
	}

}
