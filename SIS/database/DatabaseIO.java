package database;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabaseIO {

	public static final String SEPARATOR = "|";

	/** Read the contents of the given file. */
	public static ArrayList<String> read(String filename) throws IOException {
		Scanner sc = new Scanner(new FileInputStream(filename));
		ArrayList<String> data = new ArrayList<String>();
		try {
			while (sc.hasNextLine()) {
				data.add(sc.nextLine());
			}
		} finally {
			sc.close();
		}
		return data;
	}

	/** Write the contents of the given file. */
	public static void write(String fileName, List<String> data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(fileName));

		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}
	
	

}
