package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class GoalLeaf implements GoalComponent{
	private String name;
	private boolean achieved;
	
	public GoalLeaf(String name) {
		this.name=name;
		achieved=false;
	}
	@Override
	public String nameString() {
		String res="";
		if(name.equals("exit"))
			res= "E";
		else if(name.equals("enemies"))
			res= "e";
		else if(name.equals("boulders"))
			res= "B";
		else if(name.equals("treasure"))
			res= "T";
		return res;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean checkAchieved() {
		return achieved;
	}
	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}
	@Override
	public GoalComponent ifExist(GoalComponent c) {
		if(c.getName().equals(name))
			return this;
		return null;
	}
	@Override
	public List<GoalComponent> getGoals(){
		List<GoalComponent> res= new ArrayList<GoalComponent>();
		res.add(this);
		return res;
	}
	@Override
	public String getName() {
		return name;
	}
}
