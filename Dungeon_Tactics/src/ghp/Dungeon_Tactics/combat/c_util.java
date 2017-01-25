package ghp.Dungeon_Tactics.combat;
import ghp.Dungeon_Tactics.grid.Grid;
import ghp.Dungeon_Tactics.main.Game;
import ghp.Dungeon_Tactics.menu.EquipPane;
import ghp.Dungeon_Tactics.spells.spell;
import ghp.Dungeon_Tactics.spells.spell_list;

import java.util.*;

public class c_util {
	
	public String clog;

	c_util()
	{
		clog = "";
	}
	
	
	public String disp(character_list cl)
	{
		String s="";
		
		for(int i=0; i<=3; i++)
		{
		s+=cl.char_list.get(i).name;
		s+="\tHP: ";
		s+=cl.char_list.get(i).hp;
		s+="/";
		s+=cl.char_list.get(i).max_hp;
		s+="\tMANA: ";
		s+=cl.char_list.get(i).mana;
		s+="/";
		s+=cl.char_list.get(i).max_mana;
		s+='\n';
		}
		
		return s;
	}
	
	public Vector<String> actions(Vector<Integer> v, effect_list el, spell_list sl, character c, character_list cl, Grid g)
	{
		Vector<String> m = new Vector<String>();
		
		//get all coordinates of all characters
		for(int i=0; i<=cl.char_list.size()-1; i++)
		{
			cl.char_list.get(i).xy = g.FindChar(cl.char_list.get(i).name);
			
		}
		
		
		//System.out.println(g.ListTargets(c.xy, c.cwep_list.get(0).range).size());
		
			if(g.ListTargets(c.xy, c.cwep_list.get(0).range).size() != 1)
				{
				m.add("Main Hand"); //main hand weapons range has a target to hit
				}
			
			for(int i = 0; i<c.cspells_known.size(); i++)
			{
			
			if(g.ListTargets(c.xy,sl.gen_spell(el, c.cspells_known.get(i), c.cspells_rank.get(i)).range).size() != 1)
				{
				v.add(c.cspells_known.get(i));
				m.add(sl.gen_spell(el, c.cspells_known.get(i), c.cspells_rank.get(i)).name); //main hand weapons range has a target to hit
				}
		
			}
			
			//System.out.println("vector size " + m.size());
		
		return m;
		
		
	}
	
	
	
