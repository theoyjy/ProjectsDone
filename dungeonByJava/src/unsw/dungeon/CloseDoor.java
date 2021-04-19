package unsw.dungeon;

public class CloseDoor implements State{
	/**
	 * @param player 
	 * @param door
	 * Door is opened if player has key with same id as door's id.
	 * If opened,changed door state to OpenDoor. 
	 */
	@Override
	public boolean Is_open(Player player,Door door) {
		if(player.getKey() != null && player.get_key_id() == door.getId()) {
			door.setCurr(new OpenDoor());
			door.setWalkable(true);
			return true;
		}else {
			return false;
		}
	}
}
