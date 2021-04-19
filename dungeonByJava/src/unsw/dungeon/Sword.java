package unsw.dungeon;

import javafx.beans.property.SimpleIntegerProperty;

public class Sword extends Collectable{
	private int remainNumberUsage;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * default usage 5
	 */
	public Sword(int x, int y) {
		super(x,y);
		remainNumberUsage=5;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param remainNumberUsage manually input sword usage
	 */
	public Sword(int x, int y, int remainNumberUsage) {
		super(x,y);
		this.remainNumberUsage=remainNumberUsage;
	}
	public Sword(Sword sword) {
		super(sword.getX(),sword.getY());
		remainNumberUsage=sword.getRemainNumberUsage();
	}
	
	
	//getter and setter
	public int getRemainNumberUsage() {
		return remainNumberUsage;
	}

	public void setRemainNumberUsage(int remainNumberUsage) {
		this.remainNumberUsage = remainNumberUsage;
	}
	
	
}
