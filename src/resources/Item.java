package resources;

/**
 * Represents an interactable item in the game, which can have a name, description, hint, and type.
 */
public class Item {

    private String name;
    private String description;
    private String hint;
    private String type;

    /**
     * Default constructor. Initializes the item with placeholder values.
     */
    public Item() {
        this.name = "Item_Name";
        this.description = "Description";
        this.type = "item";
    }

    /**
     * Constructs an item with a specific name and description.
     * The type is set to "item" by default.
     *
     * @param name        Name of the item.
     * @param description Description of the item.
     */
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        this.type = "item";
    }

    // Getters and Setters

    /**
     * Gets the name of the item.
     *
     * @return Item name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item.
     *
     * @param name New item name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the item.
     *
     * @return Item description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the item.
     *
     * @param description New item description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the hint associated with the item.
     *
     * @return Item hint.
     */
    public String getHint() {
        return hint;
    }

    /**
     * Sets a hint for the item.
     *
     * @param hint Hint text.
     */
    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     * Gets the type of the item.
     *
     * @return Item type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the item.
     *
     * @param type New item type.
     */
    public void setType(String type) {
        this.type = type;
    }
}
