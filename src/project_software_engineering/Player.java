package project_software_engineering;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import resources.*;
import utils.RESOURCES;

public class Player {
	private boolean hungerlevel; 
	private boolean warmthlevel;
	private boolean restLevel;

	private String name;
	private Room currentRoom;
	private RESOURCES.Status status;

	private List<Item> inventory;

	private List<Runnable> roomListeners = new ArrayList<>();
	private List<Runnable> inventoryListeners = new ArrayList<>();

	public Player() {
		this.name = "TestPlayer";
		this.currentRoom = new Room("Cell2", null);
		this.hungerlevel = true;
		this.warmthlevel = true;
		this.restLevel = true;
		this.status = RESOURCES.Status.WAKEUP;
		this.inventory = new ArrayList<Item>();

	}

	public Player(String name) {
		this.name = name;
		this.currentRoom = InstantiateResources.rooms.get(0);
		this.hungerlevel = true;
		this.warmthlevel = true;
		this.restLevel = true;
		this.status = RESOURCES.Status.WAKEUP;
		this.inventory = new ArrayList<Item>();
	}

	public RESOURCES.Status getStatus() {
		return status;
	}

	public void setStatus(RESOURCES.Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Player [hungerlevel=" + hungerlevel + ", warmthlevel=" + warmthlevel + ", restLevel=" + restLevel
				+ ", name=" + name + "]";
	}

	public boolean getHungerlevel() {
		return hungerlevel;
	}

	public void setHungerlevel(boolean hungerlevel) {
		this.hungerlevel = hungerlevel;
	}

	public boolean getWarmthlevel() {
		return warmthlevel;
	}

	public void setWarmthlevel(boolean warmthlevel) {
		this.warmthlevel = warmthlevel;
	}

	public boolean getRestLevel() {
		return restLevel;
	}

	public void setRestLevel(boolean restLevel) {
		this.restLevel = restLevel;
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
		inventory.add(item);
		notifyInventoryChange();  // Notify GUI (or any other subscriber)
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



}
