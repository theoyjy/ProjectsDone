package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Collectable extends Entity{
	/**
	 * 
	 * @param x coordinate x
	 * @param y coordinate y
	 * default walkable is true
	 */
	public Collectable(int x, int y) {
		super(x, y);
		this.setWalkable(true);
	}
	
}
