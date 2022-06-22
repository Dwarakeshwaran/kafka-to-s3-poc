package dwaki.firehoseconnector;

import java.nio.ByteBuffer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehose;
import com.amazonaws.services.kinesisfirehose.model.PutRecordRequest;
import com.amazonaws.services.kinesisfirehose.model.Record;

import dwaki.firehoseconnector.config.FirehoseConnectorConfig;

@SpringBootApplication
public class FirehoseconnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirehoseconnectorApplication.class, args);

		System.out.println("Firehose Connector Functionality Starts...");

		FirehoseConnectorConfig config = new FirehoseConnectorConfig();

		// 1. Get KinesisFirehose Client
		AmazonKinesisFirehose client = config.getClient();
		
		System.out.println("Firehose Client Configured");
		
		//2. Create a PutRecordRequest
		String movieQuote = "\"Carpe diem. Seize the day, boys. Make your lives extraordinary.\"\n"
				+ "\n"
				+ "- Dead Poets Society";
		
		PutRecordRequest putRecordRequest = new PutRecordRequest();
		Record record = new Record().withData(ByteBuffer.wrap(movieQuote.getBytes()));
		putRecordRequest.setDeliveryStreamName("firehose-connector-delivery-stream");
		putRecordRequest.setRecord(record);
		
		System.out.println("Movie Quote PutRecordRequest Done!");
		
		//3. Send the request to KinesisFirehose
		client.putRecord(putRecordRequest);
		
		System.out.println("Data Sent");

	}

}