	public void menu_equip(EquipPane equippane, character_list cl){
		
		int selected = equippane.combobox.getSelectedIndex() - 1;
		character c = cl.char_list.get(equippane.currentchar);
		effect_list el = new effect_list();
		el = Game.combatdriver.el;
		
		
		if(equippane.mainhand.isSelected()){
			
			Vector<weapon> v = new Vector<weapon>();
			int size = c.cwep_inv.size();
			
			//Removes all Main hand weapons and puts them in a temporary vector
			for(int i = 0; (i < size) && (i < c.cwep_inv.size()); i++){
				if(c.cwep_inv.get(i).slot == 0)
					v.add(c.cwep_inv.remove(i));
			}
			
			String oldequip = c.cwep_list.get(0).name;
			String newequip = v.get(selected).name;
			
			//remove weapon to be equipped from temp vector, and add temp vector back into inventory
			v.remove(selected);
			v.add(Game.combatdriver.d.getweapon(oldequip));
			
			//remove "none equipped" from inv
			for(int i =0; i<=v.size()-1; i++)
			{
				if (v.get(i).name == "m_unarmed")
					v.remove(i);
			}
			
			
			c.cwep_inv.addAll(v);
			
			//Equip new weapon
			equip_weapon(el, c, Game.combatdriver.d.getweapon(newequip));
	
			return;
		}
			
		if(equippane.offhand.isSelected()){
			String newequip = (String) equippane.combobox.getSelectedItem();
			int nt = Game.combatdriver.d.gettype(newequip);
			String oldequip = null;
			
			
			if(c.cwep_list.get(1).name == "o_unarmed" && c.carmor_list.get(5).name == "s_none")
			{
				oldequip = "n1"; //dont put anything into inventory
			}
			else if(c.cwep_list.get(1).name == "o_unarmed" ) //is wearing an offhand shield
				oldequip = c.carmor_list.get(5).name;
			else if(c.carmor_list.get(5).name == "s_none" ) //is wearing offhand weapon
				oldequip = c.cwep_list.get(1).name;
			else //both equipped (ERROR)
			{
				oldequip = "n2"; //unequip both into inventory
			}
			
			//System.out.println("old equipped is: " + oldequip);
			
			int ot = -1; //will not get type if not normal case
			
			if(oldequip != "n1" && oldequip != "n2") //if one is equipped
			{
			ot = Game.combatdriver.d.gettype(oldequip);
			}
			
			//System.out.println("ot: " + ot);
			
			//--add old equip to inventory--------------
			if (ot == 0){//armor
				c.carm_inv.add(Game.combatdriver.d.getarmor(oldequip));
				
			}
			else if (ot == 1){ //weapon
				c.cwep_inv.add(Game.combatdriver.d.getweapon(oldequip));
				
			}
			else if (oldequip == "n1") //no offhand in either slot
			{
				//dont add any to inventory
			}
			
			else if (oldequip == "n2") //both are currently equipped so remove both
			{
				c.carm_inv.add(Game.combatdriver.d.getarmor(c.carmor_list.get(5).name));
				c.cwep_inv.add(Game.combatdriver.d.getweapon(c.cwep_list.get(1).name));
				
				//--add unarmed to other slot not being equipped
				
				
			}
			
			//-----------remove new equip from inv and add to current equipped----------------
			//System.out.println("nt: " + nt);
			//System.out.println("new equip is : " + newequip);
			
			
			if (nt == 0)
			{
				
				equip_weapon(el, c, Game.combatdriver.d.getweapon("o_unarmed"));
				c.carm_inv.remove(Game.combatdriver.d.getarmor(newequip));
				equip_armor(el, c, Game.combatdriver.d.getarmor(newequip));
				
			}
			
			if (nt == 1)
			{
				equip_armor(el, c, Game.combatdriver.d.getarmor("s_none"));
				c.cwep_inv.remove(Game.combatdriver.d.getweapon(newequip));
				equip_weapon(el, c, Game.combatdriver.d.getweapon(newequip));
			}
			
			
			
			return;
		}
		
		if(equippane.neck.isSelected()){
			Vector<equipment> v = new Vector<>();
			int size = c.cequ_inv.size();
			
			//Removes all neck types and puts them in a temporary vector
			for(int i = 0; (i < size) && (i < c.cequ_inv.size()); i++){
				if(c.cequ_inv.get(i).slot == 0) //amulet
					v.add(c.cequ_inv.remove(i));
			}
			
			String oldequip = c.cequip_list.get(0).name;
			String newequip = v.get(selected).name;
			
			//remove amulet to be equipped from temp vector, and add temp vector back into inventory
			v.remove(selected);
			v.add(Game.combatdriver.d.getequip(oldequip));
			
			//remove "none equipped" from inv
			for(int i =0; i<=v.size()-1; i++)
			{
				if (v.get(i).name == "a_none")
					v.remove(i);
			}
			
			
			c.cequ_inv.addAll(v);
			
			//Equip new amulet
			equip_equipment(el, c, Game.combatdriver.d.getequip(newequip));
	
			return;
		}
		
		if(equippane.finger1.isSelected()){
			Vector<equipment> v = new Vector<>();
			int size = c.cequ_inv.size();
			
			//Removes all finger types and puts them in a temporary vector
			for(int i = 0; (i < size) && (i < c.cequ_inv.size()); i++){
				if(c.cequ_inv.get(i).slot == 1) //ring
					v.add(c.cequ_inv.remove(i));
			}
			
			System.out.println("selection " + selected);
			
			String oldequip = c.cequip_list.get(1).name;
			String newequip = v.get(selected).name;
			
			//remove ring to be equipped from temp vector, and add temp vector back into inventory
			v.remove(selected);
			v.add(Game.combatdriver.d.getequip(oldequip));
			
			//remove "none equipped" from inv
			for(int i =0; i<=v.size()-1; i++)
			{
				if (v.get(i).name == "r1_none")
					v.remove(i);
			}
			
			c.cequ_inv.addAll(v);
			
			//Equip new ring
			equip_equipment(el, c, Game.combatdriver.d.getequip(newequip));
	
			return;
		}
		
		if(equippane.finger2.isSelected()){
			Vector<equipment> v = new Vector<>();
			int size = c.cequ_inv.size();
			
			//Removes all finger types and puts them in a temporary vector
			for(int i = 0; (i < size) && (i < c.cequ_inv.size()); i++){
				if(c.cequ_inv.get(i).slot == 1) //ring
					v.add(c.cequ_inv.remove(i));
			}
			
			String oldequip = c.cequip_list.get(2).name;
			String newequip = v.get(selected).name;
			
			//remove ring to be equipped from temp vector, and add temp vector back into inventory
			v.remove(selected);
			v.add(Game.combatdriver.d.getequip(oldequip));
			
			//remove "none equipped" from inv
			for(int i =0; i<=v.size()-1; i++)
			{
				if (v.get(i).name == "r2_none")
					v.remove(i);
			}
			
			c.cequ_inv.addAll(v);
			
			//Equip new ring
			equip_equipment(el, c, Game.combatdriver.d.getequip(newequip));
			return;
		}
		
		if(equippane.head.isSelected()){
			Vector<armor> v = new Vector<>();
			int size = c.carm_inv.size();
			
			//Removes all armor and puts them in a temporary vector
			for(int i = 0; (i < size) && (i < c.carm_inv.size()); i++){
				if(c.carm_inv.get(i).slot == 0) //head
					v.add(c.carm_inv.remove(i));
			}
			
			String oldequip = c.carmor_list.get(0).name;
			String newequip = v.get(selected).name;
			
			//remove armor to be equipped from temp vector, and add temp vector back into inventory
			v.remove(selected);
			v.add(Game.combatdriver.d.getarmor(oldequip));
			
			//remove "none equipped" from inv
			for(int i =0; i<=v.size()-1; i++)
			{
				if (v.get(i).name == "h_none")
					v.remove(i);
			}
			
			c.carm_inv.addAll(v);
			
			//Equip new armor
			equip_armor(el, c, Game.combatdriver.d.getarmor(newequip));
	
			return;
		}
		
		if(equippane.chest.isSelected()){
			Vector<armor> v = new Vector<>();
			int size = c.carm_inv.size();
			
			//Removes all armor and puts them in a temporary vector
			for(int i = 0; (i < size) && (i < c.carm_inv.size()); i++){
				if(c.carm_inv.get(i).slot == 1) //chest
					v.add(c.carm_inv.remove(i));
			}
			
			String oldequip = c.carmor_list.get(1).name;
			String newequip = v.get(selected).name;
			
			//remove weapon to be equipped from temp vector, and add temp vector back into inventory
			v.remove(selected);
			v.add(Game.combatdriver.d.getarmor(oldequip));
			
			//remove "none equipped" from inv
			for(int i =0; i<=v.size()-1; i++)
			{
				if (v.get(i).name == "c_none")
					v.remove(i);
			}
			
			
			c.carm_inv.addAll(v);
			
			//Equip new weapon
			equip_armor(el, c, Game.combatdriver.d.getarmor(newequip));
			
			return;
		}
		
		if(equippane.legs.isSelected()){
			Vector<armor> v = new Vector<>();
			int size = c.carm_inv.size();
			
			//Removes all armor and puts them in a temporary vector
			for(int i = 0; (i < size) && (i < c.carm_inv.size()); i++){
				if(c.carm_inv.get(i).slot == 2) //legs
					v.add(c.carm_inv.remove(i));
			}
			
			String oldequip = c.carmor_list.get(2).name;
			String newequip = v.get(selected).name;
			
			//remove weapon to be equipped from temp vector, and add temp vector back into inventory
			v.remove(selected);
			v.add(Game.combatdriver.d.getarmor(oldequip));
			
			//remove "none equipped" from inv
			for(int i =0; i<=v.size()-1; i++)
			{
				if (v.get(i).name == "l_none")
					v.remove(i);
			}
			
			c.carm_inv.addAll(v);
			
			//Equip new weapon
			equip_armor(el, c, Game.combatdriver.d.getarmor(newequip));
			return;
		}
		
		if(equippane.hands.isSelected()){
			Vector<armor> v = new Vector<>();
			int size = c.carm_inv.size();
			
			//Removes all armor and puts them in a temporary vector
			for(int i = 0; (i < size) && (i < c.carm_inv.size()); i++){
				if(c.carm_inv.get(i).slot == 3) //hands
					v.add(c.carm_inv.remove(i));
			}
			
			String oldequip = c.carmor_list.get(3).name;
			String newequip = v.get(selected).name;
			
			//remove weapon to be equipped from temp vector, and add temp vector back into inventory
			v.remove(selected);
			v.add(Game.combatdriver.d.getarmor(oldequip));
			
			//remove "none equipped" from inv
			for(int i =0; i<=v.size()-1; i++)
			{
				if (v.get(i).name == "g_none")
					v.remove(i);
			}
		
			c.carm_inv.addAll(v);
			
			//Equip new weapon
			equip_armor(el, c, Game.combatdriver.d.getarmor(newequip));
			return;
		}
		
		if(equippane.feet.isSelected()){
			Vector<armor> v = new Vector<>();
			int size = c.carm_inv.size();
			
			//Removes all armor and puts them in a temporary vector
			for(int i = 0; (i < size) && (i < c.carm_inv.size()); i++){
				if(c.carm_inv.get(i).slot == 4) //feet
					v.add(c.carm_inv.remove(i));
			}
			
			String oldequip = c.carmor_list.get(4).name;
			String newequip = v.get(selected).name;
			
			//remove weapon to be equipped from temp vector, and add temp vector back into inventory
			v.remove(selected);
			v.add(Game.combatdriver.d.getarmor(oldequip));
			
			//remove "none equipped" from inv
			for(int i =0; i<=v.size()-1; i++)
			{
				if (v.get(i).name == "b_none")
					v.remove(i);
			}
			
			
			c.carm_inv.addAll(v);
			
			//Equip new armor
			equip_armor(el, c, Game.combatdriver.d.getarmor(newequip));
			return;
		}
		
		

		
		
	}
	
	
	public character get_char(character_list cl, int a)
	{
		character c = null;
		if(a<cl.char_list.size()-1)
		{
		 c = cl.char_list.get(a);
		}
	
		return c;
		
	}
	
