package com.sevendeleven.eventfulexplosion.main;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceLocator {

	public static BufferedImage getImage(String location) {
		BufferedImage ret = null;
		
		try {
			ret = ImageIO.read(Main.class.getResourceAsStream("../../../../" + location));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return ret;
	}
	
}
