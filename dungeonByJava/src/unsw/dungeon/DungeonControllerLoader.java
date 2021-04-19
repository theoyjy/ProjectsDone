package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;

    //Images
    private Image playerImage;
    private Image enemyImage;
    
    private Image wallImage;
    private Image boulderImage;
    private Image switchImage;
    private Image doorImage;
    private Image portalImage;
    private Image opendoorImage;
    private Image gnomeImage;
    
    private Image keyImage;
    private Image swordImage;
    private Image potionImage;
    private Image treasureImage;
    
    private Image exitImage;
    
    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image("/human_new.png");
        enemyImage = new Image("/deep_elf_master_archer.png");
        
        wallImage = new Image("/brick_brown_0.png");
        boulderImage = new Image("/boulder.png");
        switchImage = new Image ("pressure_plate.png");
        doorImage = new Image("/closed_door.png");
        opendoorImage = new Image("/open_door.png");
        portalImage = new Image("/portal.png");		
        gnomeImage = new Image("/gnome.png");
        
        keyImage = new Image("/key.png");
        swordImage = new Image("/greatsword_1_new.png");
        potionImage = new Image("/brilliant_blue_new.png");
        treasureImage = new Image("/gold_pile.png");
        
        exitImage = new Image("/exit.png");
    }

    @Override
    public void onLoad(Player player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view);
    }
    
    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);
    }
    
    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }

    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }
    
    @Override
    public void onLoad(Floorswitch floorswitch) {
        ImageView view = new ImageView(switchImage);
        addEntity(floorswitch, view);
    }
    
    @Override
    public void onLoad(Exit exit) {
    	ImageView view = new ImageView(exitImage);
    	addEntity(exit,view);
    }
    
    
    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);
    }
    
    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);
    }
    @Override
	public void onLoad(Door door) {
    	ImageView view = new ImageView(doorImage);
    	if(door.isWalkable() == true) {
    		view = new ImageView(opendoorImage);
    	}
		addEntity(door,view);
	}
    
    @Override
    public void onLoad_open(Door door) {
    	ImageView view = new ImageView(doorImage);
    	view = new ImageView(opendoorImage);
    	addEntity(door,view);
    }
    
	@Override
	public void onLoad(Portal portal) {
		ImageView view = new ImageView(portalImage);
		addEntity(portal,view);
	}

	@Override
	public void onLoad(Invincibilitypotion potion) {
		ImageView view = new ImageView(potionImage);
		addEntity(potion,view);
	}

	@Override
	public void onLoad(Gnome gnome) {
		ImageView view = new ImageView(gnomeImage);
		addEntity(gnome,view);
	}
	
	@Override
	public void onLoad(Treasure treasure) {
		ImageView view = new ImageView(treasureImage);
		addEntity(treasure,view);
	}

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }
    
    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController(Popup popup,Stage primaryStage, DungeonApplication app) throws FileNotFoundException {
        return new DungeonController(load(), entities,this,popup,primaryStage,app);
    }

}
