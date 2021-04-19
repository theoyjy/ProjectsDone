package unsw.dungeon;

public class Boulder extends Entity{
	/**
	 * 
	 * @param coordinate x
	 * @param coordinate y
	 * default setWalkable is false
	 */
	public Boulder(int x, int y) {
		super(x, y);
		this.setWalkable(false);
	}
}
