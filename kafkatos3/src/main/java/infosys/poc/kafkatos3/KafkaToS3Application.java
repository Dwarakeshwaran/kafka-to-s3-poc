package infosys.poc.kafkatos3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehose;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseAsyncClient;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseClient;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseClientBuilder;
import com.amazonaws.services.kinesisfirehose.model.PutRecordRequest;
import com.amazonaws.services.kinesisfirehose.model.PutRecordResult;
import com.amazonaws.services.kinesisfirehose.model.Record;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.awssdk.services.firehose.FirehoseClient;
import software.amazon.awssdk.services.firehose.FirehoseClientBuilder;

import java.nio.ByteBuffer;

@SpringBootApplication
public class KafkaToS3Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaToS3Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Hello World!");

        final String accessKeyId = "AKIAWXLUINFOH5OF4G6C";
        final String secretKey = "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";
        final String deliveryStreamName = "kafka-to-s3-stream";

        //MyKafkaConsumer consumer = new MyKafkaConsumer();

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretKey);

        System.out.println("AccessKeyID: "+ credentials.getAWSAccessKeyId());
        System.out.println("SecretKey: "+credentials.getAWSSecretKey());

        AmazonKinesisFirehose firehoseClient = getFirehoseClient(credentials);

        PutRecordRequest putRecordRequest = new PutRecordRequest();
        putRecordRequest.setDeliveryStreamName(deliveryStreamName);

        String data = "Naa oru Thadava Sonnaa... Nooru thadava sonna maathiri..."+ "\n";

        Record record = new Record().withData(ByteBuffer.wrap(data.getBytes()));
        putRecordRequest.setRecord(record);

        firehoseClient.putRecord(putRecordRequest);

        System.out.println("Data Sent!");

    }

    public AmazonKinesisFirehose getFirehoseClient(BasicAWSCredentials credentials){

        return AmazonKinesisFirehoseClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }
}
