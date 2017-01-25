package ghp.Dungeon_Tactics.combat;

public class init_struct {

	public int ibonus;
	public int itotal;
	public int cl_id; //number of position in character list vector;
	public boolean move_action; //0
	public boolean standard_action; //1
	public boolean quick_action; //2
	public boolean full_action; //3
	
	init_struct(int b, int tot, int id)
	{
		ibonus = b;
		itotal = tot;
		cl_id = id;
		move_action = true;
		standard_action = true;
		quick_action = true;
		full_action = true;
	}
	
	public void new_turn()
	{
		move_action = true;
		standard_action = true;
		quick_action = true;
		full_action = true;
	}
	
	public void action(int n, boolean b) //use an action;
	{
		switch (n)
		{
		case 0:
			move_action = b;
			break;
		case 1:
			standard_action = b;
			break;
		case 2:
			quick_action = b;
			break;
		case 3:
			move_action = b;
			standard_action = b;
			break;
		
		}
		
	}
}
