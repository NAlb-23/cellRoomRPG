package resources;

import java.util.ArrayList;
import java.util.List;

public class InstantiateResources {

	public static List<Room> rooms = new ArrayList<>();

	public static void loadResources() {
		setupRooms();
	}

	private static void setupRooms() {

		// === CELL 2 ===
		Room cell2 = new Room("Cell 2",
				"A faint torch flickers outside, barely illuminating your stone-walled, windowless cell. " +
				"The heavy door and rusted bars stand between you and the hallway.");

		POI door = new POI("Door", "A sturdy metal door. The only exit from this cell.");
		door.addInteraction("examine", "A latch and keyhole are visible on the outer side.");
		door.addInteraction("use key", "You unlock the door. Freedom is one step closer. \n you can try to {Open} [Door] now");

		POI floor = new POI("Floor", "Cold stone floor, rough and cracked.");
		Item rag = new Item("Rag", "A filthy cloth, possibly useful for handling sharp objects.");
		rag.setHint("Could be wrapped around something dangerous.");
		floor.addItem(rag);
		floor.addInteraction("examine", "A discarded [Rag] lies here.");

		POI wall = new POI("Wall", "Solid stone with one section looking slightly off.");
		Item thinPick = new Item("Thin Metal Pick", "A sharp sliver of metal — useful for picking locks.");
		Item looseStone = new Item("Stone", "A fist-sized stone with enough heft to smash things.");
		wall.addInteraction("examine", "A loose stone catches your attention.");
		wall.addInteraction("pry with tool", "You pry the stone loose, revealing a [Thin Metal Pick] and [Heavy Stone].");
		wall.addItem(thinPick);
		wall.addItem(looseStone);

		POI bars = new POI("Bars", "Rusted iron bars line the wall, one plate seems loose.");
		Item plate = new Item("Rusted Plate", "A sharp-edged piece of metal dislodged from the bars.");
		bars.addInteraction("examine", "You notice a loose [Rusted Plate] pressed into the floor.");
		bars.addInteraction("use rag on plate", "You safely extract the [Rusted Plate] using the rag, fashioning a crude [Tool].");
		bars.addItem(plate);

		POI drawer = new POI("Drawer", "A broken wooden drawer, stuck tight.");
		drawer.addInteraction("examine", "The drawer seems stuck, but something's inside.");
		drawer.addInteraction("use stone", "After two solid strikes, the drawer gives way, revealing a [Locked Box]. However, the stone crumbles apart as well.");
	
		POI box = new POI("Locked Box", "A simple locked box.");
		Item key = new Item("Key", "A small iron key, likely for a door.");
		box.addInteraction("examine", "The lock seems simple, a thin pick could open it.");
		box.addInteraction("use thin metal pick", "You pick the lock and retrieve a [Key]. The metal pick got wedged in the box, you can't seem to take it out anymore.");
		box.addItem(key);
		box.setIsHidden(true);
		box.setLocked(true);

		cell2.addPOI(door);
		cell2.addPOI(floor);
		cell2.addPOI(wall);
		cell2.addPOI(bars);
		cell2.addPOI(drawer);
		cell2.addPOI(box);


		// === CELL 1 ===
		Room cell1 = new Room("Cell 1", "A plain, dark cell with almost nothing in it except a lone bucket.");
		
		POI cell1Door = new POI("Door", "A heavy, locked door.");
		cell1Door.addInteraction("examine", "the door leads back to the hallway.");
		cell1Door.setLocked(false);
		
		POI cell1Wall = new POI("Wall", "Nothing interesting here");
		cell1Wall.addInteraction("examine", "Nothing intresting here.");

		POI cell1Floor = new POI("Floor", "Nothing interesting here");
		cell1Floor.addInteraction("examine", "Nothing intresting here.");
		
		
		POI bucket = new POI("Bucket", "A grimy old bucket.");
		Item bucketItem = new Item("Bucket", "A nasty old bucket, still somewhat useful.");
		bucket.addInteraction("examine", "You peer inside, finding a revolting mess.");
		bucket.addInteraction("dump", "You empty the bucket. Nothing useful remains.");
		bucket.addInteraction("take", "Bucket added to inventory.");
		bucket.addItem(bucketItem);
		
		POI revoltingMess = new POI("Mess", "revolting mess that smells really bad.");
		revoltingMess.setIsHidden(true);
		revoltingMess.addInteraction("take", "Its a gross pool of liquid, you try scooping it up in your hands, its revolting. you come to your senses and move on..");
		revoltingMess.addInteraction("Examine", "You take a moment to prepare yourself and begin searching through the mess. After what feels like an eternity, you find nothing.");
		
		cell1.addPOI(cell1Door);
		cell1.addPOI(cell1Wall);
		cell1.addPOI(cell1Floor);
		cell1.addPOI(bucket);
		cell1.addPOI(revoltingMess);
		


		// === CELL 3 ===
		Room cell3 = new Room("Cell 3", "You find this cell to be somewhat furnished. There is a small {window} on the wall. In the corner is an old {bed}. The {walls} seem to have some sort of etchings on them and the {floor} littered with debris. \n");

		POI cell3Door = new POI("Door", "A locked metal door.");
		cell3Door.addInteraction("examine", "The door is locked. you can't open it.");

		POI window = new POI("Window", "You approach and examine the window. It’s located on the upper part of the wall, just past your reach. The window is sloped upwards, and you can see the night sky beyond it. A crescent moon and stars light up the sky. Besides crickets and the occasional breeze, you hear nothing from the outside. The window has vertical, metal bars preventing escape. You notice that the stone on the lower part of the window where the bars meet the wall show signs of damage and erosion.\n");
		window.addInteraction("examine", window.getDescription());
		window.addInteraction("use bar pole", "Using the long metal bar, you begin to work away the stone. It’s difficult and slow with dust and chips flying everywhere. You keep working away on it, eventually becoming drenched in sweat and exhausted. You don’t know how long it takes, but enough stone has been removed to expose the {window}’s metal bars. You believe you may be able to [pull] {bars}.\n");;
		window.addInteraction("bring chair", "With the chair in place, you can reach the opening.");
		window.addInteraction("use stone", "Smart thinking - you throw the stone aiming at the loose lower part of the window, you miss and the stone gets tossed outside.");
		window.setLocked(true);
		
		POI windowBars = new POI("Bars", "");
		windowBars.addInteraction("pull x1", "You take a moment to catch your breath and regain whatever energy you can. You wrap your hands around the bars and begin pulling. The stone begins to crack, and the bars begin to budge, ever so slightly. You will likely have to [pull] {bars} a few more times");
		windowBars.addInteraction("pull x2", "You drag yourself back to the window and take a deep breath. Once again, you pull the bars with everything you can. They move, but still find themselves attached to the wall. You think one more attempt should be enough");
		windowBars.addInteraction("pull x3", "You muster every bit of strength and energy possible and pull on the bars. This time, the bars do come loose from the wall. Both yourself and the bars fall to the ground. You find yourself exhausted. You might be able to escape through the window, you just need a long rope!");
		windowBars.setIsHidden(true);
		windowBars.setLocked(true);
		
		POI carvings = new POI("Wall", "Faded and unintelligible symbols cover the wall.");
		carvings.addInteraction("examine", "You approach the walls to better see the carvings. The carvings seem to be in a language you do not understand. They are scattered all across the walls.\n");

		POI cell3floor = new POI("floor", "Dirty and has shreds of cloth and debris, nothing useful for the player.\n");
		cell3floor.addInteraction("examine", "You kneel down and see if there is anything of use on the floor. However, all you can find are dust, scraps of cloth, and small chips of either stone or wood. There is nothing of value on the floor.\n");
		Item debris = new Item("debris", "flamable material");
		cell3floor.addItem(debris);
		
		POI bed = new POI("Bed", "An old bed with a worn-out mattress.");
		bed.addInteraction("examine", "You approach the old and dirty bed. A torn and hole-filled [blanket] sits on top of a dirty mattress. The mattress looks lumpy, as though hiding something.");
		bed.addInteraction("take blanket", "blanket added to inventory");
		Item blanket = new Item("Blanket", "Torn and filled with holes.");
		bed.addItem(blanket);
		
		POI mattress = new POI("Mattress", "The mattress fabric is patched and rough.");
		Item rope = new Item("Rope", "A knotted rope made from strips of fabric. You can make it longer by combining it with somethig");
		mattress.addInteraction("examine", "One patch looks freshly sewn. you notice that you could rip the stiches if you have the right tool.");
		mattress.addInteraction("use tool", "You cut open the patch and pull out a [Rope].");
		mattress.addItem(rope);
		
		cell3.setLocked(false);

		cell3.addPOI(cell3Door);
		cell3.addPOI(window);
		cell3.addPOI(windowBars);
		cell3.addPOI(carvings);
		cell3.addPOI(cell3floor);
		cell3.addPOI(bed);
		cell3.addPOI(mattress);


		// === GUARD ROOM ===
		Room guardRoom = new Room("Guard Room", " At the end is a large, metal {door}. One side of the room has a simple {table} and a chair. The other side has some {shelves} and a {barrel}.\n");

		guardRoom.setLocked(false);
		
		POI heavyDoor = new POI("Heavy Door", "A reinforced door, locked from the other side.");
		heavyDoor.addInteraction("examine", "Too solid to break through without heavy equipment.");
		
		POI entrenceDoor = new POI("Entrence Door", "Leads back to hallway");
		entrenceDoor.setLocked(false);

		POI table = new POI("Table", "A sturdy wooden table with some items on it.");
		Item matchbox = new Item("Matchbox", "A small box containing matches.");
		table.addItem(matchbox);
		table.addInteraction("examine", "The table has a small candle holder with some melted wax on the bottom. A [matchbox] lay near the spent candle. There is nothing else.\n");

		POI chair = new POI("Chair", "A heavy wooden chair.");
		chair.addInteraction("examine", "Sturdy enough to stand on or move.");

		POI shelves = new POI("Shelves", "Dusty wooden shelves lined with old supplies.");
		Item hardtack = new Item("Hardtack", "A rock-hard survival biscuit.");
		hardtack.setType("Dried Food");
		
		Item jerky = new Item("Dried Jerky", "Dry, tough strips of preserved meat.");
		jerky.setType("Dried Food");

		shelves.addItem(hardtack);
		shelves.addItem(jerky);
		shelves.addInteraction("examine", "You find various parchments, papers, and writing tools. However, you cannot make out what is written on them. At the bottom shelves, hidden behind a book are some [hardtack] and [Dried Jerky].\n");

		POI barrel = new POI("Barrel", "A large wooden barrel filled with water.");
		barrel.addInteraction("open with tool", "Using your tool on the barrel, you manage to pry open the lid. You find it full of a clear, odorless liquid. Floating on top is a wooden cup. You think it likely contains water and should be able to [drink] from the {barrel}. \n");
		barrel.addInteraction("drink", "You quench your thirst.");
		barrel.addInteraction("soak food", "You soften the food to make them edible.");
		barrel.addInteraction("examine", "The [barrel] has a lid securing its contents, you need something to pry it open. You try to move it, but it does not move. You hear a swishing sound, indicating that it is full of some liquid. \n");
		barrel.setLocked(true);
		
		guardRoom.addPOI(heavyDoor);
		guardRoom.addPOI(entrenceDoor);
		guardRoom.addPOI(table);
		guardRoom.addPOI(chair);
		guardRoom.addPOI(shelves);
		guardRoom.addPOI(barrel);


		// === HALLWAY ===
		Room hallway = new Room("Hallway", 
				"A stone corridor dimly lit by torches, lined with cell doors and iron bars.\nThe hallway extends both ways, ending at the Guard Room in one direction and a dead-end in the other."
				);

		// Create POI for torches
		POI torches = new POI("Torches", "Simple wall-mounted torches flicker in their sconces.");
		torches.addInteraction("examine", "They light your path but are fixed to the wall.");
		torches.addInteraction("take", "torches are fixed to the wall, you can't take them.");

		// Create POI for cell doors (Each door as a separate POI)
		POI hallwaycellDoor1 = new POI("Cell 1 Door", "A metal door leading to Cell 1.");
		hallwaycellDoor1.addInteraction("examine", "This door is locked. You cannot open it without a key.");
		hallwaycellDoor1.addInteraction("use key1", "The door unlocks!  It takes more work than the door from cell 2, but you manage to open the door. However, upon trying to remove the key, it snaps. You’re left with the loop half while the other half is now stuck inside the door.\n");
		hallwaycellDoor1.addInteraction("Enter Cell 1", "You step inside Cell 1, it’s dimly lit and empty.");
		Item keyLoop = new Item("Metal key loop", "Broken half of the key");
		hallwaycellDoor1.addItem(keyLoop);
		
		POI hallwaycellDoor2 = new POI("Cell 2 Door", "A metal door leading to Cell 2.");
		hallwaycellDoor2.setLocked(false);
		hallwaycellDoor2.addInteraction("examine", "This door is open, you can easily enter.");
		hallwaycellDoor2.addInteraction("Enter Cell 2", "You open the door and step into Cell 2. It's eerily quiet.");

		POI hallwaycellDoor3 = new POI("Cell 3 Door", "A metal door leading to Cell 3. It remains locked.");
		hallwaycellDoor3.addInteraction("examine", "This door is locked. You cannot open it without a key.");
		hallwaycellDoor3.addInteraction("use key1", "you try to open it, but they key doesn't work.");
		hallwaycellDoor3.addInteraction("Enter Cell 3", "The door is locked. You cannot enter.");

		
		POI guardRoomDoor = new POI("Guard Room Door", "The door is open, you can enter");
		guardRoomDoor.setLocked(false);
		guardRoomDoor.addInteraction("Enter", "You walk into the guards room");
		guardRoomDoor.addInteraction("examine", "The door is slightly adjar, it can easily be pushed open");
		
		POI cell3Bars = new POI("cell 3 Bars", "Rusted bars facing the hallway.");
		Item barPole = new Item("Bar Pole", "A bent metal pole, snapped from the bars.");
		cell3Bars.addInteraction("examine", "One section is bent enough to pry loose a [Bar Pole], the section becomes big enough to fit through.");
		cell3Bars.addItem(barPole);

		// Add all POIs to the hallway
		hallway.addPOI(torches);
		hallway.addPOI(hallwaycellDoor1);
		hallway.addPOI(hallwaycellDoor2);
		hallway.addPOI(hallwaycellDoor3);
		hallway.addPOI(guardRoomDoor);
		hallway.addPOI(cell3Bars);

		// === Final Room List ===
		cell2.setId(0);
		cell1.setId(1);
		cell3.setId(2);
		guardRoom.setId(3);
		hallway.setId(4);
		
		rooms.add(cell2);
		rooms.add(cell1);
		rooms.add(cell3);
		rooms.add(guardRoom);
		rooms.add(hallway);
		
		door.setLeadsTo(hallway);
		cell1Door.setLeadsTo(hallway);
		cell3Door.setLeadsTo(hallway);
		heavyDoor.setLeadsTo(guardRoom);
		hallwaycellDoor1.setLeadsTo(cell1);
		hallwaycellDoor2.setLeadsTo(cell2);
		hallwaycellDoor3.setLeadsTo(cell3);

	
	}

	public static Room findRoomByName(String value) {
		for(Room rm: rooms) {
			if (rm.getName().equalsIgnoreCase(value))
				return rm;
		}
		return null;
	}

	public static Item findItemByName(String itemName) {
		if (itemName == null || rooms == null) return null;
		for (Room rm : rooms) {
			Item targetItem = rm.getItemByName(itemName);
			if (targetItem != null) {
				return targetItem;
			}
		}
		return null;
	}

}
