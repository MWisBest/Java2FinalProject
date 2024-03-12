package bcdk.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * keep track of all the items that the player owns.
 */
public class Inventory {
	/**
	 * create the chance for the game to be used in spanish
	 * "es" to play in spanish 		"en" to play in english
	 */
	static Locale currentLocale = new Locale("es"); // Spanish locale
	
	/**
	 * create a connection to the files that will be used to provide text to the game
	 */
    static ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);

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
		this.keys = new ArrayList<>(); // 3.2 - example of ArrayList
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
	
	/**
	 * @return - the amount of rocks that the player has, currently
	 */
	public int getRockCount() {
		return this.rocks.size();
	}
	
	/**
	 * @return - the amount of keys that the player has, currently
	 */
	public int getKeyCount() {
		return this.keys.size();
	}
	
	/**
	 * counts the amount of weapons that the player has in their inventory
	 * 
	 * @return - weapon count
	 */
	public int getWeaponCount() {
		return this.weapons.size();
	}

	/**
	 * Modified so returns amount of keys once allow player to see how many keys
	 * they have in their inventory.
	 */
	public void displayKeys() {
		System.out.println(messages.getString("display_key") + " " + keys.size());
	}

	/**
	 * Modified so should only print Rock amount once allow player to see how many
	 * rocks they have in their inventory
	 */
	public void displayRocks() {
		System.out.println(messages.getString("display_rock") + " " + rocks.size());
	}

	/**
	 * Says description for each weapon in inventory. Currently only a mace. allows
	 * player to see how many weapons they have in their inventory and what each
	 * weapon in
	 */
	public void displayWeapons() {
        System.out.println(messages.getString("display_weapon") + " ");
        // only runs if statement if player has at least 1 weapon
        if (!weapons.isEmpty()) {
            weapons.forEach(weapon -> { // 2.1 Lambda  Expression and foreach statement
                System.out.println(weapon.getName() + ": " + weapon.getFullDescription() + messages.getString("weapon_info1") + " " + weapon.getDamage()+" "+ messages.getString("weapon_info2"));
            });
        } else {
            // player is informed that they do not have any weapons in inventory
            System.out.println(messages.getString("weapon_null"));
        }

    }

	/**
	 * get the array of weapons that player has so he can pick during combat
	 * 
	 * @return - array of weapons
	 */
	public List<Weapon> getWeapons() {
		return weapons;
	}
}