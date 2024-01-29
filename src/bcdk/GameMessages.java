package bcdk;
//importing hashmap to use in class
import java.util.HashMap;

//this class is going to hold all the game messages and will have a call to get which message should be used
public class GameMessages {
	//HashMap to hold Messages
	private HashMap<String, String> messages = new HashMap<String, String>();
	
	// constructor to set HashMap set
	public GameMessages() {
		
		// Game Introduction
		messages.put("Start", "You were tasked to steal plans from the enemy before they could lay siege to your kingdom"
			+ " They have found you and striped you of all your weapons and have locked you in a cell"
	        + " Goals:	Free yourself from the cell : Find your Gear : Steal the plans : Escape with your life"
	        + " As you awake in your cell you remember the lock pick hidden on your person."
	        + " You have opened the locked prison cell using the lock pick into a dark hallway.");
		
		// 1st part as soon as u exit cell it is a reference point
		messages.put("44Index",  "looking North you see where the gaurd's went"
			+ " looking South you see the wall to the end of the hallway"
			+ " looking forward to the East into an empty cell"
			+ " going back South leads to your cell");
		
		// first step up after getting out of cell
		messages.put("34F", "Going North you have met the door out of the cells peaking out you see the cost is clear");
		messages.put("34B", "South you have reentered the prison cell area");
		
		// next intersection it is reference point 2
		messages.put("24Index", "North through the door into the hallway you are met at an intersection"
			+ " You can see a path North that looks well traveled but do not hear any noise coming from it"
			+ " Looking East you can see a path that seems well lit"
			+ " Looking West you see a path that leads up and you can see what looks like steps"
			+ " Looking South leads you back to the prison cells");
		
		// walking towards guards and real plan
		messages.put("23F", "West you come along the base of stairs voices and footsteps can be heard");
		messages.put("23B", "back East you come to the base of the stairs and can see the intersection in front of you");
		
		// fight the 1st guard that was pacing
		messages.put("22F", "You get to the top of the stairs and see a guard walking the halls and 2 guards at a door and the 1st gaurd comes at you");
		messages.put("22B", "You are going back East and you can see the stairwell to go down");
		
		// fight guard that guarded the door
		messages.put("21F", "The guards at the door come at you when you approach the door");
		messages.put("21B", "You walk out the office and see a long hallway");
		
		// office
		messages.put("20F", "You enter the office and see plans on table");
		
		// door right before barracks
		messages.put("14F", " You go North and you come to a closed door to your North");
		messages.put("14B", "You go back South you and exit the room back into the corridor");
		
		// Barracks
		messages.put("04F", "You enter the barracks it looks empty but you see a sword on the table");
		
		// going right at first intersection
		messages.put("25F", "You continue East down a hallway continuing on East it looks like you see another intersection");
		messages.put("25B", "You go back west you can see the intersection that you came from the prison"); 
		
		// next intersection 3rd reference point
		messages.put("26Index", "have come to an intersection"
			+ " looking north you see a hallway that looks like a set of stairs" 
			+ " looking east you see light coming from a door"
			+ " looking west you know that the path will lead back to the prison cells intersection");
		
		// going north after the 2nd intersection
		messages.put("16F", "Going north you have come to the stairwell and have started to climb to the top");
		messages.put("16B", "Going South down the stairwell back to the intersection");
		
		// top room with fake plans
		messages.put("06F", "You walk to a room that outlooks the walls and on a table you see hastly drawn battle plans");
		
		// just inside the door to the castle
		messages.put("27F", "West you come to a door and peaking out the door you can see it leads outside the castle");
		messages.put("27B", "the door behind you you return back to the building and continuing West will lead back too the 2nd intersection");
		
		// next intersection 4th intersection
		messages.put("28Index", "You are outside" 
			+ " looking West you can see the door back inside"
			+ " looking South it looks like there is a fire burning in the distance"
			+ " looking North it looks like you see a wooden shed");
		
		// walking down to fire towards guards
		messages.put("38F", "South you see many guards around a fire");
		messages.put("38B", "North will get you back to the door to the Castle"); 
		
		// Index position with fire and guards
		messages.put("48Index", "You have come into a battle with the guards around the fire and you can see the gate to the East and the path back North");
		
		// bottom position right before gate
		messages.put("49F", "You can See the gate and 2 more guards guarding it");
		messages.put("49B", "You can see the fire to the west were the guards were");
		
		// shed space with torch
		messages.put("18F", "You go north you are next to the wooden shed and there is a torch next to the shed and a path that continues north");
		messages.put("18B", "You are going back South you can see the shed and see the torch right next to it");
		
		// top index outside top left going to shed
		messages.put("08Index", "You are continuing north you can see a stable to the East"
		+ "You are going back south will lead you back to the castle door");
		
		//2 
		messages.put("09F", "You have reached the entrance to the Stable going east to enter stable");
		messages.put("09B", "You have exited the stable and left all horses in the barn. The path leads to the west");
		
		//2
		messages.put("010Index", "You have entered the stable and can see horses and a door leading South and the exit going East");
		
		//2
		messages.put("110F", "You have left the stable and can see the path continue South");
		messages.put("110B", "The Door to the stable is right to the North");
		
		//2
		messages.put("210F", "You are continuing South you can see the gate to the South");
		messages.put("210B", "Continuing North you can see the door to the Stable");
		
		//2
		messages.put("310F", "Continuing South you can see guards at the gate");
		messages.put("310B", "You can see the path continue North");
		
		// last intersection to gate
		messages.put("411Index", "You have arrived at the gate with the guards");
		
		//invalid movements
		messages.put("NVM", "Invalid Movement");
	}
	
	// method passing to get which message should be called
	public String GetMessage(String index) {
		
		return messages.get(index);
	}
}
