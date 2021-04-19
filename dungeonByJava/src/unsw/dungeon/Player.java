package unsw.dungeon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {
	final static int up=0;
	final static int down=1;
	final static int left=2;
	final static int right=3;
    private Dungeon dungeon;
    private Sword sword;
    private Key key;
    private int hasPotion;
    private int treasure;
    private PlayerSubject subject;
    private Inventory inventory;
    Timer timer = new Timer();
    
    Media bouldersound = new Media(new File("./sounds/push_boulder.mp3").toURI().toString());
    MediaPlayer boulderPlayer = new MediaPlayer(bouldersound);
    Media pkKeyssound = new Media(new File("./sounds/pick_up_keys.mp3").toURI().toString());
    MediaPlayer pkkeyPlayer = new MediaPlayer(pkKeyssound);
    Media pkCoinsssound = new Media(new File("./sounds/pickup_coin.mp3").toURI().toString());
    MediaPlayer coinPlayer = new MediaPlayer(pkCoinsssound);
    Media pkPotionsssound = new Media(new File("./sounds/pickup_potion.mp3").toURI().toString());
    MediaPlayer potionPlayer = new MediaPlayer(pkPotionsssound);
    Media opDoorssound = new Media(new File("./sounds/open_door.mp3").toURI().toString());
    MediaPlayer doorPlayer = new MediaPlayer(opDoorssound);
    Media switchsound = new Media(new File("./sounds/switchOn.mp3").toURI().toString());
    MediaPlayer switchPlayer = new MediaPlayer(switchsound);
    Media hitsound = new Media(new File("./sounds/sword_hit.mp3").toURI().toString());
    MediaPlayer hitPlayer = new MediaPlayer(hitsound);
    Media pkSwordsound = new Media(new File("./sounds/pickup_sword.mp3").toURI().toString());
    MediaPlayer pkSwordPlayer = new MediaPlayer(pkSwordsound);
    //mediaPlayer.play();
    
    /**
     * Create a player positioned in square (x,y)
     * @param dungeon player in current dungeon
     * @param x coordinate x
     * @param y coordinate y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        sword=null;
        key=null;
        hasPotion=0;
        treasure=0;
        subject = new PlayerSubject();
        inventory= new Inventory(dungeon);
        subject.registerObserver(inventory);
    }
    /**
     * 
     * @param x coordinate x
     * @param y coordinate y
     */
    public Player(int x,int y) {
    	super(x, y);
    	sword=null;
        key=null;
        hasPotion=0;
        treasure=0;
    }
    /**
     * 
     * @param boulder 
     * @param fs
     * @param direction
     * For the player with boulder,if something block the boulder then moving no further.
     * If the boulder is on the switch,then change switch state.
     * If the boulder is off the switch,then change switch state.
     */
    public void moveWithBoulder(Boulder boulder,Floorswitch fs ,int direction) {
    	Floorswitch fs2=null;
    	List<Integer> newpos= getPosition(boulder,direction);
    	if(newpos.size()==0) return;//can't push boulder in this direction
		List<Entity> entities2 = dungeon.entitiesOnSamePosition(newpos.get(0),newpos.get(1));
		if(entities2.size()==0) {//nothing on the way, just move it
			if(fs!=null) {
				dungeon.setSwitchOff(fs);
				switchPlayer.play();
			}
			setPosition(this,direction);
    		setPosition(boulder,direction);
    		boulderPlayer.play();
    		return;
		}
		for(Entity e:entities2) {
			if(e instanceof Floorswitch ) {
				fs2=(Floorswitch)e;
			}
			if(e.isWalkable()==false) return;//something block the way
		}
		
		if(fs==null) {//no switch below the boulder that will be pushed
			if(fs2==null) {//no switch at a target position
				setPosition(this,direction);
        		setPosition(boulder,direction);
        		boulderPlayer.play();
			}else if(fs2.getState().getClass() == new offSwitch().getClass()){//there is a switch at target position
				setPosition(this,direction);
				setPosition(boulder,direction);
        		dungeon.setSwitchOn(fs2);//set switch on
        		switchPlayer.play();
        		boulderPlayer.play();
			}
		}else {//there is a switch below the boulder
			if(fs2==null) {
				setPosition(this,direction);
				setPosition(boulder,direction);
        		dungeon.setSwitchOff(fs);
        		switchPlayer.play();
        		boulderPlayer.play();
			}else if(fs2.getState().getClass() == new offSwitch().getClass()){
				setPosition(this,direction);
				setPosition(boulder,direction);
				switchPlayer.play();
				switchPlayer.play();
        		boulderPlayer.play();
        		dungeon.setSwitchOff(fs);
        		dungeon.setSwitchOn(fs2);
			}
		}
    }
    
    /**
     * @param e
     * @param direction
     * Move entity in certain direction
     */
    public void setPosition(Entity e, int direction) {
    	if(direction ==up && e.getY()>=0)	{
    		e.y().set(e.getY()-1);
    	}
    	else if(direction==down && e.getY()+1<dungeon.getHeight()) {
    		e.y().set(e.getY()+1);
    	}
    	else if(direction==left && e.getX()-1>=0) {
    		e.x().set(e.getX()-1);
    	}
    	else if(direction==right && e.getX()+1<dungeon.getWidth()) {
    		e.x().set(e.getX()+1);
    	}
    }
    
    /**
     * 
     * @param e
     * @param direction
     * @return int list with coordinate x and coordinate y 
     * After entity move in certain direction
     */
    public List<Integer> getPosition(Entity e, int direction) {
    	List<Integer> pos= new ArrayList<Integer>();
    	if(direction ==up && e.getY()>=0)	{
    		pos.add(e.getX());
    		pos.add(e.getY()-1);
    	}
    	else if(direction==down && e.getY()+1<dungeon.getHeight()) {
    		pos.add(e.getX());
    		pos.add(e.getY()+1);
    	}
    	else if(direction==left && e.getX()-1>=0) {
    		pos.add(e.getX()-1);
    		pos.add(e.getY());
    	}
    	else if(direction==right && e.getX()+1<dungeon.getWidth()) {
    		pos.add(e.getX()+1);
    		pos.add(e.getY());
    	}
    	return pos;
    }
    
    /**
     * Player can move up if nothing block 
     * If player move with boulder,call moveWithBoulder()
     */
    public void moveUp() {
    	Boulder boulder=null;
    	Floorswitch fs=null;
    	Enemy en =null;
        if (getY() > 0) {
        	List<Entity> entities = dungeon.entitiesOnSamePosition(getX(),getY()-1);
        	if(entities.size()==0) {//nothing block on the way
        		setPosition(this,up);
        	}else {
	        	for(Entity e:entities) {
	        		if(e instanceof Boulder)	boulder=(Boulder)e;
	        		else if(e instanceof Floorswitch) fs=(Floorswitch)e;
	        		else if(e instanceof Enemy) en=(Enemy)e;
	        		else if(e.isWalkable()==false)	return;
	        	}
	        	if(boulder!=null && boulder.getY()>0) {
	        		moveWithBoulder(boulder,fs ,up);
	        	}else if(boulder==null){//no boulder to be pushed
	        		setPosition(this,up);
	        	}
	        	
	        	if(en!=null) {
	        		if(hasPotion>0) {
	        			dungeon.killEnemy(en);
	        		}else {
	        			dungeon.killed();
	        		}
	        	}
        	}
        }
    }
    
    /**
     * Player can move down if nothing block 
     * If player move with boulder,call moveWithBoulder()
     */
    public void moveDown() {
    	Boulder boulder=null;
    	Floorswitch fs=null;
    	Enemy en =null;
        if (getY() < dungeon.getHeight() - 1) {
        	List<Entity> entities = dungeon.entitiesOnSamePosition(getX(),getY()+1);
        	if(entities.size()==0) {//nothing block on the way
        		setPosition(this,down);
        	}else {
	        	for(Entity e:entities) {
	        		if(e instanceof Boulder)	boulder=(Boulder)e;
	        		else if(e instanceof Floorswitch) fs=(Floorswitch)e;
	        		else if(e instanceof Enemy)	en=(Enemy)e;
	        		else if(e.isWalkable()==false)	return;
	        	}
	        	if(boulder!=null && boulder.getY()<dungeon.getHeight() - 1) {
	        		moveWithBoulder(boulder,fs ,down);
	        	}else if(boulder==null){//no boulder to be pushed
	        		setPosition(this,down);
	        	}
	        	if(en!=null) {
	        		if(hasPotion>0) {
	        			dungeon.killEnemy(en);
	        		}else {
	        			dungeon.killed();
	        		}
	        	}
        	}
        }
    }
    
    /**
     * Player can move left if nothing block 
     * If player move with boulder,call moveWithBoulder()
     */
    public void moveLeft() {
    	Boulder boulder=null;
    	Floorswitch fs=null;
    	Enemy en =null;
    	if (getX() >0) {
        	List<Entity> entities = dungeon.entitiesOnSamePosition(getX()-1,getY());
        	if(entities.size()==0) {//nothing block on the way
        		setPosition(this,left);
        	}else {
	        	for(Entity e:entities) {
	        		if(e instanceof Boulder)	boulder=(Boulder)e;
	        		else if(e instanceof Floorswitch) fs=(Floorswitch)e;
	        		else if(e instanceof Enemy)	en=(Enemy)e;
	        		else if(e.isWalkable()==false)	return;
	        	}
	        	if(boulder!=null && boulder.getX()>0) {
	        		moveWithBoulder(boulder,fs ,left);
	        	}else if(boulder==null){//no boulder to be pushed
	        		setPosition(this,left);
	        	}
	        	if(en!=null) {
	        		if(hasPotion>0) {
	        			dungeon.killEnemy(en);
	        		}else {
	        			dungeon.killed();
	        		}
	        	}
        	}
        }
    }
    
    /**
     * Player can move right if nothing block 
     * If player move with boulder,call moveWithBoulder()
     */
    public void moveRight() {
    	Boulder boulder=null;
    	Floorswitch fs=null;
    	Enemy en =null;
        if (getX()+1<dungeon.getWidth()) {
        	List<Entity> entities = dungeon.entitiesOnSamePosition(getX()+1,getY());
        	if(entities.size()==0) {//nothing block on the way
        		setPosition(this,right);
        	}else {
	        	for(Entity e:entities) {
	        		if(e instanceof Boulder)	boulder=(Boulder)e;
	        		else if(e instanceof Floorswitch) fs=(Floorswitch)e;
	        		else if(e instanceof Enemy) en=(Enemy)e;
	        		else if(e.isWalkable()==false)	return;
	        	}
	        	if(boulder!=null && boulder.getX()+1<dungeon.getWidth()) {
	        		moveWithBoulder(boulder,fs ,right);
	        	}else if(boulder==null){//no boulder to be pushed
	        		setPosition(this,right);
	        	}
	        	if(en!=null) {
	        		if(hasPotion>0) {
	        			dungeon.killEnemy(en);
	        		}else {
	        			dungeon.killed();
	        		}
	        	}
        	}
        }
    }
    
    /**
     * determine item in same coordinate with player is collectable or is a portal.
     * If item is collectable,then use collect() function
     * If item is portal,then then usefindPortal() in dungeon.
     */
    public void interact() {
    	Entity item = dungeon.getEntity(this.getX(), this.getY());
    	if(item instanceof Collectable) {
    		collect(item);
    	}else if(item instanceof Portal) {
    		moveThroughPortal(item);
    	}else if (item instanceof Exit) {
    		exit();
    	}
    	//notifyObservers();
    }
    
    public void exit() {
    	if(dungeon.getGoals().checkAchieved()) {
    		dungeon.setExit(true);
    		dungeon.success();
    	}
    }
    
    /**
     * 
     * @param portal
     * take a Entity portal and find corresponding portal with same id.
     */
    public void moveThroughPortal(Entity portal) {
    	List<Integer> target=dungeon.findPortal((Portal)portal);
    	x().set(target.get(0));
    	y().set(target.get(1));
    }
    
    /**
     * 
     * @param item 
     * Player first find which of item this is.
     * If item is a sword,player without a sword will pickup automatically
     * else if player already has a sword, then player could choose to swap sword if player wants.
     * If item is a Key,player without a key will pickup automatically
     * If item is a Invincibilitypotion,player could choose to pickup if player wants
     * If item is a treasure,player will pickup automatically
     */
    public void collect(Entity item) {
    	if(item instanceof Sword) {
    		if(sword==null) {//pick up the sword,image disappear
    			item.x().set(dungeon.getWidth()+1);
    			dungeon.removeEntity(item);
				subject.update((Sword)item);
				sword= new Sword((Sword)item);
    		}else {//already have a sword in hand
    			Sword s = (Sword) item;
    			if(s.getRemainNumberUsage()>sword.getRemainNumberUsage()) {//swap the swords, image stay still
    				int remainOnhold=sword.getRemainNumberUsage();
    				sword.setRemainNumberUsage(s.getRemainNumberUsage());
    				subject.update((Sword)item);
    				s.setRemainNumberUsage(remainOnhold);
    			}
    		}
    		pkSwordPlayer.play();
    	}else if(item instanceof Key) {
    		if(key==null) {
    			item.x().set(dungeon.getWidth()+1);
    			dungeon.removeEntity(item);
    			subject.update((Key)item);
	    		key= new Key((Key) item);
    		}else {
    			Key k = (Key) item;
    			int idOnhold=key.getId();
    			key.setId(k.getId());
    			k.setId(idOnhold);
    			subject.update((Key)item);
    		}
    		pkkeyPlayer.play();
    	}else if (item instanceof Invincibilitypotion) {
    		timer.cancel();
    		hasPotion=10;
    		item.x().set(dungeon.getWidth()+1);
			dungeon.removeEntity(item);
    		subject.update((Invincibilitypotion)item);
    		Invincibilitypotion p =(Invincibilitypotion)item;
    		invincable(hasPotion,p);
    		potionPlayer.play();
    	}else if (item instanceof Treasure) {
    		dungeon.setTreasure(dungeon.getTreasure()-1);
    		treasure+=1;
    		item.x().set(dungeon.getWidth()+1);
			dungeon.removeEntity(item);
			subject.update(treasure);
			coinPlayer.play();
    	}
    }
    
    /**
     * track how much time invincale potion is effective.
     */
    public void invincable(int time, Invincibilitypotion p) {
    	timer=new Timer();
		timer.schedule( new TimerTask() {
		    public void run() {
				hasPotion-=1;
				p.setRemaintime(hasPotion);
				subject.update(p);
				if(hasPotion==0) {
					timer.cancel();
				}
		    }
		 }, 0, 500);
    }
    
    /**
     * Player can attack enemy which is surrounded.
     * If player with a sword can defeat and sword usage minus 1.
     * Sword with 0 usage will automatically disappear.
     * Once player without a sword collided with enemy will be defeated by enemy.
     */
    public void attact() {
    	if(sword!=null) {
	    	List<Integer> positionX = new ArrayList<Integer>();
	    	List<Integer> positionY = new ArrayList<Integer>();
	    	//get all possible cooridinations where an enemy may stand
	    	for(int i=getX()-1;i<=getX()+1;i++) {
	    		if(i>=0 && i<dungeon.getWidth()) {
	    			positionX.add(i);
	    		}
	    	}
	    	for(int i=getY()-1;i<=getY()+1;i++) {
	    		if(i>=0 && i<dungeon.getHeight()) {
	    			positionY.add(i);
	    		}
	    	}
	    	for(int i : positionX) {
	    		for(int j:positionY) {
	    			Entity item = dungeon.getEntity(i, j);
	    			if(item instanceof Enemy) {//there is an enemy
	    				int hitRemain=sword.getRemainNumberUsage();
	    				if(hitRemain>1) {
	    					sword.setRemainNumberUsage(hitRemain-1);
	    					subject.update(sword);
	    				}else if(hitRemain==1) {
	    					sword.setRemainNumberUsage(0);
	    					subject.update(sword);
	    					sword=null;
	    				}
	    				dungeon.killEnemy((Enemy) item);
	    				hitPlayer.play();
	    			}
	    		}
	    	}
    	}
    }
    
    /**
     * If there is door nearby,player could to choose open the door and door image will change into open door.
     * If player have no key or key with different Id,then door remain close.
     * return None
     */
    public void openDoor() {
    	if(nearby() != null) {
    		Door door = (Door) nearby();
    		if(door.getCurr().Is_open(this, door)==true) {
    			dungeon.addImage(new ImageView(new Image("/open_door.png")), door.getX(), door.getY());
    			door.x().set(dungeon.getWidth()+1);
    			key=null;
    			subject.update(key);
    			doorPlayer.play();
    		}
    	}
    }
    
    /**
     * A function could determine if there is a target Entity nearby(At up,down,left or right
     * with one distance.
     */
    private Entity nearby() {
    	if (dungeon.getEntity(this.getX()+1,this.getY())instanceof Door) {
    		return dungeon.getEntity(this.getX()+1,this.getY());
    	}else if (dungeon.getEntity(this.getX()-1,this.getY())instanceof Door) {
    		return dungeon.getEntity(this.getX()-1,this.getY());
    	}else if (dungeon.getEntity(this.getX(),this.getY()+1)instanceof Door) {
    		return dungeon.getEntity(this.getX(),this.getY()+1);
    	}else if (dungeon.getEntity(this.getX(),this.getY()-1)instanceof Door) {
    		return dungeon.getEntity(this.getX()+1,this.getY()-1);
    	}else {
    		return null;
    	}
    }
    /**
     * 
     * @return keyId if player has a key
     */
    public int get_key_id() {
    	if(this.key !=null) {
    		return key.getId();
    	}
    	return 0;
    }
    //getter and setter
    
	public Key getKey() {
		return key;
	}
	public int getKeyId() {
		return key.getId();
	}
	public Dungeon getDungeon() {
		return dungeon;
	}

	public void setDungeon(Dungeon dungeon) {
		this.dungeon = dungeon;
	}

	public Sword getSword() {
		return sword;
	}

	public void setSword(Sword sword) {
		this.sword = sword;
	}

	public int getHasPotion() {
		return hasPotion;
	}

	public void setHasPotion(int hasPotion) {
		this.hasPotion = hasPotion;
	}

	public int getTreasure() {
		return treasure;
	}

	public void setTreasure(int treasure) {
		this.treasure = treasure;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	public int getSwordRemain() {
		return sword.getRemainNumberUsage();
	}
}
