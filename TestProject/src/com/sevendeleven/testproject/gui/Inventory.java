package com.sevendeleven.testproject.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.sevendeleven.testproject.entities.EntityBlockItem;
import com.sevendeleven.testproject.entities.EntityItem;
import com.sevendeleven.testproject.entities.Player;
import com.sevendeleven.testproject.event.Mouse;
import com.sevendeleven.testproject.item.BlockItem;
import com.sevendeleven.testproject.item.BlockItemStack;
import com.sevendeleven.testproject.item.Item;
import com.sevendeleven.testproject.item.ItemStack;
import com.sevendeleven.testproject.item.ToolItem;
import com.sevendeleven.testproject.main.GameController;
import com.sevendeleven.testproject.main.Main;
import com.sevendeleven.testproject.main.Register;
import com.sevendeleven.testproject.main.ResourceLocator;
import com.sevendeleven.testproject.main.Scheduler;
import com.sevendeleven.testproject.util.Vec2f;
import com.sevendeleven.testproject.util.Vec2i;

public class Inventory extends GUI {
	
	public static enum InventoryUpgrade {
		None,
		Small,
		Medium,
		Large
	}
	
	private BufferedImage img_inventory;
	private BufferedImage img_inventoryu1;
	private BufferedImage img_inventoryu2;
	private BufferedImage img_inventoryu3;
	private BufferedImage img_inventory_hotbar;
	private BufferedImage img_inventory_selectedslot;
	private BufferedImage img_inventory_tabSelected;
	private BufferedImage img_inventory_tabUnselected;
	
	private Container itemInventory;
	private Container blockInventory;
	private Container hotbar;
	private Container activeInventory;
	
	private InventoryUpgrade size;
	
	private int selectedSlot = 0;
	
	private Player player;
	
	private List<List<GUIButton>> inventoryButtons;
	private List<List<GUIButton>> hotbarButtons;
	private GUIButton tab1Button;
	private GUIButton tab2Button;
	
	private ItemStack cursorItemStack = null;
	
	public Inventory(Player p, InventoryUpgrade size) {
		super();
		this.size = size;
		this.player = p;
		itemInventory = new Container(Container.ContainerType.Inventory);
		blockInventory = new Container(Container.ContainerType.Inventory);
		hotbar = new Container(Container.ContainerType.Hotbar);
		activeInventory = itemInventory;
		createButtons();
		img_inventory = ResourceLocator.getImage("inventory_grid30");
		img_inventoryu1 = ResourceLocator.getImage("inventory_grid40");
		img_inventoryu2 = ResourceLocator.getImage("inventory_grid50");
		img_inventoryu3 = ResourceLocator.getImage("inventory_grid60");
		img_inventory_hotbar = ResourceLocator.getImage("inventory_grid10");
		img_inventory_selectedslot = ResourceLocator.getImage("inventory_selectedslot");
		img_inventory_tabSelected = ResourceLocator.getImage("inventory_tabSelected");
		img_inventory_tabUnselected = ResourceLocator.getImage("inventory_tabUnselected");
	}
	
	private void createButtons() { 
		tab1Button = new GUIButton(0,0, 80, 70);
		tab2Button = new GUIButton(0,0, 80, 70);
		inventoryButtons = new ArrayList<List<GUIButton>>();
		hotbarButtons = new ArrayList<List<GUIButton>>();
		List<ItemStack[]> items = itemInventory.getItems();
		for (int i = 0; i < items.size(); i++) {
			inventoryButtons.add(new ArrayList<GUIButton>());
			for (int j = 0; j < items.get(i).length; j++) {
				inventoryButtons.get(i).add(new GUIButton(0,0,64,64));
			}
		}
		items = hotbar.getItems();
		for (int i = 0; i < items.size(); i++) {
			hotbarButtons.add(new ArrayList<GUIButton>());
			for (int j = 0; j < items.get(i).length; j++) {
				hotbarButtons.get(i).add(new GUIButton(0,0,64,64));
			}
		}
	}
	
