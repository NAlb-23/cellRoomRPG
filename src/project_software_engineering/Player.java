package project_software_engineering;

import resources.Room;
import utils.RESOURCES;

public class Player {
	private boolean hungerlevel; 
	private boolean warmthlevel;
	private boolean restLevel;
	
	private String name;
	private Room currentRoom;
	private RESOURCES.Status status;
	
	public Player() {
		this.name = "TestPlayer";
		this.currentRoom = new Room("Cell 2", "NULL");
		this.hungerlevel = true;
		this.warmthlevel = true;
		this.restLevel = true;
		this.status = RESOURCES.Status.WAKEUP;
		
	}
	
	public Player(String name) {
		this.name = name;
		this.currentRoom = new Room("Cell 2", "NULL");
		this.hungerlevel = true;
		this.warmthlevel = true;
		this.restLevel = true;
		this.status = RESOURCES.Status.WAKEUP;
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

	
}
