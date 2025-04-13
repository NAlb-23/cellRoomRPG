package utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

/**
 * The GUIBuilder class provides utility methods for building graphical user interfaces (GUIs).
 * It includes methods for resizing images, creating buttons, creating button panels, 
 * and creating various GUI components. Additionally, it provides a method for displaying 
 * pop-up messages using JOptionPane.
 *
 * @see javax.swing.JOptionPane
 * @see java.awt.BorderLayout
 * @see java.awt.Color
 * @see java.awt.FlowLayout
 * @see java.awt.GridLayout
 * @see java.awt.Image
 * 
 * @since August 7 2023.
 * 
 * @author Nick A.
 * 
 * NOTE: I built this class over the summer for a personal project I was working on.
 * 
 */

public class GUIBuilder {


	/**
	 * Resizes the given ImageIcon to the specified dimensions.
	 *
	 * @param imageIcon The original ImageIcon to resize.
	 * @param width     The desired width of the resized image.
	 * @param height    The desired height of the resized image.
	 * @return The resized ImageIcon.
	 */
	public static ImageIcon resizeImage(ImageIcon imageIcon, int width, int height) {
		Image image = imageIcon.getImage(); // Get the Image object from ImageIcon
		Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImage); // Create a new ImageIcon with the resized Image
	}

	// Utility method to resize ImageIcon
	public static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImg);
	}


	/**
	 * Generates a padded HTML string for a button label with a specified width.
	 *
	 * @param label The label text to be displayed on the button.
	 * @param width The width (in pixels) for the padded label.
	 * @return A padded HTML string for the button label with the specified width.
	 */
	public static String getPaddedHTMLLabel(String label, int width) {
		return String.format("<html><div style='width: %dpx; text-align: center;'>%s</div></html>", width, label);
	}


	/**
	 * Creates a button with the specified label and action listener.
	 *
	 * @param text      		The label text for the button.
	 * @param actionListener  	The action listener for the button.
	 * @return The created button.
	 */
	public static JButton createButton(String text, ActionListener actionListener) {
		JButton button = new JButton(text);
		button.addActionListener(actionListener);
		return button;
	}

	// New method to create the exit button
	public static JButton createIconedButton(
			String title,
			ImageIcon imageIcon, 
			Color color, 
			ActionListener e, 
			String hint, 
			boolean isDefaultEnter) 
	{
		JButton xButton = new JButton(title, imageIcon);

		xButton.setBackground(color);
		xButton.setBorder(null);
		xButton.setFocusPainted(false);
		xButton.setBorderPainted(false);
		xButton.setContentAreaFilled(false);
		

		// Add mouse listener to change cursor
		xButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// Change the cursor to hand cursor when hovering over the button
				xButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Reset the cursor to default when the mouse leaves the button
				xButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// Add action listener for the button
		xButton.addActionListener(e);
		
		if(hint != null) {
			xButton.setToolTipText(hint);
			
		}
		
		if(isDefaultEnter) {
			@SuppressWarnings("serial")
			AbstractAction buttonAction = new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Add your action code here
					xButton.doClick(); // Simulate a button click
				}
			};
			
			xButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
					KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "clickButton");
			xButton.getActionMap().put("clickButton", buttonAction);
		}

		return xButton;
	}


	/**
	 * Creates a JButton with the specified text, centered within its container.
	 *
	 * @param text           The text to display on the button.
	 * @param actionListener The ActionListener to associate with the button.
	 * @return A JButton with the specified text, centered within its container.
	 */
	public static JButton createCenteredButton(String text, ActionListener actionListener) {
		JButton button = new JButton(text);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(actionListener);
		return button;
	}


	/**
	 * Creates and adds labeled JTextFields to a JPanel.
	 *
	 * @param labels    An array of labels to be used for each JTextField.
	 * @param textFields An array of JTextFields to be added to the panel.
	 * 
	 * @return the panel that was created
	 */
	public static JPanel createLabeledTextFields(List<String> labels, JTextField[] textFields) {
		if (labels.size() != textFields.length) {
			throw new IllegalArgumentException("Number of labels and text fields must match.");
		}

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		for (int i = 0; i < labels.size(); i++) {
			panel.add(new JLabel(labels.get(i)));
			panel.add(textFields[i]);
		}
		return panel;
	}

	/**
	 * Creates and adds labeled JTextFields to a JPanel.
	 *
	 * @param labels    An array of labels to be used for each JTextField.
	 * @param textFields An array of JTextFields to be added to the panel.
	 * 
	 * @return the panel that was created
	 */
	public static JPanel createLabeledLoginFields(JTextField username, JPasswordField password) {

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new JLabel("Username: "));
		panel.add(username);
		panel.add(new JLabel("Password: "));
		panel.add(password);
		return panel;
	}
	/**
	 * Creates and adds labeled JTextFields to a JPanel.
	 *
	 * @param labels    An array of labels to be used for each JTextField.
	 * 
	 * @return the panel that was created
	 */
	public static JPanel createLabeledTextFields(List<String> labels) {

		JPanel panel = new JPanel(new FlowLayout());
		for (int i = 0; i < labels.size(); i++) {
			panel.add(new JLabel(labels.get(i)));
			panel.add(new JTextField());
		}
		return panel;
	}

	/**
	 * Creates a JPanel with a grid layout containing the specified JTextFields.
	 *
	 * @param textFields The JTextFields to be added to the grid layout panel.
	 * @return A JPanel with a grid layout containing the provided JTextFields.
	 */
	public static JPanel createGridTextFieldPanel(JTextField... textFields) {
		JPanel textFieldPanel = new JPanel(new GridLayout(2, 3));

		for (JTextField textField : textFields) {
			textFieldPanel.add(textField);
		}

		return textFieldPanel;
	}

	/**
	 * Creates a JPanel containing the specified array of JButtons.
	 *
	 * @param buttons The array of JButtons to be added to the JPanel.
	 * @return A JPanel containing the provided JButtons in a centered layout with spacing.
	 */
	public static JPanel createButtonPanel(JButton... buttons) {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center align buttons with spacing

		for (JButton button : buttons) {
			buttonPanel.add(button);
		}
		return buttonPanel;
	}

	/**
	 * Creates a JPanel containing centered buttons with specified vertical spacing and padding.
	 * Uses BoxLayout
	 *
	 * @param verticalSpacing The vertical space between buttons in pixels.
	 * @param topPadding The space at the top of the button panel in pixels.
	 * @param bottomPadding The space at the bottom of the button panel in pixels.
	 * @param buttons The JButton instances to be centered within the panel.
	 * @return A JPanel containing centered buttons with the specified spacing and padding.
	 */
	public static JPanel createCenteredButtonPanel(int verticalSpacing, int topPadding, int bottomPadding, JButton... buttons) {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		for (JButton button : buttons) {
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			buttonPanel.add(Box.createRigidArea(new Dimension(0, verticalSpacing)));
			buttonPanel.add(button);
		}

		// Add padding at the top and bottom
		buttonPanel.add(Box.createRigidArea(new Dimension(0, topPadding)));
		buttonPanel.add(Box.createRigidArea(new Dimension(0, bottomPadding)));

		buttonPanel.setBackground(new Color(200, 220, 240));

		return buttonPanel;
	}

	public static JPanel createHorizontalCenteredButtonPanel(int horizontalSpacing, int leftPadding, int rightPadding, JButton... buttons) {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS)); // Set horizontal layout

		buttonPanel.add(Box.createRigidArea(new Dimension(leftPadding, 0))); // Left padding

		for (int i = 0; i < buttons.length; i++) {
			buttonPanel.add(buttons[i]);
			if (i < buttons.length - 1) {
				buttonPanel.add(Box.createRigidArea(new Dimension(horizontalSpacing, 0))); // Spacing between buttons
			}
		}

		buttonPanel.add(Box.createRigidArea(new Dimension(rightPadding, 0))); // Right padding
		buttonPanel.setBackground(new Color(200, 220, 240));

		return buttonPanel;
	}

	/**
	 * Creates a JPanel with a grid layout containing the specified JButtons.
	 *
	 * @param buttons The JButtons to be added to the grid layout.
	 * @return A JPanel with a grid layout containing the provided JButtons.
	 */
	public static JPanel createGridButtonPanel(JButton... buttons) {
		JPanel buttonPanel = new JPanel(new GridLayout(2, 3));

		for (JButton button: buttons) {
			buttonPanel.add(button);
		}

		return buttonPanel;
	}

	/**
	 * Creates the main panel with a main image label and a button panel.
	 *
	 * @param mainImageLabel  The label displaying the main image.
	 * @param buttonPanel     The panel containing buttons.
	 * @return The created main panel.
	 */
	public static JPanel createMainPanel(JLabel mainImageLabel, JPanel buttonPanel) {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding
		mainPanel.add(mainImageLabel, BorderLayout.CENTER); // Add main image to the center
		mainPanel.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom
		return mainPanel;
	}


	/**
	 * Creates the main panel with variable components.
	 *
	 * @param components The variable components to be added to the main panel.
	 * @return The created main panel.
	 */
	public static JPanel createMainPanel(Component... components) {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding

		for (Component component : components) {
			mainPanel.add(component, BorderLayout.CENTER);
		}

		return mainPanel;
	}
	/**
	 * Creates a footer panel with the specified signature text.
	 *
	 * @param signature The text to display in the footer.
	 * @return The created footer panel.
	 */
	public static JPanel createFooterPanel(String signature) {
		JPanel footerPanel = new JPanel();
		footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK)); // Add a top border for the footer
		footerPanel.setBackground(new Color(200, 220, 240));
		JLabel footerLabel = new JLabel(signature);
		footerLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

		footerPanel.add(footerLabel);
		return footerPanel;
	}

	/**
	 * Creates a footer panel with a signature label beneath a provided button panel.
	 *
	 * @param signature    The signature text to be displayed at the bottom of the footer.
	 * @param buttonPanel  The JPanel containing buttons or components to be displayed at the top of the footer.
	 * @return A JPanel representing the footer, arranged vertically with the button panel at the top and the signature label at the bottom.
	 */
	public static JPanel createFooterPanel(String signature, JPanel buttonPanel, Color color) {
		buttonPanel.setBackground(new Color(200,220,240));

		// Create the footer panel with a vertical BoxLayout
		JPanel footerPanel = new JPanel();
		footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
		footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK)); // Add a top border for the footer
		footerPanel.setBackground(color);

		// Create the signature label
		JLabel footerLabel = new JLabel(signature);
		footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		footerLabel.setBackground(color);

		// Add components to the footer panel
		footerPanel.add(buttonPanel);
		footerPanel.add(footerLabel);


		return footerPanel;
	}



	/**
	 * Creates a header panel with the specified title text.
	 *
	 * @param title The text to display in the header.
	 * @return The created header panel.
	 */
	public static JPanel createHeaderPanel(JLabel title) {
		JPanel headerPanel = new JPanel();
		headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)); // Add a bottom border for the header
		headerPanel.setBackground(new Color(200, 220, 240));

		headerPanel.add(title);
		return headerPanel;
	}

	public static JPanel createHeaderPanel(JLabel title, Color color, JButton... buttons) {
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout()); // Use BorderLayout for better control over placement
		headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)); 
		headerPanel.setBackground(color);

		headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

		// Create button panel and add buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(color); // Make sure button panel has the same color too
		for (JButton button : buttons) {
			buttonPanel.add(button);
		}

		// Add components to header panel
		headerPanel.add(title, BorderLayout.WEST);
		headerPanel.add(buttonPanel, BorderLayout.EAST);

		return headerPanel;
	}




	/**
	 * Displays a dialog to prompt the user for input and returns it as an Optional.
	 *
	 * @param fieldName The name of the field or input being requested.
	 * @return An Optional containing the user's input if provided and "OK" is clicked,
	 *         or an empty Optional if canceled or left empty.
	 */
	public static Optional<String> getUserInput(String fieldName) {
		JPanel panel = new JPanel(new GridLayout(0, 2));

		JTextField field = new JTextField();

		panel.add(new JLabel(fieldName));
		panel.add(field);

		int result = JOptionPane.showConfirmDialog(
				null,
				panel,
				"Enter Information",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE
				);

		if (result == JOptionPane.OK_OPTION) {
			String input = field.getText();
			if (!input.isEmpty()) {
				return Optional.of(input);
			} else {
				JOptionPane.showMessageDialog(
						null,
						"Input cannot be empty.",
						"Error",
						JOptionPane.ERROR_MESSAGE
						);
				return getUserInput(fieldName); // Recursive call to get input again
			}
		} else {
			// User clicked "Cancel" or closed the dialog
			return Optional.empty();

		}
	}


	/**
	 * Displays a dialog box with the specified panel and title, allowing the user to
	 * confirm or cancel an action.
	 *
	 * @param panel The JPanel to be displayed in the dialog.
	 * @param title The title of the dialog.
	 * @return An integer value representing the user's choice:
	 *         - JOptionPane.OK_OPTION if the user confirms.
	 *         - JOptionPane.CANCEL_OPTION if the user cancels.
	 *         - JOptionPane.CLOSED_OPTION if the dialog is closed.
	 */
	public static int showPanelDialog(JPanel panel, String title) {
		return JOptionPane.showConfirmDialog(
				null,
				panel,
				title,
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE
				);
	}

	/**
	 * Displays a pop-up message dialog with the given message, title, and message type.
	 *
	 * @param message      The message to display in the dialog.
	 * @param title        The title of the dialog.
	 * @param messageType  The message type (e.g., INFORMATION_MESSAGE, ERROR_MESSAGE).
	 */
	public static void showMessage(String message, String title, int messageType) {
		JOptionPane.showMessageDialog(null, message, title, messageType);
	}

	/**
	 * Displays a pop-up message dialog with the given message and title using the default message type (INFORMATION_MESSAGE).
	 *
	 * @param message  The message to display in the dialog.
	 * @param title    The title of the dialog.
	 */
	public static void showMessage(String message, String title) {
		showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Displays a pop-up Error message dialog with the given message and title 
	 * using the default message type (ERROR_MASSAGE).
	 *
	 * @param message  The message to display in the dialog.
	 * @param title    The title of the dialog.
	 */
	public static void showErrorMessage(String message, String title) {
		showMessage(message, title, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Displays a dialog box with an exit note.
	 */
	public static void showExitNote() {
		JOptionPane.showMessageDialog(
				null,
				"Exit successful",
				"Exit Note",
				JOptionPane.INFORMATION_MESSAGE
				);
	}

	/**
	 * Updates the text content of a JTextArea by appending the specified message
	 * to the existing text.
	 *
	 * @param message The message to append to the JTextArea's text content.
	 * @param textArea the textArea that is to be updated
	 */
	public static void updateTextArea(String message, JTextArea textArea) {
		String current = textArea.getText();
		textArea.setText(current + message);
	}

	/**
	 * Highlights specific rows in a `JTable` based on user-provided indices.
	 *
	 * @param table The `JTable` in which rows are to be highlighted.
	 * @param rowsToHighlight A list of row indices to be highlighted in the `JTable`.
	 */
	public static void highlightTableRows(JTable table, List<Integer> rowsToHighlight) {
		// Clear previous selections in the table
		table.clearSelection();

		for (int rowIndex : rowsToHighlight) {
			if (rowIndex >= 0 && rowIndex < table.getRowCount()) {
				// Highlight the specified row by adding it to the selection interval
				table.addRowSelectionInterval(rowIndex, rowIndex);
			}
		}
	}

	/**
	 * Adds a titled border to a `JTable`, providing it with a title displayed above the table.
	 *
	 * @param table The `JTable` to which the titled border will be added.
	 * @param title The title to be displayed above the `JTable`.
	 * @return The `JTable` with the added titled border and title.
	 */
	public static JTable addTitleToTable(JTable table, String title) {
		TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
		titledBorder.setTitleJustification(TitledBorder.ABOVE_TOP);
		table.setBorder(titledBorder);
		return table;
	}

	/**
	 * Creates a `JButton` with a default action that triggers the specified action listener when the Enter key is pressed.
	 *
	 * @param title           The text to display on the button.
	 * @param actionListener  The action listener to associate with the button.
	 * @return A `JButton` with the specified text and an Enter key trigger for the action listener.
	 */
	public static JButton createDefaultEnterButtonTrigger(String title, ActionListener actionListener) {
		JButton button = createCenteredButton(title, actionListener);

		// Create an action that simulates a button click when the Enter key is pressed
		@SuppressWarnings("serial")
		AbstractAction buttonAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Add your action code here
				button.doClick(); // Simulate a button click
			}
		};

		// Add the action to the button's input map to trigger it on Enter key press
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "clickButton");
		button.getActionMap().put("clickButton", buttonAction);

		return button;
	}

	/**
	 * Opens a local HTML file in the default web browser.
	 *
	 * @param filePath The path to the local HTML file to be opened.
	 * @throws IOException If an I/O error occurs while accessing the file.
	 * @throws IllegalArgumentException If the provided file path is invalid.
	 * @throws SecurityException If a security manager denies the operation.
	 * @throws NullPointerException If the file path is null.
	 * @throws UnsupportedOperationException If the platform does not support the "Desktop" class.
	 * @throws Exception If an unexpected error occurs during the operation.
	 */
	public static void openWebPage(String filePath) {
		try {
			// Create a File object from the given file path
			File file = new File(filePath);

			// Check if the file exists
			if (file.exists()) {
				// Convert the File to a URI and create a "file://" URL
				URI uri = file.toURI();
				String url = uri.toURL().toString();

				// Open the default web browser with the URL
				Desktop.getDesktop().browse(new URI(url));
			} else {
				showErrorMessage("File does not exist: " + filePath,"FileNotFound");
			}
		} catch (IOException | IllegalArgumentException | SecurityException | NullPointerException | UnsupportedOperationException e) {
			showErrorMessage("Error opening the web page: ", e.getMessage());
		} catch (Exception e) {
			showErrorMessage("Unexpected error: ", e.getMessage());
		}
	}
	/**
	 * Creates a JPanel containing labeled integer text fields based on the provided list of integers.
	 * Each integer in the list corresponds to a labeled text field in the panel.
	 *
	 * <p>The method generates a series of JLabels and JTextFields, associating each label with an
	 * integer value from the provided list. The resulting JPanel is suitable for displaying and
	 * editing comparison values or similar data.</p>
	 *
	 * @param comparisons A list of integers used to label the text fields.
	 * @return A JPanel containing labeled integer text fields.
	 */
	public static JPanel createLabeledIntTextFields(List<Integer> comparisons) {
		JPanel panel = new JPanel(new FlowLayout());
		for (int i = 0; i < comparisons.size(); i++) {
			panel.add(new JLabel(String.valueOf(comparisons.get(i))));
			panel.add(new JTextField());
		}
		return panel;
	}

}