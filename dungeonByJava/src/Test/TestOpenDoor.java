package Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

public class TestOpenDoor {
	@Test
	void DoorTest() { 
		Door door1 = new Door(1,1,1);
		Player player1 = new Player(1,2);
		
		assert(door1.getX()==1);
		assert(door1.getY()==1);
		
		assertFalse(door1.isWalkable());
		assertFalse(door1.getCurr().Is_open(player1, door1));
		Key key1 = new Key(1,1,1);
		Key key2 = new Key(2,2,2);
		
		//player with wrong key
		player1.setKey(key2);
		assertFalse(door1.getCurr().Is_open(player1, door1));
		assert(door1.getCurr() instanceof CloseDoor);
		
		//player with correct key
		player1.setKey(key1);
		assertTrue(door1.getCurr().Is_open(player1, door1));
		assert(door1.getCurr() instanceof OpenDoor);
		
	}
	
}
