package bcdk;

import java.util.Scanner;

public class Combat {
	private Entities Player;;
	private Entities NPC;;
	
	public Combat(Entities player, Entities npc) {
		this.Player = player;
		this.NPC = npc;
	}
	
	public Entities FightWinner() {
		Scanner scanner = new Scanner(System.in);
		
		while(Player.IsAlive() && NPC.IsAlive()){
			boolean Defend =  false;
			System.out.println("Player health: " + Player.getHealth());
            System.out.println("Enemy health: " + NPC.getHealth());
            System.out.println("Attack will cause max damage to enemy. Defend will only give you half normal enemy damage on his next turn");
            System.out.println("Enter 'A' to attack or 'D' to defend:");
            
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("A")) {
            	int playerDmg = Player.GetDamage();
            	NPC.TakeDamage(playerDmg);
            	  System.out.println("Player attacks NPC for " + playerDmg + " damage.");
            } else if (input.equalsIgnoreCase("D")) {
            	Defend = true;
            } else {
                System.out.println("Invalid input. Please enter 'A' to attack or 'D' to defend.");
            }
            
            if (NPC.IsAlive()) {
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
			System.out.println(NPC.getName() + " has been defeated");
			return Player;
		} else {
			System.out.println(Player.getName() + " has been defeated");
			return NPC;
		}
	}
}
