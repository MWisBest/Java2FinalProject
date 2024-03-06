package bcdk;

import java.sql.SQLException;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;

import bcdk.entity.Combat;
import bcdk.entity.Enemy;
import bcdk.entity.Entities;
import bcdk.entity.NPCBehavior;
import bcdk.entity.Player;
import bcdk.item.Inventory;
import bcdk.item.Items;
import bcdk.map.Direction;
import bcdk.map.GameMap;

import org.apache.logging.log4j.LogManager;

public class BCDK {
	public static boolean RUNNING = true;
	public static Logger logger = LogManager.getLogger(BCDK.class);
	static Player player = new Player("Player", 100, 0);
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
			} else {
				switch (input[1]) {
				case "NORTH":
				case "SOUTH":
				case "WEST":
				case "EAST":
					Direction dir = Direction.valueOf(input[1]);
					
					//ret.append(Movement.UpdatePostion(input[1], player));
					break;
				default:
					ret.append("Usage: " + input[0] + " [NORTH/SOUTH/WEST/EAST]");
					break;
				}
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
			switch (input[1]) {
			case "KEY":
				key1.KeyCount++;
				PlayerInventory.addKey(key1);
				// Add key into inventory
				break;
			case "ROCK":
				PlayerRocks.RockCount++;
				PlayerInventory.addRocks(PlayerRocks);
				// Update RockCount and write that into inventory
				break;
			case "MACE":
				PlayerInventory.addWeapons(mace);
				break;
			}
			break;

		// Actions that relate to player inventory
		case "INVENTORY":
		case "I":
			// Use items that are in inventory
			switch (input[1]) {
			case "USE":
				switch (input[2]) {
				case "KEY":
					key1.UseKey(); // For now placeholder text. In future if used next to locked door, then opens
									// that door.
					break;
				case "ROCK":
				case "ROCKS":// Throw rock in that direction, add thing that calls AI to it. If there is no
								// room in that direction just throw rock against wall, alert AI to player
								// location
					switch (input[3]) {
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
			// Look at items that are in inventory
			case "EXAMINE":
				switch (input[2]) {
				case "KEY":
					PlayerInventory.displayKeys();
					break;
				case "ROCK":
				case "ROCKS":
					PlayerInventory.displayRocks(); // Tell player how many rocks they have
					break;
				case "WEAPON":
				case "WEAPONS":
					PlayerInventory.displayWeapons();
				}

			}
			break;

		// Combat
		case "F":
		case "FIGHT":
			Enemy npc = new Enemy("Enemy", 100, 0);
			Combat fight = new Combat(player, npc, PlayerInventory);
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
				ret.append("Clearing save data and restarting the game!");
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

		// Get the map
		GameMap map = new GameMap();
		// Set player initial position.
		player.setLocation(map.getInitialRoom());
		// create guard with 10 health that uses current map
		// Can be moved have health changed with methods
		NPCBehavior guard1 = new NPCBehavior(map, 10);

		PlayerRocks.RockCount = 0;
		// TODO: Print some sort of introduction about the game?

		try (Scanner scanner = new Scanner(System.in)) {
			try {
				savegame = new SaveGame("bcdk");
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(e);
				System.out.println("Error initializing savegame data. Saving will be unavailable.");
				savegame = null;
			} catch (SaveGame.VersionMismatchException e) {
				System.out.println(
						"Your previous savegame uses an old format that is not compatible with this version, your progress was reset.");
				logger.warn(e);
			}

			while (RUNNING) {
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
		} finally {
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