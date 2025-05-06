package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import project_software_engineering.GameLogic;
import resources.InstantiateResources;
import resources.Item;
import resources.POI;
import resources.Room;
import utils.RESOURCES.Status;
import GUI.TextGameUI;

public class GameLoader {

    /**
     * Loads the game state from a saved file.
     * Returns the progress text that can be displayed in the UI.
     */
    public static void LoadGameState(TextGameUI ui) {
        StringBuilder progressBuilder = new StringBuilder();
        boolean isProgressSection = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("savegame.txt"))) {
            String line;
            List<Item> inventory = new ArrayList<>();
            String playerName = null;
            String currentRoomName = null;
            Status status = null;
            int hunger = 0, rest = 0, thirst = 0;
            double warmth = 0.0;

            while ((line = reader.readLine()) != null) {
                if (isProgressSection) {
                    progressBuilder.append(line).append("\n");
                    continue;
                }

                if (line.startsWith("PlayerName:")) {
                    playerName = line.split(":", 2)[1].trim();
                } else if (line.startsWith("CurrentRoom:")) {
                    currentRoomName = line.split(":", 2)[1].trim();
                } else if (line.startsWith("Status:")) {
                    status = Status.valueOf(line.split(":", 2)[1].trim());
                } else if (line.startsWith("Hunger:")) {
                    hunger = Integer.parseInt(line.split(":", 2)[1].trim());
                } else if (line.startsWith("Warmth:")) {
                    warmth = Double.parseDouble(line.split(":", 2)[1].trim());
                } else if (line.startsWith("Rest:")) {
                    rest = Integer.parseInt(line.split(":", 2)[1].trim());
                } else if (line.startsWith("Thirst:")) {
                    thirst = Integer.parseInt(line.split(":", 2)[1].trim());
                } else if (line.startsWith("Room:")) {
                    // Room: RoomName: isLocked:true
                    String[] parts = line.split(":");
                    String roomName = parts[1].trim();
                    boolean isLocked = Boolean.parseBoolean(parts[3].trim());
                    Room room = InstantiateResources.findRoomByName(roomName);
                    if (room != null) room.setLocked(isLocked);
                } else if (line.startsWith("  POI:")) {
                    //   POI: Name: isHidden:true: isLocked:false
                    String[] parts = line.split(":");
                    String poiName = parts[1].trim();
                    boolean isHidden = Boolean.parseBoolean(parts[3].trim());
                    boolean isLocked = Boolean.parseBoolean(parts[5].trim());
                    Room currentRoom = GameLogic.getPlayer().getCurrentRoom();
                    POI poi = (currentRoom != null) ? currentRoom.getPOIbyName(poiName) : null;
                    if (poi != null) {
                        poi.setIsHidden(isHidden);
                        poi.setLocked(isLocked);
                    }
                } else if (line.startsWith("  Item:")) {
                    //   Item: Name: type: Tool
                    String[] parts = line.split(":");
                    String itemName = parts[1].trim();
                    String itemType = parts[3].trim();
                    Room currentRoom = GameLogic.getPlayer().getCurrentRoom();
                    Item item = (currentRoom != null) ? currentRoom.getItemByName(itemName) : null;
                    
                    if (item != null) {
                        item.setType(itemType);
                    }
                }else if (line.startsWith("Inventory:")) {
                    inventory.clear();
                    String[] itemNames = line.split(":", 2)[1].split(",");

                    for (String rawName : itemNames) {
                        String itemName = rawName.trim();
                        Item item = InstantiateResources.findItemByName(itemName);

                        // Handle special crafted items if not found in default resources
                        if (item == null && itemName.equalsIgnoreCase("Tool")) {
                            item = new Item("Tool", "A makeshift tool created by wrapping a rag around a rusted plate. You can [Unwrap] {tool} to get back the object or [use] {rag} on {Rusted Plate} to remake it.");
                        }

                        if (item != null) {
                            inventory.add(item);
                        } else {
                            System.err.println("Unknown inventory item: " + itemName);
                        }
                    }
                } else if (line.startsWith("Progress:")) {
                    progressBuilder.append(line.split(":", 2)[1]).append("\n");
                    isProgressSection = true;
                }
            }

            // Set the player state
            GameLogic.getPlayer().setName(playerName);
            GameLogic.getPlayer().setCurrentRoom(InstantiateResources.findRoomByName(currentRoomName));
            GameLogic.getPlayer().setStatus(status);
            GameLogic.getPlayer().setHungerLevel(hunger);
            GameLogic.getPlayer().setWarmthLevel(warmth);
            GameLogic.getPlayer().setRestLevel(rest);
            GameLogic.getPlayer().setThirstLevel(thirst);
            GameLogic.getPlayer().setInventory(inventory);
            
            ui.updateInventory();
            ui.displayRoomInfo();
            ui.updatePlayerInfo();
            ui.setTextArea(progressBuilder.toString().trim());

            System.out.println("Game loaded successfully.");

        } catch (IOException e) {
            System.err.println("Error loading game: " + e.getMessage());
        }

    }
}
