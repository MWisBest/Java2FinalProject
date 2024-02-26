package bcdk;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import bcdk.Items.Key;
import bcdk.Items.Rocks;
import bcdk.Items.Weapons;

public class Inventory {
	
	// the Lists that will store all the player items 
	private List<Key> keys;
	private List<Rocks> rocks;
	private List<Weapons> weapons;
	
	/**
	 * class constructor
	 */
	public Inventory() {
		this.keys = new ArrayList<>();
        this.rocks = new ArrayList<>();
        this.weapons=new ArrayList<>();
	}
	

	/**
	 * 
	 * @param key
	 */
	 public void addKey(Key key) {
		 keys.add(key);
	 }

	 /**
	  * 
	  * @param key
	  */
	 public void removeKey(Key key) {
		 keys.remove(key);
	 }
	
	 /**
	  * 
	  * @param rocks
	  */
	 public void addRocks(Rocks rocks) {
		 this.rocks.add(rocks);
	 }

	 /**
	  * 
	  * @param rocks
	  */
	 public void removeRocks(Rocks rocks) {
		 this.rocks.remove(rocks);
	 }
	 
	 /**
	  * 
	  * @param weapons
	  */
	 public void addWeapons(Weapons weapons) {
		 this.weapons.add(weapons);
	 }

	 /**
	  * 
	  * @param weapons
	  */
	 public void removeWeapons(Weapons weapons) {
		 this.weapons.remove(weapons);
	 }
	 
	 
	/**
	 * Modified so returns amount of keys once
	 */
	 public void displayKeys() {
		 int PlaceholderKey=0;
	        System.out.println("Keys in Inventory:");
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
	        	System.out.println("You do not own any keys");
	        }
	        
	 }
	 
	 /**
	  * Modified so should only print Rock amount once
	  */
	 public void displayRocks() {
		 int PlaceholderRock = 0;
	        System.out.println("Rocks in Inventory:");
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
	        	System.out.println("You do not have any rocks");
	        }
	        
	    }
	 
	 /**
	  * Says description for each weapon in inventory. Currently only a mace.
	  */
	 public void displayWeapons() {
	        System.out.println("Weapons in inventory");
	        if(!weapons.isEmpty()) {
	        	int count = 1;
	        	for (Weapons weapon : weapons) {
	        	System.out.println(count +": "+ weapon.getWeaponName()+": "+weapon.getWeaponDescription());
	        	count++;
	        	}	        	
	        } else {
	        	System.out.println("Nothing but your fists\n");
	        }
	        
	    }
	 
	 public int WeaponCount() {
		 int count = 0;
		 if(!weapons.isEmpty()) {
	        	for (Weapons weapon : weapons) {
	        	count++;
	        	}	        	
	        }
		 return count;
	 }
	 
	 public ArrayList<Weapons> GetWapons() {
		 return (ArrayList<Weapons>) weapons;
	 }
	 
	
}