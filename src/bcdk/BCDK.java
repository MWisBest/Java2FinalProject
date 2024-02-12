package bcdk;

import java.util.Scanner;

import org.apache.logging.log4j.Logger;

import bcdk.Items.Key;
import bcdk.Items.Rocks;

import org.apache.logging.log4j.LogManager;

public class BCDK {
	public static boolean RUNNING = true;
	public static Logger logger = LogManager.getLogger(BCDK.class);
	public static Inventory Inv = new Inventory();
	
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
			
		case "INVENTORY": 
		case "I":
			switch(input[1]) {
			case "USE":
					switch(input[2]) {
					case "KEY":
					key1.UseKey(); //For now placeholder text. In future if used next to locked door, then opens that door.
					break;
					case "ROCK":
					PlayerRocks.Throw(); //For now placeholder text.  In future could do USE ROCK *DIRECTION* or have a FACE *Direction* and throw rock forward 
					break;
					}
					break;
					
			case "EXAMINE":
					switch(input[2]) {
					case "KEY":
						Inv.addKey(key1); // temporary line. checks that inventory works as intended
					key1.WhatKey(); //Tell user what key looks like, Placeholder description until map is fully designed
					break;
					case "ROCK":
						Inv.addRocks(PlayerRocks); // temporary line. checks that inventory works as intended 
					PlayerRocks.WhatRocks(); //Tell player how many rocks they have
					break;
					}
					break;
					
			case "CHECK":
				Inv.displayKeys();
				Inv.displayRocks();
			
			}
			break;
		case "A":
		case "ATTACK":
			break;
		default:
			ret.append("Unknown command: ").append(in);
			break;
		}
		return ret.toString();
	}
	
	//Create items for testing, At the moment Just tells player that the item they use does not exsist if they try to use it without finding it in the game
	static Items.Key key1=new Items.Key(); 
	static Items.Rocks PlayerRocks=new Items.Rocks(); 


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
