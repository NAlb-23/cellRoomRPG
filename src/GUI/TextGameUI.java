package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import project_software_engineering.Driver;
import project_software_engineering.Player;
import resources.POI;
import resources.Room;
import utils.GUIBuilder;
import utils.RESOURCES;

public class TextGameUI extends JFrame {



	private JPanel mainPanel;
	private JTextArea textArea;
	private JTextArea userInfo;
	private JTextArea roomInfo;
	private JTextField inputField;
	private JButton submitButton;
	private Player user;
	private JTextArea inventory;

	public TextGameUI(Player user, Boolean isSave) {
	    super("Escape Room RPG");
	    this.user = user;
	    	
	    user.addInventoryListener(this::updateInventory);
	    user.addRoomChangeListener(this::displayRoomInfo);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(600, 400);
	    setLayout(new BorderLayout());
	    setLocationRelativeTo(null);
	    setUndecorated(true);
	    ((JComponent) getRootPane().getContentPane()).setBorder(BorderFactory.createLineBorder(RESOURCES.DARKBROWN, 5, true));

	    initializeMainPanel();  // <-- Initialize before usage

	    JLayeredPane layeredPane = new JLayeredPane();
	    layeredPane.setPreferredSize(new Dimension(500, 400));

	    JLabel imageLabel = setupImage("Picture2.png", 500, 400);
	    imageLabel.setBounds(0, 0, 500, 400);

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
	    
	    displayMessage("Glad to see you’re up, " + user.getName() + ". You’ve been out for a while…"
				+ "\nWhy don’t you try to \"look around\"?");
	}


