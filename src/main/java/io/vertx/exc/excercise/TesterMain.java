/**
 * A Test Class which has no affect on the app
 */

/*package io.vertx.exc.excercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

public class TesterMain {

	static HashMap<Character, Integer> lettersAndValues;
	static ArrayList<Word> words;

	public static void main(String[] args) {

		lettersAndValues = new HashMap<>();
		char letter = 'a';
		String nullString = null;
		for (int i = 1; i <= 26; i++) {
			lettersAndValues.put(letter, i);
			letter = Character.valueOf((char) (letter + 1));
		}

		words = new ArrayList<Word>();
		printWords();
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("Enter a word:");
			String input = sc.nextLine();
			Word word = new Word(input);
			System.out.println("Word Entered: "+ word);
			if (words.isEmpty()) {
				JsonObject obj = new JsonObject().put("value", nullString).put("lexical", nullString);
				System.out.println(Json.encodePrettily(obj));
				words.add(word);
				printWords();
			} else if (words.contains(word)) {
				JsonObject obj = new JsonObject().put("value", word.getText()).put("lexical", word.getText());
				System.out.println(Json.encodePrettily(obj));
				printWords();
			} else {
				String value = getClosestWordByValue(word);
				JsonObject obj = new JsonObject().put("value", value);
				System.out.println(Json.encodePrettily(obj));
				words.add(word);
				printWords();
			}

		}

	}

	private static void printWords() {
		System.out.println("\nWords Currently");
		for (Word word : words) {
			System.out.println(" -|- "+word+" -|- ");
		}
		System.out.println("\n--------------------");
	}


	private static String getClosestWordByValue(Word word) {
		System.out.println("Performing closest word by value check");
		System.out.println("Sorting Arraylist");
		// Sort ArrayList by Value from smallest to largest:
		words.sort((Word w1, Word w2) -> w1.getValue() - w2.getValue());
		
		
		int targetValue = word.getValue();
		int n = words.size() - 1;
		System.out.println("Last Word index: n="+n);

		// In case the value is smaller or equal to smallest:
		if (targetValue <= words.get(0).getValue()) {
			System.out.println("Value is equal to or smaller than smallest value: "+words.get(0));
			return words.get(0).getText();
					}
		// In case the value is bigger or equal to highest:
		if (targetValue >= words.get(n).getValue()) {
			System.out.println("Value is equal to or bigger than biggest value: "+words.get(n));
			return words.get(n).getText();
					}

		// Performing a search from middle value:
		int first = 0, last = n, mid = 0;

		System.out.println("Getting inside loop:");
		while (first < last) {
			System.out.println("first-"+first+" is smaller than last-"+last);
			mid = (first + last) / 2;
			System.out.println("middle is: "+ mid);
			// If the middle number is equal to the targetValue return its text:
			if (words.get(mid).getValue() == targetValue) {
				System.out.println("Word has equal value as middle word: "+ words.get(mid));
				return words.get(mid).getText();
						}
			// TODO: here - perform a check if there is another close value to mid equal to
			// target.

			// In case the target is smaller than the middle value
			if (targetValue < words.get(mid).getValue()) {
				System.out.println("Word value is smaller than middle value: "+ words.get(mid));
				// Check if the target is bigger than or equal to the previous to the middle
				// target:
				if (mid > 0 && targetValue >= words.get(mid - 1).getValue()) {
					// If its smaller than middle target and bigger than previous to middle - need
					// to perform a check which is closer:
					return getWordWithClosestValue(words.get(mid), words.get(mid - 1), targetValue);
				}

				// Repeat the whole process for the left part of the list:
				last = mid;
			} else {
				// In case targetValue is bigger than the middle value:

				// Check if the target value is smaller than the value after the middle:
				if (mid < last && targetValue < words.get(mid + 1).getValue())
					return getWordWithClosestValue(words.get(mid), words.get(mid + 1), targetValue);

				// Check right half of list:
				first = mid;
			}
		}

		return words.get(mid).getText();
	}

	private static String getWordWithClosestValue(Word word, Word word2, int targetValue) {
		System.out.println("Performing a closest value check between ---"+word.getText()+"--- and ---"+word2.getText()+"---");
		
		//  If absolute value of the difference between word1 and target is greater than
		 // the absolute value of word2 and targetValue than word2 is closer, else word1
		 // is closer:

		if (Math.abs(word.getValue() - targetValue) >= Math.abs(word2.getValue() - targetValue)) {
			return word2.getText();
		} else {
			return word.getText();
		}
	}

}


*/