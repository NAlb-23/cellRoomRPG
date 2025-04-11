package GUI;

import javax.swing.*;
import project_software_engineering.Player;
import java.awt.*;
import java.awt.event.ActionEvent;
import utils.GUIBuilder;

public class NewGameGUI extends JFrame {
    
    public NewGameGUI() {
        setupUI();
    }
    
    private void setupUI() {
        setTitle("Escape Room RPG - New Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(new Color(101, 67, 33));
        setUndecorated(true);
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(500, 400));
        
        JLabel imageLabel = setupImage("Picture2.png", 500, 400);
        imageLabel.setBounds(0, 0, 500, 400);
        
        JPanel contentPanel = createContentPanel();
        contentPanel.setOpaque(false);
        contentPanel.setBounds(50, 100, 400, 200);
        
        layeredPane.add(imageLabel, Integer.valueOf(0));
        layeredPane.add(contentPanel, Integer.valueOf(1));
        
        add(layeredPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea introText = new JTextArea();
        introText.setEditable(false);
        introText.setLineWrap(true);
        introText.setWrapStyleWord(true);
        introText.setText("You slowly open your eyes and find yourself in a dimly lit cell.\n" +
                "You clutch your head as it aches and slowly get up from the floor.\n" +
                "You try to remember how you wound up here but to no avail.\n" +
                "You try to remember who you are…");
        introText.setBackground(new Color(101, 67, 33, 128));  
        introText.setFocusable(false);
        introText.setFocusTraversalPolicyProvider(false);
        introText.setForeground(Color.white);
        panel.add(introText, BorderLayout.NORTH);
        
        // Set italics font
        introText.setFont(introText.getFont().deriveFont(Font.ITALIC));
        
        JPanel inputPanel = createInputPanel();
        inputPanel.setOpaque(false);
        panel.add(inputPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton returnButton = GUIBuilder.createDefaultEnterButtonTrigger(
          		 GUIBuilder.getPaddedHTMLLabel("main menu", 50), 
           		 this::handleReturnButton);
        returnButton.setBackground(new Color(101, 67, 33));
        returnButton.setForeground(Color.white);
        returnButton.setFocusPainted(false);
        returnButton.setBorderPainted(false);
        
        buttonPanel.add(returnButton);
        buttonPanel.setOpaque(false);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
       
        return panel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel label = new JLabel("What is your name?");
        JTextField textField = new JTextField(15);
        label.setForeground(Color.white);
        
        JButton okButton = GUIBuilder.createDefaultEnterButtonTrigger(
        		 GUIBuilder.getPaddedHTMLLabel("OK", 20), 
        		 e -> handleUserInput(textField));
       
        okButton.setBackground(new Color(101, 67, 33));
        okButton.setForeground(Color.white);
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        
        panel.add(label);
        panel.add(textField);
        panel.add(okButton);
        return panel;
    }
    

    private void handleUserInput(JTextField textField) {
        String userName = textField.getText().trim();
        if (!userName.isEmpty()) {
            Player user = new Player(userName);
            SwingUtilities.invokeLater(() -> new TextGameUI(user));
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a name.");
        }
    }
    
    private JLabel setupImage(String image, int h, int w) {
        ImageIcon imageIcon = new ImageIcon(image);
        ImageIcon logoIcon = GUIBuilder.resizeImage(imageIcon, h, w);
        return new JLabel(logoIcon);
    }
    
    private void handleReturnButton (ActionEvent e) {
    	this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NewGameGUI().setVisible(true));
    }
}