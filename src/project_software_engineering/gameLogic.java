package project_software_engineering;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import resources.InstantiateResources;
import resources.Item;
import resources.POI;
import resources.Room;
import resources.RoomType;
import utils.RESOURCES;

public class GameLogic {

    private static final Player player = new Player();
    private static int useCount = 0;
    private static final List<String> passiveMessages = new ArrayList<>();

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
            default -> handleSimpleCommand(command, target);
        };

        return gatherPassiveMessages(result);
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

        Item item = player.getInventoryItemByName(itemName);
        if (item == null) return "You don't have a " + itemName + ".";

        POI poi = player.getCurrentRoom().getPOIbyName(targetName);
        if (poi != null) return handleUseOnPOI(item, poi);

        Item targetItem = player.getCurrentRoom().getItemByName(targetName);
        if (targetItem != null) return handleUseOnItem(item, targetItem);

        return "There's no '" + targetName + "' here.";
    }

    private static String handleUseOnItem(Item item, Item target) {
        if (item.getName().equalsIgnoreCase("rag") && target.getName().equalsIgnoreCase("rusted plate")) {
            player.removeItem(item);
            player.removeItem(target);
            Item tool = new Item("Tool", "A makeshift tool created by wrapping a rag around a rusted plate.");
            player.addItem(tool);
            return "You carefully wrap the sharp plate with the rag, creating a new tool. The 'Tool' has been added to your inventory.";
        }

        if (item.getName().equalsIgnoreCase("tool") && target.getName().equalsIgnoreCase("stone")) {
            player.addItem(player.getCurrentRoom().getItemByName("Stone"));
            player.addItem(player.getCurrentRoom().getItemByName("Thin Metal Pick"));
            return player.getCurrentRoom().getPOIbyName("Wall").interAct("pry with tool");
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
                    if (useCount++ < 1) return "the Drawer is starting to crack open, try stricking again.";
                    useCount = 0;
                    poi = player.getCurrentRoom().getPOIbyName("Locked Box");
                    poi.setIsHidden(false);
                    player.removeItem(item);
                    return poi.interAct("use stone");
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
                    poi.setLocked(false);
                    InstantiateResources.findRoomByName(RoomType.HALLWAY.toString()).setLocked(false);
                    InstantiateResources.findRoomByName(RoomType.CELL_2.toString()).setLocked(false);
                    return poi.interAct("use key");
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
                    if (poi.isLocked()) return "the window is too high to reach. you need to use something on the floor.";
                    if (useCount++ < 3) return "you see the bars of the window starting to give, try stricking again.";
                    useCount = 0;
                    return poi.interAct("use bar pole x4");
                }
                break;
            case "heavy door":
                if (itemName.equals("bar pole")) return "you bang repeatedly on the door, but nothing happens. there should be another way out";
                break;
            case "floor":
                if (roomName.equals(RoomType.CELL_3.toString()) && itemName.equals("bucket")) {
                    player.getCurrentRoom().getPOIbyName("window").setLocked(false);
                    return "You place the bucket on the floor next the window, its just high enough for you to reach with a pole.";
                }
                break;
            case "mattress":
                if (itemName.equals("tool")) {
                    player.addItem(player.getCurrentRoom().getItemByName("rope"));
                    return poi.interAct("use tool") + "\n\n your stomach began to rumble, if you want to make it out you need to find something to eat";
                }
                break;
            case "barrel":
                if (itemName.equals("tool")) {
                    poi.setLocked(false);
                    return poi.interAct("open with tool");
                }
                if (itemName.equalsIgnoreCase("Dried Jerky") ||itemName.equalsIgnoreCase("Hardtack")) {
                	item.setType("food");
                	return poi.interAct("soak food");
                }
                break;
        }

        return "Using the " + item.getName() + " on " + poi.getName() + " has no effect.";
    }

    private static String handleSimpleCommand(String command, String target) {
    	if(command.equalsIgnoreCase("drink")) return handleDrinkCommand();
    	if(command.equalsIgnoreCase("Sleep")) return handleSleepCommand(target);
    	
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

        return poi.interAct(command);
    }

    private static String handleItemCommand(String command, Item item) {
        if (command.equalsIgnoreCase("take")) 
            return handleTakeItemCommand(item);
        
        if(command.equalsIgnoreCase("examine"))
        	return item.getDescription();
        
        if(command.equalsIgnoreCase("eat")) {
        	player.removeItem(item);
        	return player.eatItem(item, 100);
        }
        
        return "You can't " + command + " the " + item.getName() + ".";
    }

  

	private static String handleTakeItemCommand(Item thisItem) {
        String itemName = thisItem.getName();

        // Check if the player already has the item
        if (player.hasItem(thisItem)) {
            return "You already have the " + itemName + ".";
        }

        // Special case: Stone requires a tool
        if (itemName.equalsIgnoreCase("Stone")) {
            return "The stone is wedged tight — you need a tool to pry it loose.";
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

}
