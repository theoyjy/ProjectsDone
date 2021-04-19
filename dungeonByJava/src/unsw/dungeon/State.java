package unsw.dungeon;

public interface State {
	/**
	 * 
	 * @param player 
	 * @param door
	 * @return true if door.iswalkable == true
	 * 		   false if door.iswableable==false
	 */
	public boolean Is_open(Player player, Door door);
}
