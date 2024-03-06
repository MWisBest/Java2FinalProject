package bcdk.entity;

import bcdk.map.GameMap;

public class NPCBehavior {
	private int currentRow;
	private int currentCol;
	private int health;
	private GameMap gameMap;

	public NPCBehavior(GameMap map, int startHealth) {
		gameMap = map;
		health = startHealth;
		//currentRow = map.GetStartRow();
		//currentCol = map.GetStartCol();
	}

	// Move
	// Valid spots are
	// 0,4 0,6 0,8 0,9 0,10
	// 1,4 1,6 1,8 1,10,
	// 2,0 2,1 2,2 2,3 2,4 2,5 2,6 2,7 2,8 2,10
	// 3,4 3,8 3,10
	// 4,3 4,4 4,8 4,9 4,10
	// Format is ROW,COLUMN
	public synchronized void move(int newRow, int newCol) {
		/*
		if (gameMap.IsMovementValid(newRow, newCol)) {
			currentRow = newRow;
			currentCol = newCol;
			System.out.println("Moved to row: " + newRow + ", col: " + newCol); // Testing message. Remove in final
																				// build
		} else {
			System.out.println("Invalid movement!"); // Testing message. Remove in final build
		}*/
	}

	// Get row
	public synchronized int getCurrentRow() {
		return currentRow;
	}

	// Get column
	public synchronized int getCurrentCol() {
		return currentCol;
	}

	// Get health of the NPC
	public synchronized int getHealth() {
		System.out.println("They have " + health + " health left");
		return health;
	}

	// Set health of the NPC
	public synchronized void setHealth(int newHealth) {
		health = newHealth;
	}
	
    // Lower health of guard after combat or item use
	public void takeDamage(int damage) {
		if (health > 0) {
	        health -= damage;
	        if (health <= 0) {
	            System.out.println("Guard has been defeated!");
	            // Additional actions can be taken here if the guard is defeated, such as removing it from the map
	        } else {
	            System.out.println("Guard took " + damage + " damage. Remaining health: " + health);
	        }
	    } else {
	        System.out.println("Guard is already defeated!");
	    }

	}
}