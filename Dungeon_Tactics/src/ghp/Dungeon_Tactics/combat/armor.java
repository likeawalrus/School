package ghp.Dungeon_Tactics.combat;

public class armor {
	public String name;
	public int id;
	public int slot;
	public int ac_bonus;
	
	public int[][] eff = new int[3][2]; //max 3 effects [id][lvl]
	
	armor(String n, int i, int s, int ab)
	{
		name = n;
		id = i;
		slot = s;
		ac_bonus = ab;
	}
}
