package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import project_software_engineering.GameLogic;
import resources.InstantiateResources;
import resources.Item;
import resources.POI;
import resources.Room;

public class GameSaver {

    /**
     * Saves the current game state to a file.
     * @param progress The player's progress to be saved.
     */
    public static void SaveGameState(String progress) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("savegame.txt"))) {
            writer.write("PlayerName:" + GameLogic.getPlayer().getName() + "\n");
            writer.write("CurrentRoom:" + GameLogic.getPlayer().getCurrentRoom().getName() + "\n");
            writer.write("Status:" + GameLogic.getPlayer().getStatus().name() + "\n");
            writer.write("Hunger:" + GameLogic.getPlayer().getHungerLevel() + "\n");
            writer.write("Warmth:" + GameLogic.getPlayer().getWarmthLevel() + "\n");
            writer.write("Rest:" + GameLogic.getPlayer().getRestLevel() + "\n");
            writer.write("Thirst:" + GameLogic.getPlayer().getThirstLevel() + "\n");
            saveResourceState(writer);
            writer.write("Inventory:" + GameLogic.getPlayer().getInventoryNames() + "\n");
            writer.write("Progress:" + progress + "\n");
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }
    
    private static void saveResourceState(BufferedWriter writer) throws IOException {
        for (Room room : InstantiateResources.rooms) {
            writer.write("Room: " + room.getName() + ": isLocked:" + room.isLocked() + "\n");
            for (POI poi : room.getPois()) {
                writer.write("  POI: " + poi.getName() + ": isHidden:" + poi.getIsHidden() + ": isLocked:" + poi.isLocked() + "\n");
            }
            for (Item item : room.getListOfRoomItems()) {
                writer.write("  Item: " + item.getName() + ": type: " + item.getType() + "\n");
            }
        }    
    }
}
