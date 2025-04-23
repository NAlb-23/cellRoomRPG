package utils;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class RESOURCES {
	
	// Colors
	public static String RESLOC = "res/";
	public static String SAVEFILELOC = "save";

	public static final Color DARK_BROWN = new Color(101, 67, 33);
	public static final Color LIGHT_BROWN = new Color(139, 69, 19);
    public static final Color TRANSPARENT_SCROLL_BG = new Color(101, 67, 33, 128);
    public static final Color TEXT_AREA_BG = new Color(101, 67, 33);
    public static final Color TEXT_COLOR = Color.WHITE;

    // Dimensions
    public static final Dimension FRAME_SIZE = new Dimension(600, 400);
    public static final Dimension IMAGE_DIMENSION = new Dimension(500, 400);
    public static final Dimension QUICK_INFO_SIZE = new Dimension(150, 125);
    public static final Dimension PLAYER_INFO_SIZE = new Dimension(150, 150);
    public static final Dimension INVENTORY_SIZE = new Dimension(150, 125);

    // Status Enum
    public static final RESOURCES.Status GAMEPLAY_STATUS = RESOURCES.Status.GAMEPLAY;

 // Texts
    public static final String TITLE = "Escape Room RPG";
    public static final String HEADER_TITLE = "Dungeon Escape";
    public static final String HELP_MESSAGE = "Hang in there, help is on the way";
    public static final String DEFAULT_ROOM_TEXT = "You wake up in cell 2";
    public static final String ROOM_LABEL = "Your Surrounding: ";
    public static final String PLAYER_LABEL = "Player Stats: ";
    public static final String INVENTORY_LABEL = "Invintory: ";
    public static final String START_MESSAGE = "Glad to see you’re up, %s. You’ve been out for a while…\nWhy don’t you try to \"look around\"?";
	
	
	public static final ImageIcon SUBMIT_ICON = GUIBuilder.resizeImageIcon(new ImageIcon(RESLOC + "submit_icon.png"), 20, 20);
	public static final ImageIcon EXIT_ICON = GUIBuilder.resizeImageIcon(new ImageIcon(RESLOC + "exit_icon_white.png"), 20, 20);
	public static final ImageIcon HELP_ICON = GUIBuilder.resizeImageIcon(new ImageIcon(RESLOC + "search_icon_white.png"), 20, 20);
	public static final ImageIcon LOAD_ICON = GUIBuilder.resizeImageIcon(new ImageIcon(RESLOC + "load_icon_white.png"), 20, 20);
	public static final ImageIcon NEWGAME_ICON = GUIBuilder.resizeImageIcon(new ImageIcon(RESLOC + "add_icon_white.png"), 20, 20);
	public static final ImageIcon OK_ICON = GUIBuilder.resizeImageIcon(new ImageIcon(RESLOC + "ok_icon.png"), 20, 20);
	
	public enum Status {
		WAKEUP,
		TUTORIAL,
		GAMEPLAY,
		COMPLETE
	}
}
