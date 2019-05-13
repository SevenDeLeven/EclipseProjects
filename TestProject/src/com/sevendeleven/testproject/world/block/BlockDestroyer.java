package com.sevendeleven.testproject.world.block;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.sevendeleven.testproject.entities.Entity;
import com.sevendeleven.testproject.item.ToolItem;
import com.sevendeleven.testproject.main.Main;
import com.sevendeleven.testproject.main.Register;
import com.sevendeleven.testproject.main.ResourceLocator;
import com.sevendeleven.testproject.world.BlockPos;

public class BlockDestroyer {
	
	private BlockPos pos;
	private float progress;
	private float hardness;
	private boolean active = false;
	private float efficiency = 0;
	List<Entity> destroyers = new ArrayList<Entity>();
	
	private Entity destroyer = null;
	
	public static BufferedImage img_blockDestroy_1;
	public static BufferedImage img_blockDestroy_2;
	public static BufferedImage img_blockDestroy_3;
	public static BufferedImage img_blockDestroy_4;
	
	public static void initializeImages() {
		img_blockDestroy_1 = ResourceLocator.getImage("blockDestroy_1");
		img_blockDestroy_2 = ResourceLocator.getImage("blockDestroy_2");
		img_blockDestroy_3 = ResourceLocator.getImage("blockDestroy_3");
		img_blockDestroy_4 = ResourceLocator.getImage("blockDestroy_4");
	}
	
	public BlockDestroyer(BlockPos pos) {
		this.pos = pos;
		this.hardness = Register.getBlock(Main.getMain().world.getBlock(pos)).getHardness();
	}
	
	public void update() {
		if (active()) {
			if (hardness != 0) {
				progress += (1.0f/hardness)*efficiency;
			} else {
				progress = 1.2f;
			}
		} else {
			progress -= 0.1f/30.0f;
		}
		destroyers.clear();
	}
	
	public void setActive(Entity destroyer, ToolItem item) {
		if (destroyers.contains(destroyer))
			return;
		destroyers.add(destroyer);
		this.destroyer = destroyer;
		this.active = true;
		this.efficiency += item.getEfficiency();
	}
	
	public boolean noProgress() {
		return progress <= 0;
	}
	
	public boolean active() {
		boolean ret = this.active;
		this.active = false;
		return ret;
	}
	
	public boolean shouldDestroy() {
		return progress >= 1.0f;
	}
	
	public BlockPos getPosition() {
		return this.pos;
	}
	
	public Entity getDestroyer() {
		return destroyer;
	}
	
	public BufferedImage getImage() {
		if (progress <= 0.25) {
			return img_blockDestroy_1;
		} else if (progress <= 0.5) {
			return img_blockDestroy_2;
		} else if (progress <= 0.75) {
			return img_blockDestroy_3;
		} else {
			return img_blockDestroy_4;
		}
	}
	
}