	public String[] inv_mainhand(character c)
	{
		
		int v = 0;
		Vector<String> m = new Vector<String>();
		m.add(c.cwep_list.get(0).name);
		for(int i = 0; i<=c.cwep_inv.size()-1; i++)
		{
			if(c.cwep_inv.get(i).slot == 0)
			m.add(c.cwep_inv.get(i).name);
		}
		
		v = m.size();
		
		for(int i =0; i<=10-v-1; i++)
		{
			m.add("none");
		}
		
		//System.out.println("Size" + m.size());
		
		String[] s = new String[m.size()];
		
		for(int i = 0; i<=m.size()-1; i++)
		{
			s[i] = m.get(i);
		}
		
		
		
		return s;
		
	}
	
	public String[] inv_offhand(character c)
	{
		int v = 0;
		Vector<String> m = new Vector<String>();
		if(c.cwep_list.get(1).name == "o_unarmed") //nothing in offhand wep
		{
		m.add(c.carmor_list.get(5).name); //add the off hand shield
		}
		else
		m.add(c.cwep_list.get(1).name);	//add the off hand weapon
		
		
		for(int i = 0; i<=c.cwep_inv.size()-1; i++)
		{
			if(c.cwep_inv.get(i).slot == 1)
			m.add(c.cwep_inv.get(i).name);
		}
		
		for(int i = 0; i<=c.carm_inv.size()-1; i++)
		{
			if(c.carm_inv.get(i).slot == 5)
			m.add(c.carm_inv.get(i).name);
		}
		v = m.size();
		
		for(int i =0; i<=10-v-1; i++)
		{
			m.add("none");
		}
		
		String[] s = new String[m.size()];
		
		//System.out.println("size" + m.size());
		
		for(int i = 0; i<=m.size()-1; i++)
		{
			s[i] = m.get(i);
		}
		
	
		return s;
		
	}
	
