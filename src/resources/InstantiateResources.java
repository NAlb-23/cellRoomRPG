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
                "A torch is placed on the wall outside and across your cell. From the flickering light, you " +
                        "find yourself in a windowless cell. An old, wooden table sits at the corner. The walls are " +
                        "made of solid stone. Standing between you and the hallway beyond is a set of metal bars and " +
                        "a metal door.");

        POI door = new POI("Door", "A sturdy metal door.");
        door.addInteraction("push", "The door doesn't budge.");
        door.addInteraction("examine", "Too dark, no visible mechanism.");
        door.addInteraction("examine exterior", "You find a latch and a keyhole.");

        POI floor = new POI("Floor", "The cold stone floor of the cell.");
        Item rag = new Item("Rag", "A rag that served as your mat.");
        rag.setHint("Can be used to wrap around objects for protection.");
        floor.addItem(rag);
        floor.addInteraction("examine", "You find a [Rag].");
        floor.addInteraction("take", "Rag added to inventory.");

        POI wall = new POI("Wall", "Solid stone walls.");
        Item looseStone = new Item("Loose Stone", "A stone that's slightly out of place.");
        wall.addItem(looseStone);
        wall.addInteraction("examine", "You spot a loose stone.");
        wall.addInteraction("pry", "You need a tool to extract it.");

        POI bars = new POI("Bars", "Rusted iron bars securing the cell.");
        Item plate = new Item("Rusted Plate", "A loose metal plate holding the bars to the floor.");
        bars.addItem(plate);
        bars.addInteraction("examine", "You discover a loose [Rusted Plate].");
        bars.addInteraction("pick up", "Too sharp to handle barehanded.");
        bars.addInteraction("use rag on plate", "You wrap the rag around and safely create a makeshift [Tool].");

        POI table = new POI("Table", "An old wooden table with stuck drawers.");
        Item lockedBox = new Item("Locked Box", "A box locked tight inside the table drawer.");
        table.addItem(lockedBox);
        table.addInteraction("examine", "Drawers seem stuck.");
        table.addInteraction("use stone on table", "You smash the table. A [Locked Box] is revealed.");

        POI box = new POI("Locked Box", "A sturdy locked container.");
        Item key = new Item("Key", "A key that unlocks doors.");
        box.addItem(key);
        box.addInteraction("use thin metal", "You successfully pick the lock and obtain a [Key].");

        cell2.addPOI(door);
        cell2.addPOI(floor);
        cell2.addPOI(wall);
        cell2.addPOI(bars);
        cell2.addPOI(table);
        cell2.addPOI(box);


        // === CELL 1 ===
        Room cell1 = new Room("Cell 1", "Another small prison cell, empty except for an old bucket.");

        POI cell1Door = new POI("Door", "A locked metal door.");
        cell1Door.addInteraction("examine", "Locked. A key might open it.");
        cell1Door.addInteraction("use key", "The key snaps inside the lock.");

        POI bucket = new POI("Bucket", "A filthy bucket sitting in the corner.");
        Item bucketItem = new Item("Bucket", "A disgusting old bucket.");
        bucket.addItem(bucketItem);
        bucket.addInteraction("examine", "Disgusting contents. Do you dare search?");
        bucket.addInteraction("examine after emptying", "No item found. Just a gross mess.");
        bucket.addInteraction("take", "Bucket added to inventory.");

        cell1.addPOI(cell1Door);
        cell1.addPOI(bucket);


        // === CELL 3 ===
        Room cell3 = new Room("Cell 3", "A slightly furnished prison cell with a window.");

        POI cell3Door = new POI("Door", "A locked metal door.");
        cell3Door.addInteraction("examine", "Locked. The key from Cell 2 won't fit.");

        POI cell3Bars = new POI("Bars", "Metal bars securing the cell.");
        Item metalBar = new Item("Metal Bar", "A long, bent metal bar.");
        cell3Bars.addItem(metalBar);
        cell3Bars.addInteraction("examine", "You spot a gap and find a [Metal Bar].");

        POI window = new POI("Window", "A small barred window.");
        window.addInteraction("examine", "You see eroded stone around the bars and catch a glimpse of the outside.");

        POI carvings = new POI("Carvings", "Strange carvings on the wall.");
        carvings.addInteraction("examine", "Unreadable, mysterious carvings. Flavor only.");

        POI bed = new POI("Bed", "An old, worn bed.");
        bed.addInteraction("examine", "You discover a suspicious bulge in the mattress.");

        POI mattress = new POI("Mattress", "A patched and stitched mattress.");
        Item rope = new Item("Rope", "A makeshift rope crafted from knotted cloth.");
        mattress.addItem(rope);
        mattress.addInteraction("examine", "Notice a patch. Might be cut open.");
        mattress.addInteraction("use tool", "You cut the patch open and find a [Rope].");

        cell3.addPOI(cell3Door);
        cell3.addPOI(cell3Bars);
        cell3.addPOI(window);
        cell3.addPOI(carvings);
        cell3.addPOI(bed);
        cell3.addPOI(mattress);


        // === HALLWAY ===
        Room hallway = new Room("Hallway",
                "You enter the hallway and look around. There are other torches placed on the walls illuminating the " +
                        "hallway. One side leads to a dead-end, the other to another room. Your cell door is labeled 'Cell 2'.");

        POI torches = new POI("Torches", "Wall-mounted torches lighting the hallway.");
        torches.addInteraction("examine", "They provide enough light but can't be taken.");

        POI cellDoors = new POI("Cell Doors", "Heavy locked doors for Cell 1, 2, and 3.");
        cellDoors.addInteraction("examine", "Each door is marked and appears securely locked.");

        POI cellBars = new POI("Cell Bars", "Bars of each cell along the hallway.");
        cellBars.addInteraction("examine", "For Cell 3: there's a gap revealing a [Metal Bar]. The others seem solid.");

        POI generalExplore = new POI("General Exploration", "A dim, eerie hallway.");
        generalExplore.addInteraction("look around", "You orient yourself: one side leads to a room, the other to a dead-end.");

        hallway.addPOI(torches);
        hallway.addPOI(cellDoors);
        hallway.addPOI(cellBars);
        hallway.addPOI(generalExplore);


        // Add all rooms to the master list
        rooms.add(cell2);
        rooms.add(cell1);
        rooms.add(cell3);
        rooms.add(hallway);
    }
}
