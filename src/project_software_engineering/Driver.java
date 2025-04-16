package project_software_engineering;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import GUI.MainMenuGUI;
import GUI.NewGameGUI;
import GUI.TextGameUI;
import resources.InstantiateResources;
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
				startGame();
			}
			break;

		case LOADSAVE:
			System.out.println("Load Save");
			// TODO: load and launch saved game
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
	
	private static void startGame() {
		CountDownLatch latch = new CountDownLatch(1);

		SwingUtilities.invokeLater(() -> {
			TextGameUI gui = new TextGameUI(gameLogic.player);

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


	public static String processCommand(String command) {
		if(gameLogic.player.getStatus().equals(RESOURCES.Status.TUTORIAL)) 
			return gameLogic.processTutorial(command);
		else 
			return gameLogic.processCommand(command);
	}

	public static void SaveGameState(String progress) {
		System.out.println("PLayer: "+gameLogic.player);
		System.out.println(progress);
	}
}
