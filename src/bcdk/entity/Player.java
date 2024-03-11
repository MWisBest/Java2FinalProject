package bcdk.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

import bcdk.BCDK;
import bcdk.item.Inventory;
import bcdk.map.Checkpoint;
import bcdk.map.Room;
import bcdk.savegame.ILoad;
import bcdk.savegame.ISave;
import bcdk.savegame.SaveGame;
import bcdk.savegame.SaveGame.Table;

/*
 * represents the player who will play the game. 
 */
public class Player extends Entities implements ISave, ILoad {

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
		this.checkpointsReached = new TreeSet<>(); // 3.3 - use of a TreeSet
		this.playerInventory = new Inventory();
	}
	
	/**
	 * Adds a Checkpoint to the list of Checkpoints the Player has reached.
	 * 
	 * @param checkpoint Checkpoint to add.
	 */
	public void addCheckpointReached(Checkpoint checkpoint) {
		this.checkpointsReached.add(checkpoint);
	}
	
	/**
	 * Check if the Player has reached a Checkpoint
	 * 
	 * @param checkpoint the Checkpoint to look for
	 * @return true if the Player has this Checkpoint, or false if not
	 */
	public boolean checkForCheckpoint(Checkpoint checkpoint) {
		for (Checkpoint cp : this.checkpointsReached) {
			if (cp.equals(checkpoint)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the Room the player is in
	 */
	public Room getLocation() {
		return location;
	}

	/**
	 * Set the Room the player is in.
	 * 
	 * @param room new Room the player is located in.
	 */
	public void setLocation(Room room) {
		this.location = room;
	}

	/**
	 * Get the Player's Inventory
	 * @return Player's Inventory
	 */
	public Inventory getInventory() {
		return playerInventory;
	}

	@Override
	public void loadFrom(SaveGame sg) {
		try {
			Statement stmt = sg.getDBConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + Table.PLAYER_CHECKPOINTS.name() + " WHERE PLAYER_NAME='" + this.getName() + "'");
			while (rs.next()) {
				this.addCheckpointReached(new Checkpoint(rs.getString("CHECKPOINT_NAME")));
			}
		} catch (SQLException e) {
			BCDK.logger.error(e);
		}
	}

	@Override
	public void saveTo(SaveGame sg) {
		sg.insertOrUpdate(Table.PLAYER, "'" + getName() + "'");
		for (Checkpoint cp : this.checkpointsReached) {
			sg.insertOrUpdate(Table.CHECKPOINTS, "'" + cp.getName() + "'");
			sg.insertOrUpdate(Table.PLAYER_CHECKPOINTS, "'" + getName() + "', '" + cp.getName() + "'");
		}
	}
}
