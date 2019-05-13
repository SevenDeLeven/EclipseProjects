package com.sevendeleven.testproject.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sevendeleven.testproject.item.ToolItem;
import com.sevendeleven.testproject.world.World;

public class RayCaster {
	
	private static boolean[] testPoint(Map<AABB, Boolean> boxes, Vec2f point) {
		for (Entry<AABB, Boolean> box : boxes.entrySet()) {
			if (box.getKey().contains(new Vec2f(0,0), point)) {
				return new boolean[]{true, box.getValue()};
			}
		}
		return new boolean[] {false, false};
	}
	
	public static List<Vec2f> rayCast(float startX, float startY, float endX, float endY, World world) {
		List<Vec2f> res = new ArrayList<Vec2f>();
		int minX = (int)Math.floor(startX < endX ? startX : endX);
		int maxX = (int)Math.ceil(startX > endX ? startX : endX);
		int minY = (int)Math.floor(startY < endY ? startY : endY);
		int maxY = (int)Math.ceil(startY > endY ? startY : endY);
		
		HashMap<AABB, Boolean> blocks = new HashMap<AABB, Boolean>();
		for (int i = minX; i <= maxX; i++) {
			for (int j = minY; j <= maxY; j++) {
				long block = world.getBlock(i,j);
				if (block != 0) {
					blocks.put(new AABB(i, j, i+1, j+1), false);
				}
			}
		}
		
		Vec2f startPos = new Vec2f(startX, startY);
		Vec2f endPos = new Vec2f(endX, endY);
		Vec2f difference = endPos.subtract(startPos);
		float diffMag = difference.magnitude();
		
		for (float i = 0; i < diffMag; i+=0.1f) {
			Vec2f point = (new Vec2f(difference)).setMagnitude(i).add(startPos);
			boolean[] result = testPoint(blocks, point);
			if (result[0]) {
				res.add(point);
			}
		}
		
		if (testPoint(blocks, endPos)[0]) {
			res.add(endPos);
		}
		
		return res;
	}
	
	public static List<Vec2f> rayCast(float startX, float startY, float endX, float endY, ToolItem item, World world) {
		List<Vec2f> res = new ArrayList<Vec2f>();
		int minX = (int)Math.floor(startX < endX ? startX : endX);
		int maxX = (int)Math.ceil(startX > endX ? startX : endX);
		int minY = (int)Math.floor(startY < endY ? startY : endY);
		int maxY = (int)Math.ceil(startY > endY ? startY : endY);
		
		Map<AABB, Boolean> blocks = new HashMap<AABB, Boolean>();
		for (int i = minX; i <= maxX; i++) {
			for (int j = minY; j <= maxY; j++) {
				long block = world.getBlock(i,j);
				if (block != 0) {
					blocks.put(new AABB(i, j, i+1, j+1), item.getType().canDestroy(block));
				}
			}
			
		}
		
		Vec2f startPos = new Vec2f(startX, startY);
		Vec2f endPos = new Vec2f(endX, endY);
		Vec2f difference = endPos.subtract(startPos);
		float diffMag = difference.magnitude();
		
		for (float i = 0; i < diffMag; i+=0.1f) {
			Vec2f point = (new Vec2f(difference)).setMagnitude(i).add(startPos);
			boolean[] result = testPoint(blocks, point);
			if (result[0] && result[1]) {
				res.add(point);
			} else if (result[0] && !result[1]) {
				return res;
			}
		}
		
		boolean[] result = testPoint(blocks, endPos);
		if (result[0] && result[1]) {
			res.add(endPos);
		}
		
		return res;
	}
	
}
