package project_software_engineering;

import resources.InstantiateResources;
import resources.Item;
import resources.POI;
import resources.Room;
import utils.RESOURCES;

public class gameLogic {

	public static Player player = new Player();
	private static int useCount = 0;

	public static String processTutorial(String msg) {
		if (msg == null || msg.trim().isEmpty()) {
			return "You must enter a command.";
		}

		String command = msg.trim().toLowerCase(); // Normalize input for case-insensitivity

		switch (command) {
		case "look around":
			return player.getCurrentRoom().getDescription()
					+"Why don’t you try to [push] the {door}?";

		case "push the door":
			return "You push against it, but it does not budge"
			+"\n\nQuite the optimist, aren’t you? "
			+ "\nDid you honestly expect it to be that simple? "
			+ "\nYou have to try harder than that. Why don’t you more closely "
			+ "\n“examine the door”?";

		case "examine the door":
			return "Due to your only light source being outside of your cell, "
			+ "\nyou cannot visually make out any details on the cell-side of the door"
			+ ". \nYou reach out to touch it and find it to be cold, hard, and solid. "
			+ "\nYou find no cracks, gaps, or holes."
			+ "\n\nWhat moronic builder would design a cell door that can be opened from the inside? "
			+ "\nCome on… You’re better than this. I thought you would immediately "
			+ "\n“examine the door’s exterior” instead.";

		case "examine the door's exterior":
			player.setStatus(RESOURCES.Status.GAMEPLAY);
			return "You slip your hands through the bars and run your hand across the door. "
			+ "\nYou feel some sort of latch. You try to pull on it but to no avail. "
			+ "\nYou run your hands across it and feel some sort of hole. "
			+ "\nYou suspect it’s a keyhole."
			+ "\n\nThere you go. Now, you have a goal. I’m curious to see how you get out… \nI look forward to seeing how you do. Good luck, friend."
			+ "\nTo review, you can quickly grasp your surroundings by “looking around”. \nYou can more closely examine something by “examining” the object.\n"
			;

		case "scream for help":
			return "You call out, but your voice echoes back at you. No immediate response comes from the other side of the door. [try /help]";

		case "/help":
			return callHelp();

		default:
			return "You try to '" + msg + "', but nothing happens.";
		}
	}

	public static String processCommand(String msg) {
		if (msg == null || msg.trim().isEmpty()) {
			return "You must enter a command.";
		}

		msg = msg.trim().toLowerCase();

		// Basic command check
		if (msg.equals("look around")) {
			return lookAround();
		}

		String[] words = msg.split(" ");
		if (words.length < 2) {
			return "Invalid command. Try something like: 'examine wall' or 'take rag'.";
		}

		String command = words[0];
		String target = msg.substring(command.length()).trim();

		// Detect "use X on Y" style commands
		if (command.equals("use")) {
			return handleUseCommand(msg, target);
		}

		return handleSimpleCommand(command, target);
	}

	private static String lookAround() {
		return player.getCurrentRoom().getDescription();
	}

	private static String handleUseCommand(String msg, String target) {
		int onIndex = msg.indexOf(" on ");
		if (onIndex == -1) {
			return "Invalid 'use' command format. Expected: 'use <item> on <target>'.";
		}

		String itemName = msg.substring(4, onIndex).trim();
		String targetName = msg.substring(onIndex + 4).trim();

		Item item = player.getInventoryItemByName(itemName);
		if (item == null) return "You don't have a " + itemName + ".";

		POI poi = player.getCurrentRoom().getPOIbyName(targetName);
		if (poi != null) {
			return handleUseOnPOI(item, poi);
		}

		Item targetItem = player.getCurrentRoom().getItemByName(targetName);
		if (targetItem != null) {
			return handleUseOnItem(item, targetItem);
		}

		return "There's no '" + targetName + "' here.";
	}

	private static String handleUseOnPOI(Item item, POI poi) {
		if (poi.getName().equals("Drawer")) {
			if(item.equals(player.getCurrentRoom().getItemByName("Stone"))) {
				if(useCount < 1) {
					useCount++;
					return  "the Drawer is starting to crack open, try stricking again.";
				}
				else {
					useCount = 0;
					player.getCurrentRoom().getPOIbyName("Locked Box").setIsHidden(false);
					player.removeItem(item);
					return poi.interAct("use stone");
				}
			}
		}
		else if(poi.getName().equals("Locked Box") && item.getName().equals("Thin Metal Pick")) {
			player.addItem(player.getCurrentRoom().getItemByName("Key"));
			
			return poi.interAct("use thin metal pick");
		}
		else if(poi.getName().equals("Door") && item.getName().equals("Key")) {
			poi.setLocked(false);
			
			player.setCurrentRoom(poi.getLeadsTo());
			
			return poi.interAct("use key");
		}

		// Default handling for other cases
		return "Using the " + item.getName() + " on " + poi.getName() + " has no effect.";
	}

