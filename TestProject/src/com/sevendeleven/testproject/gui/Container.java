package com.sevendeleven.testproject.gui;

import com.sevendeleven.testproject.item.ItemStack;

import java.util.*;

public class Container {
	
	public static enum ContainerType {
		Inventory,
		InventoryU1,
		InventoryU2,
		InventoryU3,
		Hotbar,
		Chest
	};
	
	private ContainerType type;
	
	private List<ItemStack[]> items;
	
	public Container(ContainerType type) {
		this.type = type;
		switch (type) {
		case Inventory:
			setSize(10, 3);
			break;
		case InventoryU1:
			setSize(10, 4);
			break;
		case InventoryU2:
			setSize(10, 5);
			break;
		case InventoryU3:
			setSize(10, 6);
			break;
		case Hotbar:
			setSize(10, 1);
			break;
		case Chest:
			setSize(10, 2);
			break;
		}
	}
	
	private void setSize(int cols, int rows) {
		items = new ArrayList<ItemStack[]>();
		for (int i = 0; i < rows; i++) {
			ItemStack[] row = new ItemStack[cols];
//			for (int j = 0; j < cols; j++) {
//				row[j] = new ItemStack(0,0,0);
//			}
			items.add(row);
		}
	}
	
	public ContainerType getType() {
		return type;
	}
	
	public List<ItemStack[]> getItems() {
		return items;
	}
	
}
