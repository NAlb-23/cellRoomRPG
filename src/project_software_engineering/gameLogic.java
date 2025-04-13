package project_software_engineering;

import resources.Room;

public class gameLogic {

	public static Player player = new Player();

	public static String processTutorial(String msg) {
		if (msg == null || msg.trim().isEmpty()) {
			return "You must enter a command.";
		}

		String command = msg.trim().toLowerCase(); // Normalize input for case-insensitivity

		switch (command) {
		case "look around":
			return player.getCurrentRoom().getDescription();

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
			return "You slip your hands through the bars and run your hand across the door. "
					+ "\nYou feel some sort of latch. You try to pull on it but to no avail. "
					+ "\nYou run your hands across it and feel some sort of hole. "
					+ "\nYou suspect it’s a keyhole."
					+ "\n\nThere you go. Now, you have a goal. I’m curious to see how you get out… \nI look forward to seeing how you do. Good luck, friend.";

		case "endTutorial":
			return "\tTo review, you can quickly grasp your surroundings by “looking around”. \n\tYou can more closely examine something by “examining” the object.\n"
					+ player.getCurrentRoom().getlistofPOINames();
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

		String command = msg.trim().toLowerCase(); // Normalize input for case-insensitivity

		switch (command) {
		case "look around":
			return player.getCurrentRoom().getDescription();

		case "inspect door":
			return "You approach the door and examine it closely. It's old and rusted, but the lock seems sturdy. You might need a key or another way to open it.";

		case "check pockets":
			return "You search your pockets and find a small piece of paper with unreadable writing on it.";

		case "scream for help":
			return "You call out, but your voice echoes back at you. No immediate response comes from the other side of the door. [try /help]";

		case "/help":
			return callHelp();

		default:
			return "You try to '" + msg + "', but nothing happens.";
		}
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