	private void initializeMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.setBackground(RESOURCES.LIGHTBROWN);
	}

	private void createHeader() {
		JLabel title = new JLabel("Dungeon Escape");
		title.setForeground(Color.WHITE);

		JButton xButton = GUIBuilder.createIconedButton(
				"",
				RESOURCES.EXIT_ICON,
				this.getBackground(),
				e -> handleExitButton(),
				"Exit",
				false
				);  

		JButton QButton = GUIBuilder.createIconedButton(
				"",
				RESOURCES.HELP_ICON,
				this.getBackground(),
				e -> JOptionPane.showMessageDialog(null,"Hang in there, help is on the way"),
				"Help",
				false
				);  

		JPanel header = GUIBuilder.createHeaderPanel(
				title, 
				RESOURCES.DARKBROWN,
				QButton,
				xButton
				);

		add(header, BorderLayout.NORTH);
	}

	private void initializeTextArea() {
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(textArea);
		scrollPane.setBackground(new Color(101, 67, 33, 128));
		scrollPane.setBorder(null);
		
//		textArea.setBackground(new Color(101, 67, 33, 128));
		textArea.setOpaque(true);
		textArea.setBackground(new Color(101, 67, 33));  // Without the alpha value

		textArea.setBorder(null);
		textArea.setForeground(Color.WHITE);
		
		mainPanel.add(scrollPane, BorderLayout.CENTER);
	}
	
	private JLabel setupImage(String image,int h, int w) {
        ImageIcon imageIcon = new ImageIcon(image);
        ImageIcon logoIcon = GUIBuilder.resizeImage(imageIcon, h, w);
        return new JLabel(logoIcon);
    }

	private void initializeInputPanel() {
		JPanel inputPanel = new JPanel(new BorderLayout());
		inputField = new JTextField();

		submitButton = GUIBuilder.createIconedButton(
				"",
				RESOURCES.SUBMIT_ICON,
				this.getBackground(),
				e -> handleUserInput(),
				"submit",
				true
				);  



		inputPanel.add(inputField, BorderLayout.CENTER);
		inputPanel.add(submitButton, BorderLayout.EAST);

		inputPanel.setBackground(RESOURCES.LIGHTBROWN);

		add(inputPanel, BorderLayout.SOUTH);
	}
	private void initializeLeftPanel() {

		JPanel inventoryPanel = initializeInventoryPanel();
		JPanel playerInfoPanel = initializePlayerInfoPanel();
		JPanel roomInfoPanel = initializeQuickInfoPanel();

		JPanel leftPanel = new JPanel(new BorderLayout());

		leftPanel.setBackground(RESOURCES.LIGHTBROWN);

		leftPanel.add(roomInfoPanel, BorderLayout.NORTH);
		leftPanel.add(playerInfoPanel, BorderLayout.CENTER);
		leftPanel.add(inventoryPanel, BorderLayout.SOUTH);
		
		leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		add(leftPanel, BorderLayout.EAST);

	}

	// Helper method to initialize an invisible JTextArea
	private JTextArea createInvisibleTextArea(String initialText, Dimension preferredSize) {
		JTextArea textArea = new JTextArea();
	    textArea.setEditable(false);
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);
	    textArea.setText(initialText);
	    textArea.setPreferredSize(preferredSize);
	    textArea.setBackground(RESOURCES.LIGHTBROWN);
	    textArea.setFocusable(false);
	    textArea.setFocusTraversalPolicyProvider(false);
	    textArea.setForeground(Color.white);
	    return textArea;
	}

	private JPanel initializeQuickInfoPanel() {
	    JPanel quickInfoPanel = new JPanel(new BorderLayout());

	    // Use the helper method to create the JTextArea
	    roomInfo = createInvisibleTextArea("You wake up in cell 2", new Dimension(150, 125));

	    // Create and configure the JScrollPane
	    JScrollPane scrollpane = new JScrollPane();
	    scrollpane.setViewportView(roomInfo);
	    scrollpane.setBorder(null);

	    // Hide both horizontal and vertical scrollbars
	    scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    scrollpane.setPreferredSize(new Dimension(150, 125));

	    quickInfoPanel.add(scrollpane, BorderLayout.CENTER);
	    quickInfoPanel.setBackground(RESOURCES.LIGHTBROWN);
	    quickInfoPanel.setPreferredSize(new Dimension(150, 125));
	    quickInfoPanel.setBorder(createTitledBorder("Your Surrounding: "));

	    return quickInfoPanel;
	}

	private JPanel initializePlayerInfoPanel() {
	    // Use the helper method to create the JTextArea
	    userInfo = createInvisibleTextArea("", new Dimension(150, 150));

	    // Create and configure the JScrollPane
	    JScrollPane playerInfoPanel = new JScrollPane();
	    playerInfoPanel.setBorder(createTitledBorder("Player Stats: "));
	    playerInfoPanel.setViewportView(userInfo);
	    playerInfoPanel.setPreferredSize(new Dimension(150, 150));
	    playerInfoPanel.setBackground(RESOURCES.LIGHTBROWN);
	    
	    playerInfoPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    playerInfoPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

	    // Create and configure the main JPanel
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.add(playerInfoPanel, BorderLayout.CENTER);
	    panel.setBackground(RESOURCES.LIGHTBROWN);
	    panel.setPreferredSize(new Dimension(150, 150));

	    // Update player info content
	    updatePlayerInfo();

	    return panel;
	}

	// Helper method to create the TitledBorder
	private TitledBorder createTitledBorder(String title) {
	    TitledBorder border = BorderFactory.createTitledBorder(title);
	    border.setTitleColor(Color.WHITE);
	    return border;
	}

	public void updatePlayerInfo() {
		String hunger = user.getHungerlevel() ? "Full" : "Hungry";
		String rest = user.getRestLevel() ? "Well rested" : "Tired";
		String warmth = user.getWarmthlevel() ? "Warm" : "Cold";

		userInfo.setText("Hunger: " + hunger);
		userInfo.append("\nRest: " + rest);
		userInfo.append("\nWarmth: " + warmth);
	}

	private JPanel initializeInventoryPanel() {
		JPanel inventoryPanel = new JPanel(new BorderLayout());

	    // Use the helper method to create the JTextArea
	   inventory = createInvisibleTextArea("", new Dimension(150, 125));

	    // Create and configure the JScrollPane
	    JScrollPane scrollpane = new JScrollPane();
	    scrollpane.setBorder(null);

	    // Hide both horizontal and vertical scrollbars
	    scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    scrollpane.setPreferredSize(new Dimension(150, 125));
	    scrollpane.setBackground(RESOURCES.LIGHTBROWN);

		scrollpane.setViewportView(inventory);

		inventoryPanel.add(scrollpane, BorderLayout.CENTER);
		inventoryPanel.setPreferredSize(new Dimension(150, 100));
		inventoryPanel.setBorder(createTitledBorder("Invintory: "));

		inventoryPanel.setBackground(RESOURCES.LIGHTBROWN);


		return inventoryPanel;
	}

	private void handleUserInput() {
		String input = inputField.getText().trim();
		if (!input.isEmpty()) {
			processCommand(input);
			inputField.setText("");
		}
	}

	private void processCommand(String command) {
	    displayMessage(Driver.processCommand(command));
	    if(user.getStatus().equals(RESOURCES.Status.GAMEPLAY)) {
	        registerPOIListeners(); // always hook into the latest room's POIs
	        displayRoomInfo();
	        updatePlayerInfo();
	    }
	}
	
	public void setTextArea(String progress) {
		textArea.setText(progress);
	}


	private void displayMessage(String message) {
		textArea.append(message + "\n");
	}

	public void displayRoomInfo() {
	    Room currentRoom = user.getCurrentRoom();

	    if (currentRoom != null) {
	        roomInfo.setText("You are in " + currentRoom.getName() + "\n");
	        roomInfo.append(currentRoom.getlistofPOINames());
	    } else {
	        roomInfo.setText("You are not in a valid room.\n");
	    }
	}

	
	public void updateInventory() {
		inventory.setText(user.showInventory());
	}

	private void registerPOIListeners() {
	    for (POI poi : user.getCurrentRoom().getPois()) {
	        poi.addVisibilityListener(() -> displayRoomInfo());
	    }
	}

	public String getSave() {

		return textArea.getText();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {

			new TextGameUI(new Player(), false).setVisible(true);      
		});
	}
	
	private void handleExitButton() {
	    int choice = JOptionPane.showConfirmDialog(
	        null, 
	        "Would you like to save the game before exiting?", 
	        "Exit Game", 
	        JOptionPane.YES_NO_CANCEL_OPTION
	    );

	    // Handle the user's choice
	    switch (choice) {
	        case JOptionPane.YES_OPTION:
	            // Call the save game method from Driver
	            Driver.SaveGameState(textArea.getText()); // Save the game state
	            System.exit(0); // Exit the game after saving
	            break;

	        case JOptionPane.NO_OPTION:
	            System.exit(0); // Exit without saving
	            break;

	        case JOptionPane.CANCEL_OPTION:
	        case JOptionPane.CLOSED_OPTION:
	            // Don't exit, just return
	            break;
	    }
	}
	
	public void loadGame() {
	    Driver.LoadGameState(this);  // Pass `this` (the `TextGameUI` instance) to update the UI after loading
	}




}
