package io.vertx.exc.excercise;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.airavata.samples.LevenshteinDistanceService;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

/**
 * The main server Class
 * 
 * @author asafs
 *
 */
public class MainVerticle extends AbstractVerticle {

	/**
	 * Stores a HashMap which specifies the value of each letter in the English
	 * alphabet. a=1, b=2, c=3 and so on...
	 */
	HashMap<Character, Integer> lettersAndValues = new LettersAndValues().getLETTERS_TO_VALUES();

	/**
	 * A main ArrayList to store the words received via requests. (The assignment
	 * specified to store the words locally, in real production mode - there should
	 * be a use of a database)
	 */
	ArrayList<Word> words = new ArrayList<Word>();

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		// Creating Vertx Server
		HttpServer server = vertx.createHttpServer();
		// Creating a router
		Router router = Router.router(vertx);
		// A text that is shown if aproaching in a get request.
		router.get().handler(r -> {
			HttpServerResponse response = r.response();
			response.end("<h3>Please Make a Post Request with a Json object with a 'text' property</h3>");
		});

		// Router for handling the post requests:
		router.post().handler(routingContext -> {

			// Getting Http - request and response:
			HttpServerRequest request = routingContext.request();
			HttpServerResponse response = routingContext.response();
			response.setChunked(true);

			// Waiting after Json has been fully received:
			request.bodyHandler(bodyHandler -> {

				// Getting Json Object:
				JsonObject json = bodyHandler.toJsonObject();
				// Debug:
				System.out.println("word received: " + json);

				// Return error if received null:
				if (json == null) {
					response.setStatusCode(400).end("No Word Received");

				} else {
					// Get the word:
					String text = json.getString("text");

					// Return error if text is null:
					if (text == null) {
						response.setStatusCode(400).end("No Word Received");
					}

					// Set to lowercase:
					text = text.toLowerCase();

					boolean textValid = checkIfTextDoesntContainForbiddenChars(text);

					// If text is not valid, return error:
					if (!textValid) {
						response.setStatusCode(400).end("Word Contains Forbidden characters, only letters allowed.");
					}

					// Create a word object:
					Word word = new Word(text);

					// Check if word list is empty or not:
					if (words.isEmpty()) {
						// If empty. add the word to the list:
						words.add(word);
						// Set a new null String:
						String nullString = null;
						// Set a JsonObject and its 'lexical' and 'value' fields to null:
						JsonObject nullObject = new JsonObject();
						nullObject.put("value", nullString).put("lexical", nullString);
						// Send the null response:
						response.putHeader("Content-Type", "application/json").end(Json.encodePrettily(nullObject));

					} else
					// If its not empty,check if it contains the word:
					if (words.contains(word)) {
						// Construct a Json object with the same word:
						JsonObject sameWord = new JsonObject().put("value", text).put("lexical", text);
						// Send the response:
						response.putHeader("Content-Type", "application/json").end(Json.encodePrettily(sameWord));
					} else
					// If none of the above are true - get the closest values to the word:
					{
						String closestByValue = getClosestWordByValue(word);
						String closestByLexical = getClosestWordByLexical(word);

						JsonObject closestWords = new JsonObject().put("value", closestByValue).put("lexical",
								closestByLexical);

						response.putHeader("Content-Type", "application/json").end(Json.encodePrettily(closestWords));
						words.add(word);
					}

				}

			});
		});

		server.requestHandler(router::accept).listen(8080, http -> {
			if (http.succeeded()) {

				System.out.println("Server Started at port 8080 successfully");

				startFuture.complete();

			} else {

				System.err.println(http.cause());
				startFuture.failed();

			}
		});

	}

	/**
	 * A Method which makes sure the characters in the String are only a-z.
	 * 
	 * @param text
	 * @return
	 */
	private boolean checkIfTextDoesntContainForbiddenChars(String text) {
		char[] chars = text.toCharArray();

		for (char c : chars) {
			if (!Character.isLetter(c)) {
				return false;
			}
		}

		return true;

	}

	/**
	 * Finds the word lexically closest to this word in the 'words' ArrayList and
	 * returns its text.
	 * 
	 * @param word
	 * @return
	 */

	/*
	 * Using apache's LevenshtineDistanceService to determine and calculate lexical closeness
	 */
	private String getClosestWordByLexical(Word word) {

		LevenshteinDistanceService distanceService = new LevenshteinDistanceService();

		int minimalDistance = Integer.MAX_VALUE;
		Word closestWord = null;

		for (Word checkedWord : words) {
			int currentDistance = distanceService.computeDistance(word.getText(), checkedWord.getText());
			if (currentDistance < minimalDistance) {
				minimalDistance = currentDistance;
				closestWord = checkedWord;
			}
		}

		return closestWord.getText();
	}

	/**
	 * Finds the closest Word by value to the word given in the 'words' ArrayList.
	 * 
	 * @param word
	 * @return
	 */
	private String getClosestWordByValue(Word word) {

		// Sort ArrayList by Value from smallest to largest:
		words.sort((Word w1, Word w2) -> w1.getValue() - w2.getValue());

		int targetValue = word.getValue();
		int n = words.size() - 1;

		// In case the value is smaller or equal to smallest:
		if (targetValue <= words.get(0).getValue())
			return words.get(0).getText();
		// In case the value is bigger or equal to highest:
		if (targetValue >= words.get(n).getValue())
			return words.get(n).getText();

		// Performing a search from middle value:
		int first = 0, last = n, mid = 0;

		while (first < last) {
			mid = (first + last) / 2;

			// If the middle number is equal to the targetValue return its text:
			if (words.get(mid).getValue() == targetValue)
				return words.get(mid).getText();
			// TODO: here - perform a check if there is another close value to mid equal to
			// target.

			// In case the target is smaller than the middle value
			if (targetValue < words.get(mid).getValue()) {

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

		System.out.println("Closest word Value found for " + word + " is " + words.get(mid));
		return words.get(mid).getText();
	}

	/**
	 * Find a word with the closest value of two values to the targetValue, and
	 * returns the words text.
	 * 
	 * @param word
	 * @param word2
	 * @param targetValue
	 * @return
	 */
	private String getWordWithClosestValue(Word word, Word word2, int targetValue) {

		/*
		 * If absolute value of the difference between word1 and target is greater than
		 * the absolute value of word2 and targetValue than word2 is closer, else word1
		 * is closer:
		 */
		if (Math.abs(word.getValue() - targetValue) > Math.abs(word2.getValue() - targetValue)) {
			return word2.getText();
		} else {
			return word.getText();
		}
	}

}
