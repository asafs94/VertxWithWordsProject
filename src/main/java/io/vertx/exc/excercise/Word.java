package io.vertx.exc.excercise;

import java.util.HashMap;

/**
 * A class that signifies a Word Object which has text and numerical value based
 * on the text.
 * 
 * @author asafs94
 */
public class Word {

	private String text;

	private int value;

	public Word(String text) {
		super();
		this.text = text;
		this.value = assignValueToText(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String string = "Word [text=" + text + ", value=" + value + "]";

		return string;
	}

	/**
	 * A Method that assigns a value based on the text given, where a= 1, b= 2 and
	 * so on...
	 * 
	 * @param text
	 * @return
	 */
	private int assignValueToText(String text) {
		HashMap<Character, Integer> lettersAndValues = new LettersAndValues().getLETTERS_TO_VALUES();

		char[] chars = text.toCharArray();
		int value = 0;
		for (char c : chars) {
			value += lettersAndValues.get(c);
		}
		return value;
	}

}
