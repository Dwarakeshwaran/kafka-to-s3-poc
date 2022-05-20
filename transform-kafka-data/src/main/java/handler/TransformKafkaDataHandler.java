package handler;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import config.S3Config;
import model.Cricket;
import service.TransformKafkaDataService;

public class TransformKafkaDataHandler implements RequestHandler<Map<Object, Object>, String> {

	private TransformKafkaDataService service = new TransformKafkaDataService();
	private S3Config config = new S3Config();
	private String s3BucketName = "kafka-to-firehose-data";


	@Override
	public String handleRequest(Map<Object, Object> event, Context context) {

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
		if(s3FileData!=null)
			matchListInString = service.getStringListOfMatches(s3FileData);

		/*
		 * 5. For each String objects in the list, store that in a list of Cricket
		 * Objects
		 */
		List<Cricket> cricketList = service.getCricketList(matchListInString);		

		/* 6. For each Cricket Objects in the list, write that data in a CSV file
		 *    and store it in tmp folder of Lambda 
		 */
		String fileName = "/tmp/cricket-results.csv";
		service.storeCsvFileInTempFolderOfLambda(fileName, cricketList);

		/*
		 * 7. Finally Get the CSV file from the tmp folder and store it in the S3 bucket 
		 * and delete the corresponding file
		 */
		if(service.storeCsvFileInS3Bucket(s3BucketName, s3FileName, config.getS3Client()))
			service.deleteOldFile(s3BucketName, s3FileName, config.getS3Client());
		

		return null;
	}

}
