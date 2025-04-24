package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import project_software_engineering.Driver;
import project_software_engineering.Player;
import resources.POI;
import resources.Room;
import utils.GUIBuilder;
import utils.RESOURCES;

/**
 * GUI class for the Escape Room RPG game.
 * Handles the display of the main interface, processes player input, and shows real-time game updates
 * such as room info, inventory, and player status.
 */
public class TextGameUI extends JFrame {

    private JPanel mainPanel;
    private JTextArea textArea;
    private JTextArea userInfo;
    private JTextArea roomInfo;
    private JTextField inputField;
    private JButton submitButton;
    private JTextArea inventory;
    private Player user;

    /**
     * Constructs the game UI and initializes all GUI components.
     *
     * @param user   the player object associated with this session.
     * @param isSave if true, loads a previously saved game state.
     */
    public TextGameUI(Player user, Boolean isSave) {
        super(RESOURCES.TITLE);
        this.user = user;

        user.addInventoryListener(this::updateInventory);
        user.addRoomChangeListener(this::displayRoomInfo);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(RESOURCES.FRAME_SIZE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setUndecorated(true);
        ((JComponent) getRootPane().getContentPane()).setBorder(
                BorderFactory.createLineBorder(RESOURCES.DARK_BROWN, 5, true)
        );

        initializeMainPanel();

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(RESOURCES.IMAGE_DIMENSION);

        JLabel imageLabel = GUIBuilder.setupImage(RESOURCES.RESLOC+"Picture2.png", 500, 400);
	    imageLabel.setBounds(0, 0, RESOURCES.IMAGE_DIMENSION.width, RESOURCES.IMAGE_DIMENSION.height);

        mainPanel.setOpaque(false);
        mainPanel.setBounds(10, 10, 310, 320);

        layeredPane.add(imageLabel, Integer.valueOf(0));
        layeredPane.add(mainPanel, Integer.valueOf(1));

        add(layeredPane, BorderLayout.CENTER);
        pack();

        createHeader();
        initializeTextArea();
        initializeInputPanel();
        initializeLeftPanel();

        if (isSave) loadGame();
        setVisible(true);
        inputField.requestFocusInWindow();

        displayMessage(String.format(RESOURCES.START_MESSAGE, user.getName()));
    }

    // ---------- Public Methods ----------

    /**
     * Sets the main text area's content.
     *
     * @param progress the text to display in the main game window.
     */
    public void setTextArea(String progress) {
        textArea.setText(progress);
    }

    /**
     * Updates the room information display with the current room's name and POIs.
     */
    public void displayRoomInfo() {
        Room currentRoom = user.getCurrentRoom();
        if (currentRoom != null) {
            roomInfo.setText("You are in " + currentRoom.getName() + "\n");
            roomInfo.append(currentRoom.getlistofPOINames());
        } else {
            roomInfo.setText("You are not in a valid room.\n");
        }
    }

    /**
     * Updates the player info display with current hunger, rest, and warmth levels.
     */
    public void updatePlayerInfo() {
     
        userInfo.setText("Hunger: " + user.getHungerLevel());
        userInfo.append("\nRest: " + user.getRestLevel());
        userInfo.append("\nWarmth: " + user.getWarmthLevel());
        userInfo.append("\nThirst :" +user.getThirstLevel());
    }

    /**
     * Updates the inventory display with the latest player inventory.
     */
    public void updateInventory() {
        inventory.setText(user.showInventory());
    }

    /**
     * Retrieves the current game text for saving.
     *
     * @return the content of the main game text area.
     */
    public String getSave() {
        return textArea.getText();
    }

    /**
     * Loads the previously saved game state.
     */
    public void loadGame() {
        Driver.LoadGameState(this);
    }

    /**
     * Application entry point.
     *
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TextGameUI(new Player(), false).setVisible(true));
    }

    // ---------- Private Initialization Methods ----------

    /**
     * Initializes the main game panel.
     */
    private void initializeMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(RESOURCES.LIGHT_BROWN);
    }

    /**
     * Builds the game window header with title and control buttons.
     */
    private void createHeader() {
        JLabel title = new JLabel(RESOURCES.HEADER_TITLE);
        title.setForeground(RESOURCES.TEXT_COLOR);

        JButton xButton = GUIBuilder.createIconedButton(
                "", RESOURCES.EXIT_ICON, getBackground(),
                e -> handleExitButton(), "Exit", false);

        JButton QButton = GUIBuilder.createIconedButton(
                "", RESOURCES.HELP_ICON, getBackground(),
                e -> JOptionPane.showMessageDialog(null, RESOURCES.HELP_MESSAGE), "Help", false);

        JPanel header = GUIBuilder.createHeaderPanel(title, RESOURCES.DARK_BROWN, QButton, xButton);
        add(header, BorderLayout.NORTH);
    }

