package unsw.dungeon;

public class Invincibilitypotion extends Collectable{
	private int remaintime;
	/**
	 * 
	 * @param coordinate x
	 * @param coordinate y
	 */
	public Invincibilitypotion(int x, int y) {
		super(x, y);
		remaintime=10;
	}
	public void setRemaintime(int time) {
		remaintime=time;
	}
	public int getRemaintime() {
		return remaintime;
	}
	
	
}
