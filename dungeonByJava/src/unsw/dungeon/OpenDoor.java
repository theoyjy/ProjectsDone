package unsw.dungeon;

public class OpenDoor implements State{
	/**
	 * @param player
	 * @param door
	 * @return true
	 * OpenDoor is always open
	 */
	@Override
	public boolean Is_open(Player player, Door door) {
		return true;
	}
	
}
