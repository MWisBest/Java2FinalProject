package bcdk.item;

public class Weapon extends Item implements Comparable<Weapon> {
	private int damage;
	
	public Weapon(String name, String description, int damage) {
		super(name, description);
		this.damage = damage;
	}
	
	public int getDamage() {
		return this.damage;
	}

	// 3.4 - use of Comparable interface
	@Override
	public int compareTo(Weapon o) {
		return o.damage - this.damage;
	}
}