	public String[] inv_neck(character c)
	{
		int v = 0;
		Vector<String> m = new Vector<String>();
		m.add(c.cequip_list.get(0).name);
		for(int i = 0; i<=c.cequ_inv.size()-1; i++)
		{
			if(c.cequ_inv.get(i).slot == 0)
			m.add(c.cequ_inv.get(i).name);
		}
		
		v = m.size();

		for(int i =0; i<=10-v-1; i++)
		{
			m.add("none");
		}
		
		String[] s = new String[m.size()];
		
		for(int i = 0; i<=m.size()-1; i++)
		{
			s[i] = m.get(i);
		}
		
		
		
		return s;
		
	}
	
	public String[] inv_ring1(character c)
	{
		int v = 0;
		Vector<String> m = new Vector<String>();
		m.add(c.cequip_list.get(1).name);
		
		for(int i = 0; i<=c.cequ_inv.size()-1; i++)
		{
			if(c.cequ_inv.get(i).slot == 1)
			m.add(c.cequ_inv.get(i).name);
		}
		
		v = m.size();

		for(int i =0; i<=10-v-1; i++)
		{
			m.add("none");
		}
		
		String[] s = new String[m.size()];
		
		for(int i = 0; i<=m.size()-1; i++)
		{
			s[i] = m.get(i);
		}
		
		
		
		return s;
		
	}
	
	public String[] inv_ring2(character c)
	{
		int v = 0;
		Vector<String> m = new Vector<String>();
		m.add(c.cequip_list.get(2).name);
		
		for(int i = 0; i<=c.cequ_inv.size()-1; i++)
		{
			if(c.cequ_inv.get(i).slot == 1)
			m.add(c.cequ_inv.get(i).name);
		}
		
		v = m.size();

		for(int i =0; i<=10-v-1; i++)
		{
			m.add("none");
		}
		
		String[] s = new String[m.size()];
		
		for(int i = 0; i<=m.size()-1; i++)
		{
			s[i] = m.get(i);
		}
		
		
		return s;
		
	}
	
	public String[] inv_head(character c)
	{
		int v = 0;
		Vector<String> m = new Vector<String>();
		m.add(c.carmor_list.get(0).name);
		for(int i = 0; i<=c.carm_inv.size()-1; i++)
		{
			if(c.carm_inv.get(i).slot == 0)
			m.add(c.carm_inv.get(i).name);
		}
		
		v = m.size();

		for(int i =0; i<=10-v-1; i++)
		{
			m.add("none");
		}
		
		String[] s = new String[m.size()];
		
		for(int i = 0; i<=m.size()-1; i++)
		{
			s[i] = m.get(i);
		}
		
		
		
		return s;
		
	}
	
	public String[] inv_chest(character c)
	{
		int v = 0;
		Vector<String> m = new Vector<String>();
		m.add(c.carmor_list.get(1).name);
		for(int i = 0; i<=c.carm_inv.size()-1; i++)
		{
			if(c.carm_inv.get(i).slot == 1)
			m.add(c.carm_inv.get(i).name);
		}
		
		v = m.size();

		for(int i =0; i<=10-v-1; i++)
		{
			m.add("none");
		}
		
		String[] s = new String[m.size()];
		
		for(int i = 0; i<=m.size()-1; i++)
		{
			s[i] = m.get(i);
		}
		
		
		
		return s;
		
	}
	
	public String[] inv_legs(character c)
	{
		int v = 0;
		Vector<String> m = new Vector<String>();
		m.add(c.carmor_list.get(2).name);
		for(int i = 0; i<=c.carm_inv.size()-1; i++)
		{
			if(c.carm_inv.get(i).slot == 2)
			m.add(c.carm_inv.get(i).name);
		}
		
		v = m.size();

		for(int i =0; i<=10-v-1; i++)
		{
			m.add("none");
		}
		
		String[] s = new String[m.size()];
		
		for(int i = 0; i<=m.size()-1; i++)
		{
			s[i] = m.get(i);
		}
		
		
		
		return s;
		
	}
	
	public String[] inv_hands(character c)
	{
		int v = 0;
		Vector<String> m = new Vector<String>();
		m.add(c.carmor_list.get(3).name);
		for(int i = 0; i<=c.carm_inv.size()-1; i++)
		{
			if(c.carm_inv.get(i).slot == 3)
			m.add(c.carm_inv.get(i).name);
		}
		
		v = m.size();

		for(int i =0; i<=10-v-1; i++)
		{
			m.add("none");
		}
		
		String[] s = new String[m.size()];
		
		for(int i = 0; i<=m.size()-1; i++)
		{
			s[i] = m.get(i);
		}
		
		
		
		return s;
		
	}
	
	public String[] inv_boots(character c)
	{
		int v = 0;
		Vector<String> m = new Vector<String>();
		m.add(c.carmor_list.get(4).name);
		for(int i = 0; i<=c.carm_inv.size()-1; i++)
		{
			if(c.carm_inv.get(i).slot == 4)
			m.add(c.carm_inv.get(i).name);
		}
		
		v = m.size();

		for(int i =0; i<=10-v-1; i++)
		{
			m.add("none");
		}
		
		String[] s = new String[m.size()];
		
		for(int i = 0; i<=m.size()-1; i++)
		{
			s[i] = m.get(i);
		}
		
		
		
		return s;
		
	}
	
