package unsw.dungeon;

import java.util.List;

public interface GoalComponent {
	public String nameString();
	public boolean checkAchieved();
	public GoalComponent ifExist(GoalComponent c);
	public List<GoalComponent> getGoals();
	public String getName();
}