	private static String handleUseOnItem(Item item, Item targetItem) {
		// Special use cases for combining Rag and Rusted Plate into Tool
		if (targetItem.getName().equalsIgnoreCase("Rusted Plate") && item.getName().equalsIgnoreCase("Rag")) {
			Item tool = new Item("Tool", "A makeshift tool created by wrapping a rag around a rusted plate.");
			player.addItem(tool);
			player.removeItem(targetItem);  // Remove the original Rusted Plate
			player.removeItem(item);        // Remove the Rag
			return "You carefully wrap the sharp plate with the rag, creating a new tool. The 'Tool' has been added to your inventory.";
		}

		// Special use case for using the Tool on Stone
		if (targetItem.getName().equalsIgnoreCase("Stone") && item.getName().equalsIgnoreCase("Tool")) {
			Item firstTargetItem = player.getCurrentRoom().getItemByName("Stone");
			Item secondTargetITem = player.getCurrentRoom().getItemByName("Thin Metal Pick");
			player.addItem(firstTargetItem);
			player.addItem(secondTargetITem);

			return player.getCurrentRoom().getPOIbyName("Wall").interAct("pry with tool");
		}

		// Default handling for other cases
		return "Using the " + item.getName() + " on " + targetItem.getName() + " has no effect.";
	}


	private static String handleSimpleCommand(String command, String target) {
		// Check POIs
		POI thisPOI = player.getCurrentRoom().getPOIbyName(target);
		if (thisPOI != null) {
			return handlePOICommand(command, thisPOI);
		}

		// Check Items in the room
		Item thisItem = player.getCurrentRoom().getItemByName(target);
		if (thisItem != null) {
			return handleItemCommand(command, thisItem);
		}

		return "You don't see any '" + target + "' here.";
	}

	private static String handlePOICommand(String command, POI thisPOI) {
		String result = thisPOI.interAct(command);

		if (thisPOI.getLeadsTo() != null && (command.equals("use") || command.equals("open"))) {
			Room nextRoom = InstantiateResources.findRoomByName(thisPOI.getLeadsTo().getName());
			if (nextRoom != null) {
				player.setCurrentRoom(nextRoom);
				result += "\nYou step into " + nextRoom.getName() + ".";
			} else {
				result += "\nBut the destination seems inaccessible.";
			}
		}

		return result;
	}

	private static String handleItemCommand(String command, Item thisItem) {
		if (command.equals("take")) {
			return handleTakeItemCommand(thisItem);
		} else {
			return thisItem.getDescription();
		}
	}

	private static String handleTakeItemCommand(Item thisItem) {
		// Special case handling
		if (thisItem.getName().equalsIgnoreCase("Stone")) {
			return "The stone is wedged tight — you need a tool to pry it loose.";
		}

		if (thisItem.getName().equalsIgnoreCase("Rusted Plate")) {
			return "The plate is too sharp to handle barehanded. You should wrap it with something first.";
		}

		player.addItem(thisItem);
		return thisItem.getName() + " added to inventory.";
	}

	private static String callHelp() {
		StringBuilder sb = new StringBuilder();

		sb.append("/help\n");
		sb.append("You can try:\n");
		sb.append("  [look around]\t\tget room description\n");
		sb.append("  [examine <POI>]\tget POI description\n");
		sb.append("  [interact with <item>]\tget item description\n");
		sb.append("  [pick up <item>]\tadd item to inventory\n");
		sb.append("  [use <item>]\t\tif possible item gets used\n");
		sb.append("  [/help]\t\tget help\n");

		return sb.toString();
	}

	public static void loadPlayer(
			String name,
			Room currentRoom, 
			boolean hungerlevel, 
			boolean warmthlevel,
			boolean restLevel) 
	{
		player.setName(name);
		player.setCurrentRoom(currentRoom);
		player.setHungerlevel(hungerlevel);
		player.setRestLevel(restLevel);
		player.setWarmthlevel(warmthlevel);
	}
}
