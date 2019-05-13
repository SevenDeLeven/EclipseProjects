package main;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import graphics.DisplayManager;

public class Main {

	public final static int WIDTH = 1080;
	public final static int HEIGHT = 720;

	private static long window = 0;
	private static int program = 0;
	private static int vaoID = 0;
	
	public static void main(String args[]) {
		
		DisplayManager.init();
		window = DisplayManager.getWindow();
		program = DisplayManager.getProgram();
		vaoID = DisplayManager.getVaoID();
		
		while (!glfwWindowShouldClose(window)) {
			final float color[] = {0.0f, 0.0f, 0.0f, 1.0f};
			GL30.glClearBufferfv(GL11.GL_COLOR, 0, color);
			
			
			GL20.glUseProgram(program);
			
			GL30.glBindVertexArray(vaoID);
			GL20.glDrawArrays(GL20.GL_TRIANGLES, 0, 3);
			GL30.glBindVertexArray(0);
			
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}

}
