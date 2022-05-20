package service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Cricket;

public class TransformKafkaDataService {

	private Cricket cricket;
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public InputStream getStreamFromKey(String s3BucketName, String s3FileName, AmazonS3 s3Client) {

		InputStream stream = null;

		try {
			S3Object s3Object = s3Client.getObject(new GetObjectRequest(s3BucketName, s3FileName));
			stream = s3Object.getObjectContent();
			System.out.println("Successfully retrieved the stream from the file: " + s3BucketName + s3FileName);
		} catch (Exception exception) {
			System.out.println("Failed to get Stream from S3 Object!!! " + exception);

		}

		return stream;
	}

	public String stringifyStreamData(InputStream stream) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int lineReader;
		byte[] data = new byte[1024];
		String s3FileData = null;
		try {
			while ((lineReader = stream.read(data, 0, data.length)) != -1)
				outputStream.write(data, 0, lineReader);

			outputStream.flush();
			byte[] byteArray = outputStream.toByteArray();

			s3FileData = new String(byteArray, StandardCharsets.UTF_8);

		} catch (IOException exception) {
			System.out.println("Failed to store S3 File Data to String " + exception);
		}

		return s3FileData;
	}

	public List<StringBuilder> getStringListOfMatches(String s3FileData) {
		List<StringBuilder> matchList = new ArrayList<StringBuilder>();

		int openBracketCounter = 0;
		int closeBracketCounter = 0;
		StringBuilder matches = new StringBuilder(s3FileData);
		StringBuilder match = null;

		try {
			for (int i = 0; i < matches.length(); i++) {

				if (openBracketCounter == closeBracketCounter) {

					match = new StringBuilder();
					matchList.add(match);
					match.setLength(0);

				}

				if (matches.charAt(i) == '{')
					openBracketCounter++;

				if (matches.charAt(i) == '}')
					closeBracketCounter++;

				match.append(matches.charAt(i));

			}
		} catch (Exception exception) {
			System.out.println("Failed to get the list of Matches in String Builder Format... " + exception);

		}

		return matchList;
	}

	public List<Cricket> getCricketList(List<StringBuilder> matchListInString) {

		List<Cricket> cricketList = new ArrayList<Cricket>();
		try {
			for (StringBuilder match : matchListInString) {
				cricket = gson.fromJson(match.toString(), Cricket.class);
				cricketList.add(cricket);
			}

		} catch (Exception exception) {
			System.out.println("Failed to get the Cricket List Object... " + exception);
		}

		return cricketList;
	}

	public void storeCsvFileInTempFolderOfLambda(String fileName, List<Cricket> cricketList) {
		// TODO Auto-generated method stub
		
	}

	public boolean storeCsvFileInS3Bucket(String s3BucketName, String s3FileName, AmazonS3 s3Client) {
		// TODO Auto-generated method stub
		return false;
	}

	public void deleteOldFile(String s3BucketName, String s3FileName, AmazonS3 s3Client) {
		// TODO Auto-generated method stub
		
	}

}
