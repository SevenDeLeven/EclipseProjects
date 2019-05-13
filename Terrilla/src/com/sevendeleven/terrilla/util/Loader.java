package com.sevendeleven.terrilla.util;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sevendeleven.terrilla.main.Main;
import com.sevendeleven.terrilla.shaders.ButtonShader;
import com.sevendeleven.terrilla.shaders.EntityShader;
import com.sevendeleven.terrilla.shaders.ImageShader;
import com.sevendeleven.terrilla.shaders.ProgressBarShader;
import com.sevendeleven.terrilla.shaders.ShaderProgram;
import com.sevendeleven.terrilla.shaders.TextShader;

public class Loader {
	
	public static List<Integer> vaos = new ArrayList<Integer>();
	public static List<Integer> vbos = new ArrayList<Integer>();
	
	public static List<ShaderProgram> shaders = new ArrayList<ShaderProgram>();
	
	//Game Shaders
	public static EntityShader entityShader;
	
	//GUI Shaders
	public static ButtonShader buttonShader;
	public static TextShader textShader;
	public static ImageShader imageShader;
	public static ProgressBarShader progressBarShader;
	
	public static void loadShaders() {
		System.out.println("loading shaders");
		entityShader = new EntityShader();
		buttonShader = new ButtonShader();
		textShader = new TextShader();
		imageShader = new ImageShader();
		progressBarShader = new ProgressBarShader();
		System.out.println("loaded shaders");
	}
	
	public static void unload() {
		deleteArraysAndBuffers();
		deleteShaders();
		deleteTextures();
	}
	
	public static void deleteTextures() {
		ResourcesManager.offloadTextures();
	}
	
	public static void deleteShaders() {
		for (ShaderProgram s : shaders) {
			s.cleanUp();
		}
	}
	
	public static void deleteArraysAndBuffers() {
		for (int i = 0; i < vaos.size(); i++) {
			glDeleteVertexArrays(vaos.get(i));
		}
		for (int i = 0; i < vbos.size(); i++) {
			glDeleteBuffers(vbos.get(i));
		}
	}
	
	public static int genVAO() {
		int vao = glGenVertexArrays();
		glBindVertexArray(vao);
		vaos.add(vao);
		return vao;
	}
	
	public static List<String> readFile(String path) {
		InputStream fis = Main.class.getResourceAsStream(path);
		if (fis == null) {
			System.err.println("Could not read from file " + path);
			return null;
		}
		Scanner sc = new Scanner(fis);
		List<String> ret = new ArrayList<String>();
		while (sc.hasNextLine()) {
			ret.add(sc.nextLine());
		}
		sc.close();
		return ret;
	}
	
}