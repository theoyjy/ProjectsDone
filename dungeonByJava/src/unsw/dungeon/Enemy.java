package unsw.dungeon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Enemy extends Entity {
	final static int up=0;
	final static int down=1;
	final static int left=2;
	final static int right=3;
	private Dungeon dungeon;
	//private EnemyObserver observer;
	private Player player;
	Timer timer=null;
	public Enemy(Dungeon dungeon,int x, int y) {
		super(x, y);
		this.dungeon=dungeon;
		setWalkable(false);
		this.player=dungeon.getPlayer();
	}

	/**
	 * Once know the player with potion effect,comparing distance x and distance y with player.
	 * Run away in direction which is shorter distance.
	 */
	public void runAway() {
		timer = new Timer();
		timer.schedule( new TimerTask() {
			@Override
		    public void run() {
				Platform.runLater(new Runnable() {
				       public void run() {
				    	int dis_x=getX()-player.getX();
						int dis_y=getY()-player.getY();
						List<Integer> directions = new ArrayList<Integer>();
						if(Math.abs(dis_x)<=Math.abs(dis_y)) {//x distance is shorter, expand x dis first
							if(dis_x<0) {//enemy is at left of player
								directions.add(left);
								if(dis_y<0) {//enemy above player
									directions.add(up);
									directions.add(down);
								}else {
									directions.add(down);
									directions.add(up);
								}
								directions.add(right);
							}else {//enemy is at right of player
								directions.add(right);
								if(dis_y<0) {//enemy above player
									directions.add(up);
									directions.add(down);
								}else {
									directions.add(down);
									directions.add(up);
								}
								directions.add(left);
							}
						}else {//x distance is longer, shorten x first
							if(dis_y<0) {//enemy above the player
								directions.add(up);
								if(dis_x<0) {//enemy is at left of player
									directions.add(left);
									directions.add(right);
								}else {//enemy is at right of player
									directions.add(right);
									directions.add(left);
								}
								directions.add(down);
							}else {//enemy is below player
								directions.add(down);
								if(dis_x<0) {//enemy is at left of player
									directions.add(left);
									directions.add(right);
								}else {//enemy is at right of player
									directions.add(right);
									directions.add(left);
								}
								directions.add(up);
							}
						}
						moveDirection(directions);
						if(player.getX()==getX() && player.getY()==getY()) {
							cancel();
							timer.cancel();
							timer.purge();
							//timer=null;
							bekilled();
						}
						if(player.getHasPotion()<=0) {
							cancel();
							timer.cancel();
							timer.purge();
							//timer=null;
							moveTowardsPlayer();
						}
						
			       }
				});
		    }
		},0,500);
	}
	public void bekilled() {
		dungeon.killEnemy(this);
	}
	/**
	 * Comparing x distance and y distance,which is longer and shorten it.
	 */
	public void moveTowardsPlayer() {
		timer = new Timer();
		TimerTask t = new TimerTask() {
			@Override
		    public void run() {
				Platform.runLater(new Runnable() {
				       public void run() {
				    	int dis_x=getX()-player.getX();
						int dis_y=getY()-player.getY();
						List<Integer> directions = new ArrayList<Integer>();
						if(Math.abs(dis_x)<=Math.abs(dis_y)) {//y distance is longer, shorten y first
							if(dis_y<0) {//enemy above the player
								directions.add(down);
								if(dis_x<0) {//enemy is at left of player
									directions.add(right);
									directions.add(left);
								}else {//enemy is at right of player
									directions.add(left);
									directions.add(right);
								}
								directions.add(up);
							}else {//enemy is below player
								directions.add(up);
								if(dis_x<0) {//enemy is at left of player
									directions.add(right);
									directions.add(left);
								}else {//enemy is at right of player
									directions.add(left);
									directions.add(right);
								}
								directions.add(down);
							}
						}else {//x distance is longer, shorten x first
							if(dis_x<0) {//enemy is at left of player
								directions.add(right);
								if(dis_y<0) {//enemy above player
									directions.add(down);
									directions.add(up);
								}else {
									directions.add(up);
									directions.add(down);
								}
								directions.add(left);
							}else {//enemy is at right of player
								directions.add(left);
								if(dis_y<0) {//enemy above player
									directions.add(down);
									directions.add(up);
								}else {
									directions.add(up);
									directions.add(down);
								}
								directions.add(right);
							}
						}
						moveDirection(directions);
						if(player.getHasPotion()>0) {
							cancel();
							timer.cancel();
							timer.purge();
							//timer=null;
							runAway();
						}
						if(getX()==player.getX() && getY()==player.getY()) {
							cancel();
							timer.cancel();
							timer.purge();
							//timer=null;
							kill();
						}
			       }
				});
		    }
		 };
		 timer.schedule(t, 0,500);
	}

	/**
	 * kill the player
	 */
	public void kill() {
		dungeon.killed();
	}
	
	/** 
	 * @param directions
	 * see which direction should enemy move
	 */
	public void moveDirection(List<Integer> directions) {
		boolean move = false;
		for(int i:directions) {
			if(i==up) {
				if(moveUp()==true)
					move=true;
			}else if(i==down) {
				if(moveDown()==true)
					move=true;
			}else if(i==left) {
				if(moveLeft()==true)
					move=true;
			}else if(i==right){
				if(moveRight()==true)
					move=true;
			}
			
			if(move==true)	break;
		}
	}
	
	/**
	 * Enemy move up if nothing block
	 * @return successful move up or not
	 */
    public boolean moveUp() {
        if (getY() > 0) {
        	Entity e =dungeon.getEntity(getX(), getY()-1);
        	if(e==null || e.isWalkable()==true) {
        		y().set(getY() - 1);
        		return true;
        	}
        }
        return false;
    }
    
    /**
	 * Enemy move down if nothing block
	 * @return successful move down or not
	 */
    public boolean moveDown() {
        if (getY() < dungeon.getHeight() - 1) {
        	Entity e =dungeon.getEntity(getX(), getY()+1);
        	if(e==null || e.isWalkable()==true) {
        		y().set(getY() + 1);
        		return true;
        	}
        }
        return false;
    }

    /**
	 * Enemy move left if nothing block
	 * @return successful move left or not
	 */
    public boolean moveLeft() {
        if (getX() > 0) {
        	Entity e =dungeon.getEntity(getX()-1, getY());
        	if(e==null || e.isWalkable()==true) {
        		x().set(getX() - 1);
        		return true;
        	}
        }
        return false;
    }

    /**
	 * Enemy move right if nothing block
	 * @return successful move right or not
	 */
    public boolean moveRight() {
        if (getX() < dungeon.getWidth() - 1) {
        	Entity e =dungeon.getEntity(getX()+1, getY());
        	if(e==null || e.isWalkable()==true) {
        		x().set(getX() + 1);
        		return true;
        	}
        }
        return false;
    }
}
