package bcdk.item;

/**
 * basic object of items that the user can use and store
 */
public abstract class Item {
	/**
	 * the name assigned to the item
	 */
	private String name;
	
	/**
	 * the description assigned to the item 
	 */
	private String description;
	
	/**
	 * class constructor
	 * @param name - name of item
	 * @param description - description of the item
	 */
	protected Item(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	/**
	 * @return - the name given to the item
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return -  the description given to the item
	 */
	public String getFullDescription() {
		return this.description;
	}
	
	
	// 1.4a - override toString
	// 1.9 - use of override annotation
	@Override
	public String toString() {
		return this.name + ": " + this.description;
	}
}
