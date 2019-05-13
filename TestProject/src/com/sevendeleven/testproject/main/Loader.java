package com.sevendeleven.testproject.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sevendeleven.testproject.loader.BlockLoader;
import com.sevendeleven.testproject.loader.ItemLoader;
import com.sevendeleven.testproject.loader.ToolLoader;

public class Loader {
	public static final File directory = new File(System.getProperty("user.dir"));
	
	private static List<File> imageFiles;
	private static List<File> blockFiles;
	private static List<File> itemFiles;
	private static List<File> toolFiles;
	
	public static void loadFiles() {
		imageFiles = new ArrayList<File>();
		blockFiles = new ArrayList<File>();
		itemFiles = new ArrayList<File>();
		toolFiles = new ArrayList<File>();
		File assetsFile = new File(directory.getAbsolutePath() + "\\assets");
		loadDirectory(assetsFile);
		for (File img : imageFiles) {
			loadImage(img);
		}
		for (File block : blockFiles) {
			loadBlock(block);
		}
		for (File item : itemFiles) {
			loadItem(item);
		}
		for (File tool : toolFiles) {
			loadTool(tool);
		}
	}
	
	public static void loadDirectory(File directory) {
		String origin = directory.getAbsolutePath() + "\\";
		for (String s : directory.list()) {
			File file = new File(origin + s);
			if (file.isDirectory()) {
				loadDirectory(file);
			} else {
				loadFile(file);
			}
		}
	}
	
	public static void loadFile(File file) {
		String[] ext = file.getName().split("\\.");
		String extension;
		if (ext.length > 1) {
			extension = ext[ext.length-1];
		} else {
			extension = file.getName();
		}
		switch (extension) {
		case "block":
			blockFiles.add(file);
			break;
		case "item":
			itemFiles.add(file);
			break;
		case "tool":
			toolFiles.add(file);
			break;
		case "png":
			imageFiles.add(file);
			break;
		default:
			break;
		}
	}
	
	public static void loadBlock(File file) {
		Register.registerBlock(BlockLoader.loadBlock(file));
	}
	
	public static void loadItem(File file) {
		Register.registerItem(ItemLoader.loadItem(file));
	}
	
	public static void loadTool(File file) {
		Register.registerTool(ToolLoader.loadTool(file));
	}
	
	public static void loadImage(File file) {
		ResourceLocator.loadImage(file);
	}
	
}
