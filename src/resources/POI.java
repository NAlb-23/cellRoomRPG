package resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Point of Interest (POI) in the game, which may have items, interactions, hints, 
 * and a locked state. POIs may also lead to other rooms and be hidden or visible.
 */
public class POI {

    private String name;
    private String description;
    private List<Item> items = new ArrayList<>();
    private String hint;
    private List<String[]> interaction = new ArrayList<>();
    private Room leadsTo = null;
    private boolean isLocked = true;
    private boolean isHidden;
    private List<Runnable> visibilityListeners = new ArrayList<>();

    /**
     * Default constructor for a POI with placeholder values.
     */
    public POI() {
        this.name = "Item_Name";
        this.description = "Description";
        this.isHidden = false;
    }

    /**
     * Constructs a POI with a specific name and description.
     * The point of interest is not hidden by default.
     *
     * @param name        Name of the POI.
     * @param description Description of the POI.
     */
    public POI(String name, String description) {
        this.name = name;
        this.description = description;
        this.isHidden = false;
    }

    // Getters and Setters

    /**
     * Gets the name of the POI.
     *
     * @return POI name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the POI.
     *
     * @param name New POI name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the POI.
     *
     * @return POI description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the POI.
     *
     * @param description New POI description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the hint associated with the POI.
     *
     * @return POI hint.
     */
    public String getHint() {
        return hint;
    }

    /**
     * Sets a hint for the POI.
     *
     * @param hint Hint text.
     */
    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     * Gets the list of items located at this POI.
     *
     * @return List of items at the POI.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Sets the list of items at this POI.
     *
     * @param items List of items to set.
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * Adds an item to the POI.
     *
     * @param item Item to add.
     */
    public void addItem(Item item) {
        this.items.add(item);
    }

    /**
     * Adds an interaction command and its resulting message.
     *
     * @param command Command that triggers the interaction.
     * @param result  The resulting message when the command is executed.
     */
    public void addInteraction(String command, String result) {
        this.interaction.add(new String[]{command, result});
    }

    /**
     * Interacts with the POI using a command.
     *
     * @param command The command to interact with the POI.
     * @return The result message from the interaction.
     */
    public String interAct(String command) {
        for (String[] i : interaction) {
            if (i[0].trim().toLowerCase().equals(command.trim().toLowerCase())) {
                return i[1];
            }
        }
        return "You try to " + command +" "+this.name+" but nothing happens.";
    }

    /**
     * Provides a help message for interacting with the POI, including description and interactions.
     *
     * @return Help string for the POI.
     */
    public String help() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDescription());
        for (String[] i : interaction) {
            sb.append(i[1]);
        }
        return sb.toString();
    }

    /**
     * Sets the room that this POI leads to.
     *
     * @param room Room that this POI leads to.
     */
    public void setLeadsTo(Room room) {
        this.leadsTo = room;
    }

    /**
     * Gets the room that this POI leads to.
     *
     * @return Room that this POI leads to.
     */
    public Room getLeadsTo() {
        return this.leadsTo;
    }

    /**
     * Adds a listener to be notified when the visibility of the POI changes.
     *
     * @param listener The listener to be added.
     */
    public void addVisibilityListener(Runnable listener) {
        visibilityListeners.add(listener);
    }

    /**
     * Notifies all listeners about a change in the POI's visibility.
     */
    private void notifyVisibilityChange() {
        for (Runnable listener : visibilityListeners) {
            listener.run();
        }
    }

    /**
     * Sets whether the POI is hidden or not.
     *
     * @param isHidden True if the POI should be hidden, false otherwise.
     */
    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
        notifyVisibilityChange();  // Notify listeners when visibility changes
    }

    /**
     * Gets whether the POI is hidden.
     *
     * @return True if the POI is hidden, false otherwise.
     */
    public boolean getIsHidden() {
        return this.isHidden;
    }

    /**
     * Checks if the POI is locked.
     *
     * @return True if the POI is locked, false otherwise.
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Sets the locked state of the POI.
     *
     * @param isLocked True if the POI should be locked, false otherwise.
     */
    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }
}
