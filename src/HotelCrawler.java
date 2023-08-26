import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class HotelCrawler {

	public static void main(String[] args) throws Exception {
		File wordCountFile = new File("src/files/Word_Count.txt");
		FrequencyCount.frequencycounter(wordCountFile);
		try (Scanner inputObj = new Scanner(System.in)) {
			System.out.println("Enter the location");
			String location = inputObj.nextLine();
			while (!location.matches("[a-zA-Z_]+")) {
				System.out.println("Invalid source");
				System.out.println("Enter the location");
				location = inputObj.nextLine();
			}
			if (location != null) {
				location = location.toLowerCase();
				location = SpellChecker.spellChecker(location);
				try (BufferedWriter BufferWriter = new BufferedWriter(
						new FileWriter("src/files/Word_Count.txt", true))) {
					BufferWriter.write(location);
					BufferWriter.newLine(); // If you want to add a new line after the content
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("-----------");
			System.out.println("Enter Check in date : yyyy-mm-dd");
			String checkInDate = inputObj.nextLine();

			System.out.println("-----------");
			System.out.println("Enter Check out date : yyyy-mm-dd");
			String checkOutDate = inputObj.nextLine();

			System.out.println("-----------");
			System.out.println("How many adults are going to stay?");
			String numAdults = inputObj.nextLine();

			System.out.println("-----------");
			System.out.println("How many rooms do you want to book? : ");
			String numRooms = inputObj.nextLine();

			System.out.println("-----------");
			System.out.println("How many children are going to stay?");
			String numChildren = inputObj.nextLine();

			System.out.println("-----------");
			System.out.println("Enter any purpose of your trip from following : business  or  leisure");
			String purpose = inputObj.nextLine();
			String[] url=new String[3];
			url[0] = "https://www.booking.com/searchresults.en-gb.html?ss="+location+"&ssne="+location+"&ssne_untouched="+location+"&checkin="+checkInDate+
					"&checkout="+ checkOutDate+"&group_adults="+ numAdults +"&no_rooms="+ numRooms +"&group_children=" + numChildren+"&sb_travel_purpose=leisure";;
			url[1] = "https://www.expedia.com/Hotel-Search?destination=" + location + "&startDate=" + checkInDate
					+ "&endDate=" + checkOutDate + "&rooms=" + numRooms + "&adults=" + numAdults + "&children="
					+ numChildren;
			url[2] = "https://vacation.hotwire.com/Hotel-Search?adults="+numAdults+"children="+numChildren+"&destination="+location+"&endDate="+checkOutDate+"&rooms="+numRooms+"&semdtl=&sort=RECOMMENDED&startDate="+checkInDate+"&theme=&useRewards=false&userIntent=";
			
			for (int i = 0; i <=2 ; i++) {
				
			
				String[] splitUrl = url[i].split("/");
	
				String file_Name = splitUrl[2];
				String fname = file_Name + ".html";
				String Fullpath = "src/files/Web-Pages/";
				System.out.println(url[i]);
				DownloadHTML.FetchPages(file_Name, url[i]);
				RankHotel.ReadFiles(fname);
//				FrequencyCount.FrequencyInHtml(Fullpath + fname);
//				Page_Score.pageScore();
		}
			RankHotel.All_Hotel_Compare();
		}
	}

}
