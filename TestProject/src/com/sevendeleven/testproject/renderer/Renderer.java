package com.sevendeleven.testproject.renderer;

import java.awt.Graphics2D;
import java.util.List;

import com.sevendeleven.testproject.entities.Entity;
import com.sevendeleven.testproject.main.Main;
import com.sevendeleven.testproject.util.Vec2f;

public class Renderer {
	
	private static Camera camera;
	
	static {
		camera = new Camera();
	}
	
	public static void render(Graphics2D g, Main main) {
		Vec2f pos = main.player.getPosition();
		pos = pos.multiply(Main.getBlockSize());
//		pos.y = -pos.y;
//		pos.y -= Main.HEIGHT;
		pos = pos.subtract(new Vec2f(Main.getMain().getWidth()/2, Main.HEIGHT-(Main.getMain().getHeight()/2)));
		
		camera.setPosition(pos);
		
		//BACKGROUND RENDERING
		
		
		
		//STOP BACKGROUND RENDERING
		camera.apply(g);
		//WORLD RENDERING

		List<Entity> entities = main.world.getEntities();
		for (Entity e : entities) {
			e.render(g);
		}
		
		main.world.draw(g);
		main.player.render(g);
		
//		g.setColor(Color.black);
//		g.fillRect((int)camera.getX(), (int)camera.getY(), Main.WIDTH, Main.HEIGHT);
		
		//STOP WORLD RENDERING
		//camera.unapply(g);
		//FOREGROUND RENDERING
		
		camera.unapply(g);
		if (Main.getMain().hasGUIOn()) {
			Main.getMain().getGUI().draw(g);
		}
		
		//STOP FOREGROUND RENDERING
	}
	
	public static Camera getCamera() {
		return camera;
	}
	
}
