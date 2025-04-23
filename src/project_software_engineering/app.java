package project_software_engineering;

import resources.InstantiateResources;

/**
 * TO-DO:
 * fix bug of taking items more than once into inventory.
 * implement proper poi and item sequence in cell 3.
 * stress test cell 2, cell 1 and hallway.
 * decide whether to expand save/load sequence to include multiple saves of the game or only one without an interface.
 * fix the saving subsystem to include saving room/object lock-state. 
 */

public class app {

	public static void main(String[] args) {
		InstantiateResources.loadResources();
		
		Driver.start();
		
	}

}
