import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class DownloadHTML {
	
	// DownloadPages
	public static void Bfrd(BufferedReader br, BufferedWriter bw) throws IOException {
		for (String first = br.readLine(); first != null; first = br.readLine()) {
			bw.write(first);
		}
	}

	// DownloadPages
	public static void FetchPages(String page,String htmlurl) throws IOException {

		URL URL = new URL(htmlurl);
		BufferedReader BufferReader = new BufferedReader(new InputStreamReader(URL.openStream()));
		BufferedWriter BufferWriter = new BufferedWriter(new FileWriter("src/files/Web-Pages/" + page + ".html"));
		Bfrd(BufferReader, BufferWriter);
		BufferReader.close();
		BufferWriter.close();

	}

}
