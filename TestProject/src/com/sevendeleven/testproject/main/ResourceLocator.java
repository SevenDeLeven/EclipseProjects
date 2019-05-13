package com.sevendeleven.testproject.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
//import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ResourceLocator {
	
	public static Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	
	public static BufferedImage getImage(String imgName) {
		if (!images.containsKey(imgName)) {
			System.err.println("No such image name " + imgName);
			System.exit(-1);
		}
		return images.get(imgName);
	}
	
	public static void loadImage(File file) {
		try {
			BufferedImage img = ImageIO.read(new FileInputStream(file));
			String[] fileName = file.getName().split("\\.");
			String imgName = "";
			for (int i = 0; i < fileName.length-1; i++) {
				imgName += fileName[i];
			}
			images.put(imgName, img);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Could not load image: '" + file.getAbsolutePath() + "', please re-install the game if this persists");
			System.exit(-2);
		}
	}
	
}
