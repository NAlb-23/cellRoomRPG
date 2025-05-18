package project_software_engineering;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import GUI.EscapeEnding;
import resources.InstantiateResources;
import resources.Item;
import resources.POI;
import resources.Room;
import resources.RoomType;
import resources.Player;
import utils.RESOURCES;

public class GameLogic {

	private static final Player player = new Player();
	private static int useCount = 0;
	private static final List<String> passiveMessages = new ArrayList<>();
	private static int escapeConditions = 0;


	public static String processTutorial(String msg) {
		if (msg == null || msg.trim().isEmpty()) return "You must enter a command.";

		String command = msg.trim().toLowerCase();

		return switch (command) {
		case "look around" -> player.getCurrentRoom().getDescription()
		+ "\n\nWhy don’t you try to [push] {door}?";
		case "push door" -> "You push against it, but it does not budge."
		+ "\n\nQuite the optimist, aren’t you?"
		+ "\nDid you honestly expect it to be that simple?"
		+ "\nYou’ll have to try harder than that."
		+ "\nWhy don’t you more closely “examine door”?";
		case "examine door" -> "Due to your only light source being outside of your cell, "
		+ "you cannot visually make out any details on the cell-side of the door."
		+ "\n\nYou reach out to touch it. It feels cold, hard, and solid. "
		+ "\nYou find no cracks, gaps, or holes."
		+ "\n\nWhat moronic builder would design a cell door "
		+ "that can be opened from the inside?"
		+ "\nCome on… you’re better than this. I thought you would immediately "
		+ "“examine door’s exterior” instead.";
		case "examine door's exterior" -> {
			player.setStatus(RESOURCES.Status.GAMEPLAY);
			yield "You slip your hands through the bars and run your fingers across the door."
			+ "\nYou feel some sort of latch. You try to pull on it, but to no avail."
			+ "\n\nContinuing your search, your fingers find a small opening — it feels like a keyhole."
			+ "\n\nThere you go. Now you have a goal."
			+ "\nI’m curious to see how you’ll get out… I look forward to seeing how you do."
			+ "\n\nGood luck, friend."
			+ "\n\n[Tip] You can quickly grasp your surroundings by typing: “look around”."
			+ "\nYou can inspect something more closely by typing: “examine [object]”.";
		}
		case "scream for help" -> "You call out, but your voice only echoes back at you."
		+ "\nNo immediate response comes from the other side of the door."
		+ "\n\nMaybe try [ /help ].";
		case "/help" -> callHelp();
		default -> "You try to '" + msg + "', but nothing happens.";
		};
	}

	public static String processCommand(String msg) {
		if (msg == null || msg.trim().isEmpty()) return "You must enter a command.";

		msg = msg.trim().toLowerCase();

		if (msg.equals("look around")) {
			return gatherPassiveMessages(lookAround());
		}

		String[] words = msg.split(" ");
		if (words.length < 2) {
			return "Invalid command. Try something like: 'examine wall' or 'take rag'.";
		}

		String command = words[0];
		String target = msg.substring(command.length()).trim();

		String result = switch (command) {
		case "use" -> handleUseCommand(msg, target);
		case "enter" -> handleEnterCommand(target);
		case "exit" -> handleExitCommand(target);
		case "combine" -> handleCombine(msg);
		default -> handleSimpleCommand(command, target);
		};

		return gatherPassiveMessages(result);
	}



	private static String handleCombine(String msg) {
		if(msg.toLowerCase().contains("rope")||msg.toLowerCase().contains("rag") || msg.toLowerCase().contains("blanket")) {
			useCount++;
			escapeConditions++;
			if(useCount == 1)
				return processCombinationRequest(msg) +" This is promising, but it doesn't look long enough to work\n";
			if(useCount == 2) {
				useCount = 0;
				InstantiateResources.findRoomByName(RoomType.CELL_3.toString()).getPOIbyName("window").setLocked(false);
				String result =  processCombinationRequest(msg) +" This is perfect, now try to [use] [long rope] on [Window]\n";
				player.removeItem(player.getInventoryItemByName("Blanket_Rag_Rope"));
				player.addItem(new Item("Long Rope", "Blanket + Rag + Rope"));
				return result;
			}
			
		}
		return "items cannot be combined";
	}


