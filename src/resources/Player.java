package resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import resources.*;
import utils.RESOURCES;

/**
 * Represents a player in the game world, maintaining inventory, current room,
 * survival needs, and status. Supports event listeners for room and inventory changes.
 */
public class Player {

	// === Player Attributes ===
	private String name;
	private Room currentRoom;
	private RESOURCES.Status status;
	private List<Item> inventory;

	// === Listeners ===
	private List<Runnable> roomListeners = new ArrayList<>();
	private List<Runnable> inventoryListeners = new ArrayList<>();
	private List<Runnable> tickListeners = new ArrayList<>();

	// === Player Survival Needs ===
	private PlayerNeeds needs = new PlayerNeeds();

	// === Constructors ===

	/**
	 * Default constructor initializing the player with test values.
	 */
	public Player() {
		this.name = "TestPlayer";
		this.currentRoom = new Room("Cell2", null);
		this.status = RESOURCES.Status.WAKEUP;
		this.inventory = new ArrayList<>();
	}

	/**
	 * Constructs a player with a given name and starting room.
	 * @param name the name of the player
	 */
	public Player(String name) {
		this.name = name;
		this.currentRoom = InstantiateResources.rooms.get(0);
		this.status = RESOURCES.Status.WAKEUP;
		this.inventory = new ArrayList<>();
	}

	// === Getters and Setters ===

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	/**
	 * Sets the player's current room and notifies room listeners.
	 * @param currentRoom the room to move the player to
	 */
	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
		notifyRoomChange();
	}

	public RESOURCES.Status getStatus() {
		return status;
	}

	public void setStatus(RESOURCES.Status status) {
		this.status = status;
	}

	public List<Item> getInventory() {
		return inventory;
	}

	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}

	// === Inventory Management ===

	/**
	 * Adds an item to the player's inventory if it's not already present.
	 * @param item the item to add
	 */
	public void addItem(Item item) {
		if (!inventory.contains(item)) {
			inventory.add(item);
			notifyInventoryChange();
		} else {
			System.out.println(item.getName() + " is already in your inventory.");
		}
	}

	/**
	 * Removes an item from the inventory.
	 * @param item the item to remove
	 */
	public void removeItem(Item item) {
		if (inventory.remove(item)) {
			System.out.println(item + " removed from inventory.");
			notifyInventoryChange();
		} else {
			System.out.println(item + " was not found in inventory.");
		}
	}

	/**
	 * Returns a string listing all inventory item names.
	 * @return formatted string of inventory items
	 */
	public String showInventory() {
		StringBuilder sb = new StringBuilder();
		for (Item item : inventory) {
			sb.append(item.getName()).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Searches for an item in the inventory by name.
	 * @param itemName name of the item
	 * @return the item if found, null otherwise
	 */
	public Item getInventoryItemByName(String itemName) {
		for (Item item : inventory) {
			if (item.getName().equalsIgnoreCase(itemName)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Clears the inventory.
	 */
	public void clearInventory() {
		this.inventory.clear();
	}

	/**
	 * Checks if the player has a specific item.
	 * @param item the item to check
	 * @return true if present, false otherwise
	 */
	public boolean hasItem(Item item) {
		return inventory.contains(item);
	}

	/**
	 * Returns comma-separated names of all items in the inventory.
	 * @return string of item names
	 */
	public String getInventoryNames() {
		return inventory.stream()
				.map(Item::getName)
				.collect(Collectors.joining(","));
	}

	// === Survival Needs Interface ===

	public int getHungerLevel() { return needs.getHunger(); }
	public double getWarmthLevel() { return needs.getWarmth(); }
	public int getRestLevel() { return needs.getEnergy(); }
	public int getThirstLevel() { return needs.getThirst(); }

	public void setHungerLevel(int level) { needs.setHunger(level); }
	public void setWarmthLevel(double warmth) { needs.setWarmth(warmth); }
	public void setRestLevel(int level) { needs.setEnergy(level); }
	public void setThirstLevel(int level) { needs.setThirst(level); }

	/**
	 * Advances game time and notifies tick listeners.
	 */
	public void tick() {
		needs.tick();
		notifyTickListeners();
	}

	public PlayerNeeds getNeeds() {
		return needs;
	}

	/**
	 * Eats a food item, increasing hunger level.
	 * @param item the item to eat
	 * @param saturationValue amount to restore
	 * @return result message
	 */
	public String eatItem(Item item, int saturationValue) {
		if (!hasItem(item)) return "You don't have that item in your inventory.";
		if (!"FOOD".equalsIgnoreCase(item.getType())) return "That item isn't food.";
		if ("Dried FOOD".equalsIgnoreCase(item.getType())) return "The food is too hard to eat, try soaking it first";
		needs.eat(saturationValue);
		removeItem(item);
		return "You eat the " + item.getName() + " and feel less hungry.";
	}

	/**
	 * Drinks water to restore thirst level.
	 * @param saturation amount to restore
	 * @return result message
	 */
	public String drink(int saturation) {
		needs.drink(saturation);
		return "Your thirst is quenched.";
	}

	/**
	 * Rest to regain energy.
	 * @return result message
	 */
	public String rest() {
		needs.rest(100);
		return "You rest for a while and feel more energized.";
	}

	/**
	 * Checks if player's survival needs are in a satisfactory state.
	 * @return true if all needs are sufficiently met
	 */
	public boolean isNeedsSatisfied() {
		return needs.getEnergy() >= 70 &&
			   needs.getHunger() >= 70 &&
			   needs.getThirst() == 100 &&
			   needs.getWarmth() >= 50;
	}

	// === Listener Management ===

	public void addInventoryListener(Runnable listener) {
		inventoryListeners.add(listener);
	}

	public void addRoomChangeListener(Runnable listener) {
		roomListeners.add(listener);
	}

	public void addTickListener(Runnable listener) {
		tickListeners.add(listener);
	}

	private void notifyInventoryChange() {
		inventoryListeners.forEach(Runnable::run);
	}

	private void notifyRoomChange() {
		roomListeners.forEach(Runnable::run);
	}

	private void notifyTickListeners() {
		tickListeners.forEach(Runnable::run);
	}
}
