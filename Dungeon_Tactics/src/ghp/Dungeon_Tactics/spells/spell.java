package ghp.Dungeon_Tactics.spells;

import ghp.Dungeon_Tactics.combat.effect;

import java.util.Vector;



public class spell {
	
	public spell()
	{
		
	}
	
	public String name;
	public int id; //index in s_list vector
	public int lv; //lv of spell
	public int lv_req; //lv needed to cast
	boolean sight; //do you need line of sight to cast on target?
	public int mana_c; //mana cost
	public int p_damage; //primary target damage (number of d6) (+ damage, - healing)
	public int s_damage; //secondary damage (number of d6)
	public int range_type; //(0) single_target, (1) cone, (2) burst area, (3) line (thin thick), (4) arcing 
	public int range; //for single target, burst
	public int jumps;
	public String type; //fire, healing
	public int save_type; //(0) grit (physical), (1) will (mental), (2) dodge, (3) no save (friendly), (4) no save
	//(5) grit and will, (6) grit and dodge, (7) will and dodge, (8) grit will and dodge
	public int save_value; //have to beat to ignore
	
	public Vector<effect> pe_list = new Vector<>();
	public Vector<effect> se_list = new Vector<>();
	
	

}
