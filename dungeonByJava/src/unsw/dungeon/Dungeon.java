/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;



/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private DungeonController controller;
    private GoalComposite goals;
    private boolean exit;
    private int boulders;
    private int enemies;
    private int treasure;
    /**
     * 
     * @param width dungeon size
     * @param height dungeon size
     */
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        controller=null;
        goals = new GoalComposite("AND");
        exit=false;
        boulders=0;
        enemies=0;
        treasure=0;
    }
    //getter and setter
    public DungeonController getController() {
		return controller;
	}

	public void setController(DungeonController controller) {
		this.controller = controller;
	}

	public boolean isExit() {
		return exit;
	}
	public void setExit(boolean exit) {
		this.exit = exit;
	}
	public int getBoulders() {
		return boulders;
	}
	public void setBoulders(int boulders) {
		this.boulders = boulders;
	}
	public int getEnemies() {
		return enemies;
	}
	public void setEnemies(int enemies) {
		this.enemies = enemies;
		if(enemies==0) {
			GoalLeaf e= (GoalLeaf) goals.ifExist(new GoalLeaf("enemies"));
	    	if(e!=null) {
	    		e.setAchieved(true);
	    		controller.setEnemies("Yes");
	    		if(goals.checkAchieved())
	    			success();
	    	}
		}
	}
	public int getTreasure() {
		return treasure;
	}
	public void setTreasure(int treasure) {
		this.treasure = treasure;
		if(treasure==0) {
			GoalLeaf t= (GoalLeaf) goals.ifExist(new GoalLeaf("treasure"));
			controller.setTreasure("Yes");
	    	if(t!=null) {
	    		t.setAchieved(true);
	    		if(goals.checkAchieved())
	    			success();
	    	}
		}
	}
	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}
	public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public GoalComposite getGoals() {
		return goals;
	}
	public void setGoals(GoalComposite goals) {
		this.goals = goals;
	}
	public List<Entity> getEntities() {
		return entities;
	}
    
    /**
     * @param entity 
     * adding a entity in dungeon
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    
    /**
     * @param entity
     * delete a entity from dungeon
     */
    public void removeEntity(Entity entity) {
    	entities.remove(entity);
    }
    
    /**
     * @param x
     * @param y
     * @return a entity on the specific coordinate 
     * Or nothing return null
     */
	public Entity getEntity(int x, int y) {
		Entity res = null;
		for (Entity e : entities) {
			if (x == e.getX() && y == e.getY() && !(e instanceof Player)) {
				res = e;
				break;
			}
		}
		return res;
	}    
	/**
	 * Once dungeon is loaded,check if switch iswalkable or not
	 * they may have boulder on that and need to be changed state
	 */
    public void initialSwitch() {
    	for (Entity e : entities) {
    		if(e instanceof Floorswitch) {
    			for(Entity w:entities) {
    				if(w instanceof Boulder && w.getX()==e.getX() && w.getY()==e.getY())
    					setSwitchOn((Floorswitch)e);
    			}
    		}
		}
    }
    
    /**
     * 
     * @param coordinate x
     * @param coordinatey
     * @return Entity on the same coordinate
     */
    public List<Entity> entitiesOnSamePosition(int x, int y){
    	List<Entity> es= new ArrayList<Entity>();
    	for(Entity e:entities) {
    		if(e.getX()==x && e.getY()==y)
    			es.add(e);
    	}
    	return es;
    }
    
	/**
	 * if Enemy entity is in the dungeon
	 * use moveTowardsPlayer() in Enemy class 
	 */
    public void wakeupEnemies() {
    	for(Entity e : entities) {
    		if(e instanceof Enemy) {
    			((Enemy) e).moveTowardsPlayer();
    		}
    	}
    }
    
    /**
     * 
     * @param i imageview 
     * @param x coordinate x
     * @param y coordinate y
     * adding image in specific coordinate
     */
    public void addImage(ImageView i, int x, int y) {
    	controller.addImage(i, x, y);
    }
    public String getGoalName() {
    	return goals.nameString();
    }
    public List<Integer> findPortal(Portal portal) {
    	List<Integer> res=new ArrayList<Integer>();
    	for(Entity e : entities) {
    		if(e instanceof Portal && e!=portal && ((Portal) e).getId()==portal.getId()) {
    			res.add(e.getX());
    			res.add(e.getY());
    			break;
    		}
    	}
    	return res;
    }
    
    /**
     * @param Floorswitch setState is onSwitch()
     * boulder is on the switch
     */
    public void setSwitchOn(Floorswitch s) {
    	s.setState(new onSwitch());
    	this.boulders-=1;
    	if(boulders==0) {
	    	GoalLeaf b= (GoalLeaf) goals.ifExist(new GoalLeaf("boulders"));
	    	if(b!=null) {
	    		b.setAchieved(true);
	    		controller.setBoulder("Yes");
	    		if(goals.checkAchieved()) {
		    		success();
		    	}
	    	}
    	}
    }
    
    /**
     * @param Floorswitch setState is offSwitch()
     * boulder is not on the switch
     */
    public void setSwitchOff(Floorswitch s) {
    	s.setState(new offSwitch());
    	this.boulders+=1;
    	GoalLeaf b= (GoalLeaf) goals.ifExist(new GoalLeaf("boulders"));
    	if(b!=null) {
    		b.setAchieved(false);
    	}
    }
    
    /**
     * Enemy kill the player 
     */
    public void killed() {
    	player.x().set((width+5));
		removeEntity(player);
		fail();
    }
    
    /**
     * Player kill an enemy
     * @param the enemy to be killed
    */
    public void killEnemy(Enemy e) {
    	e.x().set((width+5));
    	e.y().set(0);
    	this.setEnemies(getEnemies()-1);
    	removeEntity(e);
    }
    /**
     * Game over
     */
    public void fail() {
    	for(Entity e:entities) {
    		if(e instanceof Enemy) {
    			e.x().set(width+5);
    			e.y().set(0);
    		}
    	}
    	controller.fail();
    }
    /**
     * win!!
     */
    public void success() {
    	for(Entity e:entities) {
    		if(e instanceof Enemy) {
    			e.x().set(width+5);
    			e.y().set(0);
    		}
    	}
    	controller.success();
    }
}
