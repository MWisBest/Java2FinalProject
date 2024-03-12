package bcdk.item;

/**
 * constructor for weapons with data from the Item class
 */
public class Weapon extends Item implements Comparable<Weapon> {
	/**
	 * the amount of damage caused by this weapon
	 */
	private int damage;
	
	/**
	 * class constructor
	 * @param name - name of weapon
	 * @param description - description of weapon
	 * @param damage - damage caused by weapon
	 */
	public Weapon(String name, String description, int damage) {
		super(name, description);
		this.damage = damage;
	}
	
	/**
	 * @return - the amount of damage caused by this weapon
	 */
	public int getDamage() {
		return this.damage;
	}

	// 3.4 - use of Comparable interface
	@Override
	public int compareTo(Weapon o) {
		return o.damage - this.damage;
	}
}
