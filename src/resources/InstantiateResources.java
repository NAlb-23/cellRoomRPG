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
        door.addInteraction("examine", "Too dark to see details from inside.");
        door.addInteraction("examine exterior", "A latch and keyhole are visible on the outer side.");
        door.addInteraction("use key", "You unlock the door. Freedom is one step closer.");

        POI floor = new POI("Floor", "Cold stone floor, rough and cracked.");
        Item rag = new Item("Rag", "A filthy cloth, possibly useful for handling sharp objects.");
        rag.setHint("Could be wrapped around something dangerous.");
        floor.addItem(rag);
        floor.addInteraction("examine", "A discarded [Rag] lies here.");
        floor.addInteraction("take", "Rag added to your inventory.");

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
        Item lockedBox = new Item("Locked Box", "A small locked container.");
        drawer.addInteraction("examine", "The drawer seems stuck, but something's inside.");
        drawer.addInteraction("use stone", "After two solid strikes, the drawer gives way, revealing a [Locked Box]. However, the stone crumbles apart as well.");
        drawer.addItem(lockedBox);

        POI box = new POI("Locked Box", "A simple locked box.");
        Item key = new Item("Key", "A small iron key, likely for a door.");
        box.addInteraction("examine", "The lock seems simple, a thin pick could open it.");
        box.addInteraction("use thin metal pick", "You pick the lock and retrieve a [Key].");
        box.addItem(key);
        box.setIsHidden(true);

        cell2.addPOI(door);
        cell2.addPOI(floor);
        cell2.addPOI(wall);
        cell2.addPOI(bars);
        cell2.addPOI(drawer);
        cell2.addPOI(box);


        // === CELL 1 ===
        Room cell1 = new Room("Cell 1", "A plain, dark cell with almost nothing in it except a lone bucket.");

        POI cell1Door = new POI("Door", "A heavy, locked door.");
        cell1Door.addInteraction("examine", "Locked tight. No way to open from this side.");

        POI bucket = new POI("Bucket", "A grimy old bucket.");
        Item bucketItem = new Item("Bucket", "A nasty old bucket, still somewhat useful.");
        bucket.addInteraction("examine", "You peer inside, finding a revolting mess.");
        bucket.addInteraction("dump contents", "You empty the bucket. Nothing useful remains.");
        bucket.addInteraction("take", "Bucket added to inventory.");
        bucket.addItem(bucketItem);

        cell1.addPOI(cell1Door);
        cell1.addPOI(bucket);


        // === CELL 3 ===
        Room cell3 = new Room("Cell 3", "A stone cell with a view — a barred window offers the faintest glimpse of outside.");

        POI cell3Door = new POI("Door", "A locked metal door.");
        cell3Door.addInteraction("examine", "The lock is different from Cell 2's. Your current key won't fit.");

        POI cell3Bars = new POI("Bars", "Rusted bars facing the hallway.");
        Item barPole = new Item("Bar Pole", "A bent metal pole, snapped from the bars.");
        cell3Bars.addInteraction("examine", "One section is bent enough to pry loose a [Bar Pole].");
        cell3Bars.addItem(barPole);

        POI window = new POI("Window", "A barred window looking outside.");
        window.addInteraction("examine", "You notice the bars look worn.");
        window.addInteraction("use bar pole x4", "After repeated strikes, the bars snap away, leaving space to escape.");
        window.addInteraction("bring chair", "With the chair in place, you can reach the opening.");

        POI carvings = new POI("Wall Carvings", "Faded and unintelligible symbols cover the wall.");
        carvings.addInteraction("examine", "The symbols seem ancient, offering no clear clues.");

        POI bed = new POI("Bed", "An old bed with a worn-out mattress.");
        bed.addInteraction("examine", "The mattress looks lumpy, as though hiding something.");

        POI mattress = new POI("Mattress", "The mattress fabric is patched and rough.");
        Item rope = new Item("Rope", "A knotted rope made from strips of fabric.");
        mattress.addInteraction("examine", "One patch looks freshly sewn.");
        mattress.addInteraction("use tool", "You cut open the patch and pull out a [Rope].");
        mattress.addItem(rope);

        cell3.addPOI(cell3Door);
        cell3.addPOI(cell3Bars);
        cell3.addPOI(window);
        cell3.addPOI(carvings);
        cell3.addPOI(bed);
        cell3.addPOI(mattress);


        // === GUARD ROOM ===
        Room guardRoom = new Room("Guard Room", "A dimly lit guard room with scattered objects and heavy furniture.");

        POI heavyDoor = new POI("Heavy Door", "A reinforced door, locked from the other side.");
        heavyDoor.addInteraction("examine", "Too solid to break through without heavy equipment.");

        POI table = new POI("Table", "A sturdy wooden table with some items on it.");
        Item matchbox = new Item("Matchbox", "A small box containing matches.");
        table.addItem(matchbox);
        table.addInteraction("examine", "There's a [Matchbox] resting on the table.");

        POI chair = new POI("Chair", "A heavy wooden chair.");
        chair.addInteraction("examine", "Sturdy enough to stand on or move.");

        POI shelves = new POI("Shelves", "Dusty wooden shelves lined with old supplies.");
        Item hardtack = new Item("Hardtack", "A rock-hard survival biscuit.");
        Item jerky = new Item("Dried Jerky", "Dry, tough strips of preserved meat.");
        shelves.addItem(hardtack);
        shelves.addItem(jerky);
        shelves.addInteraction("examine", "You find [Hardtack] and [Dried Jerky] on the shelves.");

        POI barrel = new POI("Barrel", "A large wooden barrel filled with water.");
        barrel.addInteraction("open with tool", "You pry open the barrel lid.");
        barrel.addInteraction("drink", "You quench your thirst.");
        barrel.addInteraction("soak food", "You soften the [Hardtack] and [Dried Jerky] to make them edible.");

        guardRoom.addPOI(heavyDoor);
        guardRoom.addPOI(table);
        guardRoom.addPOI(chair);
        guardRoom.addPOI(shelves);
        guardRoom.addPOI(barrel);


        // === HALLWAY ===
        Room hallway = new Room("Hallway", "A stone corridor dimly lit by torches, lined with cell doors and iron bars.");

        POI torches = new POI("Torches", "Simple wall-mounted torches flicker in their sconces.");
        torches.addInteraction("examine", "They light your path but are fixed to the wall.");

        POI cellDoors = new POI("Cell Doors", "Metal doors leading to the cells.");
        cellDoors.addInteraction("examine", "Each cell is marked. Cell 1 is open, Cell 2 is now unlocked, Cell 3 remains locked.");
        cellDoors.addInteraction("Enter Cell1", "");
        
        POI cellBars = new POI("Cell Bars", "The hallway side of each cell's iron bars.");
        cellBars.addInteraction("examine", "Cell 3 has a noticeable gap revealing a [Bar Pole] inside.");

        POI general = new POI("General", "A long stretch of dark, damp stone.");
        general.addInteraction("look around", "The hallway extends both ways, ending at the Guard Room in one direction and a dead-end in the other.");

        hallway.addPOI(torches);
        hallway.addPOI(cellDoors);
        hallway.addPOI(cellBars);
        hallway.addPOI(general);


        // === Final Room List ===
        rooms.add(cell2);
        door.setLeadsTo(hallway);
        rooms.add(cell1);
        cell1Door.setLeadsTo(cell1);
        rooms.add(cell3);
        cell3Door.setLeadsTo(cell3);
        rooms.add(guardRoom);
        heavyDoor.setLeadsTo(guardRoom);
        rooms.add(hallway);
    }

	public static Room findRoomByName(String value) {
		for(Room rm: rooms) {
			if (rm.getName().equals(value))
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
