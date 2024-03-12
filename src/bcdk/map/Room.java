package bcdk.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bcdk.item.Item;

/**
 * Create and customize rooms to allow player to traverse through them
 */
public class Room {
	/**
	 * declares the room that will be acessable by going north
	 */
	private Room north;
	
	/**
	 * declares the room that will be accessable by going south
	 */
	private Room south;
	
	/**
	 * declares the room that will be accessable by going west
	 */
	private Room west;
	
	/**
	 * declares the room that will be acessable by going east
	 */
	private Room east;
	
	/**
	 * declares the name of the room
	 */
	private String name;
	
	/**
	 * declares a description for the room 
	 */
	private String enterDescription;
	
	/**
	 * declares the checkpoint that is required before moving forward in the game
	 */
	private Checkpoint requiredCP;
	
	// Don't make a new set unless we need to, rooms will not always have items
	private List<Item> items = null;
	
	public Room(String name, String enterDescription) {
		this.name = name;
		this.enterDescription = enterDescription;
	}
	
	// 1.3 - use of overloaded constructor
	public Room(String name, String enterDescription, Checkpoint requiredCP) {
		this.name = name;
		this.enterDescription = enterDescription;
		this.requiredCP = requiredCP;
	}
	
	/**
	 * Add an item to the floor of this room.
	 * 
	 * @param item Item to place on the floor.
	 */
	public void addFloorItem(Item item) {
		if (this.items == null) {
			this.items = new ArrayList<>();
		}
		this.items.add(item);
	}
	
	/**
	 * @return the List of Item on the floor of this room.
	 */
	public List<Item> getFloorItems() {
	    if (this.items != null) {
	        this.items.forEach((Item item) -> { //2.1 Lambda expression
	            // Print each item's name
	            System.out.println(item.getName());
	        });
	        return this.items;
	    } else {
	        return Collections.emptyList();
	    }
	}

	/**
	 * @return the Room to the North of this room, or null
	 */
	public Room getNorth() {
		return north;
	}

	/**
	 * @param north = the room to be declared as the north of the map
	 */
	private void setNorth(Room north) {
		this.north = north;
	}

	/**
	 * @return the Room to the South of this room, or null
	 */
	public Room getSouth() {
		return south;
	}

	/**
	 * @param south - the room to be declared as the south of the map
	 */
	private void setSouth(Room south) {
		this.south = south;
	}

	/**
	 * @return the Room to the West of this room, or null
	 */
	public Room getWest() {
		return west;
	}

	/**
	 * @param west - the room to be declared as the west of the map
	 */
	private void setWest(Room west) {
		this.west = west;
	}

	/**
	 * @return the Room to the East of this room, or null
	 */
	public Room getEast() {
		return east;
	}

	/**
	 * @param east - the room to be declared as the east of the map
	 */
	private void setEast(Room east) {
		this.east = east;
	}

	/**
	 * @return the name of this room
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Creates a link between two rooms in the horizontal direction.
	 * @param r1 West Room
	 * @param r2 East Room
	 */
	public static void createWestEastPair(Room r1, Room r2) {
		r1.setEast(r2);
		r2.setWest(r1);
	}
	
	/**
	 * Creates a link between two rooms in the vertical direction.
	 * @param r1 North Room
	 * @param r2 South Room
	 */
	public static void createNorthSouthPair(Room r1, Room r2) {
		r1.setSouth(r2);
		r2.setNorth(r1);
	}

	/**
	 * @return the Checkpoint required to enter this room, or null for no requirement
	 */
	public Checkpoint getRequiredCheckpoint() {
		return requiredCP;
	}

	/**
	 * @return the description of this room
	 */
	public String getEnterDescription() {
		return enterDescription;
	}
}