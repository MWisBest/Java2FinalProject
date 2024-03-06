package bcdk.map;

import java.util.ArrayList;
import java.util.List;

import bcdk.entity.Player;
import bcdk.item.Inventory;
import bcdk.item.Item;
import bcdk.item.Key;
import bcdk.item.Rock;
import bcdk.item.Weapon;

public class GameMap {
	
	private List<Room> roomList = new ArrayList<>();
	
	public Checkpoint rockThrown = new Checkpoint("RockThrow");
	public Checkpoint keyAcquired = new Checkpoint("KeyAcquired");
	
	public GameMap() {
		Room startWest = new Room("1W");
		Room central = new Room("Center");
		Room centralNorth = new Room("2N");
		Room centralSouth = new Room("2S");
		Room guards = new Room("3E", rockThrown);
		Room exit = new Room("Exit", keyAcquired);
		
		centralNorth.addFloorItem(new Weapon("Axe", "Good handle, Good for quick attacks", 5));
		centralSouth.addFloorItem(new Weapon("Sword", "A nice, sharp blade. I can definetly use this.", 8));
		central.addFloorItem(new Rock());
		central.addFloorItem(new Rock());
		guards.addFloorItem(new Key());
		
		Room.createWestEastPair(startWest, central);
		Room.createWestEastPair(central, guards);
		Room.createWestEastPair(guards, exit);
		Room.createNorthSouthPair(centralNorth, central);
		Room.createNorthSouthPair(central, centralSouth);
		
		// Insert starting room first
		roomList.add(startWest);
		roomList.add(central);
		roomList.add(centralNorth);
		roomList.add(centralSouth);
		roomList.add(guards);
		roomList.add(exit);
	}
	
	public List<Room> getRoomList() {
		return roomList;
	}
	
	public Room getRoomByName(String name) {
		for (Room r : roomList) {
			if (r.getName().equals(name)) {
				return r;
			}
		}
		
		return null;
	}
	
	public Room getInitialRoom() {
		return roomList.get(0);
	}
	
	public void move(Direction dir, Player player) {
		Room curRoom = player.getLocation();
		Room nextRoom = null;
		
		switch (dir) {
		case NORTH:
			nextRoom = curRoom.getNorth();
			break;
		case SOUTH:
			nextRoom = curRoom.getSouth();
			break;
		case WEST:
			nextRoom = curRoom.getWest();
			break;
		case EAST:
			nextRoom = curRoom.getEast();
			break;
		}
		
		if (nextRoom == null) {
			System.out.println("There doesn't appear to be anything to the " + dir.toString() + ".");
			return;
		}
		
		Checkpoint roomRequiredCP = nextRoom.getRequiredCheckpoint();
		
		if (roomRequiredCP != null) {
			if (!player.checkForCheckpoint(roomRequiredCP)) {
				// TODO: handle player not having checkpoint
				return;
			}
		}
		
		System.out.println("You move to the " + dir.toString());
		player.setLocation(nextRoom);
	}
	
	public void pickupItems(Player player) {
		Room curRoom = player.getLocation();
		List<Item> floorItems = curRoom.getFloorItems();
		if (floorItems != null && floorItems.size() > 0) {
			Inventory playerInventory = player.getInventory();
			for (Item i : floorItems) {
				if (i instanceof Rock) {
					playerInventory.addRocks((Rock)i);
					System.out.println("You picked up a rock.");
				} else if (i instanceof Key) {
					playerInventory.addKey((Key)i);
					System.out.println("You picked up a key!");
				} else if (i instanceof Weapon) {
					playerInventory.addWeapons((Weapon)i);
					System.out.println("You acquired a new weapon! Check your INVENTORY for details.");
				}
			}
			floorItems.clear();
		} else {
			System.out.println("There's nothing on the floor here.");
		}
	}
}
