package bcdk;

import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class BCDK {
	public static boolean RUNNING = true;
	public static Logger logger = LogManager.getLogger(BCDK.class);
	
	//Items
	//Create items for testing, At the moment Just tells player that the item they use does not exsist if they try to use it without finding it in the game
	static Items.Key key1=new Items.Key();  //Create Key object
	static Items.Rocks PlayerRocks=new Items.Rocks(); 

	
	/**
	 * Takes user input and attempts to process it.
	 * 
	 * @param in User input from console, GUI, whatever.
	 * @return String to display to the user as a result of the command.
	 */
	private static String parseInput(String in) {
		// double-check that the input is all uppercase, and remove any extra whitespace
		in = in.toUpperCase().strip();
		logger.debug("Got command: " + in);
		
		String[] input = in.split(" ");
		
		// return nothing if we received only whitespace
		if(input.length < 1 || input[0].isBlank()) {
			return "";
		}
		
		StringBuilder ret = new StringBuilder();
		
		// Note: switch statements use String.equals, not ==, so this is fine
		//Should add a Grab or Pickup command, maybe under LOOK case?
		switch(input[0]) {
		case "QUIT":
			RUNNING = false;
			break;
		case "HELP":
			// TODO: Implement help
			break;
		// further examples/ideas
		case "GO":
		case "MOVE":
			
			switch(input[1]) {
			case "NORTH":
				break;
			case "SOUTH":
				break;
			case "WEST":
				break;
			case "EAST":
				break;
			default:
				break;
			}
			break;
		case "LOOK":
			break;
			
		//Adding items to inventory
			//Need to remove the item from game world 
			// player grabs key, then if they look in the room there is no more key. Same for Rock
			//Only allow grab to be run one time per room? and once item is grabbed just say room has nothing of note
		case "PICKUP":
		case "P":
		case "GRAB":
		case "G":
			switch(input[1]) {
			case "KEY":
				key1.HaveKey=true; 
				//Add key into inventory
				break;
			case "ROCK":
				PlayerRocks.RockCount++;
				//Update RockCount and write that into inventory
				break;
			}
			break;	
			
		//Actions that relate to player inventory
		case "INVENTORY": 
		case "I":
			//Use items that are in inventory
			switch(input[1]) {
			case "USE":
					switch(input[2]) {
					case "KEY":
					key1.UseKey(); //For now placeholder text. In future if used next to locked door, then opens that door.
					break;
					case "ROCK":
					case "ROCKS"://Throw rock in that direction, add thing that calls AI to it. If there is no room in that direction just throw rock against wall, alert AI to player location
						switch(input[3]) {
						case "NORTH":
							PlayerRocks.Throw("north");
							break;
						case "SOUTH":
							PlayerRocks.Throw("south"); 
							break;
						case "WEST":
							PlayerRocks.Throw("west"); 
							break;
						case "EAST":
							PlayerRocks.Throw("east"); 
							break;
						default:
							break;
						}
					
					break;
					}
					break;
				//Look at items that are in inventory	
				case "EXAMINE":
					switch(input[2]) {
					case "KEY":
					key1.WhatKey(); //Tell user what key looks like, Placeholder description until map is fully designed
					break;
					case "ROCK":
					case "ROCKS":
					PlayerRocks.WhatRocks(); //Tell player how many rocks they have
					break;
				}
			
			}
			break;
			
		//Combat 	
		case "F":
		case "FIGHT":
			Entities player = new Entities("Player", 100, 20);
			Entities npc = new Entities("Enemy", 100, 0);
			Combat fight = new Combat(player, npc);
			Entities winner = fight.FightWinner();
			if(winner.getName().equals(player.getName())) {
				System.out.println("Battle ended");
				winner = null;
			} else {
				System.out.println("Player is dead");
				RUNNING = false;
			}
			break;
		default:
			ret.append("Unknown command: ").append(in);
			break;
		}
		return ret.toString();
	}

	public static void main(String[] args) {
		System.out.println("Welcome to BCDK.");
		
		//Load Items for player
		
		
		// TODO: Print some sort of introduction about the game?
		
		try(Scanner scanner = new Scanner(System.in)) {
			while(RUNNING) {
				System.out.print("> ");
				
				String input = scanner.nextLine().toUpperCase();
				
				// Echo received string back. comment out for final build.
				System.out.println(input);
				
				String output = parseInput(input);
				if(!output.isBlank()) {
					System.out.println(output);
				}
				
				// TODO: implement an auto-save after each command?
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			System.out.println("Thank you for playing!");
		}
	}
}