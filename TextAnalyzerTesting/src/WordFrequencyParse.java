import java.io.*;
import java.net.URL;
import java.util.*;

import org.jsoup.Jsoup;

public class WordFrequencyParse {

	public LinkedHashMap<String, Integer> finalList = new LinkedHashMap<>();

	public static String extractText(String file) throws IOException {

		// Using jsoup to parse html from the url and BufferedReader to read text from an input stream
		URL reader = new URL(file);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(reader.openStream()));
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String text = Jsoup.parse(sb.toString()).text();
		return text;
	}

	public static void wordFrequency(String str, Map<String, Integer> words) throws FileNotFoundException {

		// Method to get the words of the string and puts them in a collection and adds 1 for each word
		String arr[] = str.replaceAll("[^a-zA-Z '-]", "").toUpperCase().split("\\s+");
		for (String s : arr) {
			int count = 0;
			if (words.containsKey(s)) {
				count = words.get(s);
			}
			words.put(s, count + 1);
		}
	}

	// Method to sort the words from highest amount to lowest amount and adds them to a new collection
	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> words) {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(words.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		HashMap<String, Integer> newList = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			newList.put(aa.getKey(), aa.getValue());
		}
		return newList;
	}

	// Main method that specifies the URL for the text analyzer and prints the results to a txt file and the console
	// uses LinkedHashMap that is filled with words with highest values first
	public WordFrequencyParse(String url) throws IOException {
		HashMap<String, Integer> words = new HashMap<String, Integer>();
		// URL given with the assignment
		//String document = "http://shakespeare.mit.edu/macbeth/full.html";
		String document = url;

		String play = WordFrequencyParse.extractText(document);

		wordFrequency(play, words);

		Map<String, Integer> sortedList = sortByValue(words);

		LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
		sortedList.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

		finalList = reverseSortedMap;

		// Print to text document
		int count = 1;
		PrintStream o = new PrintStream(new File("MacbethWordCount.txt"));
		PrintStream console = System.out;
		System.setOut(o);
		for (Map.Entry<String, Integer> en : reverseSortedMap.entrySet()) {
			System.out.println(count + ". (" + en.getKey() + ", " + en.getValue() + ")");
			count++;
		}

		// Print to console
		count = 1;
		System.setOut(console);
		for (Map.Entry<String, Integer> en : reverseSortedMap.entrySet()) {
			System.out.println(count + ". (" + en.getKey() + ", " + en.getValue() + ")");
			count++;
		}
	}

	/**
	 * This method is used to send the word list to the WordFrequencyTest class
	 *
	 * @return sorted list with the frequency of words of the inputed file.
	 */
	public LinkedHashMap<String, Integer> getWordList() {
		return finalList;
	}

}