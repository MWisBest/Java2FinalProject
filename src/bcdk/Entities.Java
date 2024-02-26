package bcdk;

public class Entities {
	private int Health;
	private String Name;
	private int Damage; 
	private int ExtraDamage = 0;
	private int BaseDamage = 5;
	
	public Entities(String name, int health, int extraDmg) {
		this.Health = health;
		this.Name = name;
		this.ExtraDamage = extraDmg;
		
		this.Damage = BaseDamage + ExtraDamage;
	}
	
	public int GetHealth() {
        return Health;
    }

    public String GetName() {
        return Name;
    }

    public int GetDamage() {
        return Damage;
    }

    public int GetExtraDamage() {
        return ExtraDamage;
    }
    
    public void SetDamage(int dmg) {
    	ExtraDamage = dmg;
    	
    	Damage = BaseDamage + ExtraDamage;
    }
    
    public boolean IsAlive() {
    	return Health > 0;
    }
    
    public void TakeDamage(int dmg) {
    	Health -= dmg;
    }
}