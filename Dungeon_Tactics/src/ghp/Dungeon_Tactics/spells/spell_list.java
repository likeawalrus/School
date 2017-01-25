package ghp.Dungeon_Tactics.spells;

import ghp.Dungeon_Tactics.combat.effect_list;

import java.util.Vector;




public class spell_list {
	
	public spell_list()
	{
		gen_spell_templates();
	}
	
	public Vector<spell> s_list = new Vector<>(); 
	
	public void gen_spell_templates()
	{
		
		//---fireball------------------------
		spell temp = new spell(); 
		temp.id = 0;
		temp.name = "fireball";
		temp.range_type = 2; //burst
		temp.jumps = 0;
		temp.sight = true;
		temp.type = "fire";
		temp.lv_req = 3;
		temp.save_type = 6; //grit and dodge
		s_list.add(temp);
		
		//---fireburst------------------------
		temp = new spell(); 
		temp.id = 1;
		temp.name = "fireburst";
		temp.range_type = 2; //burst
		temp.jumps = 0;
		temp.sight = false;
		temp.type = "fire";
		temp.lv_req = 4;
		temp.save_type = 6; //grit and dodge
		s_list.add(temp);
		
		//----healing_light-------------------
		temp = new spell(); 
		temp.id = 2;
		temp.name = "healing light";
		temp.range_type = 0; //single
		temp.jumps = 0;
		temp.sight = true;
		temp.type = "healing";
		temp.lv_req = 1;
		temp.save_type = 3; //friendly
		s_list.add(temp);
		
		//----magic_missile-------------------
		temp = new spell(); 
		temp.id = 3;
		temp.name = "magic missile";
		temp.range_type = 0; //single
		temp.jumps = 0;
		temp.sight = true;
		temp.type = "pure";
		temp.lv_req = 1;
		temp.save_type = 4; //no save
		s_list.add(temp);
		
		
	}
	
	
	public spell gen_spell(effect_list el, int id, int lv)
	{
		spell temp = s_list.get(id);
		
		switch (id){
		
		case 0: //fireball
				temp.mana_c = 100*lv;
				temp.p_damage = lv+5; //at spell lv 1 it does 6d6;
				temp.s_damage = lv+2; //does -1 d6 to all surrounding in burst;
				temp.range =(int) 6+(lv/2);
				temp.pe_list.add(el.gen_effect(0,lv));
				temp.se_list.add(el.gen_effect(0,lv));
				temp.save_value = 10+lv;
				//System.out.println("generated fireball");
				
		break;
		
		case 1: //fireburst
			temp.mana_c = 110*lv;
			temp.p_damage = lv+4; //at spell lv 1 it does 5d6;
			temp.s_damage = lv+1; //does -1 d6 to all surrounding in burst;
			temp.range =(int) 4+(lv/2); //not as far as fireball, but can cast w/o line of sight
			temp.pe_list.add(el.gen_effect(0,lv));
			temp.se_list.add(el.gen_effect(0,lv));
			temp.save_value = 10+lv;
			//System.out.println("generated fireburst");
			
			
		break;
		
		case 2: //healing light
			temp.mana_c = 50*lv;
			temp.p_damage = (int) -1.5*lv; //not as strong as damage spells
			temp.s_damage = 0; 
			temp.range =(int) 6+(lv/2); //not as far as fireball, but can cast w/o line of sight
			temp.save_value = 0;
			//System.out.println("generated healing light");
			
			
		break;
		
		case 3: //magic missile
			temp.mana_c = 10*lv; //cheap and weak, but no save
			temp.p_damage = lv; //not as strong as damage spells
			temp.s_damage = 0; 
			temp.range =(int) 6+(lv/2); //not as far as fireball, but can cast w/o line of sight
			temp.save_value = 0;
			//System.out.println("generated magic missile");
			
			
		break;
		
		
		
		
		}//switch
		
		return temp;
		
	} //gen spell
	
	public int gets(String n)
	{
		int s =0;
		for(int i =0; i<this.s_list.size(); i++)
		{
			if(this.s_list.get(i).name == n)
			{
				s = this.s_list.get(i).id;
			}
		}
		
		return s;
		
	}

}
