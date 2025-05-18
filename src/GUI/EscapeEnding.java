package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;

import utils.GUIBuilder;
import utils.RESOURCES;


public class EscapeEnding extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextArea textArea;
    private final JButton closeButton;

    public EscapeEnding() {
        setTitle("Escape Sequence");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(600, 400);
        setLocationRelativeTo(null);
        ((JComponent) getRootPane().getContentPane()).setBorder(BorderFactory.createLineBorder(RESOURCES.DARK_BROWN, 2, true));

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(600, 400));

        JLabel backgroundImage = GUIBuilder.setupImage(RESOURCES.RESLOC + "Picture1.png", 600, 400);
        backgroundImage.setBounds(0, 0, 600, 400);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBounds(50, 50, 500, 300);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(0, 0, 0, 0));
        textArea.setText("Your escape message here...");
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setForeground(Color.WHITE);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setHighlighter(null);
        textArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        textArea.setCaret(new DefaultCaret() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void paint(Graphics g) {
                // Hides the blinking caret
            }
        });


        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        closeButton = GUIBuilder.createButton("close", e -> dispose());
        closeButton.setVisible(false);
        closeButton.setBackground(RESOURCES.DARK_BROWN);
        closeButton.setForeground(Color.white);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        layeredPane.add(backgroundImage, Integer.valueOf(0));
        layeredPane.add(contentPanel, Integer.valueOf(1));

        add(layeredPane);
        setVisible(true);
        new Thread(this::triggerEscapeEnding).start();
    }

    private void appendText(String text) {
        SwingUtilities.invokeLater(() -> textArea.append(text + "\n"));
    }

    private void triggerEscapeEnding() {
        displayText("You take one last look around the cell — the makeshift rope hangs still from the window.", 1000);
        displayText("You climb onto the table, then the bucket, steadying yourself by gripping the rope.", 1500);
        displayText("The wind brushes your face as you crawl halfway out, your feet dangling into the unknown.", 1500);
        displayText("With a deep breath, you grip the rope tightly and begin to descend.", 1500);
        displayText("The rope sways, but holds. Inch by inch, you lower yourself until your feet touch solid ground.", 1500);
        displayText("You glance up — the cell window now seems distant, just a memory.", 1000);
        displayText("You've escaped.", 1000);
        printAsciiEscape();
        SwingUtilities.invokeLater(() -> {
        	
            closeButton.setVisible(true);
        });

    }

    private void displayText(String text, long delay) {
        appendText(text);
        pause(delay);
    }

    private void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void printAsciiEscape() {
        appendText("\n[ENDING REACHED] — YOU ESCAPED!\n");
        pause(500);
        appendText(
                " _________________________\n" +
                "|                         |\n" +
                "|      YOU ESCAPED!       |\n" +
                "|_________________________|\n" +
                "        \\   ^__^\n" +
                "         \\  (oo)\\_______\n" +
                "            (__)\\       )\\/\\\n" +
                "                ||----w |\n" +
                "                ||     ||"
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EscapeEnding::new);
    }
}
