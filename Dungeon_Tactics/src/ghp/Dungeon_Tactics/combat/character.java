package ghp.Dungeon_Tactics.combat;


import ghp.Dungeon_Tactics.spells.spell_list;

import java.util.*;


public class character {

	public String name;
	public int attributepoints;
	public int str;
	public int con;
	public int dex;
	public int intel;
	public int fai;
	public int agi;
	public int movespeed; //in squares
	public int init;
	public int faction; //0 for players, 1 for enemy team 1 etc
	
	public Vector<weapon> cwep_list = new Vector<>(); 
	//0 for main hand, 1 for off-hand wep, 2+ additional (monsters)
	public Vector<equipment> cequip_list = new Vector<>();
	//0 amulet, 1 ring1, 2 ring2 
	public Vector<armor> carmor_list = new Vector<>();
	//0 helm, 1 chest, 2 legs, 3 hands, 4 boots, 5 off hand shield
	
	public Vector<weapon> cwep_inv = new Vector<>();
	public Vector<equipment> cequ_inv = new Vector<>();
	public Vector<armor> carm_inv = new Vector<>();
	
	public Vector<effect> ceff_list = new Vector<>();
	
	public Vector<Integer> cspells_known = new Vector<Integer>();
	public Vector<Integer> cspells_rank = new Vector<Integer>();
	
	
	
	public int gold;
	public int exp;
	public int ding_exp;
	public int level;
	
	public int ac; //base armor value (not total including armor)
	public int md; //magic defense
	
	public int bab; //base attack bonus
	public int bmb; //base magic bonus
	
	public int hp;
	public int max_hp;
	public int hp_regen;
	
	public int mana;
	public int max_mana;
	public int mana_regen;
	public int xy;
	
	
	
	character()
	{
		
	}
	
	public void gen_char(database d, int st, int de, int co, int inte, int fa, int ag, int ms, int ini, int f)
	{
		Random r = new Random();
		//d.generate();  needs to be made b4 character
	
		name = "noname";
		str = st;
		dex = de;
		con = co;
		intel = inte;
		fai = fa;
		agi = ag;
		movespeed = ms;
		init = ini;
		faction = f;
		
		carmor_list.add(d.getarmor("h_none"));
		carmor_list.add(d.getarmor("c_none"));
		carmor_list.add(d.getarmor("l_none"));
		carmor_list.add(d.getarmor("g_none"));
		carmor_list.add(d.getarmor("b_none"));
		carmor_list.add(d.getarmor("s_none"));
		
	
		cwep_list.add(d.getweapon("m_unarmed"));
		cwep_list.add(d.getweapon("o_unarmed"));
		
		
		cequip_list.add(d.getequip("a_none"));
		cequip_list.add(d.getequip("r1_none"));
		cequip_list.add(d.getequip("r2_none"));
		
		bab = 1;
		bmb = 1;
		md =0;
		ac = 10;
		max_hp = 3+r.nextInt(8)+con;
		hp = max_hp;
		hp_regen = (int) (con/5);
		
		max_mana = 100 + 40*(intel-10);
		mana = max_mana;
		mana_regen = (int) 1 + (intel-10) + 2*(fai-10);
 	
		
		movespeed = 6;
		init = 1;
		exp = 0;
		ding_exp = 100;
		gold = 0;
		level = 0;
		
	}
	
	public int get_spell_rank(int id)
	{
		int rank = 0;
		int temp = 0;
		
			for(int i=0; i<=this.cspells_known.size()-1; i=i+1)
			{
				temp++;
				if(this.cspells_known.get(i) == id)
				{
					rank = temp;
					
				}
				
			}
			
			return rank; //will return if found 
									
			
		}
	
	
	
	public character clone_char(character c)
	{
		character temp = new character();
		temp.name = c.name;
		temp.str = c.str;
		temp.dex = c.dex;
		temp.con = c.con;
		temp.intel = c.intel;
		temp.fai = c.fai;
		temp.agi = c.agi;
		temp.movespeed = c.movespeed;
		temp.init = c.init;
		temp.faction = c.faction;
		
		temp.carmor_list = c.carmor_list;
		temp.cwep_list = c.cwep_list;
		temp.cequip_list = c.cequip_list;
		temp.cwep_inv = c.cwep_inv;
		temp.cequ_inv = c.cequ_inv;
		temp.carm_inv = c.carm_inv;
		
		temp.ceff_list =c.ceff_list ;
		
		temp.cspells_known =c.cspells_known;
		temp.cspells_rank = c.cspells_rank;
	
		
		temp.bab = c.bab;
		temp.bmb = c.bmb;
		temp.md = c.md;
		temp.ac = c.ac;
		temp.max_hp = c.max_hp;
		temp.hp = c.hp;
		temp.hp_regen = c.hp_regen;
		
		temp.max_mana = c.max_mana;
		temp.mana = c.mana;
		temp.mana_regen = c.mana_regen;
		
		temp.exp = c.exp;
		temp.ding_exp = c.ding_exp;
		temp.gold = c.gold;
		temp.level = c.level;
		temp.xy = c.xy;
		
		
		return temp;
		
	}
	
	
}