	private static String lookAround() {
		return player.getCurrentRoom().getDescription();
	}

	private static String handleEnterCommand(String roomName) {
		Room room = InstantiateResources.findRoomByName(roomName);
		if (room != null) return handleEnterRoomCommand(room);
		return roomName + " room not found";
	}

	private static String handleUseCommand(String fullCommand, String target) {
		int onIndex = fullCommand.indexOf(" on ");
		if (onIndex == -1) return "Invalid 'use' command format. Expected: 'use <item> on <target>'.";

		String itemName = fullCommand.substring(4, onIndex).trim();
		String targetName = fullCommand.substring(onIndex + 4).trim();

		// Check if the item is in the player's inventory first
		Item item = player.getInventoryItemByName(itemName);
		if (item == null) return "You don't have a " + itemName + ".";

		// Check if the target is in the room
		POI poi = player.getCurrentRoom().getPOIbyName(targetName);
		if (poi != null) {
			return handleUseOnPOI(item, poi);
		}

		// Check if the target item is in the current room
		Item targetItem = player.getCurrentRoom().getItemByName(targetName);
		if (targetItem != null) {
			return handleUseOnItem(item, targetItem);
		}

		// If the target item is not in the room, check if it's in the player's inventory
		targetItem = player.getInventoryItemByName(targetName);
		if (targetItem != null) {
			return handleUseOnItem(item, targetItem);
		}

		return "There's no '" + targetName + "' here.";
	}
	
	private static String handleUseOnItem(Item item, Item target) {
		String itemName = item.getName().toLowerCase();
		String targetName = target.getName().toLowerCase();

		if (itemName.equals("rag") && targetName.equals("rusted plate")) {
			player.removeItem(item);
			player.removeItem(target);
			Item tool = new Item("Tool", "A makeshift tool created by wrapping a rag around a rusted plate. You can [Unwrap] {tool} to get back the object or [use] {rag} on {Rusted Plate} to remake it.");
			player.addItem(tool);
			return "You carefully wrap the sharp plate with the rag, creating a new tool. The 'Tool' has been added to your inventory.";
		}

		if (itemName.equals("tool") && targetName.equals("stone")) {
			player.addItem(player.getCurrentRoom().getItemByName("Stone"));
			player.addItem(player.getCurrentRoom().getItemByName("Thin Metal Pick"));
			return player.getCurrentRoom().getPOIbyName("Wall").interAct("pry with tool");
		}

		if (itemName.equals("matchbox") && targetName.equals("debris")) {
			player.removeItem(target);
			player.setWarmthLevel(100);
			return "You light the flammable debris on fire. It's small but enough to give some warmth. You carefully take the fire to the corner of the room, careful not to let it get out of hand.";
		}

		return "Using the " + item.getName() + " on " + target.getName() + " has no effect.";
	}

