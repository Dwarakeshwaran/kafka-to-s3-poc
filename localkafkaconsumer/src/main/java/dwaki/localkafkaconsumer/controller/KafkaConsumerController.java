package dwaki.localkafkaconsumer.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehose;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseClientBuilder;
import com.amazonaws.services.kinesisfirehose.model.PutRecordRequest;
import com.amazonaws.services.kinesisfirehose.model.Record;

import dwaki.localkafkaconsumer.avro.model.Anime;
import dwaki.localkafkaconsumer.avro.model.BillingValue;

@Service
public class KafkaConsumerController {

	@Value("${firehose.delivery-stream.name}")
	private String deliveryStreamName;

//	@KafkaListener(topics = "anime-review", groupId = "cricket")
//	public void listen(@Payload List<Anime> anime) throws IOException {
//
//		System.out.println("Anime Data Before Decoding: " + anime);
//
//		AmazonKinesisFirehose firehoseClient = getFirehoseClient();
//
//		PutRecordRequest putRecordRequest = new PutRecordRequest();
//		putRecordRequest.setDeliveryStreamName(deliveryStreamName);
//
//		Record record = new Record().withData(ByteBuffer.wrap(anime.toString().getBytes()));
//		putRecordRequest.setRecord(record);
//
//		firehoseClient.putRecord(putRecordRequest);
//
//		System.out.println("Data Sent!");
//
//	}

	@KafkaListener(topics = "billing-value-limit-3", groupId = "billing")
	public void listenBilling(@Payload List<BillingValue> value) {

		System.out.println("Billing Value: " + value);
		
		AmazonKinesisFirehose firehoseClient = getFirehoseClient();

		PutRecordRequest putRecordRequest = new PutRecordRequest();
		putRecordRequest.setDeliveryStreamName(deliveryStreamName);

		Record record = new Record().withData(ByteBuffer.wrap(value.toString().getBytes()));
		putRecordRequest.setRecord(record);

		firehoseClient.putRecord(putRecordRequest);
		
		System.out.println("Data Sent!");
		

	}

	public AmazonKinesisFirehose getFirehoseClient() {

		return AmazonKinesisFirehoseClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
	}
}
