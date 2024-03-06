package bcdk.map;

import java.util.ArrayList;
import java.util.List;

import bcdk.item.Item;

public class Room {
	private Room north;
	private Room south;
	private Room west;
	private Room east;
	
	private String name;
	private String enterDescription;
	
	private Checkpoint requiredCP;
	
	// Don't make a new set unless we need to, rooms will not always have items
	private List<Item> items = null;
	
	public Room(String name, String enterDescription) {
		this.name = name;
		this.enterDescription = enterDescription;
	}
	
	public Room(String name, String enterDescription, Checkpoint requiredCP) {
		this.name = name;
		this.enterDescription = enterDescription;
		this.requiredCP = requiredCP;
	}
	
	public void addFloorItem(Item item) {
		if (this.items == null) {
			this.items = new ArrayList<>();
		}
		this.items.add(item);
	}
	
	public List<Item> getFloorItems() {
		return this.items;
	}

	public Room getNorth() {
		return north;
	}

	private void setNorth(Room north) {
		this.north = north;
	}

	public Room getSouth() {
		return south;
	}

	private void setSouth(Room south) {
		this.south = south;
	}

	public Room getWest() {
		return west;
	}

	private void setWest(Room west) {
		this.west = west;
	}

	public Room getEast() {
		return east;
	}

	private void setEast(Room east) {
		this.east = east;
	}

	public String getName() {
		return name;
	}
	
	public static void createWestEastPair(Room r1, Room r2) {
		r1.setEast(r2);
		r2.setWest(r1);
	}
	
	public static void createNorthSouthPair(Room r1, Room r2) {
		r1.setSouth(r2);
		r2.setNorth(r1);
	}

	public Checkpoint getRequiredCheckpoint() {
		return requiredCP;
	}

	public String getEnterDescription() {
		return enterDescription;
	}
}