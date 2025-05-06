package resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a room in the game, which contains a list of Points of Interest (POIs), 
 * items, and can be locked or unlocked.
 */
public class Room {
	
    private String name;
    private String description;
    private List<POI> pois = new ArrayList<>();
    private int id;
    private boolean isLocked = true;

    /**
     * Gets the ID of the room.
     *
     * @return Room ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the room.
     *
     * @param id The new room ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Checks if the room is locked.
     *
     * @return True if the room is locked, false otherwise.
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Sets the locked state of the room.
     *
     * @param isLocked True if the room should be locked, false otherwise.
     */
    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    /**
     * Constructs a Room with a specified name and description.
     *
     * @param name        The name of the room.
     * @param description A brief description of the room.
     */
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters

    /**
     * Gets the name of the room.
     *
     * @return Room name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the room.
     *
     * @param name New room name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the room.
     *
     * @return Room description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the room.
     *
     * @param description New room description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the list of Points of Interest (POIs) within the room.
     *
     * @return List of POIs in the room.
     */
    public List<POI> getPois() {
        return pois;
    }

    /**
     * Retrieves a POI by its name.
     *
     * @param name Name of the POI to retrieve.
     * @return POI with the specified name, or null if not found.
     */
    public POI getPOIbyName(String name) {
        for (POI poi : pois) {
            if (poi.getName().toLowerCase().equals(name.toLowerCase())) {
                return poi;
            }
        }
        return null;
    }

    /**
     * Gets a list of all items in the room by gathering them from the POIs.
     *
     * @return List of items in the room.
     */
    public List<Item> getListOfRoomItems() {
        List<Item> items = new ArrayList<>();
        for (POI poi : pois) {
            items.addAll(poi.getItems());
        }
        return items;
    }

    /**
     * Retrieves an item by its name from the room.
     *
     * @param name Name of the item to retrieve.
     * @return Item with the specified name, or null if not found.
     */
    public Item getItemByName(String name) {
        for (Item item : getListOfRoomItems()) {
            if (item.getName().toLowerCase().equals(name.toLowerCase())) {
                return item;
            }
        }
        return null;
    }

    /**
     * Gets a list of names of all visible POIs in the room.
     *
     * @return A string of POI names, each on a new line.
     */
    public String getListOfPOINames() {
        StringBuilder sb = new StringBuilder();
        for (POI poi : pois) {
            if (!poi.getIsHidden()) {
                sb.append(poi.getName()).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Sets the list of POIs in the room.
     *
     * @param pois List of POIs to set in the room.
     */
    public void setPois(List<POI> pois) {
        this.pois = pois;
    }

    /**
     * Adds a new POI to the room.
     *
     * @param poi The POI to add to the room.
     */
    public void addPOI(POI poi) {
        this.pois.add(poi);
    }

    /**
     * Calculates the hash code for the room based on its name.
     *
     * @return The hash code for the room.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Compares this room with another object to check equality.
     *
     * @param obj The object to compare with.
     * @return True if the rooms are equal based on their name, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Room other = (Room) obj;
        return Objects.equals(name, other.name);
    }
}
