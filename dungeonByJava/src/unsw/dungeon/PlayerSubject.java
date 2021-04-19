package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class PlayerSubject implements Subject{
	private List<Observer> observers;
	private Sword sword;
	private Key key;
	private int treasureCount;
	private Invincibilitypotion potion;

	
	public PlayerSubject() {
		observers=new ArrayList<Observer>();
		treasureCount=0;
		sword=null;
		key=null;
		potion=null;
	}
	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}
	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}
	@Override
	public void notifyObservers() {
		for(Observer o:observers) {
			o.update(this);
		}
	}
	
	public void update(Sword s) {
		sword=s;
		notifyObservers();
	}
	public void update(Key k) {
		key=k;
		notifyObservers();
	}
	public void update(int t) {
		treasureCount=t;
		notifyObservers();
	}
	public void update(Invincibilitypotion p) {
		potion=p;
		notifyObservers();
	}
	
	public int getTreasure() {
		return treasureCount;
	}
	public int getTreasureCount() {
		return treasureCount;
	}
	public Key getKey() {
		return key;
	}
	public Sword getSword() {
		return sword;
	}
	public Invincibilitypotion getPotion() {
		return potion;
	}
}
