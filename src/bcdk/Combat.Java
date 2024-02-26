package bcdk;

import java.util.Scanner;

import bcdk.Items.Weapons;

public class Combat {
	private Entities Player;
	private Entities NPC;
	private Inventory Inv;
	private Weapons UserWeapon;
	
	public Combat(Player player, Enemy npc, Inventory PlayerInventory) {
		this.Player = player;
		this.NPC = npc;
		this.Inv = PlayerInventory;
	}
	
	public Entities FightWinner() {
		Scanner scanner = new Scanner(System.in);
		
		while(Player.IsAlive() && NPC.IsAlive()){
			boolean Defend =  false;
			Boolean SkipEnemyTurn = false;
			System.out.println("Player health: " + Player.GetHealth());
            System.out.println("Enemy health: " + NPC.GetHealth());
            System.out.println("Attack will cause max damage to enemy. Defend will only give you half normal enemy damage on his next turn");
            System.out.println("Enter 'A' to attack  \n"
            				 + "Enter 'D' to defend \n"
            				 + "Enter 'I' to check weapons:");
            
            String input = scanner.nextLine();
            
            if(input.equalsIgnoreCase("A")) {
            	int playerDmg = Player.GetDamage();
            	NPC.TakeDamage(playerDmg);
            	  System.out.println("Player attacks NPC for " + playerDmg + " damage.");
            } 
            else if (input.equalsIgnoreCase("D")) {
            	Defend = true;
            } 
            else if (input.equalsIgnoreCase("I")) {
            	ChangeWeapon(scanner);
            	SkipEnemyTurn= true;
            }
            else {
                System.out.println("Invalid input. Check the list of options");
                SkipEnemyTurn = true;
            }
            
            if (NPC.IsAlive() && !SkipEnemyTurn) {
            	int npcDamage;
            	if(Defend) {
            		npcDamage = NPC.GetDamage() / 2;
            	} else {
            		npcDamage = NPC.GetDamage();
            	}
                
                Player.TakeDamage(npcDamage);
                System.out.println("NPC attacks player for " + npcDamage + " damage.");
            }
            	
		}
		
		scanner.close();
		
		if(Player.IsAlive()) {
			System.out.println(NPC.GetName() + " has been defeated");
			return Player;
		} else {
			System.out.println(Player.GetName() + " has been defeated");
			return NPC;
		}
	}
	
	private void ChangeWeapon(Scanner scanner) {
		Inv.displayWeapons();
		int max = Inv.WeaponCount();
		
		if(Inv.WeaponCount() > 0) {
			System.out.println("Which weapon will you use? (Type number):");
			String inputz = scanner.nextLine();
			
			try {
	            int inputx = Integer.parseInt(inputz);
	            if (inputx >= 1 && inputx <= max) {
	            	UserWeapon = Inv.GetWapons().get(inputx - 1);
	            	Player.SetDamage(UserWeapon.getWeaponDamage());
	            } else {
	                System.out.println("Please enter a number between 1 and " + max);
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a valid number.");
	        }
			
		}
		
        
	}
}