	public void updateButtonLocations() {
		Main main = Main.getMain();
		int screenW = main.getWidth();
		int screenH = main.getHeight();
		
		BufferedImage inventoryImage = getInventoryImage();
		int inventoryX = (screenW/2)-(inventoryImage.getWidth()/2);
		int inventoryY = (screenH/2)-(inventoryImage.getHeight()/2);
		int hotbarX = (screenW/2)-(img_inventory_hotbar.getWidth()/2);
		int hotbarY = screenH - (img_inventory_hotbar.getHeight());
		
		int tab1X = inventoryX + 5;
		int tab2X = tab1X + 80;
		int tabY = inventoryY - 70;
		
		tab1Button.setX(tab1X).setY(tabY);
		tab2Button.setX(tab2X).setY(tabY);
		
		List<ItemStack[]> items = this.activeInventory.getItems();
		for (int i = 0; i < items.size(); i++) {
			ItemStack[] row = items.get(i);
			for (int j = 0; j < row.length; j++) {
				Vec2i b = new Vec2i(inventoryX + 16 + (76*j), inventoryY + 16 + (76*i));
				inventoryButtons.get(i).get(j).setX(b.x).setY(b.y);
			}
		}
		
		items = this.hotbar.getItems();
		for (int i = 0; i < items.size(); i++) {
			ItemStack[] row = items.get(i);
			for (int j = 0; j < row.length; j++) {
				Vec2i b = new Vec2i(hotbarX + 16 + (76*j), hotbarY + 16 + (76*i));
				hotbarButtons.get(i).get(j).setX(b.x).setY(b.y);
			}
		}
	}

	public void checkItems() {
		for (int i = 0; i < this.itemInventory.getItems().size(); i++) {
			ItemStack[] stacks = this.itemInventory.getItems().get(i);
			for (int j = 0; j < stacks.length; j++) {
				if (stacks[j] != null && stacks[j].getSize() <= 0) {
					System.out.println("SDF1");
					stacks[j] = null;
				}
			}
		}
		for (int i = 0; i < this.blockInventory.getItems().size(); i++) {
			ItemStack[] stacks = this.blockInventory.getItems().get(i);
			for (int j = 0; j < stacks.length; j++) {
				if (stacks[j] != null && stacks[j].getSize() <= 0) {
					System.out.println("SDF2");
					stacks[j] = null;
				}
			}
		}
		for (int i = 0; i < this.hotbar.getItems().size(); i++) {
			ItemStack[] stacks = this.hotbar.getItems().get(i);
			for (int j = 0; j < stacks.length; j++) {
				if (stacks[j] != null && stacks[j].getSize() <= 0) {
					System.out.println("SDF3");
					stacks[j] = null;
				}
			}
		}
	}
	
	private int getX(BufferedImage img) {
		return (Main.getMain().getWidth()/2) - (img.getWidth()/2);
	}
	
	private int getY(BufferedImage img) {
		return (Main.getMain().getHeight()/2) - (img.getHeight()/2);
	}
	
	private int getHotbarX() {
		return (Main.getMain().getWidth()/2) - (img_inventory_hotbar.getWidth()/2);
	}
	
	private int getHotbarY() {
		return Main.getMain().getHeight()-(img_inventory_hotbar.getHeight());
	}
	
	public BufferedImage getInventoryImage() {
		BufferedImage img = null;
		switch (itemInventory.getType()) {
		case Inventory:
			img = img_inventory;
			break;
		case InventoryU1:
			img = img_inventoryu1;
			break;
		case InventoryU2:
			img = img_inventoryu2;
			break;
		case InventoryU3:
			img = img_inventoryu3;
			break;
		default:
			System.err.println("Inventory is not of type INVENTORY");
			System.exit(-1);
			break;
		}
		return img;
	}
	
	public void drawHotbar(Graphics g) {
		g.drawImage(img_inventory_hotbar, getHotbarX(), getHotbarY(), null);
		g.drawImage(img_inventory_selectedslot, getHotbarX() + (selectedSlot*76), getHotbarY(), null);
		List<ItemStack[]> items = this.hotbar.getItems();
		for (int i = 0; i < items.size(); i++) {
			ItemStack[] row = items.get(i);
			for (int j = 0; j < row.length; j++) {
				if (row[j] != null) {
					this.renderItemStack(g, row[j], this.getHotbarX() + 8 + (76*j), this.getHotbarY() + 8 + (76*i));
				}
			}
		}
	}
	
