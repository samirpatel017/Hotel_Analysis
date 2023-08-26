import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import net.bytebuddy.agent.builder.AgentBuilder.CircularityLock.Default;

public class RankHotel {
	public static List<Hotel> ReadFiles(String filename) throws IOException {
		List<Hotel> hotels = new ArrayList<>();
		try {
			Elements hotelNames = null, prices = null, ratings = null;
			if (filename.contains("booking")) {
				File input = new File("src/files/Web-Pages/" + filename);
				Document document = Jsoup.parse(input, "UTF-8");

				hotelNames = document.select("[data-testid=title]");
				prices = document.select("[data-testid=price-and-discounted-price]");
				// \
				ratings = document.select(".d10a6220b4");

			} else if (filename.contains("expedia") || filename.contains("hotwire")) {
				File input = new File("src/files/Web-Pages/" + filename);
				Document document = Jsoup.parse(input, "UTF-8");
				hotelNames = document.select(".uitk-heading.uitk-heading-5.overflow-wrap");

				prices = document.select(".uitk-text.uitk-type-600.uitk-type-bold.uitk-text-emphasis-theme");
				// \
				ratings = document.select(".uitk-text.uitk-type-300.uitk-type-bold.uitk-text-default-theme");

				
			
			}
			int minSize = Math.min(hotelNames.size(), Math.min(prices.size(), ratings.size()));
			System.out.println("Minimum size: " + minSize);
			

			for (int i = 0; i < minSize - 1; i++) {
				String hotelName = hotelNames.get(i).text();
				double price = Double.parseDouble(prices.get(i).text().replaceAll("[^\\d.]", ""));
				double rating = 0;// Remove any
				if(i>1) {
					rating = Double.parseDouble(ratings.get(i).text());
				}																			// non-numeric
																									// characters

				hotels.add(new Hotel(hotelName, rating, price));
			}

//			hotels.sort(Comparator.comparing(Hotel::getRating).reversed().thenComparing(Hotel::getPrice));
//
//			for (Hotel hotel : hotels) {
//				System.out.println("Hotel Name: " + hotel.getName());
//				System.out.println("Rating: " + hotel.getRating());
//				System.out.println("Price: " + hotel.getPrice());
//				System.out.println("--------------------");
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hotels;

	}
	public static void All_Hotel_Compare() throws IOException {
		
		List<Hotel> allHotels = new ArrayList<>();

        File myFiles = new File("src/files/Web-Pages/");
        File[] fileArray = myFiles.listFiles();
        if (fileArray != null) {
            for (File file : fileArray) {
                if (file.isFile()) {
                    List<Hotel> hotels = ReadFiles(file.getName());
                    allHotels.addAll(hotels);
                }
            }
        }

        allHotels.sort(Comparator.comparing(Hotel::getRating).reversed().thenComparing(Hotel::getPrice));

        for (Hotel hotel : allHotels) {
            System.out.println("Hotel Name: " + hotel.getName());
            System.out.println("Rating: " + hotel.getRating());
            System.out.println("Price: " + hotel.getPrice());
            System.out.println("--------------------");
        }
    
	}

//	public static void main(String[] args) throws Exception {
//		ReadFiles("www.expedia.com.html");
//	}
}

class Hotel {
	private String name;
	private double rating;
	private double price;

	public Hotel(String name, double rating, double price) {
		this.name = name;
		this.rating = rating;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public double getRating() {
		return rating;
	}

	public double getPrice() {
		return price;
	}
}
