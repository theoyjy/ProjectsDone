package unsw.dungeon;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class Inventory implements Observer{
	private Sword sword;
	private Key key;
	private int treasureCount;
	private Invincibilitypotion potion;
	private DungeonController controller;
	private Dungeon dungeon;
	
	public Inventory(Dungeon dungeon) {
		this.dungeon=dungeon;
		controller=dungeon.getController();
		key=null;
		sword=null;
		potion=null;
		treasureCount=0;
	}
	@Override
	public void update(Subject s) {
		PlayerSubject p =(PlayerSubject) s;
		
		sword=p.getSword();
		key=p.getKey();
		treasureCount=p.getTreasureCount();
		potion=p.getPotion();
		
		display();
	}
	public void display() {
		if(sword!=null)
			dungeon.getController().setSword_num(Integer.toString(sword.getRemainNumberUsage()));
		else {
			
			dungeon.getController().setSword_num("0");;
		}
		
		if(key!=null)
			dungeon.getController().setKey_id(Integer.toString(key.getId()));
		else
			dungeon.getController().setKey_id("None");
		
		dungeon.getController().setTreasure_num(Integer.toString(treasureCount));
		
		if(potion!=null)
			dungeon.getController().setPotion_time(Integer.toString(potion.getRemaintime()));
		else
			dungeon.getController().setPotion_time("0");
	}

}
