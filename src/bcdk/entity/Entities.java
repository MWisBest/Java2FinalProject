package bcdk.entity;

/**
 * represents basic entity that can be expanded upon depending on entity type
 */
public class Entities {
	/**
	 * the amount of health of this entity
	 */
	private int health;

	/**
	 * the name given to this entity
	 */
	private String name;

	/**
	 * the amount of total damage that this entity can cause
	 */
	private int damage;

	/**
	 * the amount of extra damage caused by this entity
	 */
	private int extraDamage = 0;

	/**
	 * the base damage of all entities
	 */
	private int baseDamage = 5;

	/**
	 * class constructor
	 * 
	 * @param name     - name given to entity
	 * @param health   - initial health of entity
	 * @param extraDmg - extradamage given to entity
	 */
	public Entities(String name, int health, int extraDmg) {
		this.health = health;
		this.name = name;
		this.extraDamage = extraDmg;

		// sets total damage of entity
		this.damage = baseDamage + extraDamage;
	}

	/**
	 * get the amount of current health from the entity
	 * 
	 * @return - health amount
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * get the name of the entity
	 * 
	 * @return - entity name
	 */
	public String getName() {
		return name;
	}

	/**
	 * get the amount of total damage caused by this entity
	 * 
	 * @return - total damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * get the amount of extra damage caused by this entity
	 * 
	 * @return - extra damage
	 */
	public int getExtraDamage() {
		return extraDamage;
	}

	/**
	 * changes amount of extra damage caused by this entity
	 * 
	 * @param dmg - extra damage
	 */
	public void setDamage(int dmg) {
		extraDamage = dmg;

		// automatically update the total damage
		damage = baseDamage + extraDamage;
	}

	/**
	 * determintes whether this entity is alive or not depending on amount of health
	 * 
	 * @return - alive status
	 */
	public boolean isAlive() {
		return health > 0;
	}

	/**
	 * reduces the amount of current health from the entity
	 * 
	 * @param dmg - amount to reduce from entity health
	 */
	public void takeDamage(int dmg) {
		health -= dmg;
	}

	public void addHealth(int amt) {
		health += amt;
	}
}