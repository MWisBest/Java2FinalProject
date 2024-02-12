package bcdk;

public class Items {
//Item Ideas so far

	public static class Key{ //Used to open a locked door, Could implement more keys and more doors
		 String KeyDescription="A large iron key with a ___ branded on it"; // IF player examines key could give hints to where key goes to, or just says key can unlock XX door
		Boolean HaveKey=false; //Update to true if player has key
		 
		public void UseKey () {
			 if (HaveKey==true)
				 //If room contains matching door then open that door.
				 //if we want to do Multiple locked doors then assign key ID and door ID with Open or CLosed values
				 
			System.out.println("You have used the key and unlocked the door");
			else
				System.out.println("If only you had a key...");	
		}
		
		 public void WhatKey () {
			if (HaveKey==true)
			System.out.println(KeyDescription);
			else 
				System.out.println("What on earth are you looking for?");	
		}
	}
	
	public static class Rocks{ //Used to distract Guards so player can be sneaky. 
		//Once rock thrown spawn it in the room it was thrown to? Or make single use?
		
		int RockCount=4; // rework to work with inventyory
		//How many times a player can throw a rock to move a guard to a room that is NORTH, EAST,SOUTH,WEST
	
		
		 public void Throw (String direction) {
			 if (RockCount>0) {
				 switch (direction.toLowerCase()) {
				 //Call AI to where ever the rock would end up
				 // For how many rooms away the AI is have every player movement let the AI move 1 after player does. 
				 //Example: AI is 1 room east and 1 room south. So Diagonal away from where rock went. Player Move, AI move north, PLayer move, AI move west
				 
	                case "north":
	                	System.out.println("You have Thrown the rock "+direction+"!");
	                    break;
	                case "east":
	                	System.out.println("You have Thrown the rock "+direction+"!");
	                    break;
	                case "south":
	                	System.out.println("You have Thrown the rock "+direction+"!");
	                    break;
	                case "west":
	                	System.out.println("You have Thrown the rock "+direction+"!");
	                    break;
	                default:
	                    System.out.println("Invalid direction. Please choose left, right, up, or down.");
			 }
				 //Tell user how many rocks they have left 
					RockCount--;
					System.out.println("You have "+ Integer.toString(RockCount)+" more rock left" );
				 }
	
			else
				System.out.println("You need to find another rock first");	
		}
		 public void WhatRocks () {
				if (RockCount>0)
				System.out.println("You have"+ Integer.toString(RockCount)+"more rock left" );
				else 
					System.out.println("You have no rocks");	
			}
	}
	
	//Keys- Assign a key value to a door, if player has a key with matching value they can unlock door
	//Rocks-- Use a rock to make a Guard move into nearby cell. Can only work N E S W
	//Map--Show layout of dungeon they are in
	//Weapon-- 
}