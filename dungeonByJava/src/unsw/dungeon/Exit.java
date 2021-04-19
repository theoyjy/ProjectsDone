package unsw.dungeon;

public class Exit extends Entity{
	/**
	 * 
	 * @param coordinate x
	 * @param coordinate y
	 * default walkable is true
	 */
	public Exit(int x, int y) {
		super(x, y);
		this.setWalkable(true);
	}

}
