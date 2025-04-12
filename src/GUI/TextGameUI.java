package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import project_software_engineering.Driver;
import project_software_engineering.Player;

public class TextGameUI extends JFrame {

	private JPanel mainPanel;
	private JTextArea textArea;
	private JTextArea userInfo;
	private JTextField inputField;
	private JButton submitButton;
	private Player user;
	private DefaultListModel<String> inventoryModel;
	private JList<String> inventoryList;

	public TextGameUI(Player user) {
		super("Escape Room RPG");
		this.user = user;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		initializeMainPanel();
		initializeTextArea();
		initializeInputPanel();
		initializePlayerInfoPanel();

		setVisible(true);

		displayMessage("Glad to see you’re up, " + user.getName() + ". You’ve been out for a while…"
				+ "\nWhy don’t you try to \"look around\"?");
	}

	private void initializeMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(mainPanel, BorderLayout.CENTER);
	}

	private void initializeTextArea() {
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
	}

	private void initializeInputPanel() {
		JPanel inputPanel = new JPanel(new BorderLayout());
		inputField = new JTextField();
		submitButton = new JButton("Submit");

		submitButton.addActionListener(e -> handleUserInput());

		inputPanel.add(inputField, BorderLayout.CENTER);
		inputPanel.add(submitButton, BorderLayout.EAST);
		mainPanel.add(inputPanel, BorderLayout.SOUTH);
	}

	private void initializePlayerInfoPanel() {
		JPanel playerInfoPanel = new JPanel(new BorderLayout());
		userInfo = new JTextArea();
		userInfo.setEditable(false);
		userInfo.setLineWrap(true);
		userInfo.setWrapStyleWord(true);
		updatePlayerInfo();

		playerInfoPanel.add(new JLabel("Player Stats:"), BorderLayout.NORTH);
		playerInfoPanel.add(userInfo, BorderLayout.CENTER);
		playerInfoPanel.setPreferredSize(new Dimension(150, 150));
		JPanel inventoryPanel = initializeInventoryPanel();

		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(playerInfoPanel, BorderLayout.NORTH);
		rightPanel.add(inventoryPanel, BorderLayout.SOUTH);

		mainPanel.add(rightPanel, BorderLayout.EAST);
	}

	private JPanel initializeInventoryPanel() {
		JPanel inventoryPanel = new JPanel(new BorderLayout());
		inventoryPanel.setBorder(BorderFactory.createTitledBorder("Inventory"));

		inventoryModel = new DefaultListModel<>();
		inventoryList = new JList<>(inventoryModel);
		JScrollPane inventoryScrollPane = new JScrollPane(inventoryList);

		inventoryPanel.add(inventoryScrollPane, BorderLayout.CENTER);
		inventoryPanel.setPreferredSize(new Dimension(150, 100));

		return inventoryPanel;
	}

	private void handleUserInput() {
		String input = inputField.getText().trim();
		if (!input.isEmpty()) {
			processCommand(input);
			inputField.setText("");
		}
	}

	private void displayMessage(String message) {
		textArea.append(message + "\n");
	}

	private void processCommand(String command) {
		displayMessage(Driver.processCommand(command));
		// TODO: Implement game logic here
	}

	private void updatePlayerInfo() {
		String hunger = user.getHungerlevel() ? "Full" : "Hungry";
		String rest = user.getRestLevel() ? "Well rested" : "Tired";
		String warmth = user.getWarmthlevel() ? "Warm" : "Cold";

		userInfo.setText("Hunger: " + hunger);
		userInfo.append("\nRest: " + rest);
		userInfo.append("\nWarmth: " + warmth);
	}

	public void updateInventory(List<String> items) {
		inventoryModel.clear();
		for (String item : items) {
			inventoryModel.addElement(item);
		}
	}

	public String getSave() {
		
		return textArea.getText();
	}
}
