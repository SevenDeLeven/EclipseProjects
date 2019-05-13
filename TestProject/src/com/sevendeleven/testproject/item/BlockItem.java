package com.sevendeleven.testproject.item;

import java.awt.image.BufferedImage;

import com.sevendeleven.testproject.entities.Entity;
import com.sevendeleven.testproject.world.Block;
import com.sevendeleven.testproject.world.BlockPos;

public class BlockItem {
	
	protected String name;
	protected BufferedImage image;
	protected long uniqueNumber;
	protected int maxStackSize;
	
	public BlockItem(Block block) {
		this.name = block.getName();
		this.image = block.getImage();
		this.uniqueNumber = block.getUniqueNumber();
		this.maxStackSize = 1000;
	}
	
	public void onUse(ItemStack stack, BlockPos blockPos, Entity ent) {
		if (blockPos.getWorld().getBlock(blockPos) == 0) {
			BlockPos top = new BlockPos(blockPos.getWorld(), blockPos.getX(), blockPos.getY()+1);
			BlockPos bottom = new BlockPos(blockPos.getWorld(), blockPos.getX(), blockPos.getY()-1);
			BlockPos right = new BlockPos(blockPos.getWorld(), blockPos.getX()+1, blockPos.getY());
			BlockPos left = new BlockPos(blockPos.getWorld(), blockPos.getX()-1, blockPos.getY());
			if (blockPos.getWorld().getBlock(top) != 0 || blockPos.getWorld().getBlock(bottom) != 0 || blockPos.getWorld().getBlock(right) != 0 || blockPos.getWorld().getBlock(left) != 0) {
				blockPos.getWorld().placeBlock(blockPos.getX(), blockPos.getY(), this.getName(), true);
				stack.setSize(stack.getSize()-1);
			}
		}
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public void onRightClick(ItemStack stack, BlockPos blockPos, Entity ent) {
		
	}
	
	public void onDestroyBlock(ItemStack stack, BlockPos blockPos, Entity ent) {
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public long getUniqueNumber() {
		return this.uniqueNumber;
	}
	
	public int getMaxStackSize() {
		return maxStackSize;
	}
	
}