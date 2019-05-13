package com.sevendeleven.testproject.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONObject;

import com.sevendeleven.testproject.item.Item;

public class ItemLoader {
	
	private ItemLoader() {}
	public static Item loadItem(File file) {
		try {
			
			FileInputStream st = new FileInputStream(file);
			String fileContents = "";
			while (st.available() > 0) {
				byte[] buffer = new byte[st.available() < 128 ? st.available() : 128];
				st.read(buffer, 0, buffer.length);
				String read = new String(buffer);
				fileContents = fileContents.concat(read);
			}
			st.close();
			
			
			JSONObject itemObj = new JSONObject(fileContents);
			Item item = new Item(itemObj);
			return item;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Could not load file " + file.getAbsolutePath() + " for item");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
