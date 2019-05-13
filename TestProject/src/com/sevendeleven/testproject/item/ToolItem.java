package com.sevendeleven.testproject.item;

import org.json.JSONObject;

import com.sevendeleven.testproject.entities.Entity;
import com.sevendeleven.testproject.main.Register;
import com.sevendeleven.testproject.main.ResourceLocator;
import com.sevendeleven.testproject.world.Block;
import com.sevendeleven.testproject.world.BlockPos;
import com.sevendeleven.testproject.world.World;

public class ToolItem extends Item {
	
	public static enum Type {
		PICK(Block.Type.SOLID),
		PICKAXE(Block.Type.SOLID, Block.Type.WOOD),
		AXE(Block.Type.WOOD),
		SHOVEL(Block.Type.SOIL),
		ALL(Block.Type.SOLID, Block.Type.WOOD, Block.Type.SOIL);
		private Block.Type[] destroyable;
		private Type(Block.Type...types) {
			destroyable = types;
		}
		public boolean canDestroy(long block) {
			if (block == -1) {
				return false;
			}
			Block.Type bType = Register.getBlock(block).getType();
			for (Block.Type t : destroyable) {
				if (bType.equals(t)) {
					return true;
				}
			}
			return false;
		}
	}
	
	private Type type;
	private int durability;
	private float efficiency;
	private float reach;
	
	public ToolItem(JSONObject obj) {
		super();
		if (!obj.has("name")) {
			System.err.println("Tool does not have name!");
			System.exit(-1);
		}
		if (!obj.has("uniqueNumber")) {
			System.err.println("Tool " + obj.getString("name") + " does not have a unique number!");
			System.exit(-1);
		}
		if (!obj.has("texture")) {
			System.err.println("Tool " + obj.getString("name") + " does not have a texture!");
			System.exit(-1);
		}
		if (!obj.has("type")) {
			System.err.println("Tool " + obj.getString("name") + " does not have a tool type!");
			System.exit(-1);
		}
		if (!obj.has("durability")) {
			System.err.println("Tool " + obj.getString("name") + " does not have a durability!");
			System.exit(-1);
		}
		if (!obj.has("efficiency")) {
			System.err.println("Tool " + obj.getString("name") + " does not have an efficiency!");
			System.exit(-1);
		}
		if (!obj.has("reach")) {
			System.err.println("Tool " + obj.getString("name") + " does not have a reach!");
			System.exit(-1);
		}
		this.name = obj.getString("name");
		this.image = ResourceLocator.getImage(obj.getString("texture"));
		this.uniqueNumber = obj.getNumber("uniqueNumber").longValue();
		this.maxStackSize = 1;
		this.type = Type.valueOf(obj.getString("type").toUpperCase());
		this.durability = obj.getInt("durability");
		this.efficiency = obj.getFloat("efficiency");
		this.reach = obj.getFloat("reach");
	}
	
	@Override
	public void onUse(ItemStack stack, BlockPos pos, Entity ent) {
		
	}
	
	@Override
	public void onDestroyBlock(ItemStack stack, BlockPos pos, Entity ent) {
		World world = pos.getWorld();
		if (Register.getBlock(world.getBlock(pos)).isSolid()) {
			stack.meta++;
			if (stack.meta >= this.durability) {
				stack.setSize(stack.getSize()-1);
			}
		}
	}
	
	@Override
	public void onClick(ItemStack stack, BlockPos pos, Entity ent) {
		
	}
	
	public Type getType() {
		return type;
	}
	
	public float getEfficiency() {
		return this.efficiency;
	}
	
	public float getReach() {
		return this.reach;
	}
	
	public int getDurability() {
		return this.durability;
	}
	
}
