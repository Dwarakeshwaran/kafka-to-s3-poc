package service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

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
			stream = null;
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

			System.out.println("Data has been Stringified: " + s3FileData);

		} catch (IOException exception) {
			System.out.println("Failed to store S3 File Data to String " + exception);
			s3FileData = "";
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

		try {

			FileWriter fileWriter = new FileWriter(fileName);
			String[] columns = { "teamA", "teamB", "winner" };

			CustomMappingStrategy<Cricket> mappingStrategy = new CustomMappingStrategy<Cricket>();
			mappingStrategy.setType(Cricket.class);
			mappingStrategy.setColumnMapping(columns);
			mappingStrategy.generateHeader();

			StatefulBeanToCsvBuilder<Cricket> csvBuilder = new StatefulBeanToCsvBuilder<Cricket>(fileWriter);
			StatefulBeanToCsv<Cricket> csvWriter = csvBuilder.withMappingStrategy(mappingStrategy).build();
			csvWriter.write(cricketList);

			fileWriter.close();

		} catch (Exception exception) {
			System.out.println("Failed to Store CSV File in Temp Folder of Lambda " + exception);
		}

	}

	public boolean storeCsvFileInS3Bucket(String s3BucketName, String s3FileName, AmazonS3 s3Client) {

		try {

			// Get CSV File from Temp Folder Of Lambda
			String tempPath = "/tmp/cricket-results.csv";
			s3FileName = getModifiedFileName(s3FileName);

			// Store the Stream in S3 Bucket
			PutObjectRequest request = new PutObjectRequest(s3BucketName, s3FileName, new File(tempPath));
			PutObjectResult result = s3Client.putObject(request);

			System.out.println(s3FileName + " - This CSV File has been Stored in S3 Successfully " + result.toString());

			return true;

		} catch (Exception exception) {
			System.out.println("Failed to Store CSV File to S3 Buket " + exception);
			return false;
		}

	}

	private String getModifiedFileName(String s3FileName) {

		int countSlashes = (int) s3FileName.chars().filter(ch -> ch == '/').count() + 1;

		String[] directories = s3FileName.split("/", countSlashes);

		StringBuilder modifiedPathName = new StringBuilder();
		StringBuilder modifiedFileName = new StringBuilder(directories[directories.length - 1]);

		Instant instant = new Timestamp(System.currentTimeMillis()).toInstant();

		modifiedFileName.setLength(0);
		modifiedFileName.append(instant.toString());
		modifiedFileName.append("-cricket-results");
		modifiedFileName.append(".csv");

		directories[directories.length - 1] = modifiedFileName.toString();
		directories[0] = "csv";

		for (String path : directories)
			modifiedPathName.append(new String(path) + "/");

		modifiedPathName.deleteCharAt(modifiedPathName.length() - 1);

		return modifiedPathName.toString();

	}

	public void deleteOldFile(String s3BucketName, String s3FileName, AmazonS3 s3Client) {
		System.out.println("Delete Operation pending...");

	}

}

class CustomMappingStrategy<T> extends ColumnPositionMappingStrategy<T>{
    private static final String[] HEADER = new String[] {"teamA", "teamB", "winner"};
    @Override
    public String[] generateHeader() {
        return HEADER;
    }
}
