package bcdk;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.PropertyResourceBundle;

import org.apache.logging.log4j.Logger;

import bcdk.entity.Combat;
import bcdk.entity.Enemy;
import bcdk.entity.Entities;
import bcdk.entity.Player;
import bcdk.item.Inventory;
import bcdk.map.Direction;
import bcdk.map.GameMap;

import java.util.InputMismatchException;
import java.util.Locale;
import bcdk.savegame.SaveGame;

import org.apache.logging.log4j.LogManager;

public class BCDK {
	/**
	 * determintes if game is over or not
	 */
	public static boolean RUNNING = true;
	
	/**
	 * create a connection to the files that will be used to provide text to the game
	 */
    public static ResourceBundle messages = null;
	
	/**
	 * manage logs
	 */
	public static Logger logger = LogManager.getLogger(BCDK.class);
	
	/**
	 * creates the player 
	 */
	static Player player = new Player("Jon Snow", 100,0);
	
	/**
	 * creates the map for player to traverse
	 */
	//static GameMap map = GameMap.getInstance();
	
	/**
	 * allows for game data to be saved to database
	 */
	public static SaveGame savegame = null;
	
	
	

	/**
	 * Takes user input and attempts to process it.
	 * 
	 * @param in User input from console, GUI, whatever.
	 * @return String to display to the user as a result of the command.
	 */
	private static String parseInput(String in, GameMap map) {
		// double-check that the input is all uppercase, and remove any extra whitespace
		in = in.toUpperCase().strip();
		//logger.debug("Got command: " + in);

		String[] input = in.split(" ");

		// return nothing if we received only whitespace
		if (input.length < 1 || input[0].isBlank()) {
			return "";
		}

		StringBuilder ret = new StringBuilder();

		// Note: switch statements use String.equals, not ==, so this is fine
		// Should add a Grab or Pickup command, maybe under LOOK case?
		switch (input[0]) {
		case "TERMINAR":
		case "SALIR":
		case "QUIT":
			RUNNING = false;
			break;
		case "AYUDA":
		case "HELP":
			System.out.println(messages.getString("help1"));
			System.out.println(messages.getString("help2"));
			System.out.println(messages.getString("help3"));
			System.out.println(messages.getString("help4"));
			System.out.println(messages.getString("help5"));
			System.out.println(messages.getString("help6"));
			System.out.println(messages.getString("help7"));
			System.out.println(messages.getString("help8"));
			System.out.println(messages.getString("help9"));
			System.out.println(messages.getString("help10"));
			System.out.println(messages.getString("help11"));
			System.out.println(messages.getString("help12"));
			System.out.println(messages.getString("help13"));
			break;
		// further examples/ideas
		case "IR":
		case "GO":
		case "MOVE":
			if (input.length < 2) {
				ret.append(messages.getString("move_help1") +" "+ input[0] +" "+ messages.getString("move_help2"));
				break;
			}
			switch (input[1]) {
			case "NORTE":
			case "NORTH":
			case "SUR":
			case "SOUTH":
			case "OESTE":
			case "WEST":
			case "ESTE":
			case "EAST":
				Direction dir = Direction.valueOf(input[1]);
				map.move(dir, player);
				break;
			default:
				ret.append(messages.getString("move_help1")+" "+ input[0] +" "+ messages.getString("move_help2"));
				break;
			}
			break;
		case "LOOK":
		case"MIRAR":
			break;

		// Adding items to inventory
		// Need to remove the item from game world
		// player grabs key, then if they look in the room there is no more key. Same
		// for Rock
		// Only allow grab to be run one time per room? and once item is grabbed just
		// say room has nothing of note
		case "AGARRAR":
		case "RECOJER":
		case "PICKUP":
		case "P":
		case "GRAB":
		case "G":
			map.pickupItems(player);
			break;

		// Actions that relate to player inventory
		case "INVENTARIO":
		case "INVENTORY":
		case "I":
			// Use items that are in inventory
			if (input.length == 1) {
				ret.append(messages.getString("inventory_help1"));
				break;
			}
			switch (input[1]) {
			case "USAR":
			case "USE":
				if (input.length == 2) {
					ret.append(messages.getString("inventory_help2"));
					break;
				}
				switch (input[2]) {
				case "LLAVE":
				case "KEY":
					if (player.getLocation().equals(map.getRoomByName("Exit"))) {
						System.out.println(messages.getString("key_use1"));
						System.out.println(messages.getString("key_use2"));
						//map.keyAcquired.isCompleted = true;
						break;
					} else {
						System.out.println(messages.getString("key_use3"));
					}
					break;
				case "ROCA":
				case "ROCAS":
				case "ROCK":
				case "ROCKS":
					if (!player.checkForCheckpoint(map.rockThrown)) {
						System.out.println(messages.getString("rock_use1"));
						break;
					}
					Inventory inv = player.getInventory();
					if (inv.getRockCount() > 0) {
						if (player.getLocation().equals(map.getRoomByName("Center"))) {
							player.addCheckpointReached(map.rockThrown);
							System.out.println(messages.getString("rock_use21"));
							System.out.println(messages.getString("rock_use22"));
							System.out.println(messages.getString("rock_use23"));
							map.rockThrown.isCompleted = true;
						} else {
							System.out.println(messages.getString("rock_use3"));
						}
					} else {
						System.out.println(messages.getString("rock_use4"));
					}
					break;
				default:
					ret.append(messages.getString("inventory_help2"));
					break;
				}
				break;
			// Look at items that are in inventory
			case "EXAMINAR":
			case "EXAMINE":
				if (input.length == 2) {
					ret.append(messages.getString("examine1"));
					break;
				}
				switch (input[2]) {
				case "LLAVE":
				case "KEY":
					player.getInventory().displayKeys();
					break;
				case "ROCA":
				case "ROCAS":
				case "ROCK":
				case "ROCKS":
					player.getInventory().displayRocks(); // Tell player how many rocks they have
					break;
				case "ARMA":
				case "ARMAS":
				case "WEAPON":
				case "WEAPONS":
					player.getInventory().displayWeapons();
					break;
				default:
					ret.append(messages.getString("examine1"));
					break;
				}
				break;
			default:
				ret.append(messages.getString("examine2"));
				break;
			}
			break;

		// Combat 
		// not implemented into final version of game. not needed
		case "PELEAR":
		case "COMBATIR":
		case "F":
		case "FIGHT":
			Enemy npc = new Enemy("Alliser Thorne", 100, 0);
			Combat fight = new Combat(player, npc, player.getInventory());
			Entities winner = fight.FightWinner();
			// determine which entity won the fight based on the winner variable
			 Runnable printResult = () -> { //2.1 Lambda expression. 4.1 Variable in expression 
	                if (winner.getName().equals(player.getName())) { //2.5 Object Comparison 
	                    System.out.println(messages.getString("fight_end1"));
	                } else {
	                    System.out.println(messages.getString("fight_end2"));
	                    RUNNING = false;
	                }
	         };
	         printResult.run();
	         break;
	            // saves game data to the database
		case "GUARDAR":
		case "SAVE":
			savegame.save();
			ret.append(messages.getString("save_verification"));
		break;
		// loads the game data from the databse
		case "CARGAR":
		case "LOAD":
			// TODO: Load saved games, both on command and on game start
			break;
			//resets player pogress back to the begining
		case "REINICIAR":
		case "RESTART":
			if (input.length == 2 && input[1].equals("PLEASE") || input.length == 2 && input[1].equals("PORFAVOR")) {
				// TODO: Actually clear save data and restart the game.
				ret.append(messages.getString("restart1"));
				try {
					savegame.deleteSaveData();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				RUNNING = false;
			} else {
				ret.append(messages.getString("restart2"));
			}
			break;
		default:
			ret.append(messages.getString("default") + " ").append(in);
			break;
		}
		return ret.toString();
	}

	/**
	 * main method - execute the program
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		int lang = 0;
		
		// If we close System.in, we cannot re-open it.
		// This will either be taken care of later, by the JVM,
		// or by our main loop code below's try-with-resources block.
		@SuppressWarnings("resource")
		Scanner inTemp = new Scanner(System.in);
		while (true) {
			System.out.println("Enter 1 for English. Ingresa 2 para Español.");
			try {
				lang = inTemp.nextInt();
				if (lang == 1 || lang == 2) {
					break;
				}
			} catch (InputMismatchException e) {
				// will re-print the prompt on the next go-around
			}
		}
		
		Locale l;
		
		switch (lang) {
			case 1:
			default:
				l = new Locale("en");
				break;
			case 2:
				l = new Locale("es");
				break;
		}
		
		// Create the messages object now that we have a locale.
		messages = ResourceBundle.getBundle("messages", l);
        
		System.out.println(messages.getString("welcome_message"));
		
		GameMap map = GameMap.getInstance();

		// Set player initial position.
		player.setLocation(map.getInitialRoom());
		
		//initiates and declares time variables necessary
		SeasonCalendar calendar = new SeasonCalendar();
		long startTime = System.currentTimeMillis();
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		int hour = time.getHour();
		int minute = time.getMinute();
		SeasonCalendar.Season currentSeason = calendar.getSeason(date);
		
		// determines the greeting for the player based on the time of day
		String greeting = "";
		if(hour < 6 || (hour == 6 && minute == 0)) {
			greeting = messages.getString("greeting_early_morning");
		} else if(hour < 12) {
			greeting = messages.getString("greeting_morning");
		} else if(hour == 12) {
			greeting = messages.getString("greeting_noon");
		} else if(hour < 21) {
			greeting = messages.getString("greeting_afternoon");
		} else {
			greeting = messages.getString("greeting_evening");
		}
		greeting += calendar.getSeasonMessage(currentSeason);

		System.out.println(greeting);
		
		//game intro 
		System.out.println(messages.getString("game_intro1"));
		System.out.println(messages.getString("game_intro2"));
		System.out.println(messages.getString("game_intro3"));

		// 6.1 - proper use of try-catch blocks
		// 6.4 - use of try-with-resources block
		try (Scanner scanner = new Scanner(System.in)) {
			try {
				savegame = SaveGame.getInstance();
			} catch (SaveGame.VersionMismatchException e) {
				System.out.println(messages.getString("error_datareset"));
				logger.warn(e);
			}
			
			if (savegame != null) {
				savegame.registerLoadable(player);
				savegame.registerSaveable(player);
			}

			while (RUNNING) {
				// 8.1 - reading/writing to console
				System.out.print("> ");
				
				String input = scanner.nextLine().toUpperCase();

				// Echo received string back. comment out for final build.
				//System.out.println(input);

				String output = parseInput(input, map);
				if (!output.isBlank()) {
					System.out.println(output);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally { // 6.2 - use of finally block
			//time calculations
			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			long elapsedMinutes = elapsedTime / 60000;
			long elapsedSeconds = (elapsedTime % 60000) / 1000;
			
			// display data about player session length and good bye message. 
			System.out.println(messages.getString("session_time_txt") + elapsedMinutes +" "+ messages.getString("minute_txt") + elapsedSeconds +" "+ messages.getString("second_txt"));
			System.out.println(messages.getString("game_over"));
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