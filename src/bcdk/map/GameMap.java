package bcdk.map;

import java.util.ArrayList;
import java.util.List;

import bcdk.BCDK;
import bcdk.entity.Player;
import bcdk.item.Inventory;
import bcdk.item.Item;
import bcdk.item.Key;
import bcdk.item.Rock;
import bcdk.item.Weapon;

public class GameMap {
	
	private List<Room> roomList = new ArrayList<>();
	
	// 1.4 - example of final
	// 1.5 - proper use of static keyword
	// 2.3 - example of singleton pattern
	private static final GameMap INSTANCE = new GameMap();
	
	public Checkpoint rockThrown = new Checkpoint("RockThrow");
	public Checkpoint keyAcquired = new Checkpoint("KeyAcquired");
	
	private GameMap() {
		Room startWest = new Room("1W", "You awake in a dark brick building.");
		Room central = new Room("Center", "There are rocks laying around you.\nTo the east you hear voices.");
		Room centralNorth = new Room("2N", "You have stumbled into a maintenance closet of some sort.");
		Room centralSouth = new Room("2S", "You've discovered an armory!");
		Room guards = new Room("3E", "You've found the path to the exit!\nA guard has spotted you.", rockThrown);
		Room exit = new Room("Exit", "One final guard stands between you and the exit", keyAcquired);
		
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
	
	/**
	 * @return the singleton instance of this class.
	 */
	public static GameMap getInstance() {
		return INSTANCE;
	}
	
	/**
	 * @return List of all rooms on the map
	 */
	public List<Room> getRoomList() {
		return roomList;
	}
	
	/**
	 * Searches the map for a Room with a given name.
	 * 
	 * @param name Room name to look for
	 * @return Room found, or null if no Room was found
	 */
	public Room getRoomByName(String name) {
		for (Room r : roomList) {
			if (r.getName().equals(name)) {
				return r;
			}
		}
		
		return null;
	}
	
	/**
	 * @return the Room the Player starts in.
	 */
	public Room getInitialRoom() {
		return roomList.get(0);
	}
	
	/**
	 * Attempts to move the Player to a different room.
	 * 
	 * @param dir Direction to attempt to move the Player
	 * @param player the Player to move
	 */
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
				System.out.println("You need to complete a checkpoint before going that direction.");
				if (roomRequiredCP.equals(rockThrown)) {
					System.out.println("There are two guards to the east.");
					System.out.println("You need to figure out a use for your rocks to continue.");
					return;
				} else if (roomRequiredCP.equals(keyAcquired)) {
					System.out.println("You need to find the key to open the next door. It should be nearby...");
					return;
				}
			}
		}
		
		System.out.println("You move to the " + dir.toString());
		System.out.println(nextRoom.getEnterDescription());
		player.setLocation(nextRoom);
		
		// fail state: if player goes to a guard and doesn't have a weapon, they die
		if (nextRoom.equals(getRoomByName("3E")) && player.getInventory().getWeaponCount() == 0) {
			System.out.println("You have no weapons to defend yourself from the guard in this room! Game over!");
			BCDK.RUNNING = false;
		} else if(nextRoom.equals(getRoomByName("3E"))) { // player had a weapon
			System.out.println("You use your weapon to defeat the guard in this room to advance further.");
			System.out.println("The guard you defeated dropped something!");
		} else if(nextRoom.equals(getRoomByName("Exit"))) {
			System.out.println("You defeat the final guard with your weapon.");
			System.out.println("Congratulations, you completed BCDK!");
			BCDK.RUNNING = false;
		}
	}
	
	/**
	 * Picks up items off the floor of the Player's current room and adds them to their inventory.
	 * 
	 * @param player the Player to operate on
	 */
	public void pickupItems(Player player) {
		Room curRoom = player.getLocation();
		List<Item> floorItems = curRoom.getFloorItems();
		if (floorItems != null && floorItems.size() > 0) {
			Inventory playerInventory = player.getInventory();
			// 3.5 - example of foreach statement
			for (Item i : floorItems) {
				if (i instanceof Rock) {
					playerInventory.addRocks((Rock)i);
					System.out.println("You picked up a rock.");
				} else if (i instanceof Key) {
					playerInventory.addKey((Key)i);
					System.out.println("You picked up a key!");
					player.addCheckpointReached(keyAcquired);
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
