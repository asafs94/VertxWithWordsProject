package io.vertx.exc.excercise;

import java.util.HashMap;

/**
 * This is a class that holds a HashMap lettersToValues - which maps out the
 * value of each letter.<br> i.e. a = 1, b = 2...
 * 
 * @author asafs94
 *
 */
public class LettersAndValues {

	private HashMap<Character, Integer> lettersToValues;

	public LettersAndValues() {
		lettersToValues = new HashMap<>();
		setInitialHashMapValues();
	}
	
	/**
	 * A Method that sets the value for each char - a=1, b=2...
	 */
	private void setInitialHashMapValues() {
		// Setting The Letter Values:
		// Set First Letter - 'a'
		char letter = 'a';
		// Iterate over Alphabet
		for (int i = 1; i <= 26; i++) {
			// Each letter has a value starting with a=1, and incrementing by 1.
			lettersToValues.put(letter, i);
			letter = Character.valueOf((char) (letter + 1));
		}
	}
	
	public HashMap<Character, Integer> getLETTERS_TO_VALUES() {
		return lettersToValues;
	}

}
