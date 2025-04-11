package project_software_engineering;

public class Driver {
	
	public static String processCommand(String msg) {
	    if (msg == null || msg.trim().isEmpty()) {
	        return "You must enter a command.";
	    }

	    String command = msg.trim().toLowerCase(); // Normalize input for case-insensitivity

	    switch (command) {
	        case "look around":
	            return "\nA torch is placed on the wall outside and across your cell. From the flickering light, you find yourself in a windowless cell. An old, wooden table sits at the corner. The walls are made of solid stone. Standing between you and the hallway beyond is a set of metal bars and a metal door.\n"
	            		+ "\nWhy don’t you try to [push] the {door}’?";
	        
	        case "inspect door":
	            return "You approach the door and examine it closely. It's old and rusted, but the lock seems sturdy. You might need a key or another way to open it.";
	        
	        case "check pockets":
	            return "You search your pockets and find a small piece of paper with unreadable writing on it.";

	        case "shout for help":
	            return "You call out, but your voice echoes back at you. No immediate response comes from the other side of the door.";

	        default:
	            return "You try to '" + msg + "', but nothing happens.";
	    }
	}

}
