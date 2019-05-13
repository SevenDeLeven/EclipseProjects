package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {

	public static String read(String path) {
		File file = new File("res/" + path);
		FileReader fr;
		BufferedReader reader = null;

		try {
			fr = new FileReader(file);
			reader = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find " + path);
			System.exit(-1);
		}

		String total = "";

		while (true) {
			String line = null;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (line == null)
				break;
			total += line + "\n";
		}
		
		return total;

	}

}
