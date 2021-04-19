package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.scene.image.Image;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }
    
    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);
        
        JSONArray jsonEntities = json.getJSONArray("entities");
        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        JSONObject jsonObject = json.getJSONObject("goal-condition");
        loadGoal(dungeon, jsonObject, dungeon.getGoals());
        
        return dungeon;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "enemy":
        	Enemy enemy = new Enemy(dungeon,x,y);
        	onLoad(enemy);
        	entity = enemy;
        	dungeon.setEnemies(dungeon.getEnemies()+1);
        	break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "boulder":
        	Boulder boulder = new Boulder(x,y);
        	onLoad(boulder);
        	entity = boulder;
        	break;
        case "switch":
        	Floorswitch floorswitch = new Floorswitch(x,y);
        	onLoad(floorswitch);
        	entity = floorswitch;
        	dungeon.setBoulders(dungeon.getBoulders()+1);
        	break;
        case "sword":
        	Sword sword = new Sword(x,y);
        	onLoad(sword);
        	entity = sword;
        	break;
        case "invincibility":
        	Invincibilitypotion potion = new Invincibilitypotion(x,y);
        	onLoad(potion);
        	entity = potion;
        	break;
        case "treasure":
        	Treasure treasure = new Treasure(x,y);
        	onLoad(treasure);
        	entity = treasure;
        	dungeon.setTreasure(dungeon.getTreasure()+1);
        	break;
        case "exit":
        	Exit exit = new Exit(x,y);
        	onLoad(exit);
        	entity = exit;
        	break;
        case "portal":
        	int id = json.getInt("id");
        	Portal portal = new Portal(x,y,id);
        	onLoad(portal);
        	entity = portal;
        	break;
        case "key":
        	int id1 = json.getInt("id");
        	Key key = new Key(x,y,id1);
        	onLoad(key);
        	entity = key;
        	break;
    	case "door":
    		int id2 = json.getInt("id");
    		Door door = new Door(x,y,id2);
    		onLoad(door);
    		entity = door;
    		break;
    	case "gnome":
    		Gnome gnome = new Gnome(x,y);
    		onLoad(gnome);
    		entity = gnome;
    		break;
        }
        dungeon.addEntity(entity);
    }

	private void loadGoal(Dungeon dungeon, JSONObject json, GoalComposite OverallGoal) {
    	String goal = json.getString("goal");
        switch(goal) {
        	case "enemies":
        		OverallGoal.add(new GoalLeaf("enemies"));
        		break;
        	case "treasure":
        		OverallGoal.add(new GoalLeaf("treasure"));
        		break;
        	case "exit":
        		OverallGoal.add(new GoalLeaf("exit"));
        		break;
        	case "boulders":
        		OverallGoal.add(new GoalLeaf("boulders"));
        		break;
        	
        	case "OR":
        		GoalComposite or = new GoalComposite("OR");
                JSONArray jsonSubgoals = json.getJSONArray("subgoals");
                for (int i = 0; i < jsonSubgoals.length(); i++) {
                    loadGoal(dungeon, jsonSubgoals.getJSONObject(i), or);
                }
                OverallGoal.add(or);
             	break;
            case "AND":
            	GoalComposite and = new GoalComposite("AND");
                JSONArray jsonSubGoals = json.getJSONArray("subgoals");
                for (int i = 0; i < jsonSubGoals.length(); i++) {
                    loadGoal(dungeon, jsonSubGoals.getJSONObject(i), and);
                }
                OverallGoal.add(and);
             	break;
         }       		
    }
    public abstract void onLoad(Player player);

    public abstract void onLoad(Enemy enemy);
    
    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Floorswitch floorswitch);
    
    public abstract void onLoad(Door door);
    
    public abstract void onLoad(Gnome gnome);
    
    public abstract void onLoad(Exit exit);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Invincibilitypotion potion);
    
    public abstract void onLoad(Treasure treasure);
    
    public abstract void onLoad(Boulder boulder);
    
    public abstract void onLoad(Key key);
    
    public abstract void onLoad(Sword sword);
    
    public abstract void onLoad_open(Door door);
}