	public void turn_manager(effect_list el, spell_list sl, character_list cl2, init_manager im2)
	{
		//int turn_count = 1;
		Vector<character> c_list_temp = new Vector<>();
		//boolean incombat = true;
		
		for(int i=0; i<=cl2.char_list.size()-1; i++) //make temps of each character (for effects)
		{
			character a = cl2.char_list.get(i).clone_char(cl2.char_list.get(i)); 
			stat_eff_update(a);
			c_list_temp.add(a);
		}
		
		im2.gen_inits(cl2);
		
		/*
		while(incombat)
		{
			
			System.out.println("Turn Number:" + turn_count);
		
		for(int i=0; i<=im2.init_list.size()-1; i++) //each turn
		{
			System.out.println(c_list_temp.get(im2.init_list.get(i).cl_id).name + "'s turn");
			//up keep 
			upkeep(c_list_temp.get(im2.init_list.get(i).cl_id));
			
			//action queue	
			phys_attack(c_list_temp.get(im2.init_list.get(i).cl_id), c_list_temp.get(0), 0);
			
			
			//down keep
			downkeep(c_list_temp.get(im2.init_list.get(i).cl_id));
		}
		
		
		spell_att(el, sl, c_list_temp.get(0), c_list_temp.get(1), 0); 
		System.out.println("sam's hp " + c_list_temp.get(1).hp);
		spell_att(el, sl, c_list_temp.get(0), c_list_temp.get(1), 1); 
		System.out.println("sam's hp " + c_list_temp.get(1).hp);
		spell_att(el, sl, c_list_temp.get(0), c_list_temp.get(1), 2); 
		System.out.println("sam's hp " + c_list_temp.get(1).hp);
		spell_att(el, sl, c_list_temp.get(0), c_list_temp.get(1), 3); 
		System.out.println("sam's hp " + c_list_temp.get(1).hp);
		
		turn_count++;
		
		//--check if end of combat (all non faction == 0 (enemies) hps are below 0)
		for(int i=0; i<=im2.init_list.size()-1; i++)
		{
			
		if(c_list_temp.get(im2.init_list.get(i).cl_id).faction != 0 && c_list_temp.get(im2.init_list.get(i).cl_id).hp <= 0)
		incombat = false;
	
		}
		
		incombat = false;
		
		} //while
		
		*/
	} //turn manager
	
	public int rolld6s(int n)
	{
		Random r = new Random();
		int t = 0;
		for(int i = 0; i <=n-1; i++)
		{
		t+=r.nextInt(6)+1;
		}
		return t;
	}
	
	
	public void upkeep(character c)
	{
		//System.out.println(c.name + "'s upkeep");
		
		//------------regen----------------------------------
	//	System.out.println(c.name + " regened " + c.hp_regen);
		c.movespeed =6; //reset move
		
		
		c.hp = c.hp + c.hp_regen;
		if(c.hp > c.max_hp)
			c.hp = c.max_hp;
			
		c.mana = c.mana + c.mana_regen;
		if(c.mana > c.max_mana)
			c.mana = c.max_mana;
	
	}
	
	public void downkeep(character c)
	{
		/*for(int i = 0; i <= c.ceff_list.size()-1; i++)
		{
			System.out.println(c.ceff_list.get(i).name);
		}
		*/
		
		System.out.println(c.name + "'s downkeep");
		Random r = new Random();
		//apply dots-------------------------------
		boolean saved = false;
		for(int i = 0; i <= c.ceff_list.size()-1; i++)
		{
			//grit save
			if(c.ceff_list.get(i).save_type == 0 || c.ceff_list.get(i).save_type == 5 || c.ceff_list.get(i).save_type == 6)
			{
				if((stat_mod(c.con) + r.nextInt(10)+1) > c.ceff_list.get(i).save_value)
						saved = true;
			}
				
			//will save
			if(c.ceff_list.get(i).save_type == 1 || c.ceff_list.get(i).save_type == 5 || c.ceff_list.get(i).save_type == 7)
			{
				if((stat_mod(c.fai) + r.nextInt(10)+1) > c.ceff_list.get(i).save_value)
						saved = true;
			}
			//dodge save
			if(c.ceff_list.get(i).save_type == 2 || c.ceff_list.get(i).save_type == 6 || c.ceff_list.get(i).save_type == 7)
			{
				if((stat_mod(c.agi) + r.nextInt(10)+1) > c.ceff_list.get(i).save_value)
							saved = true;
			}				
					
			if(!saved && c.ceff_list.get(i).dur >= 0 )	//if active
			{
				if(c.ceff_list.get(i).save_type != 3)
				System.out.println("dot damage: " + c.ceff_list.get(i).dot);
				
				c.hp = c.hp - c.ceff_list.get(i).dot;
			}
				
			if(c.ceff_list.get(i).dur < 0) 
				c.ceff_list.get(i).dur++;
			
			if(c.ceff_list.get(i).dur >= 1) 
			{
				if(c.ceff_list.get(i).dur == 1) 
					c.ceff_list.remove(i); //delete as its duration has expired
				else
					c.ceff_list.get(i).dur = c.ceff_list.get(i).dur - 1;
			}
			
			
			
			
		} //for loop
		
		
	}
	
