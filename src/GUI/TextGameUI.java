package GUI;

import javax.swing.*;

import project_software_engineering.Driver;
import project_software_engineering.Player;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TextGameUI {

	private JFrame frame;
	private JPanel mainPanel;
	private JTextArea textArea;
	private JTextArea userInfo;
	private JTextField inputField;
	private JButton submitButton;
	private Player user;
	private DefaultListModel<String> inventoryModel;
	private JList<String> inventoryList;

	public TextGameUI(Player user) {
		this.user = user;
		initializeFrame();
		initializeTextArea();
		initializeInputPanel();
		initializePlayerInfoPanel();
		frame.setVisible(true);
		displayMessage("Glad to see you’re up, "+user.getName()+". You’ve been out for a while…"
				+ "\nWhy don’t you try to \"look around\"?");
	}

	private void initializeFrame() {
		frame = new JFrame("Escape Room RPG");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);

		// Create a main panel with margins
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		frame.add(mainPanel, BorderLayout.CENTER);
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

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleUserInput();
			}
		});

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

		// Create a container panel for Player Info & Inventory
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
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
		String hunger = new String();
		if(user.getHungerlevel()) hunger = "Full";
		else hunger = "hungry";
		userInfo.setText("Hunger: " + hunger);
		
		String rest = new String();
		if(user.getRestLevel()) rest = "well rested";
		else rest = "tired";
		userInfo.append("\nRest: " + rest);
		
		String warmth = new String();
		if(user.getWarmthlevel()) warmth = "Warm";
		else warmth = "cold";
		userInfo.append("\nWarmth: " + warmth);
	}

	public void updateInventory(List<String> items) {
		inventoryModel.clear();
		for (String item : items) {
			inventoryModel.addElement(item);
		}
	}


	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);
            
            JDialog dialog = new JDialog(frame, "Hello", true);
            dialog.setLayout(new BorderLayout());
            
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            // Introduction text area
            JTextArea introText = new JTextArea();
            introText.setEditable(false);
            introText.setLineWrap(true);
            introText.setWrapStyleWord(true);
            introText.setText("You slowly open your eyes and find yourself in a dimly lit cell.\n" +
                    "You clutch your head as it aches and slowly get up from the floor.\n" +
                    "You try to remember how you wound up here but to no avail.\n" +
                    "You try to remember who you are…");
            
            introText.setBackground(frame.getBackground());
            
            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            textPanel.add(introText, BorderLayout.CENTER);
            
            // Input Panel
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new FlowLayout());
            inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel label = new JLabel("What is your name?");
            JTextField textField = new JTextField(15);
            JButton okButton = new JButton("OK");
            
            okButton.addActionListener(e -> {
            	String userName = textField.getText().trim();
                if (!userName.isEmpty()) {
                    Player user = new Player(userName); 
                    SwingUtilities.invokeLater(() -> new TextGameUI(user)); 
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please enter a name.");
                }
            });
            
            inputPanel.add(label);
            inputPanel.add(textField);
            inputPanel.add(okButton);
            
            // Adding panels to content panel
            contentPanel.add(textPanel, BorderLayout.NORTH);
            contentPanel.add(inputPanel, BorderLayout.SOUTH);
            
            dialog.add(contentPanel, BorderLayout.CENTER);
            dialog.pack();
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        });
    }

}
