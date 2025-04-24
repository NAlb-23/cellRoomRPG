package project_software_engineering;

public class PlayerNeeds {
 

	private int hunger = 100;
    private int thirst = 100;
    private int warmth = 100;
    private int energy = 100;

    private final int DECAY_RATE = 1;

    public void tick() {
        hunger = Math.max(0, hunger - DECAY_RATE);
        thirst = Math.max(0, thirst - DECAY_RATE * 2); // thirst drains faster
        warmth = Math.max(0, warmth - DECAY_RATE / 2); // slower in general
        energy = Math.max(0, energy - DECAY_RATE);

        checkCriticalNeeds();
    }

    private void checkCriticalNeeds() {
        if (hunger <= 25) {
            GameLogic.addMessage("You feel weak from hunger...");
        }
        if (thirst <= 25) {
            GameLogic.addMessage("Your mouth is dry. You're dangerously dehydrated.");
        }
        if (warmth <= 25) {
            GameLogic.addMessage("You're freezing. You need warmth soon.");
        }
        if (energy <= 25) {
            GameLogic.addMessage("You're utterly exhausted. You should rest.");
        }
    }

    public void eat(int foodValue) {
        hunger = Math.min(100, hunger + foodValue);
    }

    public void drink(int waterValue) {
        thirst = Math.min(100, thirst + waterValue);
    }

    public void warmUp(int heatValue) {
        warmth = Math.min(100, warmth + heatValue);
    }

    public void rest(int restValue) {
        energy = Math.min(100, energy + restValue);
    }

    public String getStatus() {
        return String.format("Hunger: %d, Thirst: %d, Warmth: %d, Energy: %d",
                hunger, thirst, warmth, energy);
    }
    
    
    public int getHunger() {
 		return hunger;
 	}

 	public void setHunger(int hunger) {
 		this.hunger = hunger;
 	}

 	public int getThirst() {
 		return thirst;
 	}

 	public void setThirst(int thirst) {
 		this.thirst = thirst;
 	}

 	public int getWarmth() {
 		return warmth;
 	}

 	public void setWarmth(int warmth) {
 		this.warmth = warmth;
 	}

 	public int getEnergy() {
 		return energy;
 	}

 	public void setEnergy(int energy) {
 		this.energy = energy;
 	}
}
