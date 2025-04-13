package resources;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private String description;
    private List<POI> pois = new ArrayList<>();

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
    
    public String getlistofPOINames() {
    	StringBuilder sb = new StringBuilder();
    	for(POI poi: pois) {
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

}