	public void spell_att(effect_list el, spell_list sl, character at, character dt, int spell_id)
	{
		int spell_type = sl.s_list.get(spell_id).range_type;
		spell s = new spell();
		
		Random r = new Random();
		
		switch (spell_type){
		
		case 0: //single target damage
			
				if(spell_id == 3)//magic missile
				{

				s = sl.gen_spell(el, spell_id, at.get_spell_rank(spell_id));
				
				int dam = rolld6s(s.p_damage);
				
				//System.out.println("magic missile stats: p_damage: " + s.p_damage + "and rolled damage: " + dam);
				
				dt.hp = dt.hp - dam;
				
				//System.out.println("magic missile " + dam);
				clog = clog + "\nmagic missile " + dam +'\n';
				clog = clog + dt.name + "'s hp: " + dt.hp;
				at.mana = at.mana - s.mana_c;
				
				}
				
				//-------------------------------------------------------------------
				
				if(spell_id == 2)//healing light
				{

				s = sl.gen_spell(el, spell_id, at.get_spell_rank(spell_id));
				
				int dam = rolld6s(-s.p_damage);
				
				//System.out.println("healing light stats: p_damage: " + s.p_damage + "and rolled damage: " + dam);
				
				dt.hp = dt.hp + dam;
			
				
				//System.out.println("healing light: " + dam);
				clog = clog + "\nhealing light: " + dam +'\n';
				clog = clog + dt.name + "'s hp: " + dt.hp;
				
				at.mana = at.mana - s.mana_c;
				
				}
				
			break;
			
		case 1: 
			
			break;
			
		case 2: 
			
		if(spell_id == 0)//fireball
			{
			String h = "hit";
			s = sl.gen_spell(el, spell_id, at.get_spell_rank(spell_id));
			
			//get primary target
			character pt = dt;
			
			
			int dm = stat_mod(pt.agi);
			int am = stat_mod(at.intel);
			int roll = r.nextInt(20)+1;
			int roll2 = r.nextInt(20)+1;
			int dm2 = stat_mod(pt.fai);
			int dam = 0;
			
			//System.out.println("fire dam" + dam);
			
			if(roll==20 || dt.md + (dm-am) + roll*2 >= 40 ) //dodged
			{
				h="dodged";
			}
			
			
			else if (roll2 == 20 || dt.md + (dm2-am) + roll2*2 >= 40) //resisted
			{
				h="resisted";
			}
			
			else
			{
				dam = rolld6s(s.p_damage);
				
				if((stat_mod(dt.con) + r.nextInt(10)+1) > el.gen_effect(0, at.get_spell_rank(spell_id)).save_value)
				dt.ceff_list.add(el.gen_effect(0, at.get_spell_rank(spell_id))); //add burn
				
				//System.out.println("fire dam 1" + dam);
				
				pt.hp = pt.hp - dam;
				clog = clog +'\n' + dt.name + "'s hp: " + dt.hp;
				
			}

			//System.out.println(h+ " fireball " + dam);
			clog = clog + '\n' + h + " fireball " + dam;
			at.mana = at.mana - s.mana_c;
			

			
			}
			
		if(spell_id == 1)//fireburst
		{
		String h = "hit";
		s = sl.gen_spell(el, spell_id, at.get_spell_rank(spell_id));
		
		//get primary target
		character pt = dt;
		
		
		int dm = stat_mod(pt.agi);
		int am = stat_mod(at.intel);
		int roll = r.nextInt(20)+1;
		int roll2 = r.nextInt(20)+1;
		int dm2 = stat_mod(pt.fai);
		int dam = 0;
		
		//System.out.println("fire dam" + dam);
		
		if(roll==20 || dt.md + (dm-am) + roll*2 >= 40 ) //dodged
		{
			h="dodged";
		}
		
		
		else if (roll2 == 20 || dt.md + (dm2-am) + roll2*2 >= 40) //resisted
		{
			h="resisted";
		}
		
		else
		{
			dam = rolld6s(s.p_damage);
			
			if((stat_mod(dt.con) + r.nextInt(10)+1) > el.gen_effect(0, at.get_spell_rank(spell_id)).save_value)
			dt.ceff_list.add(el.gen_effect(0, at.get_spell_rank(spell_id))); //add burn
			
			//System.out.println("fire dam 1" + dam);
			
			pt.hp = pt.hp - dam;
			clog = clog  +'\n'+ dt.name + "'s hp: " + dt.hp;
			
		}

		//System.out.println(h+ " fireburst " + dam);
		clog = clog + '\n' + h+ " fireburst " + dam;
		at.mana = at.mana - s.mana_c;
		

		
		}
		
		
			
			break;
		
		}
	}
	
	
	
	public String inv_out(character c) //i is index in character list
	{
		
		String total = " ";
		
		total = "GOLD:";
		
		
		total = total + "  " + c.gold + "\n";
		
		
		total += "\nARMOR:\n";
		
		for(int i =0; i<=c.carm_inv.size()-1; i++)
		{
			total = total + "    " + c.carm_inv.get(i).name + "\n";
		}
		
		total = total + "\nWEAPONS:\n";
		
		for(int i =0; i<=c.cwep_inv.size()-1; i++)
		{
			total = total + "    " + c.cwep_inv.get(i).name + "\n";
		}
		
		total = total + "\nEQUIPMENT:\n";
		
		for(int i =0; i<=c.cequ_inv.size()-1; i++)
		{
			total = total + "    " + c.cequ_inv.get(i).name + "\n";
		}
		
		
		
		
		return total;
		
	}
	
