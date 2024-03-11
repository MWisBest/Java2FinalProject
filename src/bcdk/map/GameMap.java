package bcdk.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import bcdk.BCDK;
import bcdk.entity.Player;
import bcdk.item.Inventory;
import bcdk.item.Item;
import bcdk.item.Key;
import bcdk.item.Rock;
import bcdk.item.Weapon;

public class GameMap {
	static Locale currentLocale = new Locale("en"); // Spanish locale
    static ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);
	
	
	private HoldList<Room> rooms = new HoldList();
	// 1.4 - example of final
	// 1.5 - proper use of static keyword
	// 2.3 - example of singleton pattern
	private static final GameMap INSTANCE = new GameMap();
	
	public Checkpoint rockThrown = new Checkpoint("RockThrow");
	public Checkpoint keyAcquired = new Checkpoint("KeyAcquired");
	
	private GameMap() {
		ArrayList<Room> roomList = new ArrayList<>();
		Room startWest = new Room("1W", messages.getString("start_west"));
		Room central = new Room("Center", messages.getString("central1")+"\n"+messages.getString("central2"));
		Room centralNorth = new Room("2N", messages.getString("central_north"));
		Room centralSouth = new Room("2S", messages.getString("central_south"));
		Room guards = new Room("3E", messages.getString("guards1")+"\n"+messages.getString("guards2"), rockThrown);
		Room exit = new Room("Exit", messages.getString("exit"), keyAcquired);
		
		centralNorth.addFloorItem(new Weapon("Axe", messages.getString("weapon_axe"), 5));
		centralSouth.addFloorItem(new Weapon("Sword", messages.getString("weapon_sword"), 8));
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
		
		rooms.SetList(roomList);
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
		return rooms.GetList();
	}
	
	/**
	 * Searches the map for a Room with a given name.
	 * 
	 * @param name Room name to look for
	 * @return Room found, or null if no Room was found
	 */
	public Room getRoomByName(String name) {
		for (Room r : rooms.GetList()) {
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
		return rooms.GetList().get(0);
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
		case NORTE:
			nextRoom = curRoom.getNorth();
			break;
		case SUR:
			nextRoom = curRoom.getSouth();
			break;
		case OESTE:
			nextRoom = curRoom.getWest();
			break;
		case ESTE:
			nextRoom = curRoom.getEast();
			break;
		}
		
		if (nextRoom == null) {
			System.out.println(messages.getString("room_null") + " " + dir.toString() + ".");
			return;
		}
		
		Checkpoint roomRequiredCP = nextRoom.getRequiredCheckpoint();
		
		if (roomRequiredCP != null) {
			if (!player.checkForCheckpoint(roomRequiredCP)) {
				System.out.println(messages.getString("move_cp1"));
				if (roomRequiredCP.equals(rockThrown)) {
					System.out.println(messages.getString("move_cp2"));
					System.out.println(messages.getString("move_cp3"));
					return;
				} else if (roomRequiredCP.equals(keyAcquired)) {
					System.out.println(messages.getString("move_cp4"));
					return;
				}
			}
		}
		
		System.out.println(messages.getString("move_indication") + " " + dir.toString());
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
