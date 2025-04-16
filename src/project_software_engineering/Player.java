package project_software_engineering;

import java.util.ArrayList;
import java.util.List;

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
	
	public Player() {
		this.name = "TestPlayer";
		this.currentRoom = new Room("Cell2", null);
		this.hungerlevel = true;
		this.warmthlevel = true;
		this.restLevel = true;
		this.status = RESOURCES.Status.WAKEUP;
		this.inventory = new ArrayList();
		
	}
	
	public Player(String name) {
		this.name = name;
		this.currentRoom = InstantiateResources.rooms.get(0);
		this.hungerlevel = true;
		this.warmthlevel = true;
		this.restLevel = true;
		this.status = RESOURCES.Status.WAKEUP;
		this.inventory = new ArrayList();
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
	}

	public void addItem(Item item) {
        inventory.add(item);
        System.out.println(item + " added to inventory.");
    }

    public void removeItem(Item item) {
        if (inventory.remove(item)) {
            System.out.println(item + " removed from inventory.");
        } else {
            System.out.println(item + " was not found in inventory.");
        }
    }

    public void showInventory() {
        System.out.println(name + "'s Inventory: " + inventory);
    }
}
