package com.sevendeleven.testproject.world.block;

import java.awt.image.BufferedImage;

import com.sevendeleven.testproject.main.Main;
import com.sevendeleven.testproject.world.Block;
import com.sevendeleven.testproject.world.BlockPos;

public class Air extends Block {

	public Air() {
		super((short)0);
	}

	public void initialize() {
		this.img = new BufferedImage(Main.getBlockSize(), Main.getBlockSize(), BufferedImage.TYPE_INT_ARGB);
		int color = 0x00000000;
		for (int x = 0; x < Main.getBlockSize(); x++) {
			for (int y = 0; y < Main.getBlockSize(); y++) {
				img.setRGB(x, y, color);
			}
		}
	}

	@Override
	public void onNeighborUpdated(BlockPos pos) {
	}

	@Override
	public void onUpdate(boolean updateNeighbors, BlockPos pos) {
	}

	@Override
	public void onUse(BlockPos pos) {
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}
	
	@Override
	public float getHardness() {
		return 0;
	}

	@Override
	public String getName() {
		return "air";
	}
	
	public Type getType() {
		return Type.SOIL;
	}
	
}
