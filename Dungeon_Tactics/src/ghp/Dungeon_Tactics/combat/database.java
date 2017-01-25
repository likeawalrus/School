package ghp.Dungeon_Tactics.combat;

import java.util.Vector;

public class database {

	public Vector<equipment> equip_list = new Vector<>();
	public Vector<weapon> wep_list = new Vector<>();
	public Vector<armor> armor_list = new Vector<>();
	
	
	//-------------------------------------------------

	public int gettype(String n)
	{
		int a=0;
		for(int i=0; i<=equip_list.size()-1; i=i+1)
		{
			if(equip_list.get(i).name == n)
			{
				a=2;
				
			}
			
		}
		
		for(int i=0; i<=wep_list.size()-1; i=i+1)
		{
			if(wep_list.get(i).name == n)
			{
				a=1;
				
			}
			
		}
		
		for(int i=0; i<=armor_list.size()-1; i=i+1)
		{
			if(armor_list.get(i).name == n)
			{
				a=0;
				
			}
			
		}
		
		return a; //a==0 if armor, a==1 if weapon, a==2 equipment
	}
	
	
	public equipment getequip(String n) //
	{
		int a=0;
		for(int i=0; i<=equip_list.size()-1; i=i+1)
		{
			if(equip_list.get(i).name == n)
			{
				a=i;
				
			}
			
		}
		
		return equip_list.get(a); //will return if found 
								//otherwise returns none
		
	}
	
	public weapon getweapon(String n) //
	{
		int a=0;
		for(int i=0; i<=wep_list.size()-1; i=i+1)
		{
			if(wep_list.get(i).name == n)
			{
				a=i;
				
			}
			
		}
		
		return wep_list.get(a); //will return if found 
								//otherwise returns none
		
	}
	
	public armor getarmor(String n) //
	{
		int a=0;
		for(int i=0; i<=armor_list.size()-1; i=i+1)
		{
			if(armor_list.get(i).name == n)
			{
				a=i;
				
			}
			
		}
		
		return armor_list.get(a); //will return if found 
								//otherwise returns the 'none'
		
	}
	
	
	public void generate()
	{
		//none lists
		weapon temp1 = new weapon("m_unarmed", 1, 0, 1, 2, 1, 1, 2, 0);
		wep_list.add(temp1);
		temp1 = null;
		
		temp1 = new weapon("o_unarmed", 2, 1, 1, 2, 1, 1, 2, 0);
		wep_list.add(temp1);
		temp1 = null;
		//------------------------------------------
		
		armor temp3 = new armor("h_none", 100, 0, 0);
		armor_list.add(temp3);
		temp3 = null;
		
		temp3 = new armor("c_none", 101, 1, 0);
		armor_list.add(temp3);
		temp3 = null;
		
		temp3 = new armor("l_none", 102, 2, 0);
		armor_list.add(temp3);
		temp3 = null;
		
		temp3 = new armor("g_none", 103, 3, 0);
		armor_list.add(temp3);
		temp3 = null;
		
		temp3 = new armor("b_none", 104, 4, 0);
		armor_list.add(temp3);
		temp3 = null;
		
		temp3 = new armor("s_none", 105, 5, 0);
		armor_list.add(temp3);
		temp3 = null;
		
		//-------------------------------------------------
		equipment temp2 = new equipment("a_none", 200, 0);
		equip_list.add(temp2);
		temp2 = null;
		
		temp2 = new equipment("r1_none", 201, 1);
		equip_list.add(temp2);
		temp2 = null;
		
		temp2 = new equipment("r2_none", 202, 2);
		equip_list.add(temp2);
		temp2 = null;
		
		
		
		//-------------------------------------------
		//-----Weapons---------------------------
		//-------("name", id , slot, type, damage, range, crit_range, crit_mult, attack_bonus)
		
		//iron scimitar
		temp1 = new weapon("iron scimitar", 3, 0, 1, 6, 1, 1, 2, 1);
		wep_list.add(temp1);
		temp1 = null;
		
		//short bow
		temp1 = new weapon("short bow", 4, 0, 3, 6, 6, 1, 2, 4);
		wep_list.add(temp1);
		temp1 = null;
		
		temp1 = new weapon("offhand sword", 5, 1, 1, 4, 1, 1, 2, 1);
		wep_list.add(temp1);
		temp1 = null;
		
		//-------------------------------------------
		//-----Armor---------------------------
		// ---("name", id , slot, defense_bonus)
				
		//iron full plate
		temp3 = new armor("iron full plate", 105, 1, 3);
		armor_list.add(temp3);
		temp3 = null;
		
		//mana boots
		temp3 = new armor("mana boots", 106, 4, 0);
		temp3.eff[0][0] = 4; //spiritual effect
		temp3.eff[0][1] = 1; //lv 1
		armor_list.add(temp3);
		temp3 = null;
				
		temp3 = new armor("kite shield", 107, 5, 5);
		armor_list.add(temp3);
		temp3 = null;
		
		//---------------------------------------------
		//-------Equipment------------------------------
		//----(name, id, slot)
		
		
		//power amulet
		temp2 = new equipment("power amulet", 203, 0); 
		temp2.eff[0][0] = 2; //power effect
		temp2.eff[0][1] =1; //lv 1
		equip_list.add(temp2);
		temp2 = null;
		
		temp2 = new equipment("power ring", 203, 1); 
		temp2.eff[0][0] = 2; //power effect
		temp2.eff[0][1] =1; //lv 1
		equip_list.add(temp2);
		temp2 = null;
		
	}
	
}
