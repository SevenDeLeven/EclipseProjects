package com.sevendeleven.terrilla.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.sevendeleven.terrilla.entity.Entity;

public class ConcurrentHandler {
	
	private Queue<SpriteData> renderUpdates = new ConcurrentLinkedQueue<SpriteData>();
	
	public ConcurrentHandler() {
		
	}
	
	public void updateRenderer(long milliTime, long deltaTick, Entity ent) {
		renderUpdates.add(new SpriteData(milliTime, deltaTick, ent));
	}
	
	public Queue<SpriteData> getRenderUpdates() {
		return renderUpdates;
	}
	
}
