package config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class S3Config {

	private static AmazonS3 s3Client;

	public AmazonS3 getS3Client() {

		try {
			s3Client = AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain())
					.withRegion("us-east-1").build();
			System.out.println("S3 Client Built Successfully");
		} catch (Exception exception) {
			System.out.println("Failed to Build S3 Client " + exception);

		}
		return s3Client;
	}

}
