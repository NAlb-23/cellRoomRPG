package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import project_software_engineering.Driver;
import project_software_engineering.Player;
import utils.GUIBuilder;
import utils.RESOURCES;

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
		setUndecorated(true);

		createHeader();
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
		mainPanel.setBackground(RESOURCES.LIGHTBROWN);
		add(mainPanel, BorderLayout.CENTER);
	}

	private void createHeader() {
	    JLabel title = new JLabel("Dungeon Escape");
	    title.setForeground(Color.WHITE);

	    JButton xButton = GUIBuilder.createIconedButton(
	    		"",
	    		RESOURCES.EXIT_ICON,
	    		this.getBackground(),
	    		e -> this.dispose(),
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
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setBackground(getBackground());
		mainPanel.add(scrollPane, BorderLayout.CENTER);
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
		
		mainPanel.add(inputPanel, BorderLayout.SOUTH);
	}

	private void initializePlayerInfoPanel() {
		JPanel playerInfoPanel = new JPanel(new BorderLayout());
		userInfo = new JTextArea();
		userInfo.setEditable(false);
		userInfo.setLineWrap(true);
		userInfo.setWrapStyleWord(true);
		updatePlayerInfo();

		TitledBorder b = BorderFactory.createTitledBorder("Player Stats:");
		b.setTitleColor(Color.WHITE);
		playerInfoPanel.setBorder(b);
		
		playerInfoPanel.add(userInfo, BorderLayout.CENTER);
		playerInfoPanel.setPreferredSize(new Dimension(150, 150));
		
		playerInfoPanel.setBackground(RESOURCES.LIGHTBROWN);
		
		JPanel inventoryPanel = initializeInventoryPanel();

		JPanel rightPanel = new JPanel(new BorderLayout());
		
		rightPanel.setBackground(RESOURCES.LIGHTBROWN);
		
		rightPanel.add(playerInfoPanel, BorderLayout.CENTER);
		rightPanel.add(inventoryPanel, BorderLayout.SOUTH);

		mainPanel.add(rightPanel, BorderLayout.EAST);
	}

	private JPanel initializeInventoryPanel() {
		JPanel inventoryPanel = new JPanel(new BorderLayout());
		TitledBorder b = BorderFactory.createTitledBorder("Inventory");
		b.setTitleColor(Color.WHITE);
		inventoryPanel.setBorder(b);

		inventoryModel = new DefaultListModel<>();
		inventoryList = new JList<>(inventoryModel);
		JScrollPane inventoryScrollPane = new JScrollPane(inventoryList);

		inventoryPanel.add(inventoryScrollPane, BorderLayout.CENTER);
		inventoryPanel.setPreferredSize(new Dimension(150, 100));
		
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

	private void displayMessage(String message) {
		textArea.append(message + "\n");
	}

	private void processCommand(String command) {
		displayMessage(Driver.processCommand(command));

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

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new TextGameUI(new Player()).setVisible(true);      
		});
	}

}
