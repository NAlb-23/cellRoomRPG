package resources;

import java.util.ArrayList;
import java.util.List;

public class instantaite_resources {
	public static List<Room> rooms = new ArrayList<Room>(); 
	public static List<Item> items = new ArrayList<>();

	public static void load_resources() {
		setupRooms();
		setupItems();

	}

	private static void setupRooms() {
		// Create rooms
		Room cell2 = new Room("Cell 2", "A torch is placed on the wall outside and across your cell. \nFrom the flickering light, you find yourself in a windowless cell. \nAn old, wooden table sits at the corner. The walls are made of solid stone. \nStanding between you and the hallway beyond is a set of metal bars and a metal door.");
		Room cell1 = new Room("Cell 1", "Another small prison cell, empty except for an old bucket.");
		Room cell3 = new Room("Cell 3", "A slightly furnished prison cell with a window.");
		Room hallway = new Room("Hallway", "You [enter] the {hallway} and [look around]. There are other [torches] placed on the walls illuminating the {hallway}. \nOn one side of the hallway is a deadend while the other side leads to another {room}. \nThe door of the cell you came from is labeled {Cell 2}. \nThere are two other cells on either side of your cell. \nEach of them with a metal door of their own inscribed with {cell 1} and {cell 2}. \nSurrounding the doors are {cell 1 bars} and {cell 2 bars}.");
		Room guardRoom = new Room("Guard Room", "A simple room with a table, shelves, and a locked exit door.");

		// Add rooms to the list
		rooms.add(cell2);
		rooms.add(cell1);
		rooms.add(cell3);
		rooms.add(hallway);
		rooms.add(guardRoom);
	}

	private static void setupItems() {

		// Create items based on the narrative
		Item rag = new Item("rag", "A rag that served as your mat.");
		rag.setHint("Can be used to wrap around objects for protection.");
		items.add(rag);

		Item plate = new Item("plate", "A loose metal plate holding the bars to the floor.");
		plate.setHint("Can be picked up if you use something to hold it.");
		items.add(plate);

		Item tool = new Item("tool", "A makeshift tool to pry open objects.");
		tool.setHint("Use it to pry open stones or help with other tasks.");
		items.add(tool);

		Item thinMetal = new Item("thin metal", "A thin, sturdy piece of metal found behind a loose stone.");
		thinMetal.setHint("Can be used to unlock certain things.");
		items.add(thinMetal);

		Item woodenPieces = new Item("wooden pieces", "The remains of a broken table.");
		woodenPieces.setHint("Can be used for various tasks.");
		items.add(woodenPieces);

		Item key = new Item("key", "A key that unlocks doors.");
		key.setHint("Used to unlock the door to Cell 1.");
		items.add(key);

		Item bucket = new Item("bucket", "A bucket found in the corner of a cell.");
		bucket.setHint("Can be used to collect things.");
		items.add(bucket);

		Item metalBar = new Item("metal bar", "A long, bent metal bar.");
		metalBar.setHint("Can be used to pry open windows or other items.");
		items.add(metalBar);

		Item matchbox = new Item("matchbox", "A matchbox found near a candle.");
		matchbox.setHint("Can be used to light fires.");
		items.add(matchbox);

		Item hardtack = new Item("hardtack", "A tough, dry biscuit.");
		hardtack.setHint("Needs water to soften before you can eat it.");
		items.add(hardtack);

		Item driedMeats = new Item("dried meats", "Tough, dried pieces of meat.");
		driedMeats.setHint("Needs water to soften before you can eat it.");
		items.add(driedMeats);

		Item rope = new Item("rope", "A makeshift rope made by knotting cloth together.");
		rope.setHint("Used for climbing or securing things.");
		items.add(rope);

		Item blanket = new Item("blanket", "A tattered blanket found on an old bed.");
		blanket.setHint("Can be used for warmth or as a rope extension.");
		items.add(blanket);
	}

	public static void addItemsToRooms() {
		List<Item> roomItems = new ArrayList<>();
		roomItems.add(items.get(0)); //add rag to cell2
		
	}

}
