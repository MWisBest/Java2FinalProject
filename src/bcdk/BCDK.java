package bcdk;

import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class BCDK {
	public static boolean RUNNING = true;
	public static Logger logger = LogManager.getLogger(BCDK.class);
	
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
		switch(input[0]) {
		case "QUIT":
			RUNNING = false;
			break;
		case "HELP":
			// TODO: Implement help
			break;
		// further examples/ideas
		case "GO":
			
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

	// main method
	public static void main(String[] args) {
		System.out.println("Welcome to BCDK.");
		
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
