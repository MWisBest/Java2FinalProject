package bcdk.map;

public class Checkpoint {
	private String name;
	
	public Checkpoint(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object cp) {
		return cp instanceof Checkpoint && ((Checkpoint)cp).name.equals(this.name);
	}
}