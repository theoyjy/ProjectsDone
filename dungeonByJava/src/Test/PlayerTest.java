package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.dungeon.*;

public class PlayerTest {
	@Test
	void TestPlayerMove() {
		Dungeon dungeon = new Dungeon(5,5);
		Player player = new Player(dungeon,2,2);
		dungeon.setPlayer(player);
		dungeon.addEntity(new Door(2,1,12));
		dungeon.addEntity(new Wall(1,1));
		
		//able to move left
		player.moveLeft();
		assertEquals(player.getX(),1);
		assertEquals(player.getY(),2);
		
		//not able to move up because of wall
		player.moveUp();
		assertEquals(player.getX(),1);
		assertEquals(player.getY(),2);
		
		//able to move right
		player.moveRight();
		assertEquals(player.getX(),2);
		assertEquals(player.getY(),2);
		
		//not able to move up because of closed door
		player.moveUp();
		assertEquals(player.getX(),2);
		assertEquals(player.getY(),2);
		
		//player on the edge
		player = new Player(dungeon,0,0);
		dungeon.setPlayer(player);
		player.moveLeft();
		assertEquals(player.getX(),0);
		assertEquals(player.getY(),0);
		
		player = new Player(dungeon,4,4);
		dungeon.setPlayer(player);
		player.moveDown();
		assertEquals(player.getX(),4);
		assertEquals(player.getY(),4);
	}
	
	@Test
	void TestPlayerCollect() {
		
		Dungeon dungeon = new Dungeon(5,5);
		Player player = new Player(dungeon,2,2);
		dungeon.setPlayer(player);
		dungeon.addEntity(new Key(2,2,12));
	
		//pick up the key 
		assert(player.getKey() == null);
		player.collect(new Key(2,2,12));
		assert(player.getKey().getId() == 12);
		
		// pick up the sword
		dungeon.addEntity(new Sword(2,2));
		player.collect(new Sword(2,2));
		assert(player.getSword().getRemainNumberUsage()==5);
		
		// switch other sword
		player.getSword().setRemainNumberUsage(3);
		assert(player.getSword().getRemainNumberUsage()==3);
		dungeon.addEntity(new Sword(2,2));
		player.collect(new Sword(2,2));
		assert(player.getSword().getRemainNumberUsage()==5);
	}
	
	@Test
	void TestPlayerAttack() {
		
		Dungeon dungeon = new Dungeon(5,5);
		Player player = new Player(dungeon,2,2);
		dungeon.setPlayer(player);
		dungeon.addEntity(new Enemy(dungeon,3,3));
		player.setSword(new Sword(2,2));
		
		player.attact();
		//sword usage minus 1
		assert(player.getSword().getRemainNumberUsage()==4);
		//no enemy in the dungeon
		assert(dungeon.getEntities().size()==0);
	}
	
	@Test
	void TestPlayerMoveWithBoulder() {
		
		//test boulder move with player
		Dungeon dungeon = new Dungeon(5,5);
		Player player = new Player(dungeon,2,2);
		dungeon.setPlayer(player);
		Boulder boulder = new Boulder(1,2);
		dungeon.addEntity(boulder);
		player.moveLeft();
		assert(boulder.getX() == 0);
		assert(boulder.getY() == 2);
		
		//test switch with boulder
		Floorswitch fl = new Floorswitch(0,1);
		assertTrue(fl.isWalkable());
		dungeon.addEntity(fl);
		player = new Player(dungeon,0,3);
		dungeon.setPlayer(player);
		
		player.moveWithBoulder(boulder,fl ,0);
		assertFalse(fl.isWalkable());
	}
	@Test
	void TestPlayerUsePortal() {
		Dungeon dungeon = new Dungeon(5,5);
		Player player = new Player(dungeon,2,2);
		dungeon.setPlayer(player);
		
		Portal p1 = new Portal(2,2,2);
		Portal p2 = new Portal(3,3,2);
		dungeon.addEntity(p1);
		dungeon.addEntity(p2);
		player.interact();
		assert(player.getX()==3);
		assert(player.getY()==3);
	}
	@Test
	void TestPotion() {
		Dungeon dungeon = new Dungeon(5,5);
		Player player = new Player(dungeon,2,2);
		Enemy  enemy = new Enemy(dungeon,4,4);
		dungeon.setPlayer(player);
		
		Invincibilitypotion p = new Invincibilitypotion(2,2);
		dungeon.addEntity(p);
		dungeon.addEntity(enemy);
		player.collect(p);
		assert(player.getHasPotion()==10);
		enemy.moveTowardsPlayer();
	}
}
