package unsw.dungeon;

public class offSwitch implements SwitchState{
	/**
	 * boulder is not on the switch
	 */
	@Override
	public boolean isTurnOn() {
		return false;
	}
	
}
