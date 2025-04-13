package resources;

import java.util.ArrayList;
import java.util.List;

public class POI {

	private String name;
	private String description;
	private List<Item> items = new ArrayList<>();
	private String hint;
	private List<String[]> interaction = new ArrayList<>();

	public POI() {
		this.name = "Item_Name";
		this.description = "Description";
	}

	public POI(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void addInteraction(String command, String result) {
	    this.interaction.add(new String[] {command, result});
	}

	public void addItem(Item item) {
		this.items.add(item);
		
	}

}