	private static String handleUseOnPOI(Item item, POI poi) {
		String itemName = item.getName().toLowerCase();
		String poiName = poi.getName().toLowerCase();
		String roomName = player.getCurrentRoom().getName().toLowerCase();

		switch (poiName) {
			case "drawer":
				if (itemName.equals("stone")) {
					if (useCount++ < 1) return "The drawer is starting to crack open, try striking again.";
					useCount = 0;
					POI lockedBox = player.getCurrentRoom().getPOIbyName("Locked Box");
					lockedBox.setIsHidden(false);
					player.getCurrentRoom().getPOIbyName("Drawer").setIsHidden(true);
					player.removeItem(item);
					return lockedBox.interAct("use stone");
				}
				break;

			case "locked box":
				if (itemName.equals("thin metal pick") && !poi.getIsHidden()) {
					player.addItem(player.getCurrentRoom().getItemByName("Key"));
					poi.setLocked(false);
					player.removeItem(item);
					return poi.interAct("use thin metal pick");
				}
				break;

			case "door":
				if (itemName.equals("key")) {
					unlockConnectedRooms(RoomType.HALLWAY, RoomType.CELL_2);
					poi.setLocked(false);
					return poi.interAct("use key")+"\n you can now navigate between rooms, try {Enter} Hallway or {Exit} Cell 2";
				}
				break;

			case "cell 1 door":
				if (itemName.equals("key")) {
					poi.setLocked(false);
					player.removeItem(item);
					player.addItem(InstantiateResources.findItemByName("Metal key loop"));
					InstantiateResources.findRoomByName(RoomType.CELL_1.toString()).setLocked(false);
					return poi.interAct("use key1");
				}
				break;

			case "cell 2 door":
				if (itemName.equals("key")) return "The door is already unlocked.";
				break;

			case "cell 3 door":
				if (itemName.equals("key")) return poi.interAct("use key1");
				break;

			case "window":
				if (itemName.equals("stone")) {
					player.removeItem(item);
					return poi.interAct("use stone");
				} else if (itemName.equals("bar pole")) {
					if (poi.isLocked()) return "The window is too high to reach. You need to use something on the floor. Try using the [bucket]";
					unlockPOI("Bars");
					return poi.interAct("use bar pole");
				} else if (itemName.equals("long rope")) {
					if (!poi.isLocked()) {
						return checkEscapeCondition() ?
							processEscapeSequence("use long rope on window") :
							"Your stats are too low. Try resting, eating, or drinking first.";
					}
				}
				break;

			case "heavy door":
				if (itemName.equals("bar pole"))
					return "You bang repeatedly on the door, but nothing happens. There should be another way out.";
				break;

			case "floor":
				if (roomName.equals(RoomType.CELL_3.toString()) && itemName.equals("bucket")) {
					player.getCurrentRoom().getPOIbyName("window").setLocked(false);
					return "You place the bucket on the floor next to the window. It's just high enough for you to reach with a pole.";
				}
				break;

			case "mattress":
				if (itemName.equals("tool")) {
					player.addItem(player.getCurrentRoom().getItemByName("rope"));
					return poi.interAct("use tool");
				}
				break;

			case "barrel":
				if (itemName.equals("tool")) {
					poi.setLocked(false);
					return poi.interAct("open with tool");
				}
				if (itemName.equals("dried jerky") || itemName.equals("hardtack")) {
					item.setType("FOOD");
					return poi.interAct("soak food");
				}
				break;
		}

		return "Using the " + item.getName() + " on " + poi.getName() + " has no effect.";
	}

	

	private static String handleSimpleCommand(String command, String target) {
		if(command.equalsIgnoreCase("drink")) return handleDrinkCommand();
		if(command.equalsIgnoreCase("Sleep")) return handleSleepCommand(target);

		if(target.toLowerCase().contains("window")) target = "window";
		POI poi = player.getCurrentRoom().getPOIbyName(target);
		if (poi != null && !poi.getIsHidden()) return handlePOICommand(command, poi);

		Item item = player.getCurrentRoom().getItemByName(target);
		if (item != null) return handleItemCommand(command, item);

		Item inventoryItem = player.getInventoryItemByName(target);
		if (inventoryItem != null) return handleItemCommand(command, inventoryItem);

		return "You don't see any '" + target + "' here.";
	}

	private static String handleSleepCommand(String target) {
		if(player.getCurrentRoom().getName().equalsIgnoreCase(RoomType.CELL_3.toString()) && target.toLowerCase().contains("bed")) {
			return player.rest();
		}
		return "you don't find a place you can rest here.";
	}

