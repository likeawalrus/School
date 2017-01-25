package ghp.Dungeon_Tactics.combat;


public class weapon {
	public String name;
	public int id;
	public int slot; //(0) main hand, (1) off-hand
	public int type; //(0) blunt, (1) slash, (2) stab, (3) ranged_pierce, (4) ranged_blunt
	public int att_b; 
	public int dam;
	public int range; //for ranged only
	public int crit_r; //threat range
	public int crit_m; //multiplier
	
	public int[][] eff = new int[3][2]; //max 3 effects [id][lvl]
	
	weapon(String n, int i, int s, int t, int d, int r, int cr, int cm, int ab)
	{
		
		name = n;
		id = i;
		slot = s;
		type = t;
		dam = d;
		range = r;
		crit_r = cr;
		crit_m = cm;
		att_b = ab;
	}
}
