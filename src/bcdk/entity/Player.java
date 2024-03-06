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
