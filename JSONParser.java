/*******************************************************************************
 * Robert Eng
 * Taboola Coding Challenge
 * Question 1: JSONParser.java
 * 
 * This is a JSON parser which accepts valid JSON and dumps it into an
 * appropriate data structure.
 *
 * To run, go to terminal and run commands
 * javac JSONParser.java
 * java JSONParser
 * If any of the asserts trigger, then something went wrong.
 * 
 * ASSUMPTIONS MADE IN THE INTEREST OF TIME:
 * - Just to make it easier on myself, I'm assuming the strings in JSON do not
 *   include escaped quotes. It's one extra line to check for the '\'.
 * - Numbers are whitespace padded or more specifically there is whitespace
 *   after a number. I did this because I did not want to check for all the
 *   different possible cases. Without whitespace, there would be commas and
 *   such.
 * - All JSON is valid
 *
 * TODO: UNIMPLEMENTED ITEMS
 * - Error checking. Catching exceptions. etc.
 * - The big unimplemented item is the lack of array support. Only objects are
 *   supported currently.
 * - Making sure in the default case of value there is an integer rather than
 *   assuming an integer.
 * - The compiler continually complains to me about an unchecked cast.
 ******************************************************************************/

import java.util.Map;
import java.util.HashMap;

public class JSONParser {
	/* This parse function will be recursive, converting the inputted JSON to
	 * Java Map Objects.
	 *
	 * preconditions: json begins with '{' and ends with '}'
	 * 				  json is valid JSON
	 */
	public static Object parse(String json) {
		// Create the new Map
		Map<String, Object> map = new HashMap<String, Object>();

		// look for ':' as an indication of a key value pair
		int index = json.indexOf(':', 0);
		while(index != -1) {
			// Find the key before the colon at index
			int rKeyInd = json.lastIndexOf('"', index);
			int lKeyInd = json.lastIndexOf('"', rKeyInd - 1);
			// System.out.println(json.substring(lKeyInd + 1, rKeyInd));

			// Figure out what the value assocaited with the key is.
			// Can be a string, number, object, array, true, false, or null
			// Need to find the start of value. Must get rid of all whitespace
			int lValInd = index + 1;
			while(Character.isWhitespace(json.charAt(lValInd))) {
				lValInd++;
			}

			// Respond to each of the cases
			char valchar = json.charAt(lValInd);
			int rValInd;
			switch(valchar) {
				case 't': // true
					if(!json.startsWith("true", lValInd)) {
						System.out.println("Something wrong when parsing true");
					}
					map.put(json.substring(lKeyInd + 1, rKeyInd),
						new Boolean(true));
					break;
				case 'f': // false
					map.put(json.substring(lKeyInd + 1, rKeyInd),
						new Boolean(false));
					break;
				case 'n': // null
					map.put(json.substring(lKeyInd + 1, rKeyInd), null);
					break;
				case '"': // string
					rValInd = json.indexOf('"', lValInd + 1);
					map.put(json.substring(lKeyInd + 1, rKeyInd), 
						json.substring(lValInd + 1, rValInd));
					break;
				case '[': // array
						// TODO: implement array parsing
					break;
				case '{': // object
					// Find the '}' to this '{'.
					int count = 1, curlyInd = lValInd;
					while(count > 0) {
						int nextOpen = json.indexOf('{', curlyInd + 1);
						int nextClose = json.indexOf('}', curlyInd + 1);
						if(nextOpen < nextClose && nextOpen != -1) {
							count++;
							curlyInd = nextOpen;
						} else if(nextClose != -1) {
							count--;
							curlyInd = nextClose;
						} else {
							System.out.println("Error parsing curly braces.");
							System.out.println("open = " + nextOpen + " close = " + nextClose);
							return null;
						}

					}

					// curlyInd should have '}' so we run parse up to it
					map.put(json.substring(lKeyInd + 1, rKeyInd),
						JSONParser.parse(
							json.substring(lValInd, curlyInd + 1)));
					break;
				default:
					// To save time and effort, I will make an assumption there
					// is whitespace after the number. I do not have the time to
					// implement a full number parser either.
					// Look for the whitespace which indicates the end of num
					rValInd = lValInd;
					while(!Character.isWhitespace(json.charAt(rValInd))) {
						rValInd++;
					}

					map.put(json.substring(lKeyInd + 1, rKeyInd),
						Integer.parseInt(json.substring(lValInd, rValInd)));

					// TODO: error checking if no int present
			}

			// move index past the current key value pair we just looked at
			index = json.indexOf(':', index + 1);
		}
		// System.out.println(json.indexOf('{', 3));
		// System.out.println(json.indexOf('\"', 3));
		// System.out.println(json);
		return null;
	}

	// public static Object valueFinder(String json) {

	// }


	public static void main(String [] args) {
		String input = "{ \"debug\": \"on\", \"window\": { \"title\": \"sample\", \"size\": 500 } }";
		Map<String, Object> output = (Map<String, Object>)JSONParser.parse(input);


		assert output.get("debug").equals("on");
		assert ((Map<String, Object>)(output.get("window"))).get("title").equals("sample");
		assert ((Map<String, Object>)(output.get("window"))).get("size").equals(500);

	}
}