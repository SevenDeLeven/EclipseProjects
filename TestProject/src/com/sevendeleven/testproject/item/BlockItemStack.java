package com.sevendeleven.testproject.item;

public class BlockItemStack extends ItemStack {
	
	public BlockItemStack(long block, int size, int meta) {
		super(block, size, meta);
	}
	
	public boolean equals(Object o) {
		if (o != null && o.getClass().equals(getClass())) {
			BlockItemStack i = (BlockItemStack) o;
			return i.getItem() == this.getItem() && i.getMeta() == this.getMeta();
		}
		return false;
	}
	
	public BlockItemStack setSize(int size) {
		this.size = size;
		return this;
	}
	
	public BlockItemStack copy() {
		return new BlockItemStack(this.getItem(), this.getSize(), this.getMeta());
	}
	
}
