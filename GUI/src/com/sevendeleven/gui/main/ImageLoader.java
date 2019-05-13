package com.sevendeleven.gui.main;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	public static BufferedImage getImage(String path) {
		try {
			return ImageIO.read(ImageLoader.class.getResourceAsStream(path));
		} catch (Exception e) {
			System.err.println("There was a problem loading the image");
			System.exit(-1);
			return null;
		}
	}
	
}
