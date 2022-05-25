package rough;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		String data = "{\"teamA\":\"IND\",\"teamB\":\"RSA\",\"winner\":\"IND\"}{\"teamA\":\"IND\",\"teamB\":\"SL\",\"winner\":\"SL\"}{\"teamA\":\"AUS\",\"teamB\":\"SL\",\"winner\":\"AUS\"}";
		String data2 = "{\"ordertime\": 1497014222380,\"orderid\": 18,\"itemid\": \"Item_184\",\"address\": {\"city\": \"Mountain View\",\"state\": \"CA\",\"zipcode\": 94041}}{\"ordertime\": 1497014222380,\"orderid\": 18,\"itemid\": \"Item_184\",\"address\": {\"city\": \"Mountain View\",\"state\": \"CA\",\"zipcode\": 94041}}";

		for (StringBuilder m : getStringListOfMatches(data2))
			System.out.println("Match: " + m);

		String fileName = "kafka-to-firehose-data/backup/2022/05/20/18/kafka-to-s3-stream-9-2022-05-20-18-30-32-f3df907f-103d-481f-bb2a-18daf3a4dd74";

		fileName = getModifiedFileName(fileName);

		System.out.println("Modified FileName: " + fileName);
	}

	private static String getModifiedFileName(String s3FileName) {

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
		directories[1] = "csv";
		for (String path : directories) {
			modifiedPathName.append(new String(path) + "/");
			System.out.println(path);
		}
			

		modifiedPathName.deleteCharAt(modifiedPathName.length()-1);
		
		return modifiedPathName.toString();
	}

	public static List<StringBuilder> getStringListOfMatches(String s3FileData) {
		List<StringBuilder> matchList = new ArrayList<StringBuilder>();

		int openBracketCounter = 0;
		int closeBracketCounter = 0;
		StringBuilder matches = new StringBuilder(s3FileData);
		StringBuilder match = null;

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

		return matchList;
	}

}
