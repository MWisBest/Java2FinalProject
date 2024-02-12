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
	 * class constructor
	 */
	public Inventory() {
		this.keys = new ArrayList<>();
        this.rocks = new ArrayList<>();
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
	  * 
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