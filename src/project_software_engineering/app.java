package project_software_engineering;

import resources.InstantiateResources;

/**
 * Done:
 * implemented thirst mechanics and changed the stats to out of 100.
 * fix bug of taking items more than once into inventory.
 * implement player hunger/rest mechanics.
 * added an enum class for the rooms to not use strings constantly
 * 
 * TO-DO:
 * decide if i should use enums for other structures or maybe records
 * handle exit room logic
 * implement proper poi and item sequence in cell 3 and guardroom.
 * stress test cell 2, cell 1 and hallway.
 * decide whether to expand save/load sequence to include multiple saves of the game or only one without an interface.
 * fix the saving subsystem to include saving room/object lock-state. 
 * implement player warmth mechanics
 */

public class app {

	public static void main(String[] args) {
		InstantiateResources.loadResources();
		
		Driver.start();
		
	}

}
