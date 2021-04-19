package unsw.dungeon;

public class onSwitch implements SwitchState {
	/**
	 * boulder is on the switch
	 */
	@Override
	public boolean isTurnOn() {
		return true;
	}
}