	@Override
	public void draw(Graphics g) {
		
		BufferedImage img = getInventoryImage();
		g.drawImage(img, this.getX(img), getY(img), null);
		List<ItemStack[]> items = this.activeInventory.getItems();
		for (int i = 0; i < items.size(); i++) {
			ItemStack[] row = items.get(i);
			for (int j = 0; j < row.length; j++) {
				if (row[j] != null) {
					this.renderItemStack(g, row[j], this.getX(img) + 16 + (76*j), this.getY(img) + 16 + (76*i));
				}
			}
		}
		if (activeInventory == this.itemInventory) {
			g.drawImage(img_inventory_tabSelected, tab1Button.getX(), tab1Button.getY(), null);
			g.drawImage(img_inventory_tabUnselected, tab2Button.getX(), tab2Button.getY(), null);
		} else {
			g.drawImage(img_inventory_tabUnselected, tab1Button.getX(), tab1Button.getY(), null);
			g.drawImage(img_inventory_tabSelected, tab2Button.getX(), tab2Button.getY(), null);
		}
		
		drawHotbar(g);
		if (cursorItemStack != null) {
			if (cursorItemStack.getClass().equals(BlockItemStack.class)) {
				this.activeInventory = blockInventory;
			} else if (cursorItemStack.getClass().equals(ItemStack.class)) {
				this.activeInventory = itemInventory;
			}
			renderItemStack(g, cursorItemStack, Mouse.position.x-32, Mouse.position.y-32);
		}
	}
	
	public void scroll(int amount) {
		selectedSlot += amount;
		selectedSlot = selectedSlot < 0 ? 0 : (selectedSlot > 9 ? 9 : selectedSlot);
	}
	
	public void renderItemStack(Graphics g, ItemStack e, int x, int y) {
		if (e.getClass().equals(BlockItemStack.class)) {
			renderBlockItemStack(g, e, x, y);
			return;
		}
		Item item = Register.getItem(e.getItem());
		BufferedImage img = item.getImage();
		g.drawImage(img, x, y, null);
		Font f = new Font("Arial", Font.PLAIN, 12);
		g.setFont(f);
		g.setColor(Color.WHITE);
		g.drawString(Integer.toString(e.getSize()), x, y);
		if (item.getClass().equals(ToolItem.class)) {
			ToolItem tool = (ToolItem)item;
			
			g.setColor(Color.DARK_GRAY);
			g.fillRect(x-1, y+58, 66, 7);
			float percentDown = ((float)tool.getDurability()-(float)e.getMeta())/(float)tool.getDurability();
			int red = (int) ((1.0f-percentDown)*255);
			int green = (int) (percentDown*255);
			g.setColor(new Color(red < 0 ? 0 : red, green < 0 ? 0 : green, 0));
			g.fillRect(x, y+59, (int)(64*percentDown), 5);
		}
	}
	public void renderBlockItemStack(Graphics g, ItemStack e, int x, int y) {
		BlockItem item = Register.getBlock(e.getItem()).getBlockItem();
		BufferedImage img = item.getImage();
		g.drawImage(img, x+16, y+16, 32, 32, null);
		Font f = new Font("Arial", Font.PLAIN, 12);
		g.setFont(f);
		g.setColor(Color.WHITE);
		g.drawString(Integer.toString(e.getSize()), x, y);
	}
	
	public ItemStack getItemInHand() {
		return this.hotbar.getItems().get(0)[this.selectedSlot];
	}
	
