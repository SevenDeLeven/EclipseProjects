package com.sevendeleven.testproject.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONObject;

import com.sevendeleven.testproject.world.Block;

public class BlockLoader {
	
	private BlockLoader() {}
	public static Block loadBlock(File file) {
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
			
			
			JSONObject blockObj = new JSONObject(fileContents);
			Block block = new Block(blockObj);
			return block;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Could not load file " + file.getAbsolutePath() + " for block");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
