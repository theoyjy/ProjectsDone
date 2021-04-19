package Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import unsw.dungeon.*;

public class DungeonTest {
	@Test
	void TestDungeon() {
		Dungeon dungeon = new Dungeon(18,16);
		dungeon.setPlayer(new Player(1,1));
		dungeon.addEntity(new Door(2,2,12));
		dungeon.addEntity(new Key(1,1,12));
		
		
		assert(dungeon.getEntity(1,1)instanceof Key);
		dungeon.removeEntity(new Key(1,1,12));
		assert(dungeon.getEntity(1,1)instanceof Entity);
		
	}
}