	public void phys_attack(character at, character dt, int cwep_slot)
	{
		
		character a = at.clone_char(at); //needs to be done at start of turn
		//stat_eff_update(a);
		
		character d = dt.clone_char(dt);
		//stat_eff_update(d);
		
		//System.out.println("Frodo's mana in combat " + a.mana);
		
		Random r = new Random();
		
		
		int att_b = get_att_bonus(a, a.cwep_list.get(cwep_slot).slot);
		int def_b = get_def_bonus(d, a.cwep_list.get(cwep_slot).slot);
		
		int att_roll = r.nextInt(20)+1;
		
		boolean hit = false;
		boolean crit = false;
		
		if(att_roll == 20) //guaranteed hit
		{
			hit = true;
			crit = true;
		}
		else if ((att_roll+att_b) >= def_b)
		{
			hit = true;
			if(att_roll >= 21-a.cwep_list.get(cwep_slot).crit_r) //crit chance
					{
						if ((att_roll+att_b) >= def_b) //confirmed crit
						{
							crit = true;
						}
					}
		}
		
		
		if(hit == true)
		{
			
			//System.out.println(a.name + " hit with roll of " + att_roll + " with base " + att_b);
			clog = clog + '\n' +  a.name + " hit with " + at.cwep_list.get(cwep_slot).name;
					//System.out.println(a.name + " hit with " + at.cwep_list.get(cwep_slot).name);
			if(crit == true)
			{
				System.out.println("critical hit");
			}
			
			
			int damage = phys_dam(a, cwep_slot, crit);
			//System.out.println("dam " + damage);
			clog = clog + "\ndam " + damage;
			dt.hp = dt.hp - damage;
			clog = clog +'\n' + dt.name + "'s hp: " + dt.hp;
			
			//System.out.println("defenders hp: " + dt.hp);
			
			
		}
		
		
		if(hit == false)
		{
			//System.out.println(d.name + " defended att roll of " + att_roll + " with base " + att_b + " with def of " + def_b);
			System.out.println(d.name + " defended " + a.name + "'s " + at.cwep_list.get(cwep_slot).name + " att");
			clog = clog + '\n' +  d.name + " defended " + a.name + "'s " + at.cwep_list.get(cwep_slot).name + " att";
		}
		
		
		
	}
	
	
	
	
	public void equip_weapon(effect_list ef, character c, weapon w)
	{
		//undo current effects
		c.cwep_list.set(w.slot, w);
		eff_update(ef,c);
		
	}
	
	public void equip_equipment(effect_list ef, character c, equipment e)
	{
		
		//ring case
		if(e.slot == 1)
		{
			if(c.cequip_list.get(1).name == "r1_none") //nothing in main ring
			{
				c.cequip_list.set(e.slot, e);
			}
			else if(c.cequip_list.get(1).name != "r1_none" &&c.cequip_list.get(2).name == "r2_none" )
			{
				//has a ring in first slot, not in second
				c.cequip_list.set(2, e);
			}
		}
		else //amulet
		c.cequip_list.set(e.slot, e);
		eff_update(ef,c);
		
	}
	
	public void equip_armor(effect_list ef, character c, armor a)
	{
		c.carmor_list.set(a.slot, a);
		eff_update(ef,c);
		
	}
	
	public int stat_mod(int i)
	{
		int temp = i;
		temp = (temp - 10)/5;
		
		return temp;
		
	}
	
	//------------------------------------------------------------------------------------
	
	public int phys_dam(character c, int ws, boolean cr)
	{
		Random r = new Random();
		int d = c.cwep_list.get(ws).dam; //initially the side of dice to be rolled for damage of weapon
		 
		//melee damage
		if(c.cwep_list.get(ws).type == 0 || c.cwep_list.get(ws).type == 1 || c.cwep_list.get(ws).type == 2)
		{
			int roll = r.nextInt(d)+1;
			//System.out.println("damage roll " + roll + " str bonus " + (c.str-10));
			d = stat_mod(c.str) + roll;
			
			//plus effect
			
			
		}
		else if(c.cwep_list.get(ws).type == 3 || c.cwep_list.get(ws).type == 4)
		{
			int roll = r.nextInt(d)+1;
			int md = (int) (0.25 * (stat_mod(c.str)+0.5));
			int rd = (int) (0.35 * stat_mod(c.dex) + 0.5);
			//System.out.println("damage roll " + roll + " " + md + " " + rd);
			d = md + rd + roll;
			//plus eff
		}
		
		if(cr == true)
		{
			//System.out.println("crit dam before " + d);
			d = (c.cwep_list.get(ws).crit_m)*d; //multiply by crit mult
			
		}
		
		
		return d;
	}
	
	//------------------------------------------------------------------------------------
	
	public int get_att_bonus(character c, int ws)
	{
		
		
		//melee attack, use str for bonus
		if(c.cwep_list.get(ws).type == 0 || c.cwep_list.get(ws).type == 1 || c.cwep_list.get(ws).type == 2)
		{
			int base_att = c.bab + stat_mod(c.str) + c.cwep_list.get(ws).att_b;
			
			for(int i=0; i<=c.ceff_list.size()-1; i=i+1) //apply effect bonuses
			{
				if(c.ceff_list.get(i).wepeff.get(1) > 0 && c.ceff_list.get(i).wepeff.get(0) == ws)
				{
					base_att = base_att + c.ceff_list.get(i).wepeff.get(1);
				}
			}
			
			return base_att;
		}
		 
		//ranged attack, use dex bonus
		else if(c.cwep_list.get(ws).type == 3 || c.cwep_list.get(ws).type == 4)
		{
			int base_att = c.bab + stat_mod(c.dex) + c.cwep_list.get(ws).att_b;
			
			for(int i=0; i<=c.ceff_list.size()-1; i=i+1) //apply effect bonuses
			{
				if(c.ceff_list.get(i).wepeff.get(1) > 0 && c.ceff_list.get(i).wepeff.get(0) == ws)
				{
					base_att = base_att + c.ceff_list.get(i).wepeff.get(1);
				}
			}
			
			return base_att;
		}
		
		return 0; //if no cases are met
	}
	
	//------------------------------------------------------------------------------------
	
