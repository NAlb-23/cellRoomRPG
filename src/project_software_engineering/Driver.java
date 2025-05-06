package project_software_engineering;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import GUI.EscapeEnding;
import GUI.MainMenuGUI;
import GUI.NewGameGUI;
import GUI.TextGameUI;
import resources.InstantiateResources;
import resources.Item;
import resources.POI;
import resources.Room;
import utils.RESOURCES;
import utils.GameSaver;

/**
 * The Driver class serves as the entry point for the game.
 * It handles displaying menus, starting new games, loading saved games, and saving the game state.
 */
public class Driver {
    private static final int EXIT = -1;
    private static final int NEWSAVE = 1;
    private static final int LOADSAVE = 0;

    private static boolean isTickPending = false; // Tracks whether a tick is pending for the next command
    

    public static String processCommand(String command) {
        if (GameLogic.getPlayer().getStatus() == RESOURCES.Status.TUTORIAL) {
            return GameLogic.processTutorial(command);
        } else if(GameLogic.getPlayer().getStatus() == RESOURCES.Status.COMPLETE) {
        	String result = GameLogic.processEscapeSequence(command);
        	if(result.equals("complete")) {
        		SwingUtilities.invokeLater(EscapeEnding::new);
        	}
        	return result;
        }
        else {
            if (isTickPending) {
                GameLogic.getPlayer().tick();
            }
            isTickPending = !isTickPending;

            String result = GameLogic.processCommand(command);

            return result;
        }
        
    }

    /**
     * Starts the game by showing the main menu and handling the user's choice.
     */
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

    /**
     * Displays the main menu and waits for the user's choice.
     * @return The user's choice as an integer.
     */
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

    /**
     * Displays the new game screen and waits for the user's choice.
     * @return The user's choice as an integer.
     */
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

    /**
     * Waits for the given CountDownLatch to reach zero.
     * @param latch The latch to await.
     */
    private static void awaitLatch(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Latch interrupted: " + e.getMessage());
        }
    }

    /**
     * Starts the main game with the specified save state.
     * @param isSave True if the game should be loaded from a save state.
     */
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
                            GameSaver.SaveGameState(progress);
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

   
}
