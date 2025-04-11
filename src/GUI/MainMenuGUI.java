package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import utils.GUIBuilder;


public class MainMenuGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MainMenuGUI() {
		setupUI();
	}
	
	private void setupUI() {
	    setTitle("Escape Room RPG");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	    setLayout(new BorderLayout());
	    setBackground(new Color(101, 67, 33));
	    setUndecorated(true);

	    JLayeredPane layeredPane = new JLayeredPane();
	    layeredPane.setPreferredSize(new Dimension(500, 400));

	    JLabel imageLabel = setupImage("Picture2.png", 500, 400);
	    imageLabel.setBounds(0, 0, 500, 400);

	    JPanel buttonPanel = createButtonPanel();
	    buttonPanel.setOpaque(false);
	    buttonPanel.setBounds(150, 200, 200, 300); // Adjust position over the image

	    layeredPane.add(imageLabel, Integer.valueOf(0));
	    layeredPane.add(buttonPanel, Integer.valueOf(1));

	    add(layeredPane, BorderLayout.CENTER);
	    pack();
	    setLocationRelativeTo(null);
	}

	private JPanel createButtonPanel() {
		JButton startButton = new JButton(
				 GUIBuilder.getPaddedHTMLLabel("Start New Game", 80), 
				 GUIBuilder.resizeImageIcon(new ImageIcon("icons8-add-48.png"), 
						 20,20));
		 startButton.addActionListener(this::handleStartButton);
		 startButton.setBackground(new Color(101, 67, 33));
		 startButton.setForeground(Color.white);
		 startButton.setFocusPainted(false);
		 startButton.setBorderPainted(false);
		 
		 JButton loadButton = new JButton(
				 GUIBuilder.getPaddedHTMLLabel("Load Saved Game", 80), 
				 GUIBuilder.resizeImageIcon(new ImageIcon("icons8-oval-loop-48.png"), 
						 20,20));
		 loadButton.addActionListener(this::handleLoadButton);
		 loadButton.setBackground(new Color(101, 67, 33));
		 loadButton.setForeground(Color.white);
		 loadButton.setFocusPainted(false);
		 loadButton.setBorderPainted(false);
		 
		 JButton ExitButton = new JButton(
				 GUIBuilder.getPaddedHTMLLabel("Exit", 80), 
				 GUIBuilder.resizeImageIcon(new ImageIcon("icons8-exit-48.png"), 
						 20,20));
		 ExitButton.addActionListener(this::handleExitButton);
		 ExitButton.setBackground(new Color(101, 67, 33));
		 ExitButton.setForeground(Color.white);
		 ExitButton.setFocusPainted(false);
		 ExitButton.setBorderPainted(false);
		 
		 
		 JPanel ButtonPanel = GUIBuilder.createCenteredButtonPanel(10, 10, 10,
	    			startButton, loadButton, ExitButton);
		 
	   
	    ButtonPanel.setOpaque(false);
	   
	    
	    return ButtonPanel;
	}


	
	private JLabel setupImage(String image,int h, int w) {
        ImageIcon imageIcon = new ImageIcon(image);
        ImageIcon logoIcon = GUIBuilder.resizeImage(imageIcon, h, w);
        return new JLabel(logoIcon);
    }
	
	private void handleStartButton(ActionEvent e) {
        // Handle start button action
    }
	
	private void handleLoadButton(ActionEvent e) {
        // Handle start button action
    }
	
	private void handleExitButton(ActionEvent e) {
        this.dispose();
    }
	
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            new MainMenuGUI().setVisible(true);      
	        });
	    }

}
