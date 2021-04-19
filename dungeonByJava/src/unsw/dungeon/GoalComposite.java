package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class GoalComposite implements GoalComponent{
	private List<GoalComponent> components;
	private String name;
	private boolean achieved;
	
	public GoalComposite(String name) {
		components = new ArrayList<GoalComponent>();
		this.name=name;
	}
	public void add(GoalComponent c) {
		components.add(c);
	}
	public void remove(GoalComponent c) {
		components.remove(c);
	}
	@Override
	public String nameString() {
		String res="";
		if(name.equals("AND"))
			res+="A[";
		else if(name.equals("OR")) 
			res+="O[";
		
		for(GoalComponent c: components) {
			if(components.indexOf(c)==components.size()-1)
				res+=c.nameString();
			else
				res+=c.nameString()+",";
		}
		res+="]";
		return res;
	}
	public boolean isAchieved() {
		return achieved;
	}
	public boolean checkAchieved() {
		boolean res=false;
		
		if(name.equals("OR")) {
			res=false;
			for(GoalComponent c: components) {
				res=res || c.checkAchieved();
			}
			
		}else if(name.equals("AND")) {
			res=true;
			for(GoalComponent c: components) {
				res=res && c.checkAchieved();
			}
		}
		achieved=res;
		return res;
	}
	@Override
	public GoalComponent ifExist(GoalComponent c) {
		GoalComponent res=null;
		for(GoalComponent co:components) {
			res=co.ifExist(c);
			if(res!=null)	return res;
		}
		return null;
	}
	
	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}
	@Override
	public List<GoalComponent> getGoals(){
		List<GoalComponent> res= new ArrayList<GoalComponent>();
		res.add(this);
		for(GoalComponent c: components) {
			res.addAll(c.getGoals());
		}
		return res;
	}
	@Override
	public String getName() {
		return name;
	}
}
