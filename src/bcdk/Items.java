package bcdk;

public class Items {
    //Item Ideas so far

    public static class Key { // Used to open a locked door, Could implement more keys and more doors
        String KeyDescription = "A large iron key"; // IF player examines key could give hints to where key goes to, or just says key can unlock XX door
        int KeyCount=0; //Amount of keys 

        public void UseKey() {
            if (KeyCount>0) {
                // If room contains door then open that door.
                System.out.println("You have used the key and unlocked the door");
                //key is consumed 
                KeyCount--;
            } else
                System.out.println("If only you had a key...");
        }
        
    }

    public static class Rocks { // Used to distract Guards so player can be sneaky.
        // Once rock thrown spawn it in the room it was thrown to? Or make single use?

        int RockCount = 4; // rework to work with inventory
        // How many times a player can throw a rock to move a guard to a room that is NORTH, EAST,SOUTH,WEST


        public void Throw(String direction) {
            if (RockCount > 0) {
                switch (direction.toLowerCase()) {
                    // Call AI to where ever the rock would end up
                    // For how many rooms away the AI is have every player movement let the AI move 1 after player does.
                    // Example: AI is 1 room east and 1 room south. So Diagonal away from where rock went. Player Move, AI move north, PLayer move, AI move west

                    case "north":
                        System.out.println("You have Thrown the rock " + direction + "!");
                        //Get rock landed location
                        //Call NPC move funtion to this location
                        break;
                    case "east":
                        System.out.println("You have Thrown the rock " + direction + "!");
                      //Get rock landed location
                        //Call NPC move funtion to this location
                        break;
                    case "south":
                        System.out.println("You have Thrown the rock " + direction + "!");
                      //Get rock landed location
                        //Call NPC move funtion to this location
                        break;
                    case "west":
                        System.out.println("You have Thrown the rock " + direction + "!");
                      //Get rock landed location
                        //Call NPC move funtion to this location
                        break;
                    default:
                        System.out.println("Invalid direction. Please choose north, east, south, or west.");
                }
                
                RockCount--;
             // Tell user how many rocks they have left. Grammar is changed depending on the rock amount
                if (RockCount>1) {
                	System.out.println("You have " + RockCount + " more rocks left");
                }
                else
                {
                	System.out.println("You have " + RockCount + " more rock left");
                }
            } else
                System.out.println("You need to find another rock first");
        }

        public void WhatRocks() {
            if (RockCount > 0)
                System.out.println("You have " + RockCount + " more rocks left");
            else
                System.out.println("You have no rocks");
        }
    }
        
       //Used for Combat and actions in the world like breaking door down
       public  class Weapons{ 
    	  private  String WeaponName; //name of item
    	  private  String WeaponDescription; //Description of Item for player
    	  private int WeaponDamage;  //How much damage this will do. Update when combat is implemented 
    	   
    	  //Constructor so we can make various weapons if feel like 
    	  public Weapons(String WeaponName,String WeaponDescription, int WeaponDamage) {
    		  this.WeaponName=WeaponName;
    		  this.WeaponDescription=WeaponDescription;
    		  this.WeaponDamage=WeaponDamage;
    	  }
    	  
    	  public String getWeaponDescription() {
    	        return WeaponDescription;
    	    }
    	  
    	  public int getWeaponDamage() {
  	        return WeaponDamage;
  	    }
    	  
    	  public String getWeaponName() {
  	        return WeaponName;
  	    }
    	  
    	   //Tell user about their weapon
    	   public void WhatWeapon() {
    		   System.out.println(WeaponDescription);
    	   }
    	   
    	   public void SmashDoor() {
    		   //Break door description
    		   System.out.println("As the door splinters you hear distant footstep approach ever closer");
    		   //Remove door- Get door ID and delete?
    		   //Call NPC's within 3 rooms over
    	   }
    	   
       }
       
       public static class Phial {
    		    private String PhialName; // Name of the phial
    		    private String PhialDescription; // Description of the phial
    		    private int damage; // Amount of damage the phial inflicts

    		    // Constructor
    		    public Phial(String PhialName, String PhialDescription, int damage) {
    		        this.PhialName = PhialName;
    		        this.PhialDescription = PhialDescription;
    		        this.damage = damage;
    		    }

    		    // Getter methods
    		    public String getPotionName() {
    		        return PhialName;
    		    }

    		    public String getPotionDescription() {
    		        return PhialDescription;
    		    }

    		    public int getDamage() {
    		        return damage;
    		    }

    		    // Method to describe the potion
    		    public void DescribePotion() {
    		        System.out.println(PhialDescription);
    		    }

    		    // Method to use the potion and inflict damage to an enemy
    		    public void UsePotion(NPCBehavior guard) {
    		        System.out.println("You throw the " + PhialName + " at the enemy!");
    		        System.out.println("It inflicts " + damage + " damage to the enemy!");
    		        guard.takeDamage(damage);
    		    }
    		}
}