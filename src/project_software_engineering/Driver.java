package project_software_engineering;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import GUI.MainMenuGUI;
import GUI.NewGameGUI;
import GUI.TextGameUI;
import resources.InstantiateResources;
import resources.Item;
import utils.RESOURCES;

public class Driver {
	// Constants for game state
	private static final int EXIT = -1;
	private static final int NEWSAVE = 1;
	private static final int LOADSAVE = 0;

	public static void start() {
		int saveChoice = showMainMenu();

		switch (saveChoice) {
		case EXIT:
			System.out.println("Exited");
			return;

		case NEWSAVE:
			System.out.println("New Save");
			int newGameChoice = showNewGameScreen();

			if (newGameChoice == 1) {
				// Return to main menu
				System.out.println("Returning to main menu...");
				start(); // recursive call to restart flow
			} else {
				// TODO: launch main game
				System.out.println("Launching main game...");
				startGame(false);
			}
			break;

		case LOADSAVE:
		    System.out.println("Load Save");
		    startGame(true);  
		    
		    break;

		default:
			System.err.println("Unexpected input from main menu.");
			break;
		}
	}


	private static int showMainMenu() {
		CountDownLatch latch = new CountDownLatch(1);
		AtomicInteger userChoice = new AtomicInteger(EXIT);

		SwingUtilities.invokeLater(() -> {
			MainMenuGUI gui = new MainMenuGUI();

			gui.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					userChoice.set(gui.getUserChoice()); 
					latch.countDown();
				}

			});

			gui.setVisible(true);
		});

		awaitLatch(latch);
		return userChoice.get();
	}

	private static int showNewGameScreen() {
		CountDownLatch latch = new CountDownLatch(1);
		AtomicInteger userChoice = new AtomicInteger(EXIT);

		SwingUtilities.invokeLater(() -> {
			NewGameGUI gui = new NewGameGUI();

			gui.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					userChoice.set(gui.BTNPRESS);
					if (gui.USERNAME != null) {
						gameLogic.player.setName(gui.USERNAME);
						gameLogic.player.setStatus(RESOURCES.Status.TUTORIAL);
						gameLogic.player.setCurrentRoom(InstantiateResources.rooms.get(0));
						System.out.println(gameLogic.player.toString());
					}
					latch.countDown();
				}
			});

			gui.setVisible(true);
		});

		awaitLatch(latch);
		return userChoice.get();
	}

	private static void awaitLatch(CountDownLatch latch) {
		try {
			latch.await();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.err.println("Latch waiting interrupted: " + e.getMessage());
		}
	}
	
	private static void startGame(boolean isSave) {
		CountDownLatch latch = new CountDownLatch(1);

		SwingUtilities.invokeLater(() -> {
			TextGameUI gui = new TextGameUI(gameLogic.player, isSave);

			gui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			gui.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					int choice = JOptionPane.showConfirmDialog(
							gui,
							"Would you like to save before exiting?",
							"Confirm Exit",
							JOptionPane.YES_NO_CANCEL_OPTION
					);

					switch (choice) {
						case JOptionPane.YES_OPTION:
							System.out.println("User chose YES - save and exit.");
							String progress = gui.getSave();
							Driver.SaveGameState(progress);
							gui.dispose(); // this will trigger windowClosed below
							break;

						case JOptionPane.NO_OPTION:
							System.out.println("User chose NO - exit without saving.");
							gui.dispose();
							break;

						case JOptionPane.CANCEL_OPTION:
						case JOptionPane.CLOSED_OPTION:
							System.out.println("User canceled or closed dialog.");
							break;
					}
				}

				@Override
				public void windowClosed(WindowEvent e) {
					latch.countDown(); // now guaranteed to be part of the same listener
				}
			});

			gui.setVisible(true);
		});
	}
	
	public static void SaveGameState(String progress) {
	    try {
	        FileWriter writer = new FileWriter("savegame.txt");
	        writer.write("PlayerName:" + gameLogic.player.getName() + "\n");
	        writer.write("CurrentRoom:" + gameLogic.player.getCurrentRoom().getName() + "\n");
	        writer.write("Status:" + gameLogic.player.getStatus().name() + "\n");
	        writer.write("Hunger:" + gameLogic.player.getHungerlevel() + "\n");
	        writer.write("Warmth:" + gameLogic.player.getWarmthlevel() + "\n");
	        writer.write("Rest:" + gameLogic.player.getRestLevel() + "\n");
	        writer.write("Inventory:" + gameLogic.player.getInventoryNames() + "\n");
	        writer.write("Progress:" + progress + "\n");
	        writer.close();
	        System.out.println("Game saved successfully.");
	    } catch (IOException e) {
	        System.err.println("Error saving game: " + e.getMessage());
	    }
	}



	public static String processCommand(String command) {
		if(gameLogic.player.getStatus().equals(RESOURCES.Status.TUTORIAL)) 
			return gameLogic.processTutorial(command);
		else 
			return gameLogic.processCommand(command);
	}
	

	public static void LoadGameState(TextGameUI ui) {
	    try (BufferedReader reader = new BufferedReader(new FileReader("savegame.txt"))) {
	        String line;
	        StringBuilder progressBuilder = new StringBuilder();
	        boolean isProgressSection = false;

	        while ((line = reader.readLine()) != null) {

	            // If we're in Progress section, append everything till EOF
	            if (isProgressSection) {
	                progressBuilder.append(line).append("\n");
	                continue;
	            }

	            String[] parts = line.split(":", 2);
	            if (parts.length < 2) continue;

	            String key = parts[0];
	            String value = parts[1];

	            switch (key) {
	                case "PlayerName":
	                    gameLogic.player.setName(value);
	                    break;
	                case "CurrentRoom":
	                    gameLogic.player.setCurrentRoom(InstantiateResources.findRoomByName(value));
	                    break;
	                case "Status":
	                    gameLogic.player.setStatus(RESOURCES.Status.valueOf(value));
	                    break;
	                case "Hunger":
	                    gameLogic.player.setHungerlevel(Boolean.parseBoolean(value));
	                    break;
	                case "Warmth":
	                    gameLogic.player.setWarmthlevel(Boolean.parseBoolean(value));
	                    break;
	                case "Rest":
	                    gameLogic.player.setRestLevel(Boolean.parseBoolean(value));
	                    break;
	                case "Inventory":
	                    gameLogic.player.clearInventory();
	                    for (String itemName : value.split(",")) {
	                        Item item = InstantiateResources.findItemByName(itemName);
	                        if (item != null) gameLogic.player.addItem(item);
	                    }
	                    break;
	                case "Progress":
	                    isProgressSection = true;
	                    progressBuilder.append(value).append("\n");
	                    break;
	            }
	        }

	        // If progress was collected, print or assign it somewhere
	        String fullProgress = progressBuilder.toString().trim();
	        if (!fullProgress.isEmpty()) {
	            System.out.println("Loaded progress:\n" + fullProgress);
	            // You could also: ui.setProgressText(fullProgress);
	        }

	        ui.updateInventory();
	        ui.displayRoomInfo();
	        ui.updatePlayerInfo();
	        ui.setTextArea(fullProgress);

	        System.out.println("Game loaded successfully.");

	    } catch (IOException e) {
	        System.err.println("Error loading game: " + e.getMessage());
	    }
	}



}
