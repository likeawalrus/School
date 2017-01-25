package ghp.Dungeon_Tactics.combat;

public class equipment { //rings, capes, amulets etc

	public String name;
	public int id;
	public int slot; //0 amulet, 1 ring
	public int[][] eff = new int[3][2]; //max 3 effects [id][lvl]
	
	equipment(String n, int i, int s)
	{
		name = n;
		id = i;
		slot = s;

	}
	
}
