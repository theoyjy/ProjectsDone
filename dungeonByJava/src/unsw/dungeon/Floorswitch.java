package unsw.dungeon;

public class Floorswitch extends Entity{
	SwitchState state = new offSwitch();
	/**
	 * 
	 * @param coordinate x
	 * @param coordinate y
	 * default walkable is true
	 */
	public Floorswitch(int x, int y) {
		super(x, y);
		this.setWalkable(true);
	}
	
	//getter and setter
	public SwitchState getState() {
		return state;
	}
	
	public void setState(SwitchState state) {
		this.state = state;
		if(state instanceof offSwitch)	setWalkable(true);
		else setWalkable(false);
	}
	

}
