package bcdk.map;

/**
 * create checkpoints for the game so the user has to follow the story
 */
public class Checkpoint implements Comparable<Checkpoint> {
	/**
	 * the name given to this checkpoint
	 */
	private String name;
	
	/**
	 * determine wether checkpoint can be passed though
	 */
	public boolean isCompleted = false;
	
	/**
	 * class constructor
	 * @param name - name given to the checkpoint
	 */
	public Checkpoint(String name) {
		this.name = name;
	}
	
	/**
	 * @return - the name given to this checkpoint
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * checks to see if the object passed is a Checkpoint instance and if it has a name
	 */
	@Override
	public boolean equals(Object cp) {
		return cp instanceof Checkpoint && ((Checkpoint)cp).name.equals(this.name);
	}

	/**
	 * compares checkpoint passed name to another checkpoint name
	 */
	@Override
	public int compareTo(Checkpoint o) {
		return o.name.compareTo(this.name);
	}
}
