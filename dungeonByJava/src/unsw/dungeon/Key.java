package unsw.dungeon;

public class Key extends Collectable {
	private int id;
	/**
	 * 
	 * @param x coordinate x
	 * @param y coordinate y
	 * @param id id for the key
	 */
	public Key(int x, int y,int id) {
		super(x,y);
		this.id=id;
	}
	public Key(Key key) {
		super(key.getX(),key.getY());
		id=key.getId();
	}
	
	//getter and setter
	public void setId(int id) {
		this.id=id;
	}
	
	public int getId() {
		return id;
	}
}
