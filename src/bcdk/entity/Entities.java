package bcdk.entity;

/**
 * represents basic entity that can be expanded upon depending on entity type
 * Has a few immutable objects that demonstrate immutable object pattern.
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
	private CalculateDamage Damage;

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
		this.Damage = () -> baseDamage + extraDamage; //2.1 Lambda 
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
	public int GetDamage() {
		return Damage.calculateDamage();
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
		this.Damage = () -> baseDamage + extraDamage; //2.1 Lambda
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

	/**
	 * add health to the entity
	 * 
	 * @param amt - amount of health to add to the entity
	 */
	public void addHealth(int amt) {
		health += amt;
	}
}

@FunctionalInterface //Used for 2 Lambda expressions in this class 
interface CalculateDamage {
    int calculateDamage();
}