package bcdk;

public class Movement {

	// point on map for intersections to test which message to send back
	private static int[][] refPoints = { { 4, 4 }, { 2, 4 }, { 2, 6 }, { 2, 8 }, { 0, 8 }, { 4, 8 }, { 4, 11 } };
	private static int UsingRefPoint = 0;

	// setting up object for class to pull up the right story message to feed back
	// to player and to update as story progresses
	private static GameMessages gm = new GameMessages();

	// Method to call start of story
	public String StartStory() {
		// returns start message for story
		return gm.GetMessage("Start");
	}

	public void UpdateStory() {

	}

	// method called to see if movement is valid and sending message to user
	public static String UpdatePostion(String direction, Player player) {

		// calling the map to test if movement is possible
		Map m = Map.getInstance();

		// setting up index to return after testing and if testing fails sending
		// movement is invalid
		String index = "Invalid Movement";
		
		int x = player.getLocationX();
		int y = player.getLocationY();

		// switch statement to see if direction is valid
		switch (direction) {

		// testing going north to see if it was valid movement
		case "NORTH":
			if (m.IsMovementValid(x + 1, y)) {
				String gamemsg = Integer.toString(x + 1) + Integer.toString(y) + SetDirectionMessage(x + 1, y, direction, player);
				
				BCDK.logger.debug("North gamemsg: " + gamemsg);

				// combine code to send to story to see message it should return
				index = gm.GetMessage(gamemsg);
				
				BCDK.logger.debug("North index: " + index);

				// if its valid move in that direction
				player.setLocationX(x + 1);
			}
			break;

		// testing south
		case "SOUTH":
			if (m.IsMovementValid(x - 1, y)) {
				String gamemsg = Integer.toString(x - 1) + Integer.toString(y) + SetDirectionMessage(x - 1, y, direction, player);
				
				BCDK.logger.debug("South gamemsg: " + gamemsg);

				// getting story message to send to user
				index = gm.GetMessage(gamemsg);
				
				BCDK.logger.debug("South index: " + index);

				// updating position
				player.setLocationX(x - 1);
			}
			break;

		// testing west
		case "WEST":
			if (m.IsMovementValid(x, y - 1)) {
				String gamemsg = Integer.toString(x) + Integer.toString(y - 1) + SetDirectionMessage(x, y - 1, direction, player);
				
				BCDK.logger.debug("West gamemsg: " + gamemsg);

				// getting story mission
				index = gm.GetMessage(gamemsg);
				
				BCDK.logger.debug("West index: " + index);

				// updating position
				player.setLocationY(y - 1);
			}
			break;

		// testing east
		case "EAST":
			if (m.IsMovementValid(x, y + 1)) {
				String gamemsg = Integer.toString(x) + Integer.toString(y + 1) + SetDirectionMessage(x, y + 1, direction, player);
				
				BCDK.logger.debug("East gamemsg: " + gamemsg);

				// getting story message
				index = gm.GetMessage(gamemsg);
				
				BCDK.logger.debug("East index: " + index);

				// updating position
				player.setLocationY(y + 1);
			}
			break;

		// default for invalid messages maybe handled later but added it in this portion
		// at the moment
		default:
			index = "NVM";
		}

		// returning the story mission to user
		return index;
	}

	// testing to see if we are coming back to reference point or moving away from
	// it
	private static String SetDirectionMessage(int row, int col, String direction, Player player) {

		// setting up string to pass
		String mess = "F";

		boolean isRef = false;

		// looping through each reference point to see if it changed
		for (int i = 0; i < 7; i++) {
			// if reference point changed update value and end loop
			if (player.getLocationX() == refPoints[i][0] && player.getLocationY() == refPoints[i][1]) {
				UsingRefPoint = i;
				// changing variable so it doesn't continue direction
				isRef = true;
				// sending index as keyword
				mess = "Index";
				break;
			}
		}

		if (isRef == false) {
			// finding the difference from old point to test which direction user is going
			// from reference point
			int difFindMeRow = player.getLocationX() - refPoints[UsingRefPoint][0];
			int difFindMeColumn = player.getLocationY() - refPoints[UsingRefPoint][1];

			// finding the difference from new point
			int newRow = row - refPoints[UsingRefPoint][0];
			int newCol = col - refPoints[UsingRefPoint][1];

			if (direction.equals("NORTH") && difFindMeRow < newRow) {
				mess = "B";
			} else if (direction.equals("SOUTH") && difFindMeRow > newRow) {
				mess = "B";
			} else if (direction.equals("WEST") && difFindMeColumn < newCol) {
				mess = "B";
			} else if (direction.equals("EAST") && difFindMeColumn > newCol) {
				mess = "B";
			}

		}

		return mess;
	}

}