	private static String handleDrinkCommand() {
		if(player.getCurrentRoom().getName().equalsIgnoreCase(RoomType.GUARD_ROOM.toString()) && !player.getCurrentRoom().getPOIbyName("Barrel").isLocked())
			return player.drink(100);

		return "you don't see water around to drink";
	}

	private static String handlePOICommand(String command, POI poi) {
		if ((command.equals("use") || command.equals("open")) && poi.getLeadsTo() != null) {
			if (poi.isLocked()) return "the " + poi.getName() + " is locked";
			Room nextRoom = poi.getLeadsTo();
			player.setCurrentRoom(nextRoom);
			return "\nYou step into " + nextRoom.getName() + ".";
		}

		if (poi.getName().equalsIgnoreCase("Bucket")) {
			if (command.equalsIgnoreCase("take")) {
				return handleTakeItemCommand(poi.getItems().getFirst());
			} else if (command.equalsIgnoreCase("dump")) {
				poi.setLocked(false);
				player.getCurrentRoom().getPOIbyName("Mess").setIsHidden(false);
			}
		}

		if(command.equalsIgnoreCase("pull") && poi.getName().equalsIgnoreCase("bars") && !poi.isLocked()) {
			useCount++;
			if(useCount == 1) return poi.interAct("pull x1");
			if(useCount == 2) return poi.interAct("pull x2");
			if(useCount == 3) {
				useCount = 0;
				escapeConditions++;
				return poi.interAct("pull x3");
			}
		}
		
		if(command.equalsIgnoreCase("escape") && poi.getName().equalsIgnoreCase("windows") && !poi.isLocked()) {
			return "escape";
		}

		return poi.interAct(command);
	}

	private static String handleItemCommand(String command, Item item) {
		if (command.equalsIgnoreCase("take")) 
			return handleTakeItemCommand(item);

		if(command.equalsIgnoreCase("examine"))
			return item.getDescription();

		if(command.equalsIgnoreCase("eat")) {
			return player.eatItem(item, 100);
		}

		if(command.equalsIgnoreCase("unwrap")) {
			player.removeItem(item);
			player.addItem(InstantiateResources.findRoomByName(RoomType.CELL_2.toString()).getItemByName("Rag"));
			player.addItem(InstantiateResources.findRoomByName(RoomType.CELL_2.toString()).getItemByName("Rusted Plate"));
		}

		return "You can't " + command + " the " + item.getName() + ".";
	}



	private static String handleTakeItemCommand(Item thisItem) {
		String itemName = thisItem.getName();

		// Check if the player already has the item
		if (player.hasItem(thisItem)) {
			return "You already have the " + itemName + ".";
		}
		
		if(itemName.equals("Rag") && player.getInventoryItemByName("Tool")!= null) {
			return "You already used the " + itemName + " to make a [tool].";
		}

		// Special case: Stone requires a tool
		if (itemName.equalsIgnoreCase("Stone")) {
			return "The [stone] is wedged tight — you need a tool to pry it loose.";
		}

		// Special case: Thin Metal Pick isn't visible until stone is moved
		if (itemName.equalsIgnoreCase("Thin Metal Pick")) {
			return "You don't see a metal pick — try prying off the stone.";
		}

		// Special case: Rusted Plate is dangerous
		if (itemName.equalsIgnoreCase("Rusted Plate")) {
			return "The plate is too sharp to handle barehanded. You should wrap it with something first.";
		}

		// Special case: Bucket must be emptied first
		if (itemName.equalsIgnoreCase("Bucket") && player.getCurrentRoom().getPOIbyName("Bucket").isLocked()) {
			return "The bucket is full — try dumping it first.";
		}

		// Special case: Key is hidden in a locked box
		if (itemName.equalsIgnoreCase("Key")) {
			var lockedBox = player.getCurrentRoom().getPOIbyName("Locked Box");
			if (lockedBox.isLocked()) {
				if (lockedBox.getIsHidden()) {
					return "You don't see a key.";
				} else {
					return "You don't see a key — try [examine]{Locked Box}.";
				}
			}
		}

		player.addItem(thisItem);
		return itemName + " added to inventory.";
	}


