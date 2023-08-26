import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Page_Score{

	//get all words from html pages and input And in return give the counts
	private static int pageScore(String htmlString, String input) {
		//check if htmlString is null or empty 
		if (htmlString == null || htmlString.isEmpty()) {
			return 0;
		}
		// split all words by spacing
		String[] arrayOfWords = htmlString.split("\\s+");
		ArrayList<String> newChangedWords = new ArrayList<>();
		//pattern that will remove all unnecessary stopwords
		Pattern newStringForMatch = Pattern.compile("[a-zA-Z]+");
        for (String word : arrayOfWords) {
            Matcher patternMatcher = newStringForMatch.matcher(word);
            //check if pattern is matching or not
            if(patternMatcher.matches()) {
            	newChangedWords.add(word);
            }
        }
		int count = 0;
		//create an array of input words
		String[] inputWords = input.split("\\s+");
		//check input words in words array
		for (String s : arrayOfWords) {
			for (String k :inputWords) {
				if (s.equalsIgnoreCase(k)) {
					//check frequency of occurrence
					count++;
				}
			}
		}
		return count;
	}
	//sort pages by the number of counts
	private static Map<String, Integer> sortTopPages(Map<String, Integer> pageMap) {
		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(pageMap.entrySet());

		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> g1, Entry<String, Integer> g2) {
				return g2.getValue().compareTo(g1.getValue());
			}
		});

		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		// return sorted pages(sorted by values)
		return sortedMap;
	}

	public static void pageScore() throws FileNotFoundException {
		System.out.print("\n Keyword :");
		//take input from the user
		Scanner taken = new Scanner(System.in);
		String input = taken.nextLine();
		System.out.print(input);
		//get all the files from the html folder
		File myFiles = new File("src/files/Web-Pages/");
		File[] fileArray = myFiles.listFiles();

		try {
			//for loop over all the files 
			Map<String, Integer> totalCount = new HashMap<>();
			for (File x : fileArray) {
				//check if file is html or not
				if (x.isFile() && x.getName().endsWith(".html")) {
					int s = 0;
					//read strings from html files
					String content = new String(Files.readAllBytes(Paths.get(x.toString())));
					//Function for the count of words
					s = pageScore(content, input);

					totalCount.put(x.getName(), s);
				}
			}

			Map<String, Integer> sortedPages = sortTopPages(totalCount);
			List<Map.Entry<String, Integer>> list = new ArrayList<>(sortedPages.entrySet());
			System.out.println("Here are top pages : \n");
			for (int d = 0; d < fileArray.length; d++) {
				// print top 10 sorted pages 	
				System.out.println("("+d+") "+list.get(d).getKey() + ": " + list.get(d).getValue());
			}
		} catch (IOException e) {
			System.out.println(e);
			return;

		}
	}
}