    /**
     * Initializes the text display area.
     */
    private void initializeTextArea() {
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(true);
        textArea.setBackground(RESOURCES.TEXT_AREA_BG);
        textArea.setForeground(RESOURCES.TEXT_COLOR);
        textArea.setBorder(null);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBackground(RESOURCES.TRANSPARENT_SCROLL_BG);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Initializes the user input panel with a text field and submit button.
     */
    private void initializeInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();

        submitButton = GUIBuilder.createIconedButton(
                "", RESOURCES.SUBMIT_ICON, getBackground(),
                e -> handleUserInput(), "submit", true);

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);
        inputPanel.setBackground(RESOURCES.LIGHT_BROWN);

        add(inputPanel, BorderLayout.SOUTH);
    }

    /**
     * Initializes the left-side panel with room info, player info, and inventory.
     */
    private void initializeLeftPanel() {
        JPanel inventoryPanel = initializeInventoryPanel();
        JPanel playerInfoPanel = initializePlayerInfoPanel();
        JPanel roomInfoPanel = initializeQuickInfoPanel();

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(RESOURCES.LIGHT_BROWN);
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        leftPanel.add(roomInfoPanel, BorderLayout.NORTH);
        leftPanel.add(playerInfoPanel, BorderLayout.CENTER);
        leftPanel.add(inventoryPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.EAST);
    }

    private JPanel initializeQuickInfoPanel() {
        roomInfo = createInvisibleTextArea(RESOURCES.DEFAULT_ROOM_TEXT, RESOURCES.QUICK_INFO_SIZE);
        JScrollPane scrollpane = new JScrollPane(roomInfo);
        scrollpane.setBorder(null);
        scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollpane.setPreferredSize(RESOURCES.QUICK_INFO_SIZE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(RESOURCES.LIGHT_BROWN);
        panel.setPreferredSize(RESOURCES.QUICK_INFO_SIZE);
        panel.setBorder(createTitledBorder(RESOURCES.ROOM_LABEL));
        panel.add(scrollpane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel initializePlayerInfoPanel() {
        userInfo = createInvisibleTextArea("", RESOURCES.PLAYER_INFO_SIZE);
        JScrollPane playerInfoPanel = new JScrollPane(userInfo);
        playerInfoPanel.setBorder(createTitledBorder(RESOURCES.PLAYER_LABEL));
        playerInfoPanel.setPreferredSize(RESOURCES.PLAYER_INFO_SIZE);
        playerInfoPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        playerInfoPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        playerInfoPanel.setBackground(RESOURCES.LIGHT_BROWN);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(playerInfoPanel, BorderLayout.CENTER);
        panel.setBackground(RESOURCES.LIGHT_BROWN);
        panel.setPreferredSize(RESOURCES.PLAYER_INFO_SIZE);

        updatePlayerInfo();
        return panel;
    }

    private JPanel initializeInventoryPanel() {
        inventory = createInvisibleTextArea("", RESOURCES.INVENTORY_SIZE);
        JScrollPane scrollpane = new JScrollPane(inventory);
        scrollpane.setBorder(null);
        scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollpane.setPreferredSize(RESOURCES.INVENTORY_SIZE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollpane, BorderLayout.CENTER);
        panel.setBackground(RESOURCES.LIGHT_BROWN);
        panel.setPreferredSize(RESOURCES.INVENTORY_SIZE);
        panel.setBorder(createTitledBorder(RESOURCES.INVENTORY_LABEL));
        return panel;
    }

    // ---------- Private Utility Methods ----------

    private JTextArea createInvisibleTextArea(String initialText, Dimension size) {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(initialText);
        textArea.setPreferredSize(size);
        textArea.setBackground(RESOURCES.LIGHT_BROWN);
        textArea.setForeground(RESOURCES.TEXT_COLOR);
        textArea.setFocusable(false);
        return textArea;
    }

    private TitledBorder createTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleColor(RESOURCES.TEXT_COLOR);
        return border;
    }

    private void handleUserInput() {
        String input = inputField.getText().trim();
        if (!input.isEmpty()) {
            processCommand(input);
            inputField.setText("");
        }
    }

    private void processCommand(String command) {
        displayMessage("> " + command);  // Show user input first
        displayMessage(Driver.processCommand(command));  // Then show the response

        if (user.getStatus().equals(RESOURCES.Status.GAMEPLAY)) {
            registerPOIListeners();
            displayRoomInfo();
            updatePlayerInfo();
        }
    }

    private void displayMessage(String message) {
        textArea.append("====================\n" + message + "\n");
    }




    private void registerPOIListeners() {
        for (POI poi : user.getCurrentRoom().getPois()) {
            poi.addVisibilityListener(this::displayRoomInfo);
        }
    }

    private void handleExitButton() {
        int choice = JOptionPane.showConfirmDialog(
                null,
                "Would you like to save the game before exiting?",
                "Exit Game",
                JOptionPane.YES_NO_CANCEL_OPTION
        );

        switch (choice) {
            case JOptionPane.YES_OPTION -> {
                Driver.SaveGameState(textArea.getText());
                System.exit(0);
            }
            case JOptionPane.NO_OPTION -> System.exit(0);
            case JOptionPane.CANCEL_OPTION, JOptionPane.CLOSED_OPTION -> {
            }
        }
    }
}
