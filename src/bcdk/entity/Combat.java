package bcdk.entity;

import java.util.Scanner;

import bcdk.item.Inventory;
import bcdk.item.Weapon;

/**
 * represents the combat area where player will fight an enemy
 */
public class Combat {
	/**
	 * the player and its stats
	 */
	private Player Player;

	/**
	 * the enemy and their stats
	 */
	private Enemy NPC;

	/**
	 * the current inventory of the player
	 */
	private Inventory Inv;

	/**
	 * the weapon that the user is currenty using in combat
	 */
	private Weapon UserWeapon;

	/**
	 * class constructor
	 * 
	 * @param player          - the player who will fight
	 * @param npc             - the enemy that the player will fight
	 * @param PlayerInventory - the current inventory of the player
	 */
	public Combat(Player player, Enemy npc, Inventory PlayerInventory) {
		this.Player = player;
		this.NPC = npc;
		this.Inv = PlayerInventory;
	}

	/**
	 * the fight between the 2 entities where they can cause harm to each other.
	 * 
	 * @return - winner of the fight
	 */
	public Entities FightWinner() {
		Scanner scanner2 = new Scanner(System.in);

		// the fight will go on until either the player or the enemy is dead.
		while (Player.IsAlive() && NPC.IsAlive()) {

			boolean Defend = false;// determines if the damage from enemy should be halfed
			Boolean SkipEnemyTurn = false; // determine if enemy turn should be skipped
			Player.ChangeCoolDown();

			if (NPC.getSpecialCharge() == 60) {
				System.out.println(NPC.GetName() + " will use special attack on his next turn");
			}

			// give player current status of the battle and their available options
			System.out.println("Player health: " + Player.GetHealth());
			System.out.println("Enemy health: " + NPC.GetHealth());
			System.out.println(
					"Attack will cause max damage to enemy. Defend will only give you half normal enemy damage on his next turn");
			System.out.println("Enter 'A' to attack  \n" + "Enter 'D' to defend \n" + "Enter 'H' to heal \n"
					+ "Enter 'I' to check weapons:");

			String input = scanner2.nextLine();

			// player attacks enemy with full damage
			if (input.equalsIgnoreCase("A")) {
				int playerDmg = Player.GetDamage();
				NPC.TakeDamage(playerDmg);
				System.out.println("Player attacks NPC for " + playerDmg + " damage.");
			}
			// player defends so enemy does not harm them as much
			else if (input.equalsIgnoreCase("D")) {
				Defend = true;
			}
			// player heals if available
			else if (input.equalsIgnoreCase("H")) {
				if (Player.getHealCoolDown() == 0) {
					Player.setHealAmount();
					Player.AddHealth(Player.getHealAmount());
					System.out.println("Player heals for " + Player.getHealAmount() + " points.");
				} else {
					System.out.println("Healing is unavailable.");
					System.out.println("Cooldown status: " + Player.getHealCoolDown());
				}
			}
			// player gets to check inventory to change weapon
			else if (input.equalsIgnoreCase("I")) {
				ChangeWeapon(scanner2);
				SkipEnemyTurn = true;
			}
			// player picks invalid choice so enemy turn is skipped so player can try again
			else {
				System.out.println("Invalid input. Check the list of options");
				SkipEnemyTurn = true;
			}

			// if enemy is not killed by player, the get to attack back.
			if (NPC.IsAlive() && !SkipEnemyTurn) {
				NPC.IncreaseSpecial();
				int npcDamage = 0;
				boolean isSpecialAttack = false;

				if (NPC.getSpecialCharge() == 80) {
					NPC.SetSpecialDmg();
					npcDamage = NPC.getSpecialDmg();
					NPC.ResetSpecial();
					isSpecialAttack = true;
				}

				if (!isSpecialAttack) {
					// determines the amount of damage to cause to the player
					if (Defend) {
						npcDamage = NPC.GetDamage() / 2;
					} else {
						npcDamage = NPC.GetDamage();
					}
				} else {
					if (Defend) {
						npcDamage /= 3;
					}
				}

				// health is reduced from the player
				Player.TakeDamage(npcDamage);
				System.out.println("NPC attacks player for " + npcDamage + " damage.\n\n");
			}

		}

		scanner2.close();

		// determines output based on who won the fight
		if (Player.IsAlive()) {
			System.out.println(NPC.GetName() + " has been defeated");
			return Player;
		} else {
			System.out.println(Player.GetName() + " has been defeated");
			return NPC;
		}
	}

	/**
	 * allows the player to change the weapon they use in combat to fight the enemy
	 * 
	 * @param scanner - detect player input
	 */
	private void ChangeWeapon(Scanner scanner) {
		Inv.displayWeapons();
		int max = Inv.WeaponCount();

		// only allow weapon change if the player has at least 1 weapon
		if (Inv.WeaponCount() > 0) {
			System.out.println("Which weapon will you use? (Type number):");
			String inputz = scanner.nextLine();

			try {
				int inputx = Integer.parseInt(inputz);
				// verify that use input falls within the available weapons
				if (inputx >= 1 && inputx <= max) {
					UserWeapon = Inv.GetWeapons().get(inputx - 1);
					Player.SetDamage(UserWeapon.getDamage());
				} else {
					// inform player when they make an invalid weapon choice
					System.out.println("Please enter a number between 1 and " + max + "\n");
				}
				// inform player when they make an invalid weapon choice
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid number.\n");
			}

		}

	}
}
