package com.sevendeleven.testproject.main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.sevendeleven.testproject.item.Item;
import com.sevendeleven.testproject.item.ToolItem;
import com.sevendeleven.testproject.util.ID;
import com.sevendeleven.testproject.world.Block;

public class Register {

	private static final HashMap<ID, Block> blocks;
	private static final HashMap<ID, Item> items;
	
	static {
		blocks = new HashMap<ID, Block>();
		items = new HashMap<ID, Item>();
	}
	
	public static void registerItem(Class<? extends Item> clazz) {
		if (clazz == null) {
			System.err.println("An item was registered null (WHAT?!)");
			return;
		}
		try {
			Method initialize = clazz.getMethod("initialize");
			Item instance = clazz.getConstructor().newInstance();
			if (initialize != null) {
				initialize.invoke(instance);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occurred in initializing the item " + clazz.getName());
			System.exit(-1);
		} catch (InstantiationException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occurred in constructing the item " + clazz.getName());
			System.exit(-1);
		}
	}
	
	public static void registerItem(Item item) {
		if (item == null) {
			System.err.println("An item was registered as null! (WHAT?!)");
			System.exit(-1);
		}
		if (getItemId(item.getUniqueNumber()) != null) {
			ID i = getItemId(item.getUniqueNumber());
			System.err.println("Item " + item.getName() + " is using an already existing unique integer ID " + item.getUniqueNumber() + ", " + i.getName() + " is already using it!");
			System.exit(-1);
		}
		if (getItemId(item.getName()) != null) {
			System.err.println("Item " + item.getName() + " already exists!");
			System.exit(-1);
		}
		items.put(new ID(item.getUniqueNumber(), item.getName()), item);
	}
	
	public static void registerTool(ToolItem tool) {
		if (tool == null) {
			System.err.println("A tool was registered as null! (WHAT?!)");
			System.exit(-1);
		}
		if (getItemId(tool.getUniqueNumber()) != null) {
			ID i = getItemId(tool.getUniqueNumber());
			System.err.println("Tool " + tool.getName() + " is using an already existing unique integer ID " + tool.getUniqueNumber() + ", " + i.getName() + " is already using it!");
		}
		if (getItemId(tool.getName()) != null) {
			System.err.println("Tool " + tool.getName() + " already exists!");
			System.exit(-1);
		}
		items.put(new ID(tool.getUniqueNumber(), tool.getName()), tool);
	}
	
	/*
	public static void registerBlock(Block block) {
		if (block == null) {
			System.err.println("A block was registered as null! (What?!)");
			return;
		}
		if (getBlockId(block.getUniqueNumber()) != null) {
			ID i = getBlockId(block.getUniqueNumber());
			System.err.println("Block " + block.getName() + " is using an already existing unique integer ID " + block.getUniqueNumber() + ", " + i.getName() + " is already using it!");
			System.exit(-1);
		}
		if (getBlockId(block.getName()) != null) {
			System.err.println("Block " + block.getName() + " already exists!");
			System.exit(-1);
		}
		blocks.put(new ID(block.getUniqueNumber(), block.getName()), block);
	}
	*/
	
	@SuppressWarnings("unlikely-arg-type")
	public static ID getNextItemId(String name) {
		for (int i = 0; i < 255; i++) {
			if (!items.containsKey(i)) {
				return new ID(i, name);
			}
		}
		return null;
	}
	
	public static Item getItem(ID id) {
		return items.get(id);
	}
	
	public static Item getItemUNItem = null;
	public static Item getItem(String uniqueName) {
		getItemUNItem = null;
		items.forEach((id, it) -> {
			if (id.getName().equals(uniqueName)) {
				getItemUNItem = it;
				return;
			}
		});
		return getItemUNItem;
	}
	
	public static Item getItemUIItem = null;
	public static Item getItem(long uniqueInteger) {
		getItemUIItem = null;
		items.forEach((id, it) -> {
			if (id.getNumber() == uniqueInteger) {
				getItemUIItem = it;
				return;
			}
		});
		return getItemUIItem;
	}
	
	private static ID getItemIdRet = null;
	public static ID getItemId(Class<? extends Item> item) {
		getItemIdRet = null;
		items.forEach((id, it) -> {
			if (item.getClass().equals(it.getClass())) {
				getItemIdRet = id;
				return;
			}
		});
		return getItemIdRet;
	}
	
	private static ID getItemIdUNRet = null;
	public static ID getItemId(String uniqueName) {
		getItemIdUNRet = null;
		items.keySet().forEach((id) -> {
			if (id.getName().equals(uniqueName)) {
				getItemIdUNRet = id;
				return;
			}
		});
		return getItemIdUNRet;
	}
	
	private static ID getItemIdUIRet = null;
	public static ID getItemId(long uniqueId) {
		getItemIdUIRet = null;
		items.keySet().forEach((id) -> {
			if (id.getNumber() == uniqueId) {
				getItemIdUIRet = id;
				return;
			}
		});
		return getItemIdUIRet;
	}
	
	
	
	
	
	
	
	
	
	public static void registerBlock(Class<? extends Block> clazz) {
		if (clazz == null) {
			System.err.println("A block was registered null (What?!)");
			return;
		}
		try {
			Method initialize = clazz.getMethod("initialize");
			Block instance = clazz.getConstructor().newInstance();
			if (initialize != null) {
				initialize.invoke(instance);
			}
			registerBlock(instance);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occurred in initializing the block " + clazz.getName());
			System.exit(-1);
		} catch (InstantiationException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occurred in constructing the block " + clazz.getName());
			System.exit(-1);
		}
	}
	
	public static void registerBlock(Block block) {
		if (block == null) {
			System.err.println("A block was registered as null! (What?!)");
			return;
		}
		if (getBlockId(block.getUniqueNumber()) != null) {
			ID i = getBlockId(block.getUniqueNumber());
			System.err.println("Block " + block.getName() + " is using an already existing unique integer ID " + block.getUniqueNumber() + ", " + i.getName() + " is already using it!");
			System.exit(-1);
		}
		if (getBlockId(block.getName()) != null) {
			System.err.println("Block " + block.getName() + " already exists!");
			System.exit(-1);
		}
		blocks.put(new ID(block.getUniqueNumber(), block.getName()), block);
	}
	
	public static ID getNextBlockId(String name) {		//Public because it does not change any values
		for (int i = 0; i < 255; i++) {
			if (getBlock(i) == null) {
				return new ID(i, name);
			}
		}
		return null;
	}
	
	public static Block getBlock(ID id) {
		return blocks.get(id);
	}
	
	
	
	
	
	
	
	
	
	public static Block getBlockUNBlock = null;
	public static Block getBlock(String uniqueName) {
		getBlockUNBlock = null;
		blocks.forEach((id, it) -> {
			if (id.getName().equals(uniqueName)) {
				getBlockUNBlock = it;
				return;
			}
		});
		return getBlockUNBlock;
	}
	
	public static Block getBlockUIBlock = null;
	public static Block getBlock(long uniqueInteger) {
		getBlockUIBlock = null;
		blocks.forEach((id, it) -> {
			if (id.getNumber() == uniqueInteger) {
				getBlockUIBlock = it;
				return;
			}
		});
		return getBlockUIBlock;
	}
	
	
	
	
	
	
	
	
	
	private static ID getBlockIdRet = null;
	public static ID getBlockId(Class<? extends Block> block) {
		getBlockIdRet = null;
		blocks.forEach((id, it) -> {
			if (block.equals(it.getClass())) {
				getBlockIdRet = id;
				return;
			}
		});
		return getBlockIdRet;
	}
	
	public static ID getBlockIdUNBlock = null;
	public static ID getBlockId(String uniqueName) {
		getBlockIdUNBlock = null;
		blocks.keySet().forEach((id) -> {
			if (id.getName().equals(uniqueName)) {
				getBlockIdUNBlock = id;
				return;
			}
		});
		return getBlockIdUNBlock;
	}
	
	public static ID getBlockIdUIBlock = null;
	public static ID getBlockId(long uniqueInteger) {
		getBlockIdUIBlock = null;
		blocks.keySet().forEach((id) -> {
			if (id.getNumber() == uniqueInteger) {
				getBlockIdUIBlock = id;
				return;
			}
		});
		return getBlockIdUIBlock;
	}
	
}