	public int get_def_bonus(character c, int ws)
	{
		int base_def = c.ac;
		if(c.cwep_list.get(ws).type == 3 || c.cwep_list.get(ws).type == 4) //ranged attack
		{
		
			base_def = (int) (base_def + (0.5 * stat_mod(c.agi) + 0.5)); //bonus 50% agi on ranged attacks
			
		}
		
		for(int i=0; i<=5; i++)
		{
			base_def = base_def + c.carmor_list.get(i).ac_bonus;
		}
		
		base_def = base_def + stat_mod(c.agi); //dodge bonus
		
		for(int i=0; i<=c.ceff_list.size()-1; i=i+1) //apply effect bonuses
		{
			if(c.ceff_list.get(i).aceff.get(1) > 0 )
			{
				base_def = base_def + c.ceff_list.get(i).aceff.get(1);
			}
		}
		
		return base_def;
		
	}
	//------------------------------------------------------------------------------------
	
	public void eff_update(effect_list el, character c) //add effects to character effect list form all weps, equipment etc
	{
		character temp = c.clone_char(c);
		
		Vector<effect> teff_list = new Vector<>(); 
		
		for(int i = 0; i<=c.ceff_list.size()-1; i++) //store non-equipment/wep/armor effects
		{
			if(c.ceff_list.get(i).dur != 0) //outside effects
			{
				teff_list.add(c.ceff_list.get(i));
			}
			
		}
		
		temp.ceff_list.clear();
		
		for(int x=0; x<=temp.carmor_list.size()-1; x++) //armor
		{
			for(int i=0; i<=2; i++) //can have max of 3 effects on each armor
			{
				if(temp.carmor_list.get(x).eff[i][0] != 0)
				{
	temp.ceff_list.add(el.gen_effect(temp.carmor_list.get(x).eff[i][0], temp.carmor_list.get(x).eff[i][1]));
				}
			}
				
		}
		
		for(int x=0; x<=temp.cwep_list.size()-1; x++) //weapons
		{
			for(int i=0; i<=2; i++) //can have max of 3 effects on each weapon
			{
				if(temp.cwep_list.get(x).eff[i][0] != 0)
				{
	temp.ceff_list.add(el.gen_effect(temp.cwep_list.get(x).eff[i][0], temp.cwep_list.get(x).eff[i][1]));
				}
			}
				
		}
		
		for(int x=0; x<=temp.cequip_list.size()-1; x++) //equipment
		{
			for(int i=0; i<=2; i++) //can have max of 3 effects on each equipment
			{
				if(temp.cequip_list.get(x).eff[i][0] != 0)
				{
	temp.ceff_list.add(el.gen_effect(temp.cequip_list.get(x).eff[i][0], temp.cequip_list.get(x).eff[i][1]));
				}
			}
				
		}
		
		c.ceff_list = temp.ceff_list;
		
		for(int i = 0; i<=teff_list.size()-1; i++) //add in non-equip/wep/armor effects
		{
			c.ceff_list.add(teff_list.get(i)); 
		}
		
		teff_list.clear();
		
		
	}
	
	//------------------------------------------------------------------------------------
	public void stat_eff_update(character c)
	{
		
		for(int i=0; i<=c.ceff_list.size()-1; i=i+1) //apply effect bonuses
		{
			//str con dex int fai agi
			
				if(c.ceff_list.get(i).stateff.get(0) > 0) //str
				{
				   c.str = c.str + c.ceff_list.get(i).stateff.get(0);
				}
				
				if(c.ceff_list.get(i).stateff.get(1) > 0) //con
				{
				   c.con = c.con + c.ceff_list.get(i).stateff.get(1);
				}
				
				if(c.ceff_list.get(i).stateff.get(2) > 0) //dex
				{
				   c.dex = c.dex + c.ceff_list.get(i).stateff.get(2);
				}
				
				if(c.ceff_list.get(i).stateff.get(3) > 0) //intel
				{
				   c.intel = c.intel + c.ceff_list.get(i).stateff.get(3);
				}
				
				if(c.ceff_list.get(i).stateff.get(4) > 0) //fai
				{
				   c.fai = c.fai + c.ceff_list.get(i).stateff.get(4);
				}
				
				if(c.ceff_list.get(i).stateff.get(5) > 0) //agi
				{
				   c.agi = c.agi + c.ceff_list.get(i).stateff.get(5);
				}
				
				if(c.ceff_list.get(i).othereff.get(0) > 0) //move speed
				{
				   c.movespeed = c.movespeed + c.ceff_list.get(i).othereff.get(0);
				}
				
				if(c.ceff_list.get(i).othereff.get(1) > 0) //init
				{
				   c.init = c.init + c.ceff_list.get(i).othereff.get(1);
				}
				
				if(c.ceff_list.get(i).othereff.get(2) > 0) //max mana
				{
				   c.max_mana = c.max_mana + c.ceff_list.get(i).othereff.get(2);
				}
				
				if(c.ceff_list.get(i).othereff.get(3) > 0) //current mana
				{
				   c.mana = c.mana + c.ceff_list.get(i).othereff.get(3);
				}
				
				if(c.ceff_list.get(i).othereff.get(4) > 0) //mana regen
				{
				   c.mana_regen = c.mana_regen + c.ceff_list.get(i).othereff.get(4);
				}
				
				if(c.ceff_list.get(i).othereff.get(5) > 0) //max hp
				{
				   c.max_hp = c.max_hp + c.ceff_list.get(i).othereff.get(5);
				   
				}
				
				if(c.ceff_list.get(i).othereff.get(6) > 0) //hp bonus
				{
				   c.hp = c.hp + c.ceff_list.get(i).othereff.get(6);
				}
				
				if(c.ceff_list.get(i).othereff.get(7) > 0) //hp regen
				{
				   c.hp_regen = c.hp_regen + c.ceff_list.get(i).othereff.get(7);
				}
				
				
			
		}
		
	}
	
	
}
