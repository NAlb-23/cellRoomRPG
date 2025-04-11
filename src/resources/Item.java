package resources;

public class Item {
	private String name;
	private String description;
	private String hint;
	
	public Item() {
		this.name = "Item_Name";
		this.description = "Description";
	}
	
	public Item(String name, String description) {
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
	
	
	
}
