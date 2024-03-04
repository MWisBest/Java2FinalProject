package bcdk;

/*
 * represents the enemies that will be encountered in the game by the player
 */
public class Enemy extends Entities {
	/**
	 * determintes how close enemy is to doing a special attack
	 */
	private int SpecialAttackCharge = 0;
	
	/**
	 * determines the amount of damage caused by special attack
	 */
	private int SpecialAttackDmg;
	
	/**
	 * class constructor
	 * @param name - the name of the enemy
	 * @param health - the amount of health given to the enemy
	 * @param extraDmg - the amount of extra damage that can be caused by enemy
	 */
    public Enemy(String name, int health, int extraDmg) {
        super(name, health, extraDmg); // the derived variables
    }
    
    /**
     * gets the current charge of the special attack 
     * @return - current charge
     */
    public int getSpecialCharge() {
    	return SpecialAttackCharge;
    }
    
    /**
     * Get the special damage amount 
     * @return - amount of special damage
     */
    public int getSpecialDmg() {
    	return SpecialAttackDmg;
    }
    
    /**
     * sets special attack charge back to zero
     */
    public void ResetSpecial() {
    	SpecialAttackCharge = 0;
    }
    
    /**
     * increases the special attack charge
     */
    public void IncreaseSpecial() {
    	SpecialAttackCharge += 20;
    }
    
    /**
     * sets the damage of the special attack 
     */
    public void SetSpecialDmg() {
    	SpecialAttackDmg = GetHealth() / 3;
    }
}