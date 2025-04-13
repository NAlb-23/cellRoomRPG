package utils;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class RESOURCES {

	public static final Color DARKBROWN = new Color(101, 67, 33);
	public static final Color LIGHTBROWN = new Color(131, 97, 63);
	
	public static final ImageIcon SUBMIT_ICON = GUIBuilder.resizeImageIcon(new ImageIcon("submit_icon.png"), 20, 20);
	public static final ImageIcon EXIT_ICON = GUIBuilder.resizeImageIcon(new ImageIcon("exit_icon_white.png"), 20, 20);
	public static final ImageIcon HELP_ICON = GUIBuilder.resizeImageIcon(new ImageIcon("search_icon_white.png"), 20, 20);
	public static final ImageIcon LOAD_ICON = GUIBuilder.resizeImageIcon(new ImageIcon("load_icon_white.png"), 20, 20);
	public static final ImageIcon NEWGAME_ICON = GUIBuilder.resizeImageIcon(new ImageIcon("add_icon_white.png"), 20, 20);
	public static final ImageIcon OK_ICON = GUIBuilder.resizeImageIcon(new ImageIcon("ok_icon.png"), 20, 20);
	
	public enum Status {
		WAKEUP,
		TUTORIAL,
		GAMEPLAY,
		COMPLETE
	}
}
