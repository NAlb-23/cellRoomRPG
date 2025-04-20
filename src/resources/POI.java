package resources;

import java.util.ArrayList;
import java.util.List;

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

	public POI() {
		this.name = "Item_Name";
		this.description = "Description";
		this.isHidden = false;
	}

	public POI(String name, String description) {
		this.name = name;
		this.description = description;
		this.isHidden = false;
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

	public String interAct(String command) {
		for(String[] i: interaction) {
			if(i[0].trim().toLowerCase().equals(command.trim().toLowerCase())) {
				return i[1];
			}
		}
		return null;
	}

	public String help() {
		StringBuilder sb = new StringBuilder();
		sb.append(getDescription());
		for(String[] i: interaction) {
			sb.append(i[1]);
		}

		return sb.toString();
	}

	
	public void setLeadsTo(Room roomName) {
	    this.leadsTo = roomName;
	}

	public Room getLeadsTo() {
	    return this.leadsTo;
	}



	public void addVisibilityListener(Runnable listener) {
	    visibilityListeners.add(listener);
	}

	private void notifyVisibilityChange() {
	    for (Runnable listener : visibilityListeners) {
	        listener.run();
	    }
	}

	public void setIsHidden(boolean isHidden) {
	    this.isHidden = isHidden;
	    notifyVisibilityChange();  // Notify when changed
	}
	
	public boolean getIsHidden() {
		return this.isHidden;
	}
	
	public boolean isLocked() {
	    return isLocked;
	}

	public void setLocked(boolean isLocked) {
	    this.isLocked = isLocked;
	}


}
