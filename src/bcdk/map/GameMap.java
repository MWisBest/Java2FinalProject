package bcdk.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.PropertyResourceBundle;

import bcdk.BCDK;
import bcdk.entity.Combat;
import bcdk.entity.Enemy;
import bcdk.entity.Entities;
import bcdk.entity.Player;
import bcdk.item.Inventory;
import bcdk.item.Item;
import bcdk.item.Key;
import bcdk.item.Rock;
import bcdk.item.Weapon;

public class GameMap {
    /**
     * the list of rooms available in the map
     */
	private HoldList<Room> rooms = new HoldList<>();
	
	// 1.4 - example of final
	// 1.5 - proper use of static keyword
	// 2.3 - example of singleton pattern
	private static final GameMap INSTANCE = new GameMap();
	
	/**
	 * creates a checkpoint where a rock must be used before proceeding
	 */
	public Checkpoint rockThrown = new Checkpoint("RockThrow");
	
	/**
	 * creates a checkpoint where the key is needed to proceed
	 */
	public Checkpoint keyAcquired = new Checkpoint("KeyAcquired");
	
	/**
	 * class constructor
	 * initiate all the rooms necessary and add the items needed for the game
	 */
	private GameMap() {
		ArrayList<Room> roomList = new ArrayList<>();
		Room startWest = new Room("1W", BCDK.messages.getString("start_west"));
		Room central = new Room("Center", BCDK.messages.getString("central1")+"\n"+BCDK.messages.getString("central2"));
		Room centralNorth = new Room("2N", BCDK.messages.getString("central_north"));
		Room centralSouth = new Room("2S", BCDK.messages.getString("central_south"));
		Room guards = new Room("3E", BCDK.messages.getString("guards1")+"\n"+BCDK.messages.getString("guards2"), rockThrown);
		Room exit = new Room("Exit", BCDK.messages.getString("exit"), keyAcquired);
		
		centralNorth.addFloorItem(new Weapon("Axe", BCDK.messages.getString("weapon_axe"), 5));
		centralSouth.addFloorItem(new Weapon("Sword", BCDK.messages.getString("weapon_sword"), 8));
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
		
		// tells user desired room does not exist
		if (nextRoom == null) {
			System.out.println(BCDK.messages.getString("room_null") + " " + dir.toString() + ".");
			return;
		}
		
		Checkpoint roomRequiredCP = nextRoom.getRequiredCheckpoint();
		// use of optional type
		Optional<Checkpoint> optionalCheckpoint = Optional.ofNullable(roomRequiredCP); 
		
		if(!optionalCheckpoint.isEmpty()) {
			System.out.println(optionalCheckpoint);
			if (player.checkForCheckpoint(roomRequiredCP)) {
				
				System.out.println(BCDK.messages.getString("move_cp1"));
				if (roomRequiredCP.equals(rockThrown) && rockThrown.isCompleted == false) {
					System.out.println(BCDK.messages.getString("move_cp2"));
					System.out.println(BCDK.messages.getString("move_cp3"));
					return;
				} else if (roomRequiredCP.equals(keyAcquired) && keyAcquired.isCompleted == false) {
					System.out.println(BCDK.messages.getString("move_cp4"));
					return;
				}
			}
		}
		
		System.out.println(BCDK.messages.getString("move_indication") + " " + dir.toString());
		System.out.println(nextRoom.getEnterDescription());
		player.setLocation(nextRoom);
		
		// fail state: if player goes to a guard and doesn't have a weapon, they die
		if (nextRoom.equals(getRoomByName("3E")) && player.getInventory().getWeaponCount() == 0) {
			System.out.println(BCDK.messages.getString("Guard_Map1"));
			BCDK.RUNNING = false;
		} else if(nextRoom.equals(getRoomByName("3E"))) { // player had a weapon
			Enemy npc = new Enemy("Ramsay Bolton", 100, 0);
			Combat fight = new Combat(player, npc, player.getInventory());
			Entities winner = fight.FightWinner();
			// determine which entity won the fight based on the winner variable
			 Runnable printResult = () -> { //2.1 Lambda expression. 4.1 Variable in expression 
	                if (winner.getName().equals(player.getName())) { //2.5 Object Comparison 
	                    System.out.println(BCDK.messages.getString("fight_end1"));
	                    System.out.println(BCDK.messages.getString("Guard_Map3"));
	                } else {
	                    System.out.println(BCDK.messages.getString("fight_end2"));
	                    BCDK.RUNNING = false;
	                }
	         };
	         printResult.run();
			//System.out.println(BCDK.messages.getString("Guard_Map2"));
			
		} else if(nextRoom.equals(getRoomByName("Exit"))) {
			System.out.println(BCDK.messages.getString("Guard_Map4"));
			Enemy npc = new Enemy("Roose Bolton", 100, 0);
			Combat fight = new Combat(player, npc, player.getInventory());
			Entities winner = fight.FightWinner();
			// determine which entity won the fight based on the winner variable
			 Runnable printResult = () -> { //2.1 Lambda expression. 4.1 Variable in expression 
	                if (winner.getName().equals(player.getName())) { //2.5 Object Comparison 
	                    System.out.println(BCDK.messages.getString("fight_end1"));
	                    System.out.println(BCDK.messages.getString("Guard_Map5"));
	                } else {
	                    System.out.println(BCDK.messages.getString("fight_end2"));
	                    BCDK.RUNNING = false;
	                }
	         };
	         printResult.run();
			
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
				// rock is added to inventory
				if (i instanceof Rock) {
					playerInventory.addRocks((Rock)i);
					System.out.println(BCDK.messages.getString("map_rock_pickup"));
					// key is added to inventory
				} else if (i instanceof Key) {
					playerInventory.addKey((Key)i);
					System.out.println(BCDK.messages.getString("map_key_pickup"));
					player.addCheckpointReached(keyAcquired);
					keyAcquired.isCompleted = true;
					// weapon is added to the game
				} else if (i instanceof Weapon) {
					playerInventory.addWeapons((Weapon)i);
					System.out.println(BCDK.messages.getString("map_weapon_pickup"));
				}
			}
			floorItems.clear();
		} else {
			System.out.println(BCDK.messages.getString("map_null_pickup"));
		}
	}
}
