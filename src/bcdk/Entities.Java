package bcdk;

/**
 * represents basic entity that can be expanded upon depending on entity type
 */
public class Entities {
	/**
	 * the amount of health of this entity
	 */
	private int Health;
	
	/**
	 * the name given to this entity 
	 */
	private String Name;
	
	/**
	 * the amount of total damage that this entity can cause
	 */
	private int Damage; 
	
	/**
	 * the amount of extra damage caused by this entity
	 */
	private int ExtraDamage = 0;
	
	/**
	 * the base damage of all entities
	 */
	private int BaseDamage = 5;
	
	/**
	 * class constructor
	 * @param name - name given to entity
	 * @param health - initial health of entity
	 * @param extraDmg - extradamage given to entity
	 */
	public Entities(String name, int health, int extraDmg) {
		this.Health = health;
		this.Name = name;
		this.ExtraDamage = extraDmg;
		
		//sets total damage of entity 
		this.Damage = BaseDamage + ExtraDamage;
	}
	
	/**
	 * get the amount of current health from the entity
	 * @return - health amount
	 */
	public int GetHealth() {
        return Health;
    }

	/**
	 * get the name of the entity 
	 * @return - entity name
	 */
    public String GetName() {
        return Name;
    }

    /**
     * get the amount of total damage caused by this entity
     * @return - total damage
     */
    public int GetDamage() {
        return Damage;
    }

    /**
     * get the amount of extra damage caused by this entity
     * @return - extra damage
     */
    public int GetExtraDamage() {
        return ExtraDamage;
    }
    
    /**
     * changes amount of extra damage caused by this entity 
     * @param dmg - extra damage
     */
    public void SetDamage(int dmg) {
    	ExtraDamage = dmg;
    	
    	// automatically update the total damage
    	Damage = BaseDamage + ExtraDamage;
    }
    
    /**
     * determintes whether this entity is alive or not depending on amount of health 
     * @return - alive status
     */
    public boolean IsAlive() {
    	return Health > 0;
    }
    
    /**
     * reduces the amount of current health from the entity
     * @param dmg - amount to reduce from entity health
     */
    public void TakeDamage(int dmg) {
    	Health -= dmg;
    }
    
    public void AddHealth(int amt) {
    	Health += amt;
    }
}