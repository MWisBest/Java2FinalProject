package bcdk;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;

import bcdk.entity.Combat;
import bcdk.entity.Enemy;
import bcdk.entity.Entities;
import bcdk.entity.Player;
import bcdk.item.Inventory;
import bcdk.map.Direction;
import bcdk.map.GameMap;

import org.apache.logging.log4j.LogManager;

public class BCDK {
	public static boolean RUNNING = true;
	public static Logger logger = LogManager.getLogger(BCDK.class);
	static Player player = new Player("Player", 100, 0);
	static GameMap map = GameMap.getInstance();
	public static SaveGame savegame = null;

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
		if (input.length < 1 || input[0].isBlank()) {
			return "";
		}

		StringBuilder ret = new StringBuilder();

		// Note: switch statements use String.equals, not ==, so this is fine
		// Should add a Grab or Pickup command, maybe under LOOK case?
		switch (input[0]) {
		case "QUIT":
			RUNNING = false;
			break;
		case "HELP":
			// TODO: Implement help
			break;
		// further examples/ideas
		case "GO":
		case "MOVE":
			if (input.length < 2) {
				ret.append("Usage: " + input[0] + " [NORTH/SOUTH/WEST/EAST]");
				break;
			}
			switch (input[1]) {
			case "NORTH":
			case "SOUTH":
			case "WEST":
			case "EAST":
				Direction dir = Direction.valueOf(input[1]);
				map.move(dir, player);
				break;
			default:
				ret.append("Usage: " + input[0] + " [NORTH/SOUTH/WEST/EAST]");
				break;
			}
			break;
		case "LOOK":
			break;

		// Adding items to inventory
		// Need to remove the item from game world
		// player grabs key, then if they look in the room there is no more key. Same
		// for Rock
		// Only allow grab to be run one time per room? and once item is grabbed just
		// say room has nothing of note
		case "PICKUP":
		case "P":
		case "GRAB":
		case "G":
			map.pickupItems(player);
			break;

		// Actions that relate to player inventory
		case "INVENTORY":
		case "I":
			// Use items that are in inventory
			if (input.length == 1) {
				ret.append("Usage: INVENTORY [USE/EXAMINE]");
				break;
			}
			switch (input[1]) {
			case "USE":
				if (input.length == 2) {
					ret.append("Usage: INVENTORY USE [KEY/ROCK]");
					break;
				}
				switch (input[2]) {
				case "KEY":
					if (player.getLocation().equals(map.getRoomByName("Exit"))) {
						System.out.println("You use the key to leave the castle!");
						System.out.println("Congratulations, you've completed the game!");
						break;
					} else {
						System.out.println("There doesn't seem to be any use for your key in this location.");
					}
					break;
				case "ROCK":
				case "ROCKS":
					if (player.checkForCheckpoint(map.rockThrown)) {
						System.out.println("You have no further use for rocks.");
						break;
					}
					Inventory inv = player.getInventory();
					if (inv.getRockCount() > 0) {
						if (player.getLocation().equals(map.getRoomByName("Center"))) {
							player.addCheckpointReached(map.rockThrown);
							System.out.println("You throw a rock far to the east.");
							System.out.println("One of the two guards to the east went away to check out the noise!");
							System.out.println("Checkpoint reached!");
						} else {
							System.out.println("There doesn't seem to be any use for your rocks in this location.");
						}
					} else {
						System.out.println("You don't have any rocks to use.");
					}
					break;
				default:
					ret.append("Usage: INVENTORY USE [KEY/ROCK]");
					break;
				}
				break;
			// Look at items that are in inventory
			case "EXAMINE":
				if (input.length == 2) {
					ret.append("Usage: INVENTORY EXAMINE [KEY/ROCK/WEAPONS]");
					break;
				}
				switch (input[2]) {
				case "KEY":
					player.getInventory().displayKeys();
					break;
				case "ROCK":
				case "ROCKS":
					player.getInventory().displayRocks(); // Tell player how many rocks they have
					break;
				case "WEAPON":
				case "WEAPONS":
					player.getInventory().displayWeapons();
					break;
				default:
					ret.append("Usage: INVENTORY EXAMINE [KEY/ROCK/WEAPONS]");
					break;
				}
				break;
			default:
				ret.append("Usage: INVENTORY [USE/EXAMINE]");
				break;
			}
			break;

		// Combat
		case "F":
		case "FIGHT":
			Enemy npc = new Enemy("Enemy", 100, 0);
			Combat fight = new Combat(player, npc, player.getInventory());
			Entities winner = fight.FightWinner();
			// determine which entity won the fight based on the winner variable
			if (winner.GetName().equals(player.GetName())) {
				System.out.println("Battle ended");
				winner = null;
			} else {
				System.out.println("Player is dead");
				RUNNING = false;
			}
			break;
		case "SAVE":
			ret.append("Saving is automatically done when you reach a checkpoint.");
			break;
		case "LOAD":
			// TODO: Load saved games, both on command and on game start
			break;
		case "RESTART":
			if (input.length == 2 && input[1].equals("PLEASE")) {
				// TODO: Actually clear save data and restart the game.
				ret.append("Clearing save data! Restart the game afterwards.");
				try {
					savegame.deleteSaveData();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				RUNNING = false;
			} else {
				ret.append("Usage: RESTART PLEASE. Clears your saved data and restarts the game.");
			}
		default:
			ret.append("Unknown command: ").append(in);
			break;
		}
		return ret.toString();
	}

	public static void main(String[] args) {
		System.out.println("Welcome to BCDK.");

		// Set player initial position.
		player.setLocation(map.getInitialRoom());

		// TODO: Print some sort of introduction about the game?
		
		LocalTime time = LocalTime.now();
		int hour = time.getHour();
		int minute = time.getMinute();
		
		String greeting = "";
		if(hour < 6 || (hour == 6 && minute == 0)) {
			greeting = "Are you just going to bed or just waking up?";
		} else if(hour < 12) {
			greeting = "Good morning!";
		} else if(hour == 12) {
			greeting = "Having a good lunch break?";
		} else if(hour < 21) {
			greeting = "Good afternoon!";
		} else {
			greeting = "Good evening!";
		}

		System.out.println(greeting);

		// 6.1 - proper use of try-catch blocks
		// 6.4 - use of try-with-resources block
		try (Scanner scanner = new Scanner(System.in)) {
			try {
				savegame = new SaveGame("bcdk");
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(e);
				System.out.println("Error initializing savegame data. Saving will be unavailable.");
				savegame = null;
			// 6.3 - use of custom exception
			} catch (SaveGame.VersionMismatchException e) {
				System.out.println(
						"Your previous savegame uses an old format that is not compatible with this version, your progress was reset.");
				logger.warn(e);
			}

			while (RUNNING) {
				// 8.1 - reading/writing to console
				System.out.print("> ");

				String input = scanner.nextLine().toUpperCase();

				// Echo received string back. comment out for final build.
				System.out.println(input);

				String output = parseInput(input);
				if (!output.isBlank()) {
					System.out.println(output);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally { // 6.2 - use of finally block
			System.out.println("Thank you for playing!");
			try {
				if (savegame != null) {
					savegame.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
			}
		}
	}
}