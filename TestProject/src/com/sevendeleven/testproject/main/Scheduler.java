package com.sevendeleven.testproject.main;

import java.util.ArrayList;
import java.util.List;

import com.sevendeleven.testproject.entities.Entity;
import com.sevendeleven.testproject.world.block.BlockDestroyer;

public class Scheduler {
	
	List<Entity> entitiesToAdd = new ArrayList<Entity>();
	List<Entity> entitiesToRemove = new ArrayList<Entity>();
	List<BlockDestroyer> blockDestroyersToRemove = new ArrayList<BlockDestroyer>();
	
	private static Scheduler instance = new Scheduler();
	
	private Scheduler() {
		
	}
	
	public static Scheduler getScheduler() {
		return instance;
	}
	
	public void removeEntity(Entity e) {
		entitiesToRemove.add(e);
		e.setRemoved(true);
	}
	
	public void addEntityToWorld(Entity e) {
		entitiesToAdd.add(e);
	}
	
	public void removeDestruction(BlockDestroyer bd) {
		blockDestroyersToRemove.add(bd);
	}
	
	public void enactDestruction() {
		for (BlockDestroyer bd : blockDestroyersToRemove) {
			bd.getPosition().getWorld().removeDestruction(bd);
		}
	}
	
	public void enactEntities() {
		for (Entity e : entitiesToAdd) {
			e.getWorld().addEntity(e);
		}
		for (Entity e : entitiesToRemove) {
			e.getWorld().removeEntity(e);
		}
		entitiesToAdd.clear();
	}
	
}
