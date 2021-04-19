package unsw.dungeon;

public class Door extends Entity {
	private int id;
	State curr = new CloseDoor();
	/**
	 * 
	 * @param x coordinate x
	 * @param y coordinate y
	 * @param id id for the key
	 */
	
	public Door(int x, int y, int id) {
		super(x, y);
		setWalkable(false);
		this.id=id;
	}
	
	//getter and setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public State getCurr() {
		return curr;
	}

	public void setCurr(State curr) {
		this.curr = curr;
	}
	
}
