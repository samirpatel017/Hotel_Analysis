import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SpellChecker {
	private static int findDistance(String data1, String data2) {

		int[][] distanceFounder = new int[data1.length() + 1][data2.length() + 1];

		for (int i = 0; i <= data1.length(); i++) {
			distanceFounder[i][0] = i;
		}

		for (int j = 0; j <= data2.length(); j++) {
			distanceFounder[0][j] = j;
		}
		// for loop to check the all the letters
		for (int i = 0; i < data1.length(); i++) {
			char x1 = data1.charAt(i);
			for (int j = 0; j < data2.length(); j++) {
				char x2 = data2.charAt(j);

				if (x1 == x2) {
					distanceFounder[i + 1][j + 1] = distanceFounder[i][j];
				} else {
					int change = distanceFounder[i][j] + 1;
					int add = distanceFounder[i][j + 1] + 1;
					int remove = distanceFounder[i + 1][j] + 1;

					int min = change > add ? add : change;
					min = remove > min ? min : remove;
					distanceFounder[i + 1][j + 1] = min;
				}
			}
		}

		return distanceFounder[data1.length()][data2.length()];
	}


	static Set<String> GetCityDataList() throws IOException {

		// fetching document
		File cities = new File("src/files/Cities.txt");
		ArrayList<String> cityList = new ArrayList<>();
		Scanner Cityreader = new Scanner(cities);
		while (Cityreader.hasNextLine()) {
			cityList.add(Cityreader.nextLine());
		}

		// adding words in an array
//		String[] str = body.split("\\s+");
		Set<String> name = new HashSet<>();
		for (int i = 0; i < cityList.size(); i++) {
			// deleting non alphanumeric characters
			name.add(cityList.get(i).replaceAll("[^\\w]", "").toLowerCase());
		}

		// System.out.println("Length of string is: "+ str.length);

		return name;

	}

	public static String spellChecker(String word) throws IOException {

		// using array list to store the resulting list
		ArrayList<String> words = new ArrayList<String>();

		// converting to lower-case to have uniform results
		int nearestWord = 100000;
		String correct = null;
		boolean isTrue = false;
		// Parsed Array stored in Set
		Set<String> wordsArray = GetCityDataList();

		for (String i : wordsArray) {

			int near = findDistance(word, i);
			if (near <= nearestWord) {
				nearestWord = near;
			}
			// condition for being similar words is having edit distance of 2
			if (near <= 2) {
				if (near == 0) {
					isTrue = true;
					correct = i;
					break;
				}
				words.add(i);
			}
		}

		if (isTrue) {
			return correct;
		} else {
			Scanner inputObj = new Scanner(System.in);
			// Printing list of similar words
			System.out.println("Did you mean " + word + ":");
			System.out.println(words + "\nplease select the word: 1 / 2 /..");
			int selectedOptionIndex = inputObj.nextInt() - 1;

			return words.get(selectedOptionIndex);
		}

	}

}
