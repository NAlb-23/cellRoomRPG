package resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room {
	private String name;
	private String description;
	private List<POI> pois = new ArrayList<>();
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	private boolean isLocked = true;

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public Room(String name, String description) {
		this.name = name;
		this.description = description;
	}

	// Getters and Setters
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

	public List<POI> getPois() {
		return pois;
	}

	public POI getPOIbyName(String name) {
		for(POI poi : pois) {
			if (poi.getName().toLowerCase().equals(name.toLowerCase()))
				return poi;
		}
		return null;
	}

	public List<Item> getListOfRoomItems(){
		List<Item> items = new ArrayList<>();
		for (POI poi: pois) {
			items.addAll(poi.getItems());
		}
		return items;
	}

	public Item getItemByName(String name) {
		for(Item item: getListOfRoomItems()) {
			if(item.getName().toLowerCase().equals(name.toLowerCase()))
				return item;
		}
		return null;
	}

	public String getlistofPOINames() {
		StringBuilder sb = new StringBuilder();
		for(POI poi: pois) {
			if(!poi.getIsHidden())
				sb.append(poi.getName()+"\n");
		}
		return sb.toString();
	}

	public void setPois(List<POI> pois) {
		this.pois = pois;
	}

	public void addPOI(POI poi) {
		this.pois.add(poi);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}


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
