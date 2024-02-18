package bcdk;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import bcdk.Items.Key;
import bcdk.Items.Rocks;

public class Inventory {
	
	// the Lists that will store all the player items 
	private List<Key> keys;
	private List<Rocks> rocks;
	
	/**
	 * class constructor to initialize the player inventory
	 */
	public Inventory() {
		this.keys = new ArrayList<>();
        this.rocks = new ArrayList<>();
	}
	

	/**
	 * indentifies the key to be added
	 * @param key - the name of the key
	 */
	 public void addKey(Key key) {
		 keys.add(key);
	 }

	 /**
	  * indentifies and removes a key from the player inventory
	  * @param key - the key to be removed
	  */
	 public void removeKey(Key key) {
		 keys.remove(key);
	 }
	
	 /**
	  * adds more rocks to the player inventory
	  * @param rocks - the name of the Rocks instance
	  */
	 public void addRocks(Rocks rocks) {
		 this.rocks.add(rocks);
	 }

	 /**
	  * identifies and removes a rock from the player inventory
	  * @param rocks -  the name of the rock instance to be removed from the player inventory
	  */
	 public void removeRocks(Rocks rocks) {
		 this.rocks.remove(rocks);
	 }
	 
	/**
	 * Method to display all the keys found in the player inventory
	 */
	 public void displayKeys() {
	        System.out.println("Keys in Inventory:");
	        if(!keys.isEmpty()) {
	        	for (Key key : keys) {
	            System.out.println("- " + key.KeyDescription);
	        	}
	        } else {
	        	System.out.println("You do not own any keys");
	        }
	        
	 }
	 
	 /**
	  * Method to display all the rocks found in the player inventory
	  */
	 public void displayRocks() {
	        System.out.println("Rocks in Inventory:");
	        if(!rocks.isEmpty()) {
	        	for (Rocks rock : rocks) {
	            System.out.println("- " + rock.RockCount + " rocks left");
	        	}
	        } else {
	        	System.out.println("You do not have any rocks");
	        }
	        
	    }
	
}