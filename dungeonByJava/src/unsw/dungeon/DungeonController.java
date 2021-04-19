package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;
    
    
    private List<ImageView> initialEntities;
    private Player player;
    private Dungeon dungeon;
    private DungeonControllerLoader conLoader;
    private Popup popup;
    private Stage primaryStage;
    private Button restart;
    private Button end;
    private DungeonApplication app;
    
    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities,
    				DungeonControllerLoader conLoader,Popup popup,Stage primaryStage,DungeonApplication app) {
        this.dungeon = dungeon;
        dungeon.setController(this);
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.conLoader=conLoader;
        dungeon.wakeupEnemies();
        this.popup=popup;
        this.primaryStage=primaryStage;
        restart= new Button();
        end= new Button();
        this.app=app;
    }
    
    private Text sword_num = new Text("0");
    private Text potion_time = new Text("0");
    private Text key_id = new Text("None");
    private Text treasure_num = new Text("0");
    
    private Text exit = new Text("false");
    private Text enemies = new Text("false");
    private Text boulder = new Text("false");
    private Text treasure = new Text("false");
    
    private Text Goal = new Text("Goal");
    
    @FXML
    public void initialize() {
    	squares.getChildren().clear();
    	Image ground = new Image("/dirt_0_new.png");
        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }
        for (ImageView entity : initialEntities) {
        	if(squares.getChildren().contains(entity)==false) {
        		squares.getChildren().add(entity);
        	}
        }
        dungeon.initialSwitch();
        dungeon.wakeupEnemies();

        setInstuction();
        setInventory();
        setGoalDisplay();
		
		//add restart, end Buttons
		restart.setText("restart");
		restart.setId("restartBtn");
		restart.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
            	popup.hide();
            	primaryStage.close();
            	Platform.runLater( () -> {
					try {
						app.start(new Stage());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				//app.start(primaryStage);
            }
        });
		GridPane.setColumnSpan(restart, 3); 
		squares.add(restart, dungeon.getWidth()+1, 12);
		
		end.setText("End Game");
		end.setCancelButton(true);
		end.setId("endBtn");
		end.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
            	Stage stage = (Stage) end.getScene().getWindow();
                stage.close();
                System.exit(0);
            }
        });
		GridPane.setColumnSpan(end,3); 
		squares.add(end, dungeon.getWidth()+1, 13);
		
    }

    public GridPane getSquares() {
		return squares;
	}

	public void setSquares(GridPane squares) {
		this.squares = squares;
	}

	public List<ImageView> getInitialEntities() {
		return initialEntities;
	}

	public void setInitialEntities(List<ImageView> initialEntities) {
		this.initialEntities = initialEntities;
	}

	@FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case W:
            player.moveUp();
            break;
        case S:
            player.moveDown();
            break;
        case A:
            player.moveLeft();
            break;
        case D:
            player.moveRight();
            break;
        case J:
        	player.interact();
        	break;
        case K:
        	player.attact();
        	break;
        case O:
        	player.openDoor();
        	break;
        case R:
        	break;
        default:
            break;
        }
    }
    public void addImage(ImageView i, int x, int y) {
    	squares.add(i,x,y);
    }

	public void setSword_num(String sword_num) {
		this.sword_num.setText(sword_num);
	}

	public void setPotion_time(String potion_time) {
		this.potion_time.setText(potion_time);
	}

	public void setKey_id(String key_id) {
		this.key_id.setText(key_id);
	}

	public void setTreasure_num(String treasure_num) {
		this.treasure_num.setText(treasure_num);
	}

	public void setExit(String exit) {
		this.exit.setFill(Color.GREEN);
		this.exit.setText(exit);
	}

	public void setEnemies(String enemies) {
		this.enemies.setFill(Color.GREEN);
		this.enemies.setText(enemies);
	}

	public void setBoulder(String boulder) {
		this.boulder.setFill(Color.GREEN);
		this.boulder.setText(boulder);
	}

	public void setTreasure(String treasure) {
		this.treasure.setFill(Color.GREEN);
		this.treasure.setText(treasure);
	}
	/**
	 * display inventory of player
	 */
	public void setInventory() {
		int heightLimit =0;
		if(dungeon.getHeight()+2>14)
			heightLimit=dungeon.getHeight()+2;
		else
			heightLimit=14;
		for (int x = 0; x < dungeon.getWidth(); x++) {
            for(int y = dungeon.getHeight();y<heightLimit;y++)
                squares.add(new ImageView(new Image("./caveBlock.png")), x, y);
            
        }
        
        squares.add(new ImageView(new Image("./gold_pile.png")), 0,dungeon.getHeight());
        squares.add(treasure_num, 1, dungeon.getHeight());
        squares.add(new ImageView(new Image("./key.png")), 2, dungeon.getHeight());
        key_id.setFont(new Font(12));
        squares.add(key_id, 3, dungeon.getHeight());
        squares.add(new ImageView(new Image("./brilliant_blue_new.png")), 4,dungeon.getHeight());
        squares.add(potion_time, 5, dungeon.getHeight());
        squares.add(new ImageView(new Image("./greatsword_1_new.png")), 6,dungeon.getHeight());
        squares.add(sword_num, 7, dungeon.getHeight());
	}
	/**
	 * display Goals for this dungeon
	 */
	public void setGoalDisplay() {
		GoalComposite total=dungeon.getGoals();
        String goals=dungeon.getGoalName();
        goals=goals.substring(2, goals.length()-1);
        int i=0,j=0;
        Text t= null;
        for(j=0;j< goals.length();j++) {
        	if(goals.charAt(j)=='A') {
    			t=new Text("AND");
        		squares.add(t, i, dungeon.getHeight()+1);
        		i++;
        	}else if(goals.charAt(j)=='O') {
    			t=new Text("OR");
        		squares.add(t, i, dungeon.getHeight()+1);
        		i++;
        	}else if(goals.charAt(j)=='E') {
        		squares.add(new ImageView(new Image("/exit.png")), i, dungeon.getHeight()+1);
        		i++;
        		exit.setFill(Color.RED);
        		squares.add(exit, i, dungeon.getHeight()+1);
        		i++;
        	}else if(goals.charAt(j)=='e') {
        		squares.add(new ImageView(new Image("/deep_elf_master_archer.png")), i, dungeon.getHeight()+1);
        		i++;
        		enemies.setFill(Color.RED);
        		squares.add(enemies, i, dungeon.getHeight()+1);
        		i++;
        	}else if(goals.charAt(j)=='T') {
        		squares.add(new ImageView(new Image("/gold_pile.png")), i, dungeon.getHeight()+1);
        		i++;
        		treasure.setFill(Color.RED);
        		squares.add(treasure, i, dungeon.getHeight()+1);
        		i++;
        	}else if(goals.charAt(j)=='B') {
        		squares.add(new ImageView(new Image("/boulder.png")), i, dungeon.getHeight()+1);
        		i++;
        		boulder.setFill(Color.RED);
        		squares.add(boulder, i, dungeon.getHeight()+1);
        		i++;
        	}else if(goals.charAt(j)=='[') {
        		t=new Text(" :[");
        		squares.add(t, i, dungeon.getHeight()+1);
        		i++;
        	}else if(goals.charAt(j)==']') {
        		t=new Text("  ]");
        		squares.add(t, i, dungeon.getHeight()+1);
        		i++;
        	}else if(goals.charAt(j)==',') {
        		t=new Text("  ,");
        		squares.add(t, i, dungeon.getHeight()+1);
        		i++;
        	}
        }
	}
	/**
	 * display play Instruction
	 */
	public void setInstuction() {
		for(int x=dungeon.getWidth();x<(dungeon.getWidth()+5);x++) {
        	for(int y=0;y<14;y++) {
                squares.add(new ImageView(new Image("./caveBlock.png")), x, y);
        	}
        }
		Text text = new Text("Instruction:");
		//text.setFont(new Font(11));
		GridPane.setColumnSpan(text, 3); 
		squares.add(text, dungeon.getWidth(), 0);
		
		text = new Text("Movement:");
		text.setFont(new Font(12));
		GridPane.setColumnSpan(text, 3); 
		squares.add(text, dungeon.getWidth(), 1);
		squares.add(new Text("W"),dungeon.getWidth()+1,2);
		squares.add(new Text("A"), dungeon.getWidth(), 3);
		squares.add(new Text("S"), dungeon.getWidth()+1,3);
		squares.add(new Text("D"), dungeon.getWidth()+2,3);
		
		text = new Text("Action:");
		text.setFont(new Font(12));
		GridPane.setColumnSpan(text, 2); 
		squares.add(text, dungeon.getWidth(), 4);
		
		text = new Text("Kill");
		squares.add(text, dungeon.getWidth()+1, 5);
		squares.add(new Text("K"),dungeon.getWidth()+2,5);
		
		text = new Text("Open Door");
		GridPane.setColumnSpan(text, 3); 
		squares.add(text, dungeon.getWidth()+1, 6);
		squares.add(new Text("O"), dungeon.getWidth()+4, 6);
		
		text = new Text("Pick Up:");
		GridPane.setColumnSpan(text, 2); 
		squares.add(text,dungeon.getWidth()+1,7);
		squares.add(new Text("J"), dungeon.getWidth()+3, 7);
		
		text = new Text("GoThroughProtal");
		GridPane.setColumnSpan(text, 3); 
		text.setFont(new Font(11));
		squares.add(text,dungeon.getWidth()+1,8);
		squares.add(new Text("J"), dungeon.getWidth()+4, 8);
		
		text = new Text("exit");
		text.setFont(new Font(11));
		squares.add(text,dungeon.getWidth()+1,9);
		squares.add(new Text("J"), dungeon.getWidth()+3, 9);
		
		text = new Text("(gnome:max holding");
		GridPane.setColumnSpan(text, 5); 
		text.setFont(new Font(11));
		squares.add(text,dungeon.getWidth(),10);
		text = new Text("sword's availibility)");
		GridPane.setColumnSpan(text, 5); 
		text.setFont(new Font(11));
		squares.add(text,dungeon.getWidth(),11);
	}
	public void fail() {
		popup.setX(primaryStage.getX());
		popup.setY(primaryStage.getY()+20);
		popup.setWidth(squares.getWidth()-160);
		popup.setHeight(squares.getHeight()+5);
        Rectangle rec= new Rectangle(squares.getWidth()-160,squares.getHeight()+5);
        rec.setFill(new ImagePattern(new Image("/You_lose.png")));
        popup.getContent().addAll(rec);
		popup.show(this.primaryStage);
	}
	public void success() {
		popup.setX(primaryStage.getX());
		popup.setY(primaryStage.getY()+20);
		popup.setWidth(squares.getWidth()-160);
		popup.setHeight(squares.getHeight()+5);
		Rectangle rec= new Rectangle(squares.getWidth()-160,squares.getHeight()+5);
        rec.setFill(new ImagePattern(new Image("/You_win.png")));
        popup.getContent().addAll(rec);
		popup.show(this.primaryStage);
	}
}
