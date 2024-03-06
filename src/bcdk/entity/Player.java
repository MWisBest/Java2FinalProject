package bcdk.entity;

import java.util.HashSet;
import java.util.Set;

import bcdk.item.Inventory;
import bcdk.map.Checkpoint;
import bcdk.map.Room;

/*
 * represents the player who will play the game. 
 */
public class Player extends Entities {
	/**
	 * the cooldown of the heal ability
	 */
	private int HealCooldown = 100;

	/**
	 * the amount that player can heal
	 */
	private int HealAmount;
	
	private Room location;
	
	private Set<Checkpoint> checkpointsReached;
	
	private Inventory playerInventory;

	/**
	 * class constructor
	 * 
	 * @param name     - the name given to the player
	 * @param health   - the amount of initial health given to player
	 * @param extraDmg - the amount of initial extra damage that player can cause
	 */
	public Player(String name, int health, int extraDmg) {
		super(name, health, extraDmg); // the derived variables
		this.checkpointsReached = new HashSet<>();
		this.playerInventory = new Inventory();
	}
	
	public void addCheckpointReached(Checkpoint checkpoint) {
		this.checkpointsReached.add(checkpoint);
	}
	
	public boolean checkForCheckpoint(Checkpoint checkpoint) {
		for (Checkpoint cp : this.checkpointsReached) {
			if (cp.equals(checkpoint)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * gets the amount of cooldown to go before healing is available
	 * 
	 * @return cooldown amount
	 */
	public int getHealCoolDown() {
		return HealCooldown;
	}

	/**
	 * reduce the amount of cooldown needed before healing becomes available
	 */
	public void ChangeCoolDown() {
		HealCooldown -= 20;
	}

	/**
	 * reset cool down back to 0
	 */
	public void resetCoolDown() {
		HealCooldown = 100;
	}

	/**
	 * changes the amount of healing that player can do
	 */
	public void setHealAmount() {
		HealAmount = GetHealth() / 3;
	}

	/**
	 * gets the amount that the player can heal
	 * 
	 * @return - heal amunt
	 */
	public int getHealAmount() {
		return HealAmount;
	}

	public Room getLocation() {
		return location;
	}

	public void setLocation(Room room) {
		this.location = room;
	}

	public Inventory getInventory() {
		return playerInventory;
	}
}
