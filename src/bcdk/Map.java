package bcdk;

public class Map {

	// just a boolean array holding values for valid spots to move on map
	private boolean[][] gameMap = {{false, false, false, false, true, false, true, false, true, true, true},
									{false, false, false, false, true, false, true, false, true, false, true},
									{true, true, true, true, true, true, true, true, true, false, true},
									{false, false, false, false, true, false, false, false, true, false, true},
									{false, false, false, true, true, false, false, false, true, true, true}};
	
	// variables to hold starting point set up in this class to hold the ability to possibly dynamically call it with database
	private int startRow = 4;
	private int startCol = 3;
	
	// this will call the place on the map movement is attempting to go too
	// will return false if not found
	public boolean IsMovementValid(int a, int b) {
		
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
	public int GetStartRow() {
		return startRow;
	}
	
	// method to get starting column
	public int GetStartCol() {
		return startCol;
	}
}
