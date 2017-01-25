package ghp.Dungeon_Tactics.combat;

import ghp.Dungeon_Tactics.spells.spell_list;

import java.util.Vector;

public class character_list { 

	
	public Vector<character> char_list = new Vector<>();
	
	character_list()
	{
		
	}
	
	public void clear_list()
	{
		char_list.clear();
	}
	
	public void gen_dummies(database d, spell_list sl, effect_list el)
	{
		character player1 = new character();
		player1.gen_char(d, 15, 12, 12, 12, 12, 12, 12, 4, 0);
		player1.name = "Frodo";
		player1.cspells_known.add(3); //magic missile, based on rank below
		player1.cspells_rank.add(1); //lv 1
		
		player1.cspells_known.add(0); //fireball, based on rank below
		player1.cspells_rank.add(1); //lv 1
		
		player1.cspells_known.add(1); //fireburst, based on rank below
		player1.cspells_rank.add(1); //lv 1
		
		player1.cspells_known.add(2); //healing light, based on rank below
		player1.cspells_rank.add(1); //lv 1
		
		player1.cwep_inv.add(d.getweapon("iron scimitar"));
		//player1.cwep_inv.add(d.getweapon("short bow"));
		player1.cwep_inv.add(d.getweapon("offhand sword"));
		
		player1.carm_inv.add(d.getarmor("iron full plate"));
		player1.carm_inv.add(d.getarmor("kite shield"));
		
		player1.cequ_inv.add(d.getequip("power amulet"));
		player1.cequ_inv.add(d.getequip("power ring"));
		player1.attributepoints = 12;
		
		
		char_list.add(player1);
		player1 = null;
		
		character player2 = new character();
		player2.gen_char(d, 16, 11, 12, 12, 12, 12, 12, 4, 0);
		player2.name = "Sam";
		//player2.equip_weapon(d.getweapon("iron scimitar"));
		//player2.equip_armor(d.getarmor("iron full plate"));
		//player2.equip_equipment(d.getequip("power amulet"));
		char_list.add(player2);
		player2 = null;
		
		character monster3 = new character();
		monster3.gen_char(d, 15, 12, 12, 12, 12, 12, 12, 2, 1);
		monster3.name = "Shelob";
		char_list.add(monster3);
		monster3 = null;
		
		character monster4 = new character();
		monster4.gen_char(d, 15, 12, 12, 12, 12, 12, 12, 6, 2);
		monster4.name = "Golem";
		char_list.add(monster4);
		monster4 = null;
		
		character monster1 = new character();
		monster1.gen_char(d, 15, 12, 12, 12, 12, 12, 12, 2, 1);
		monster1.name = "Skeleton1";
		char_list.add(monster1);
		monster1 = null;
		
		character monster2 = new character();
		monster2.gen_char(d, 15, 12, 12, 12, 12, 12, 12, 6, 2);
		monster2.name = "Skeleton2";
		char_list.add(monster2);
		monster2 = null;
		
		
	}
	
	public character getc(String n)
	{
		character c = this.char_list.get(0);
		for(int i =0; i<this.char_list.size(); i++)
		{
			if(this.char_list.get(i).name == n)
			{
				c = this.char_list.get(i);
			}
		}
		return c;
	}

	
}
