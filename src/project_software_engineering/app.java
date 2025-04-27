package project_software_engineering;

import resources.InstantiateResources;

/**
 * Main entry point of the game application.
 * This class initializes the resources and starts the game by invoking the Driver class.
 */

/**
 * Done:
 * removed unused playerInventory class
 * 
 * TO-DO:
 * Game Architecture & Core Logic
 * - Decide if I should use enums for other structures or maybe records
 * - Fix the saving subsystem to include saving room/object lock-state
 * - Decide whether to expand save/load sequence to include multiple saves 
 *   of the game or only one without an interface
 * 
 * Room & Object Interaction
 * - Implement moving chair to Cell 3 from Guard Room
 * - Implement proper POI and item sequence in Cell 3 and Guard Room
 * 
 * Player Warmth Mechanics
 * - Add wood debris on floor of Cell 1													done
 * - Implement use of matches on debris to light fire									done
 * - Allow bucket to be thrown into the fire
 * 
 * Testing & Stress Scenarios
 * - Stress test Cell 2, Cell 1, and Hallway
 */

public class app {

	public static void main(String[] args) {
		// Load all necessary game resources
		InstantiateResources.loadResources();

		// Start the game by calling the Driver's start method
		Driver.start();
	}
}
