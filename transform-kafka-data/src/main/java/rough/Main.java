package rough;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		String data = "{\"teamA\":\"IND\",\"teamB\":\"RSA\",\"winner\":\"IND\"}{\"teamA\":\"IND\",\"teamB\":\"SL\",\"winner\":\"SL\"}{\"teamA\":\"AUS\",\"teamB\":\"SL\",\"winner\":\"AUS\"}";
		String data2 = "{\"ordertime\": 1497014222380,\"orderid\": 18,\"itemid\": \"Item_184\",\"address\": {\"city\": \"Mountain View\",\"state\": \"CA\",\"zipcode\": 94041}}{\"ordertime\": 1497014222380,\"orderid\": 18,\"itemid\": \"Item_184\",\"address\": {\"city\": \"Mountain View\",\"state\": \"CA\",\"zipcode\": 94041}}";
		
		for (StringBuilder m : getStringListOfMatches(data2))
			System.out.println("Match: " + m);

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
