package bcdk;

public class Map {
	
	// just a boolean array holding values for valid spots to move on map
	private final boolean[][] gameMap = {{false, false, false, false, true, false, true, false, true, true, true},
									{false, false, false, false, true, false, true, false, true, false, true},
									{true, true, true, true, true, true, true, true, true, false, true},
									{false, false, false, false, true, false, false, false, true, false, true},
									{false, false, false, true, true, false, false, false, true, true, true}};
	
	private Map() {};
	private static final Map instance = new Map();
	
	public static Map getInstance() {
		return instance;
	}
	
	// variables to hold starting point set up in this class to hold the ability to possibly dynamically call it with database
	private final int startRow = 4;
	private final int startCol = 3;
	
	// this will call the place on the map movement is attempting to go too
	// will return false if not found
	public synchronized boolean IsMovementValid(int a, int b) {
		
		// exception handling if bound goes outside of the array
		try {
			// returns array value
			return gameMap[a][b];
		}catch(Exception e)
		{
			// returns false if value outside of array
			return false;
		}
	}
	
	//methods to call starting point on map
	
	// method to get starting row
	public synchronized int GetStartRow() {
		return startRow;
	}
	
	// method to get starting column
	public synchronized int GetStartCol() {
		return startCol;
	}
}