	private static String handleEnterRoomCommand(Room room) {
		switch (room.getId()) {
		case 0: // cell2
			if (!room.isLocked()) {
				player.setCurrentRoom(room); 
				return "You enter cell 2";
			} else {
				return "Cell 2 is locked.";
			}

		case 1: // cell1
			if (!room.isLocked() && !InstantiateResources.findRoomByName(RoomType.CELL_2.toString()).isLocked()) {
				player.setCurrentRoom(room);
				return "You enter cell 1";
			} else {
				return "Cell 1 is locked.";
			}

		case 2: // cell3
			if (!room.isLocked() && (player.getInventoryItemByName("Bar Pole") != null)) {
				player.setCurrentRoom(room);
				return "after prying off the bar pole, you squeeze into the gap into cell 3";
			} else {
				return "Cell 3 Door is locked. But the bars have a section loose enough to snap, try taking [Bar Pole]";
			}

		case 3: // guardRoom
			if (!room.isLocked()) {
				player.setCurrentRoom(room);
				return "You enter the guard room.";
			} else {
				return "The guard room is locked.";
			}

		case 4: // hallway
			if (!room.isLocked()  && !InstantiateResources.findRoomByName(RoomType.CELL_2.toString()).isLocked()) {
				player.setCurrentRoom(room);
				return "You enter the Hallway.";
			} else {
				return "The Hallway is locked.";
			}

		default:
			return "Room not found!";
		}
	}

	private static String handleExitCommand(String target) {
		Room currentRoom = player.getCurrentRoom();
		String currentRoomName = currentRoom.getName();

		if (currentRoomName.equalsIgnoreCase(RoomType.HALLWAY.toString())) {
			return "You look around, but there’s no clear way out from the Hallway.";
		}

		Room hallway = InstantiateResources.findRoomByName(RoomType.HALLWAY.toString());

		if (hallway == null) {
			return "Something seems off — the Hallway can't be found.";
		}

		if (currentRoom.isLocked()) {
			return "You try to leave, but the " + currentRoomName + " is still locked.";
		}

		if (hallway.isLocked()) {
			return "The way to the Hallway is blocked.";
		}

		player.setCurrentRoom(hallway);
		return "You make your way to the Hallway.";
	}



