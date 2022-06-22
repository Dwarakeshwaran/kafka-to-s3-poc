package dwaki.firehoseconnector.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehose;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseClientBuilder;

public class FirehoseConnectorConfig {

	public AmazonKinesisFirehose getClient() {

		ClientConfiguration config = new ClientConfiguration();
		config.setConnectionTimeout(5000);
		config.setMaxErrorRetry(3);
		config.setClientExecutionTimeout(5000);

		AmazonKinesisFirehose clientBuilder = AmazonKinesisFirehoseClientBuilder.standard()
				.withClientConfiguration(config).withCredentials(new DefaultAWSCredentialsProviderChain())
				.withRegion("us-east-1").build();

		return clientBuilder;

	}

}
