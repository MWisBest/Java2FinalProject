package bcdk;

public class Movement {
	
	// variable to hold position on map located at
	private int[] findMe = new int[2];
	
	// point on map for intersections to test which message to send back
	private int[][] refPoints = { {4, 4}, {2, 4}, {2, 6}, {2, 8}, {0, 8}, {4, 8}, {4, 11}};
	private int UsingRefPoint = 0;
	
	// setting up object for class to pull up the right story message to feed back to player and to update as story progresses
	private GameMessages gm = new GameMessages();
	
	// constructor just to setup start position
	public Movement() {
		SetStartingPosition();
	}
	
	private void SetStartingPosition() {

		// getting starting position for spot on map
		Map m = new Map();
		// setting starting spots on array
		findMe[0] = m.GetStartRow();
		findMe[1] = m.GetStartCol();
	}
	
	// Method to call start of story
	public String StartStory(){
		// returns start message for story
		return gm.GetMessage("Start");
	}
	

	public void UpdateStory() {
		
	}
	
	// method called to see if movement is valid and sending message to user
	public String UpdatePostion(String Direction) {
		
		// calling the map to test if movement is possible
		Map m = new Map();
		
		// setting up index to return after testing and if testing fails sending movement is invalid
		String index = "Invalid Movement";
		
		// switch statement to see if direction is valid
		switch(Direction) {
		
			// testing going north to see if it was valid movement
			case "North":
				if(m.IsMovementValid(findMe[0]++, findMe[1])) {
					
					// combine code to send to story to see message it should return
					 index = gm.GetMessage(Integer.toString(findMe[0]++) + Integer.toString(findMe[1]) + SetDirectionMessage(findMe[0]++, findMe[1]));
					 
						// if its valid move in that direction 
						findMe[0] = findMe[0]++;
				}
				break;
				
			// testing south
			case "South":
				if(m.IsMovementValid(findMe[0]-- , findMe[1])) {

					//getting story message to send to user
					index = gm.GetMessage(Integer.toString(findMe[0]--) + Integer.toString(findMe[1]) + SetDirectionMessage(findMe[0]--, findMe[1]));
					
					// updating position
					findMe[0] = findMe[0]--;
					
				}
				break;
				
			// testing west	
			case "West":
				if(m.IsMovementValid(findMe[0], findMe[1]--)) {

					// getting story mission
					index = gm.GetMessage(Integer.toString(findMe[0]) + Integer.toString(findMe[1]--) + SetDirectionMessage(findMe[0], findMe[1]--));
									
					// updating position
					findMe[1] = findMe[1]--;
				}
				break;
				
			// testing east
			case "East":
				if(m.IsMovementValid(findMe[0], findMe[1]++)) {
					
					// getting story message
					index = gm.GetMessage(Integer.toString(findMe[0]) + Integer.toString(findMe[1]++) + SetDirectionMessage(findMe[0], findMe[1]++));
					
					// updating position
					findMe[1] = findMe[1]++;
				}
				break;
				
			// default for invalid messages maybe handled later but added it in this portion at the moment
			default:
				index = "NVM";
		}
		
		// returning the story mission to user
		return index;
	}
	
	// testing to see if we are coming back to reference point or moving away from it
	private String SetDirectionMessage(int row, int col) {
		
		// setting up string to pass
		String mess = "F";
		
		boolean isRef = false;
		
		// looping through each reference point to see if it changed
		for(int i = 0; i < 7; i++) {
			// if reference point changed update value and end loop
			if(findMe[0] == refPoints[i][0] && findMe[1] == refPoints[i][1]) {
				UsingRefPoint = i;
				//changing variable so it doesn't continue direction
				isRef = true;
				// sending index as keyword
				mess = "Index";
				break;
			}
		}
		
		if(isRef == false)
		{
			// finding the difference from old point to test which direction user is going from reference point
			int difFindMeRow = findMe[0] - refPoints[UsingRefPoint][0];
			int difFindMeColumn = findMe[1] - refPoints[UsingRefPoint][1];
		
			// finding the difference from new point
			int newRow = row - refPoints[UsingRefPoint][0];
			int newCol = col - refPoints[UsingRefPoint][1];
		
		
			//if(difFindMeRow > 0) {
			
			//} else if(difFindMeRow < 0){
			
			//} else if(difFindMeColumn > 0){
		
			//} else {
		
			//}
		}
		
		return mess;
	}
	
	
}
