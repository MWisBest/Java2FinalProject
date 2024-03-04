package bcdk;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import bcdk.Items.Key;
import bcdk.Items.Rocks;
import bcdk.Items.Weapons;

/**
 * keep track of all the items that the player owns. 
 */
public class Inventory {
	
	/**
	 * store the keys that the player has in inventory
	 */
	private List<Key> keys;
	
	/**
	 * store the rocks that the player has in inventory
	 */
	private List<Rocks> rocks;
	
	/**
	 * store the weapons that the player has in inventory
	 */
	private List<Weapons> weapons;
	
	/**
	 * class constructor. initiate track of items
	 */
	public Inventory() {
		this.keys = new ArrayList<>();
        this.rocks = new ArrayList<>();
        this.weapons=new ArrayList<>();
	}
	

	/**
	 * adds a key to to the player inventory
	 * @param key - key to add
	 */
	 public void addKey(Key key) {
		 keys.add(key);
	 }

	 /**
	  * deletes a key from the player inventory
	  * @param key - key to delete
	  */
	 public void removeKey(Key key) {
		 keys.remove(key);
	 }
	
	 /**
	  * adds a rock to the player inventory
	  * @param rocks -  rock to add
	  */
	 public void addRocks(Rocks rocks) {
		 this.rocks.add(rocks);
	 }

	 /**
	  * removes a rock from the player inventory 
	  * @param rocks -  the rock to remove
	  */
	 public void removeRocks(Rocks rocks) {
		 this.rocks.remove(rocks);
	 }
	 
	 /**
	  * adds a weapon to the player inventory
	  * @param weapons - weapon to add
	  */
	 public void addWeapons(Weapons weapons) {
		 this.weapons.add(weapons);
	 }

	 /**
	  * deleted a weapon from the player inventory 
	  * @param weapons - weapon to remove
	  */
	 public void removeWeapons(Weapons weapons) {
		 this.weapons.remove(weapons);
	 }
	 
	 
	/**
	 * Modified so returns amount of keys once
	 * allow player to see how many keys they have in their inventory. 
	 */
	 public void displayKeys() {
		 int PlaceholderKey=0;
	        System.out.println("Keys in Inventory:");
	        // if player has zero keys, forloop is skipped.
	        if(!keys.isEmpty()) { 
	        	for (Key key : keys) {
	        		PlaceholderKey=key.KeyCount;
	        	}
	        		if (PlaceholderKey>1) {
	                	System.out.println("You have " + PlaceholderKey + " iron keys left");
	                }
	                else
	                {
	                	System.out.println("You have " + PlaceholderKey + " iron key left");
	                }
	        } else { 
	        	// player is notified that they have no keys
	        	System.out.println("You do not own any keys");
	        }
	        
	 }
	 
	 /**
	  * Modified so should only print Rock amount once
	  * allow player to see how many rocks they have in their inventory 
	  */
	 public void displayRocks() {
		 int PlaceholderRock = 0;
	        System.out.println("Rocks in Inventory:");
	        // if player has zero rocks, forloop is skipped
	        if(!rocks.isEmpty()) {
	        	for (Rocks rock : rocks) {
	        		PlaceholderRock=rock.RockCount;
	        	}
	        		if (PlaceholderRock>1) {
	                	System.out.println("You have " + PlaceholderRock + " more rocks left");
	                }
	                else
	                {
	                	System.out.println("You have " + PlaceholderRock + " more rock left");
	                }
	        	
	        } else {
	        	// player is notified that they have no rocks
	        	System.out.println("You do not have any rocks");
	        }
	        
	    }
	 
	 /**
	  * Says description for each weapon in inventory. Currently only a mace.
	  * allows player to see how many weapons they have in their inventory and what each weapon in
	  */
	 public void displayWeapons() {
	        System.out.println("Weapons in inventory");
	        // only runs if statement if player has at least 1 weapon
	        if(!weapons.isEmpty()) {
	        	int count = 1;
	        	for (Weapons weapon : weapons) {
	        	System.out.println(count +": "+ weapon.getWeaponName()+": "+weapon.getWeaponDescription());
	        	count++;
	        	}	        	
	        } else {
	        	// player is informed that they do not have any weapons in inventory
	        	System.out.println("Nothing but your fists\n");
	        }
	        
	    }
	 
	 /**
	  * counts the amount of weapons that the player has in their inventory 
	  * @return - weapon count
	  */
	 public int WeaponCount() {
		 int count = 0;
		 if(!weapons.isEmpty()) {
	        	for (Weapons weapon : weapons) {
	        	count++;
	        	}	        	
	        }
		 return count;
	 }
	 
	 /**
	  * get the array of weapons that player has so he can pick during combat
	  * @return - array of weapons
	  */
	 public ArrayList<Weapons> GetWapons() {
		 return (ArrayList<Weapons>) weapons;
	 }
	 
	
}