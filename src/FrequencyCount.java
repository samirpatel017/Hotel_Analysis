import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.*;

public class FrequencyCount {
	static void frequencycounter(File wordCountFile) throws FileNotFoundException {
		List<String> terms = new ArrayList<String>();
		Scanner fileReader = new Scanner(wordCountFile);
		while (fileReader.hasNextLine()) {
			terms.add(fileReader.nextLine());
		}
		fileReader.close();
		Map<String, Integer> termFreq = new HashMap<>();
		for (String term : terms) {
			Integer count = termFreq.get(term);
			if (count == null)
				termFreq.put(term, 1);
			else {
				termFreq.put(term, count + 1);
			}
		}
		
		for (String key : termFreq.keySet()) {
			System.out.println(key.toUpperCase() + " " + termFreq.get(key) + " Times");
		}
	}
	private static String[] fileWords;
	private static int[] frequencies;
	private static int currentSize, maximumLimit;

	
	//findingInitialPosition function will return the position for specific words given in the arguments
	
	private static int findingInitialPosition(String word) {
		int position = Math.abs(word.hashCode() % maximumLimit);
		return position;
	}

	// function will search the first empty space for insertion 
	
	private static int searchForFirstEmptyPlace(String word, int i) {
		int position = findingInitialPosition(word);
		return Math.abs((position + i * i) % maximumLimit);
	}


	public static void FrequencyInHtml(String Path) throws Exception {

		// Read the html file from the local directory
//		Scanner s = new Scanner(System.in);
//		System.out.print("Enter the file path: ");
//		String filePath = s.nextLine();
		File fileTemp = new File(Path);

		//checks file exists or not
		if (fileTemp.exists() || !Path.isEmpty()) {
			System.out.println("Directrory found");
			Path = fileTemp.getAbsolutePath();
		} else {
			System.out.println("Directrory not found");
		}

		File file = new File(Path);

		
		Document doc = Jsoup.parse(file, "UTF-8");
		String htmlText = doc.text();
		StringTokenizer st = new StringTokenizer(htmlText);
		int sizeOfHashTable = st.countTokens();

		currentSize = 0;
		maximumLimit = sizeOfHashTable;
		fileWords = new String[maximumLimit];
		frequencies = new int[maximumLimit];
		Arrays.fill(frequencies, -1);

		// Initialization of the generated tokens
		
		while (st.hasMoreTokens()) {

			// checks for characters and digits string and convert to Upper case
			String alphaNumericString = st.nextToken().replaceAll("[^a-zA-Z0-9]", "").toUpperCase();

			// inserting values in Hash Table
			int index = findingInitialPosition(alphaNumericString);
			int i = 1;

			while (fileWords[index] != null && !fileWords[index].equals(alphaNumericString)) {
				index = searchForFirstEmptyPlace(alphaNumericString, i);
				i++;
			}
			if (fileWords[index] == null) {
				fileWords[index] = alphaNumericString;
				frequencies[index] = 1;
				currentSize++;
			} else {
				frequencies[index]++;
			}
		}

		//Printing Hash Table
		
		for (int i = 0; i < fileWords.length; i++) {
			if (fileWords[i] != null) {
				System.out.println(fileWords[i] + ": " + frequencies[i]);
			}
		}
	}
	
}





	// Hash table for storing the word frequencies
	
	/*
	 *  Here fileWords are keys of the Hash Table which will store words read from the file
	 *  frequencies are values of Hash Table which will store the counts of the respective words.
	 *  currentSize is showing the current size of the Hash Table
	 *  maximumLimit is denoting the maximum limit of the Hash Table.  
	 */
	



