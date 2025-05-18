package resources;

import java.io.Serializable;

import project_software_engineering.GameLogic;
import utils.RESOURCES;

/**
 * Manages the player's survival needs such as hunger, thirst, warmth, and energy.
 * These stats gradually decay over time and can be restored using specific actions.
 */
public class PlayerNeeds{

    private int hunger = 100;
    private int thirst = 100;
    private double warmth = 100.0;
    private int energy = 100;
    
    private int hungerWarningCooldown = 0;
    private int thirstWarningCooldown = 0;
    private int warmthWarningCooldown = 0;
    private int energyWarningCooldown = 0;

    private final int WARNING_COOLDOWN_TICKS = 10; // Number of ticks between warnings

    private final int DECAY_RATE = 1;

    /**
     * Updates the player's needs by decreasing their levels over time.
     * Called periodically (e.g., once per game tick).
     */
    public void tick() {
        hunger = Math.max(0, hunger - DECAY_RATE);
        thirst = Math.max(0, thirst - DECAY_RATE * 2);
        warmth = Math.max(0, warmth - (DECAY_RATE / 2.0));
        energy = Math.max(0, energy - DECAY_RATE);

        if (hungerWarningCooldown > 0) hungerWarningCooldown--;
        if (thirstWarningCooldown > 0) thirstWarningCooldown--;
        if (warmthWarningCooldown > 0) warmthWarningCooldown--;
        if (energyWarningCooldown > 0) energyWarningCooldown--;

        checkCriticalNeeds();
        checkGameOver();
    }

    /**
     * Checks for critically low levels of any need and notifies the player through messages.
     */
    private void checkCriticalNeeds() {
        if (hunger <= 25 && hungerWarningCooldown == 0) {
            GameLogic.addMessage("You feel weak from hunger...");
            hungerWarningCooldown = WARNING_COOLDOWN_TICKS;
        }
        if (thirst <= 25 && thirstWarningCooldown == 0) {
            GameLogic.addMessage("Your mouth is dry. You're dangerously dehydrated.");
            thirstWarningCooldown = WARNING_COOLDOWN_TICKS;
        }
        if (warmth <= 25 && warmthWarningCooldown == 0) {
            GameLogic.addMessage("You're freezing. You need warmth soon.");
            warmthWarningCooldown = WARNING_COOLDOWN_TICKS;
        }
        if (energy <= 25 && energyWarningCooldown == 0) {
            GameLogic.addMessage("You're utterly exhausted. You should rest.");
            energyWarningCooldown = WARNING_COOLDOWN_TICKS;
        }
    }
    
    /**
     * Checks if any survival stat has reached zero. If so, ends the game.
     */
    private void checkGameOver() {
        if (hunger == 0 || thirst == 0 || warmth == 0.0 || energy == 0) {
            GameLogic.addMessage("One of your vital needs has dropped to zero. You collapse...");
            GameLogic.endGame(); // This method should trigger game over logic in your GameLogic class
        }
    }



    /**
     * Increases hunger level by the given amount (e.g., after eating).
     * @param foodValue Amount of hunger restored.
     */
    public void eat(int foodValue) {
        hunger = Math.min(100, hunger + foodValue);
    }

    /**
     * Increases thirst level by the given amount (e.g., after drinking).
     * @param waterValue Amount of thirst restored.
     */
    public void drink(int waterValue) {
        thirst = Math.min(100, thirst + waterValue);
    }

    /**
     * Increases warmth level by the given amount (e.g., after warming up).
     * @param heatValue Amount of warmth restored.
     */
    public void warmUp(int heatValue) {
        warmth = Math.min(100, warmth + heatValue);
    }

    /**
     * Increases energy level by the given amount (e.g., after resting).
     * @param restValue Amount of energy restored.
     */
    public void rest(int restValue) {
        energy = Math.min(100, energy + restValue);
    }

    /**
     * Returns a summary of the player's current needs.
     * @return String representation of all need levels.
     */
    public String getStatus() {
        return String.format("Hunger: %d, Thirst: %d, Warmth: %d, Energy: %d",
                hunger, thirst, warmth, energy);
    }
    
    

    // Getters and Setters

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

    public double getWarmth() {
        return warmth;
    }

    public void setWarmth(double warmth) {
        this.warmth = warmth;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
