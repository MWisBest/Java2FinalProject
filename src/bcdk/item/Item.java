package bcdk.item;

public abstract class Item {
	private String name;
	private String description;
	
	protected Item(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getFullDescription() {
		return this.description;
	}
}
