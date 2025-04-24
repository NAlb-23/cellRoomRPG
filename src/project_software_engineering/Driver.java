package project_software_engineering;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
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
				System.out.println("Returning to main menu...");
				start();
			} else {
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
						GameLogic.getPlayer().setName(gui.USERNAME);
						GameLogic.getPlayer().setStatus(RESOURCES.Status.TUTORIAL);
						GameLogic.setPlayerStartRoom(InstantiateResources.rooms.get(0));
						System.out.println(GameLogic.getPlayer());
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
			System.err.println("Latch interrupted: " + e.getMessage());
		}
	}

	private static void startGame(boolean isSave) {
		CountDownLatch latch = new CountDownLatch(1);

		SwingUtilities.invokeLater(() -> {
			TextGameUI gui = new TextGameUI(GameLogic.getPlayer(), isSave);
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
						String progress = gui.getSave();
						SaveGameState(progress);
						gui.dispose();
						break;
					case JOptionPane.NO_OPTION:
						gui.dispose();
						break;
					}
				}

				@Override
				public void windowClosed(WindowEvent e) {
					latch.countDown();
				}
			});

			gui.setVisible(true);
		});
	}

	public static void SaveGameState(String progress) {
		try (FileWriter writer = new FileWriter("savegame.txt")) {
			writer.write("PlayerName:" + GameLogic.getPlayer().getName() + "\n");
			writer.write("CurrentRoom:" + GameLogic.getPlayer().getCurrentRoom().getName() + "\n");
			writer.write("Status:" + GameLogic.getPlayer().getStatus().name() + "\n");
			writer.write("Hunger:" + GameLogic.getPlayer().getHungerLevel() + "\n");
			writer.write("Warmth:" + GameLogic.getPlayer().getWarmthLevel() + "\n");
			writer.write("Rest:" + GameLogic.getPlayer().getRestLevel() + "\n");
			writer.write("Thirst:" + GameLogic.getPlayer().getThirstLevel() + "\n");
			writer.write("Inventory:" + GameLogic.getPlayer().getInventoryNames() + "\n");
			writer.write("Progress:" + progress + "\n");
			System.out.println("Game saved successfully.");
		} catch (IOException e) {
			System.err.println("Error saving game: " + e.getMessage());
		}
	}

	public static String processCommand(String command) {
		boolean isTickPending = false;
		if (GameLogic.getPlayer().getStatus() == RESOURCES.Status.TUTORIAL) {
			return GameLogic.processTutorial(command);
		} else {
			// Only call tick every other command
			if (isTickPending) {
				GameLogic.getPlayer().tick();
			}
			// Toggle the tick flag for the next command
			isTickPending = !isTickPending;
			return GameLogic.processCommand(command);
		}
	}

	public static void LoadGameState(TextGameUI ui) {
		try (BufferedReader reader = new BufferedReader(new FileReader("savegame.txt"))) {
			String line;
			StringBuilder progressBuilder = new StringBuilder();
			boolean isProgressSection = false;

			while ((line = reader.readLine()) != null) {
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
					GameLogic.getPlayer().setName(value);
					break;
				case "CurrentRoom":
					GameLogic.setPlayerStartRoom(InstantiateResources.findRoomByName(value));
					break;
				case "Status":
					GameLogic.getPlayer().setStatus(RESOURCES.Status.valueOf(value));
					break;
				case "Hunger":
					GameLogic.getPlayer().setHungerLevel(Integer.parseInt(value));
					break;
				case "Warmth":
					GameLogic.getPlayer().setWarmthLevel(Integer.parseInt(value));
					break;
				case "Rest":
					GameLogic.getPlayer().setRestLevel(Integer.parseInt(value));
					break;
				case "Thirst":
					GameLogic.getPlayer().setThirstLevel(Integer.parseInt(value));
					break;
				case "Inventory":
					GameLogic.getPlayer().clearInventory();
					for (String itemName : value.split(",")) {
						Item item = InstantiateResources.findItemByName(itemName);
						if (item != null) GameLogic.getPlayer().addItem(item);
					}
					break;
				case "Progress":
					isProgressSection = true;
					progressBuilder.append(value).append("\n");
					break;
				}
			}

			String fullProgress = progressBuilder.toString().trim();
			if (!fullProgress.isEmpty()) {
				ui.setTextArea(fullProgress);
			}

			ui.updateInventory();
			ui.displayRoomInfo();
			ui.updatePlayerInfo();

			System.out.println("Game loaded successfully.");

		} catch (IOException e) {
			System.err.println("Error loading game: " + e.getMessage());
		}
	}
}