	private static String callHelp() {
		return """
				[Help Menu]
				Available commands:
				- look around
				- examine [object]
				- use [item] on [target]
				- take [item]
				- open [object]
				- enter [room]
				- dump [object]
				""";
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayerStartRoom(Room room) {
		player.setCurrentRoom(room);
	}
	
	public static int getEscapeCount() {
		return escapeConditions;
	}


	public static void addMessage(String msg) {
		passiveMessages.add(msg);
	}

	private static String gatherPassiveMessages(String mainResponse) {
		if (passiveMessages.isEmpty()) return mainResponse;
		StringBuilder sb = new StringBuilder(mainResponse).append("\n\n");
		for (String m : passiveMessages) {
			sb.append("[Notice] ").append(m).append("\n");
		}
		passiveMessages.clear();
		return sb.toString();
	}

	private static String processCombinationRequest(String command) {
		String[] parts = command.split(" ");  // Split the command into words (e.g., "combine rope rag")
		String action = parts[0];  // First word is the action (e.g., "combine")

		if ("combine".equals(action)) {
			String item1 = parts[1];  // First item in the combination (e.g., "rope")
			String item2 = parts[2];  // Second item in the combination (e.g., "rag")

			// Find the items from the player's inventory
			Item firstItem = player.getInventoryItemByName(item1);
			Item secondItem = player.getInventoryItemByName(item2);

			if (firstItem != null && secondItem != null) {
				// Combine the two items
				Item combinedItem = combineItems(firstItem, secondItem);

				// Check if the combined item already exists in the inventory
				if (player.getInventoryItemByName(combinedItem.getName()) == null) {
					// Add the combined item back to inventory if it's new
					player.addItem(combinedItem);

					// Optionally, remove the original items from the inventory
					player.removeItem(firstItem);
					player.removeItem(secondItem);

					return ("Successfully combined " + firstItem.getName() + " with " + secondItem.getName());
				} else {
					return  ("The combined item already exists in the inventory.");
				}
			} else {
				return ("One or more items not found in inventory.");
			}



		} else {
			return ("Invalid action: " + action);
		}

	}

	private static Item combineItems(Item item1, Item item2) {
		String name1 = item1.getName();
		String name2 = item2.getName();

		// Generic case: combine item names with an underscore
		String combinedName = (name1.compareTo(name2) < 0) ? name1 + "_" + name2 : name2 + "_" + name1;

		return new Item(combinedName, "combination of " + item1.getName() +" and "+item2.getName());
	}

	public static String processEscapeSequence(String msg) {
	    if (msg == null || msg.trim().isEmpty()) return "You must enter a command.";

	    player.setStatus(RESOURCES.Status.COMPLETE);
	    String command = msg.trim().toLowerCase();

	    return switch (command) {
	        case "use long rope on window" -> 
	            "The window is too high to reach. Try [drag] {chair} from {guard room}.";
	        case "drag chair from guard room" -> 
	            "Although you can throw the rope out the window, there's no way to tell if it's long enough. "
	            + "Perhaps if you attach something to the other end as an anchor, "
	            + "you might be able to judge whether it's feasible to escape with it. "
	            + "Try [use] {bar pole} on {long rope}.";
	        case "use bar pole on long rope" -> 
	            "You tie the metal bar to the end of the rope, forming a makeshift anchor. "
	            + "Now try [use] {rope} on {window}.";
	        case "use rope on window" -> 
	            "You toss the rope out the window and slowly lower it. "
	            + "As you reach the end, you feel no more tension. "
	            + "After pulling and testing it several times, it seems the rope is long enough to reach another surface. "
	            + "Before using it, you'll need to secure it. Try [tie] {rope} to {metal bar}.";
	        case "tie rope to metal bar" -> 
	            "You tie the other end of the rope to the long metal bar. "
	            + "It's sturdy and long enough to serve as an anchor against the window. "
	            + "You tug it several times until you're sure it's secure. Try [use] {bucket} on {window}.";
	        case "use bucket on window" -> 
	            "You flip the bucket upside down and place it beneath the window. "
	            + "The added height isn't enough for you to crawl through. Try [push] {table} to {window}.";
	        case "push table to window" -> 
	            "You push the table from the outer room and position it beneath the window. "
	            + "Standing on it gives you enough height to crawl through and [escape].";
	        case "escape" -> 
	            "complete";
	        default -> 
	            "You try to '" + msg + "', but nothing happens.";
	    };
	}
	
	private static void unlockConnectedRooms(RoomType... rooms) {
		for (RoomType room : rooms) {
			InstantiateResources.findRoomByName(room.toString()).setLocked(false);
		}
	}

	private static void unlockPOI(String poiName) {
		POI bars = player.getCurrentRoom().getPOIbyName(poiName);
		bars.setIsHidden(false);
		bars.setLocked(false);
	}

	public static void endGame() {
	    System.out.println("Game Over!");
	    player.setStatus(RESOURCES.Status.GAMEOVER);
	}

	private static boolean checkEscapeCondition() {
		System.out.println("Escape condition count:" +escapeConditions);
		if(player.isNeedsSatisfied() && escapeConditions >= 3) {
			return true;
		}
		return false;
	}
}
