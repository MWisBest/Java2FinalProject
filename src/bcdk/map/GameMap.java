package bcdk.map;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
	
	private List<Room> roomList = new ArrayList<>();
	
	public GameMap() {
		Room startWest = new Room("1W");
		Room central = new Room("Center");
		Room centralNorth = new Room("2N");
		Room centralSouth = new Room("2S");
		Room guards = new Room("3E");
		Room exit = new Room("Exit");
		
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
}
