package bcdk.item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
	private List<Rock> rocks;

	/**
	 * store the weapons that the player has in inventory
	 */
	private List<Weapon> weapons;

	/**
	 * class constructor. initiate track of items
	 */
	public Inventory() {
		this.keys = new ArrayList<>();
		this.rocks = new ArrayList<>();
		this.weapons = new ArrayList<>();
	}

	/**
	 * adds a key to to the player inventory
	 * 
	 * @param key - key to add
	 */
	public void addKey(Key key) {
		keys.add(key);
	}

	/**
	 * deletes a key from the player inventory
	 * 
	 * @param key - key to delete
	 */
	public void removeKey(Key key) {
		keys.remove(key);
	}

	/**
	 * adds a rock to the player inventory
	 * 
	 * @param rocks - rock to add
	 */
	public void addRocks(Rock rocks) {
		this.rocks.add(rocks);
	}

	/**
	 * removes a rock from the player inventory
	 * 
	 * @param rocks - the rock to remove
	 */
	public void removeRocks(Rock rocks) {
		this.rocks.remove(rocks);
	}

	/**
	 * adds a weapon to the player inventory
	 * 
	 * @param weapons - weapon to add
	 */
	public void addWeapons(Weapon weapons) {
		this.weapons.add(weapons);
		// sort weapons using internal comparator
		this.weapons.sort(null);
	}

	/**
	 * deleted a weapon from the player inventory
	 * 
	 * @param weapons - weapon to remove
	 */
	public void removeWeapons(Weapon weapons) {
		this.weapons.remove(weapons);
		// sort not required on remove
	}
	
	public int getRockCount() {
		return this.rocks.size();
	}
	
	public int getKeyCount() {
		return this.keys.size();
	}
	
	public int getWeaponCount() {
		return this.weapons.size();
	}

	/**
	 * Modified so returns amount of keys once allow player to see how many keys
	 * they have in their inventory.
	 */
	public void displayKeys() {
		System.out.println("Keys in inventory: " + keys.size());
	}

	/**
	 * Modified so should only print Rock amount once allow player to see how many
	 * rocks they have in their inventory
	 */
	public void displayRocks() {
		System.out.println("Rocks in inventory: " + rocks.size());
	}

	/**
	 * Says description for each weapon in inventory. Currently only a mace. allows
	 * player to see how many weapons they have in their inventory and what each
	 * weapon in
	 */
	public void displayWeapons() {
		System.out.println("Weapons in inventory: ");
		// only runs if statement if player has at least 1 weapon
		if (!weapons.isEmpty()) {
			int count = 1;
			for (Weapon weapon : weapons) {
				System.out.println(count + ": " + weapon.getName() + ": " + weapon.getFullDescription() + ", does " + weapon.getDamage() + " damage.");
				count++;
			}
		} else {
			// player is informed that they do not have any weapons in inventory
			System.out.println("Nothing but your fists.");
		}

	}

	/**
	 * counts the amount of weapons that the player has in their inventory
	 * 
	 * @return - weapon count
	 */
	public int WeaponCount() {
		return weapons.size();
	}

	/**
	 * get the array of weapons that player has so he can pick during combat
	 * 
	 * @return - array of weapons
	 */
	public List<Weapon> GetWeapons() {
		return weapons;
	}

}