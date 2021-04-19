package unsw.dungeon;

public class Portal extends Entity{
	private int id;
	/**
	 * 
	 * @param coordinate x
	 * @param coordinate y
	 * @param id of the portal
	 */
	public Portal(int x, int y, int id) {
		super(x, y);
		this.setWalkable(true);
		this.id = id;
	}
	
	//getter and setter 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
