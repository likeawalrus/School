package ghp.Dungeon_Tactics.combat;
import java.util.*;


public class effect {
	
	public int id;
	public String name;
	public int dur; //duration
	//(+) time until dispelled, (0) permanent, (-) time until active again
	public String type; //elemental (fire, healing) 
	public int dot; //(+) for damage,(-) for healing
	public int save_type; //(0) grit (physical), (1) will (mental), (2) dodge, (3) no save (friendly), (4) no save
	//(5) grit and will, (6) grit and dodge, (7) will and dodge, (8) grit will and dodge
	public int save_value; //have to beat to ignore
	public Vector<Integer> stateff = new Vector<>(); //6
	//str con dex int fai agi
	public Vector<Integer> othereff = new Vector<>(); //8
	//(0)movespeed (1)init (2)max_mana (3)mana (4)mana_regen (5)max_hp (6)hp_bonus (7)hp_regen
	public Vector<Integer> wepeff = new Vector<>(); //5
	//slot att dam critrange critmult
	public Vector<Integer> aceff = new Vector<>(); //4
	//slot def spelldefense dr 
	
	
	
	effect(int i, String n, String t, int st)
	{
		id = i;
		name = n;
		type = t;
		save_type = st;
		
		save_value = 0;
		dot = 0;
		dur = 0;
		
		for(int x=0; x<6; x++)
		{
			stateff.add(0);
		}
		for(int x=0; x<8; x++)
		{
			othereff.add(0);
		}
		for(int x=0; x<5; x++)
		{
			wepeff.add(0);
		}
		for(int x=0; x<4; x++)
		{
			aceff.add(0);
		}
		
		
	}
	
	
}