	private boolean addBlockItem(BlockItemStack stack) {
		if (this.hasBlock(stack.getItem())) {
			List<ItemStack[]> items = hotbar.getItems();
			for (int i = 0; i < items.size(); i++) {
				ItemStack[] row = items.get(i);
				for (int j = 0; j < row.length; j++) {
					if (row[j] == null) {
						continue;
					}
					if (row[j].equals(stack)) {
						BlockItem item = Register.getBlock(row[j].getItem()).getBlockItem();
						if (item.getMaxStackSize() < stack.getSize()+row[j].getSize()) {
							stack.setSize((stack.getSize()+row[j].getSize())-item.getMaxStackSize());
							row[j].setSize(item.getMaxStackSize());
						} else {
							row[j].setSize(row[j].getSize()+stack.getSize());
							return true;
						}
					}
				}
			}
			items = blockInventory.getItems();
			for (int i = 0; i < items.size(); i++) {
				ItemStack[] row = items.get(i);
				for (int j = 0; j < row.length; j++) {
					if (row[j] == null) {
						continue;
					}
					if (row[j].equals(stack)) {
						BlockItem item = Register.getBlock(row[j].getItem()).getBlockItem();
						if (item.getMaxStackSize() < stack.getSize()+row[j].getSize()) {
							stack.setSize((stack.getSize()+row[j].getSize())-item.getMaxStackSize());
							row[j].setSize(item.getMaxStackSize());
						} else {
							row[j].setSize(row[j].getSize()+stack.getSize());
							return true;
						}
					}
				}
			}
		}
		List<ItemStack[]> items = hotbar.getItems();
		for (int i = 0; i < items.size(); i++) {
			ItemStack[] row = items.get(i);
			for (int j = 0; j < row.length; j++) {
				if (row[j] == null) {
					row[j] = stack;
					return true;
				} else {
					if (row[j].equals(stack)) {
						BlockItem item = Register.getBlock(row[j].getItem()).getBlockItem();
						if (item.getMaxStackSize() < stack.getSize()+row[j].getSize()) {
							stack.setSize((stack.getSize()+row[j].getSize())-item.getMaxStackSize());
							row[j].setSize(item.getMaxStackSize());
						} else {
							row[j].setSize(row[j].getSize()+stack.getSize());
							return true;
						}
					}
				}
			}
		}
		items = blockInventory.getItems();
		for (int i = 0; i < items.size(); i++) {
			ItemStack[] row = items.get(i);
			for (int j = 0; j < row.length; j++) {
				if (row[j] == null) {
					row[j] = stack;
					return true;
				} else {
					if (row[j].equals(stack)) {
						BlockItem item = Register.getBlock(row[j].getItem()).getBlockItem();
						if (item.getMaxStackSize() < stack.getSize()+row[j].getSize()) {
							stack.setSize((stack.getSize()+row[j].getSize())-item.getMaxStackSize());
							row[j].setSize(item.getMaxStackSize());
						} else {
							row[j].setSize(row[j].getSize()+stack.getSize());
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean hasBlock(long item) {
		List<ItemStack[]> items = this.blockInventory.getItems();
		for (int i = 0; i < items.size(); i++) {
			ItemStack[] row = items.get(i);
			for (int j = 0; j < row.length; j++) {
				ItemStack stack = row[j];
				if (stack != null && stack.getItem() == item) {
					return true;
				}
			}
		}
		items = hotbar.getItems();
		for (int i = 0; i < items.size(); i++) {
			ItemStack[] row = items.get(i);
			for (int j = 0; j < row.length; j++) {
				ItemStack stack = row[j];
				if (stack != null && stack.getClass().equals(BlockItemStack.class) && stack.getItem() == item) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasItem(long item) {
		List<ItemStack[]> items = this.itemInventory.getItems();
		for (int i = 0; i < items.size(); i++) {
			ItemStack[] row = items.get(i);
			for (int j = 0; j < row.length; j++) {
				ItemStack stack = row[j];
				if (stack != null && stack.getItem() == item) {
					return true;
				}
			}
		}
		items = hotbar.getItems();
		for (int i = 0; i < items.size(); i++) {
			ItemStack[] row = items.get(i);
			for (int j = 0; j < row.length; j++) {
				ItemStack stack = row[j];
				if (stack != null && stack.getClass().equals(ItemStack.class) && stack.getItem() == item) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean addItem(ItemStack stack) {
		if (stack.getClass().equals(BlockItemStack.class)) {
			return addBlockItem((BlockItemStack)stack);
		}
		if (this.hasItem(stack.getItem())) {
			
			List<ItemStack[]> items = hotbar.getItems();
			for (int i = 0; i < items.size(); i++) {
				ItemStack[] row = items.get(i);
				for (int j = 0; j < row.length; j++) {
					if (row[j] == null) {
						continue;
					}
					if (row[j].equals(stack)) {
						Item item = Register.getItem(row[j].getItem());
						if (item.getMaxStackSize() < stack.getSize()+row[j].getSize()) {
							stack.setSize((stack.getSize()+row[j].getSize())-item.getMaxStackSize());
							row[j].setSize(item.getMaxStackSize());
						} else {
							row[j].setSize(row[j].getSize()+stack.getSize());
							return true;
						}
					}
				}
			}
			items = itemInventory.getItems();
			for (int i = 0; i < items.size(); i++) {
				ItemStack[] row = items.get(i);
				for (int j = 0; j < row.length; j++) {
					if (row[j] == null) {
						continue;
					}
					if (row[j].equals(stack)) {
						Item item = Register.getItem(row[j].getItem());
						if (item.getMaxStackSize() < stack.getSize()+row[j].getSize()) {
							stack.setSize((stack.getSize()+row[j].getSize())-item.getMaxStackSize());
							row[j].setSize(item.getMaxStackSize());
						} else {
							row[j].setSize(row[j].getSize()+stack.getSize());
							return true;
						}
					}
				}
			}
		}
		List<ItemStack[]> items = hotbar.getItems();
		for (int i = 0; i < items.size(); i++) {
			ItemStack[] row = items.get(i);
			for (int j = 0; j < row.length; j++) {
				if (row[j] == null) {
					row[j] = stack;
					return true;
				} else {
					if (row[j].equals(stack)) {
						Item item = Register.getItem(row[j].getItem());
						if (item.getMaxStackSize() < stack.getSize()+row[j].getSize()) {
							stack.setSize((stack.getSize()+row[j].getSize())-item.getMaxStackSize());
							row[j].setSize(item.getMaxStackSize());
						} else {
							row[j].setSize(row[j].getSize()+stack.getSize());
							return true;
						}
					}
				}
			}
		}
		items = itemInventory.getItems();
		for (int i = 0; i < items.size(); i++) {
			ItemStack[] row = items.get(i);
			for (int j = 0; j < row.length; j++) {
				if (row[j] == null) {
					row[j] = stack;
					return true;
				} else {
					if (row[j].equals(stack)) {
						Item item = Register.getItem(row[j].getItem());
						if (item.getMaxStackSize() < stack.getSize()+row[j].getSize()) {
							stack.setSize((stack.getSize()+row[j].getSize())-item.getMaxStackSize());
							row[j].setSize(item.getMaxStackSize());
						} else {
							row[j].setSize(row[j].getSize()+stack.getSize());
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public InventoryUpgrade getSize() {
		return size;
	}
	
	public boolean removeItem(ItemStack stack) {
		List<ItemStack[]> items = hotbar.getItems();
		for (int i = 0; i < items.size(); i++) {
			ItemStack[] row = items.get(i);
			for (int j = 0; j < row.length; j++) {
				if (row[j].equals(stack)) {
					if (row[j].getSize() >= stack.getSize()) {
						row[j].setSize(row[j].getSize()-stack.getSize());
						if (row[j].getSize() == 0) {
							row[j] = null;
						}
						return true;
					} else {
						stack.setSize(stack.getSize()-row[j].getSize());
						row[j] = null;
					}
				}
			}
		}
		items = itemInventory.getItems();
		for (int i = 0; i < items.size(); i++) {
			ItemStack[] row = items.get(i);
			for (int j = 0; j < row.length; j++) {
				if (row[j].equals(stack)) {
					if (row[j].getSize() >= stack.getSize()) {
						row[j].setSize(row[j].getSize()-stack.getSize());
						if (row[j].getSize() == 0) {
							row[j] = null;
						}
						return true;
					} else {
						stack.setSize(stack.getSize()-row[j].getSize());
						row[j] = null;
					}
				}
			}
		}
		return false;
	}
	
	public void dropBlockItem(BlockItemStack stack) {
		if (stack != null) {
			EntityBlockItem item = new EntityBlockItem(stack.copy(), player.getWorld(), player.getPosition().x, player.getPosition().y, GameController.nextEntityId());
			Scheduler.getScheduler().addEntityToWorld(item);
			item.setVelocity(new Vec2f(player.getDirX() * 0.5f, 0));
		}
	}
	
	public void dropItem(ItemStack stack) {
		if (stack.getClass().equals(BlockItemStack.class)) {
			dropBlockItem((BlockItemStack) stack);
			return;
		}
		if (stack != null) {
			EntityItem item = new EntityItem(stack.copy(), player.getWorld(), player.getPosition().x, player.getPosition().y, GameController.nextEntityId());
			Scheduler.getScheduler().addEntityToWorld(item);
			item.setVelocity(new Vec2f(player.getDirX() * 0.5f, 0));
		}
	}
	
	public void dropItemInHand() {
		ItemStack s = getItemInHand();
		if (s != null) {
			if (s.getClass().equals(BlockItemStack.class)) {
					BlockItemStack ss = (BlockItemStack) s;
					EntityBlockItem item = new EntityBlockItem(ss.copy().setSize(1), player.getWorld(), player.getPosition().x, player.getPosition().y+1, GameController.nextEntityId());
					Scheduler.getScheduler().addEntityToWorld(item);
					item.setVelocity(new Vec2f(player.getDirX() * 0.5f, 0));
					s.setSize(s.getSize()-1);
					if (s.getSize() <= 0) {
						this.hotbar.getItems().get(0)[selectedSlot] = null;
					}
			} else {
				EntityItem item = new EntityItem(s.copy().setSize(1), player.getWorld(), player.getPosition().x, player.getPosition().y+1, GameController.nextEntityId());
				Scheduler.getScheduler().addEntityToWorld(item);
				item.setVelocity(new Vec2f(player.getDirX() * 0.5f, 0));
				s.setSize(s.getSize()-1);
				if (s.getSize() <= 0) {
					this.hotbar.getItems().get(0)[selectedSlot] = null;
				}
			}
		}
	}

	@Override
	public boolean mouseDown(MouseEvent e) {
		return true;
	}

	@Override
	public boolean mouseUp(MouseEvent e) {
		for (int i = 0; i < inventoryButtons.size(); i++) {
			for (int j = 0; j < inventoryButtons.get(i).size(); j++) {
				GUIButton b = inventoryButtons.get(i).get(j);
				if (b.click(e)) {
					ItemStack stack = activeInventory.getItems().get(i)[j];
					if (cursorItemStack != null && cursorItemStack.equals(stack)) {
						int maxStackSize;
						if (cursorItemStack.getClass().equals(BlockItemStack.class)) {
							BlockItem item = Register.getBlock(cursorItemStack.getItem()).getBlockItem();
							maxStackSize = item.getMaxStackSize();
						} else {
							Item item = Register.getItem(cursorItemStack.getItem());
							maxStackSize = item.getMaxStackSize();
						}
						if (cursorItemStack.getSize()+stack.getSize() > maxStackSize) {
							int prevStackSize = stack.getSize();
							stack.setSize(maxStackSize);
							cursorItemStack.setSize((cursorItemStack.getSize()+prevStackSize)-maxStackSize);
						} else {
							stack.setSize(stack.getSize()+cursorItemStack.getSize());
							cursorItemStack = null;
						}
					} else {
						ItemStack placeholder = cursorItemStack;
						cursorItemStack = activeInventory.getItems().get(i)[j];
						activeInventory.getItems().get(i)[j] = placeholder;
					}
					return true;
				}
			}
		}
		for (int i = 0; i < hotbarButtons.size(); i++) {
			for (int j = 0; j < hotbarButtons.get(i).size(); j++) {
				GUIButton b = hotbarButtons.get(i).get(j);
				if (b.click(e)) {
					ItemStack stack = hotbar.getItems().get(i)[j];
					if (cursorItemStack != null && cursorItemStack.equals(stack)) {
						int maxStackSize;
						if (cursorItemStack.getClass().equals(BlockItemStack.class)) {
							BlockItem item = Register.getBlock(cursorItemStack.getItem()).getBlockItem();
							maxStackSize = item.getMaxStackSize();
						} else {
							Item item = Register.getItem(cursorItemStack.getItem());
							maxStackSize = item.getMaxStackSize();
						}
						if (cursorItemStack.getSize()+stack.getSize() > maxStackSize) {
							int prevStackSize = stack.getSize();
							stack.setSize(maxStackSize);
							cursorItemStack.setSize((cursorItemStack.getSize()+prevStackSize)-maxStackSize);
						} else {
							stack.setSize(stack.getSize()+cursorItemStack.getSize());
							cursorItemStack = null;
						}
					} else {
						ItemStack placeholder = cursorItemStack;
						cursorItemStack = hotbar.getItems().get(i)[j];
						hotbar.getItems().get(i)[j] = placeholder;
					}
					return true;
				}
			}
		}
		
		if (tab1Button.click(e)) {
			activeInventory = itemInventory;
			return true;
		}
		
		if (tab2Button.click(e)) {
			activeInventory = blockInventory;
			return true;
		}
		if (cursorItemStack != null) {
			dropItem(cursorItemStack);
			cursorItemStack = null;
			return true;
		}
		return true;
	}

	@Override
	public boolean keyDown(KeyEvent e) {
		
		return false;
	}

	@Override
	public boolean keyUp(KeyEvent e) {
		
		return false;
	}

	@Override
	public boolean mouseMove(MouseEvent e) {
		
		return false;
	}
	
	@Override
	public boolean mouseDrag(MouseEvent e) {
		
		return false;
	}
	
	@Override
	public boolean mouseClick(MouseEvent e) {  
		return false;
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	
}
