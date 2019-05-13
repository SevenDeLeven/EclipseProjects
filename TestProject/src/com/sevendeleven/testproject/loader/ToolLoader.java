package com.sevendeleven.testproject.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONObject;

import com.sevendeleven.testproject.item.ToolItem;

public class ToolLoader {
	
	private ToolLoader() {}
	
	public static ToolItem loadTool(File tool) {
		try {
			
			FileInputStream st = new FileInputStream(tool);
			String fileContents = "";
			while (st.available() > 0) {
				byte[] buffer = new byte[st.available() < 128 ? st.available() : 128];
				st.read(buffer, 0, buffer.length);
				String read = new String(buffer);
				fileContents = fileContents.concat(read);
			}
			st.close();
			
			
			JSONObject itemObj = new JSONObject(fileContents);
			ToolItem item = new ToolItem(itemObj);
			return item;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Could not load file " + tool.getAbsolutePath() + " for item");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
