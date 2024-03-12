package bcdk.entity;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import bcdk.BCDK;
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
		System.out.println(Player.getName() + " will fight " + NPC.getName());
		// the fight will go on until either the player or the enemy is dead.
		while (Player.isAlive() && NPC.isAlive()) {

			boolean Defend = false;// determines if the damage from enemy should be halfed
			Boolean SkipEnemyTurn = false; // determine if enemy turn should be skipped
			Player.ChangeCoolDown();

			if (NPC.getSpecialCharge() == 60) {
				System.out.println(NPC.getName() + " " + BCDK.messages.getString("combat_enemy_special"));
			}

			// give player current status of the battle and their available options
			System.out.println(BCDK.messages.getString("combat_player_health") + " " + Player.getHealth());
			System.out.println(BCDK.messages.getString("combat_enemy_health") + " " + NPC.getHealth());
			System.out.println(BCDK.messages.getString("combat_description"));
			System.out.println(BCDK.messages.getString("combat_options1") +"\n" 
							 + BCDK.messages.getString("combat_options2")+ "\n" 
							 + BCDK.messages.getString("combat_options3")+ "\n"
							 + BCDK.messages.getString("combat_options4"));

			System.out.print(">");
			String input = scanner2.nextLine();

			// player attacks enemy with full damage
			if (input.equalsIgnoreCase("A")) {
				int playerDmg = Player.GetDamage();
				NPC.takeDamage(playerDmg);
				System.out.println(BCDK.messages.getString("combat_player_attack1") + " " + playerDmg + " " + BCDK.messages.getString("combat_player_attack2"));
			}
			// player defends so enemy does not harm them as much
			else if (input.equalsIgnoreCase("D")) {
				Defend = true;
			}
			// player heals if available
            else if (input.equalsIgnoreCase("H")) {
            	if(Player.getHealCoolDown() <= 0) {
            		Player.setHealAmount();
            		Player.addHealth(Player.getHealAmount());
            		System.out.println("Player heals for " + Player.getHealAmount() + " points.");
            	} else {
            		System.out.println("Healing is unavailable.");
            		System.out.println("Cooldown status: " + Player.getHealCoolDown());
            		SkipEnemyTurn = true;
            	}
            } 
            // player gets to check inventory to change weapon
			// player gets to check inventory to change weapon
			else if (input.equalsIgnoreCase("I")) {
				ChangeWeapon(scanner2);
				SkipEnemyTurn = true;
			}
			// player picks invalid choice so enemy turn is skipped so player can try again
			else {
				System.out.println(BCDK.messages.getString("combat_invalid_input"));
				SkipEnemyTurn = true;
			}

			// if enemy is not killed by player, the get to attack back.
			if (NPC.isAlive() && !SkipEnemyTurn) {
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
				Player.takeDamage(npcDamage);
				System.out.println(BCDK.messages.getString("combat_enemy_attack1") + " " + npcDamage + " " + BCDK.messages.getString("combat_enemy_attack2") +" .\n\n");
			}

		}

		//scanner2.close();

		// determines output based on who won the fight
		if (Player.isAlive()) {
			System.out.println(NPC.getName() + " " + BCDK.messages.getString("combat_ending"));
			return Player;
		} else {
			System.out.println(Player.getName() + " " + BCDK.messages.getString("combat_ending"));
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
		int max = Inv.getWeaponCount();

		// only allow weapon change if the player has at least 1 weapon
		if (Inv.getWeaponCount() > 0) {
			System.out.println(BCDK.messages.getString("combat_weapon1"));
			String inputz = scanner.nextLine();

			try {
				int inputx = Integer.parseInt(inputz);
				// verify that use input falls within the available weapons
				if (inputx >= 1 && inputx <= max) {
					UserWeapon = Inv.getWeapons().get(inputx - 1);
					Player.setDamage(UserWeapon.getDamage());
				} else {
					// inform player when they make an invalid weapon choice
					System.out.println(BCDK.messages.getString("combat_weapon2") + " " + max + "\n");
				}
				// inform player when they make an invalid weapon choice
			} catch (NumberFormatException e) {
				System.out.println(BCDK.messages.getString("combat_weapon3") + "\n");
			}

		}

	}
}
