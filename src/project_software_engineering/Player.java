package project_software_engineering;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import resources.*;
import utils.RESOURCES;

public class Player {

	private String name;
	private Room currentRoom;
	private RESOURCES.Status status;

	private List<Item> inventory;

	private List<Runnable> roomListeners = new ArrayList<>();
	private List<Runnable> inventoryListeners = new ArrayList<>();

	private PlayerNeeds needs = new PlayerNeeds();

	public Player() {
		this.name = "TestPlayer";
		this.currentRoom = new Room("Cell2", null);
		this.status = RESOURCES.Status.WAKEUP;
		this.inventory = new ArrayList<Item>();

	}

	public Player(String name) {
		this.name = name;
		this.currentRoom = InstantiateResources.rooms.get(0);
		this.status = RESOURCES.Status.WAKEUP;
		this.inventory = new ArrayList<Item>();
	}

	public RESOURCES.Status getStatus() {
		return status;
	}

	public void setStatus(RESOURCES.Status status) {
		this.status = status;
	}

	public int getHungerLevel() {
		return needs.getHunger();  // Delegate to PlayerNeeds
	}

	public int getWarmthLevel() {
		return needs.getWarmth();  // Delegate to PlayerNeeds
	}

	public int getRestLevel() {
		return needs.getEnergy();  // Delegate to PlayerNeeds
	}

	public int getThirstLevel() {
		return needs.getThirst();  // Delegate to PlayerNeeds
	}

	public void setHungerLevel(int hungerLevel) {
		needs.setHunger(hungerLevel);  // Delegate to PlayerNeeds
	}

	public void setWarmthLevel(int warmthLevel) {
		needs.setWarmth(warmthLevel);  // Delegate to PlayerNeeds
	}

	public void setRestLevel(int restLevel) {
		needs.setEnergy(restLevel);  // Delegate to PlayerNeeds
	}

	public void setThirstLevel(int thirstLevel) {
		needs.setThirst(thirstLevel);  // Delegate to PlayerNeeds
	}


	public List<Item> getInventory() {
		return inventory;
	}

	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
		notifyRoomChange();  // Notify subscribers when the room changes.
	}

	public void addItem(Item item) {
	    // Check if the item is already in the inventory
	    if (!inventory.contains(item)) {
	        inventory.add(item);
	        notifyInventoryChange();  // Notify GUI (or any other subscriber)
	    } else {
	        System.out.println(item.getName() + " is already in your inventory.");
	    }
	}


	public void removeItem(Item item) {
		if (inventory.remove(item)) {
			System.out.println(item + " removed from inventory.");
			notifyInventoryChange();  // notify here too!
		} else {
			System.out.println(item + " was not found in inventory.");
		}
	}


	public String showInventory() {
		StringBuilder sb = new StringBuilder();

		for(Item item: inventory) {
			sb.append(item.getName()+"\n");
		}
		return sb.toString();
	}

	public Item getInventoryItemByName(String itemName) {
		for(Item item: inventory) {
			if(item.getName().toLowerCase().equals(itemName.toLowerCase())) {
				return item;
			}
		}
		return null;
	}



	public void addInventoryListener(Runnable listener) {
		inventoryListeners.add(listener);
	}

	private void notifyInventoryChange() {
		for (Runnable listener : inventoryListeners) {
			listener.run();
		}
	}



	public void addRoomChangeListener(Runnable listener) {
		roomListeners.add(listener);
	}

	private void notifyRoomChange() {
		for (Runnable listener : roomListeners) {
			listener.run();
		}
	}

	public String getInventoryNames() {
		return inventory.stream()
				.map(Item::getName)
				.collect(Collectors.joining(","));
	}

	public void clearInventory() {
		this.inventory.clear();
	}

	public boolean hasItem(Item item) {
		return inventory.contains(item);
	}

	public void tick() {
		needs.tick();
	}

	public PlayerNeeds getNeeds() {
		return needs;
	}


	public String eatItem(Item item, int saturationValue) {
		if (!hasItem(item)) {
			return "You don't have that item in your inventory.";
		}

		if (item.getType().equalsIgnoreCase("FOOD")) {
			needs.eat(saturationValue);  // This would be implemented in PlayerNeeds
			removeItem(item);       // Removes the item from inventory after eating
			return "You eat the " + item.getName() + " and feel less hungry.";
		} else {
			return "That item isn't food.";
		}
	}

	public String drink(int satuartion) {

		needs.drink(satuartion);

		return "you're thirst is quinched";
	}

	public String rest() {
		needs.rest(100);  
		return "You rest for a while and feel more energized.";
	}



}
