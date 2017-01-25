package ghp.Dungeon_Tactics.combat;
import java.util.*;

public class effect_list {

	public Vector<effect> e_list = new Vector<>(); //list of all effect types
	
	 public effect_list()
	{
		gen_templates();
	}
	
	public void gen_templates() //create the list
	{
		// (id, name, type, save_type
		effect temp = new effect(0, "burn", "fire", 1 );
		e_list.add(temp);
		temp = new effect(1, "frozen", "cold", 1);
		e_list.add(temp);
		temp = new effect(2, "power", "buff", 3);
		e_list.add(temp);
		temp = new effect(3, "might", "buff", 3);
		e_list.add(temp);
		temp = new effect(4, "spiritual", "buff", 3);
		e_list.add(temp);
		
	}
	
	public effect gen_effect(int i, int lvl) //get custom effect based on action
	//i is effect id, lvl is level of effect
	{
		effect temp = e_list.get(i);
		
		switch (i){
		
		case 0: //apply burn  id: 0
			
				temp.dot = 2*lvl;
				temp.dur = lvl;
				temp.save_value = 5 + lvl;
				//add other effects
				
			
			break;
			
		case 1: //apply frozen	id: 1
		
			temp.dot = lvl;
			temp.dur = 2*lvl;
			temp.othereff.add(0,-1); //set movespeed to -1 meaning no move
			temp.save_value = 7 + lvl;
			//add more
			
			break;
			
		case 2: //power (buff weapon) id: 2
			
			temp.wepeff.add(0,0); //main haind
			temp.wepeff.add(1,1); //+1 att
			temp.save_value = 0;
			temp.dur = (int) 3+ (lvl/4);
			break;
			
		case 3: //might (buff physical stats) id: 3
			
			temp.stateff.add(0,2); //buff str by 2
			temp.stateff.add(1,2); //buff con by 2
			temp.stateff.add(2,2); //buff dex by 2
			temp.dur = (int) 3+ (lvl/4);
			
			temp.save_value = 0;
			
			break;
			
		case 4: //spiritual (buff mana) id: 4
			
			temp.othereff.add(2,100); //buff max mana by 100
			temp.othereff.add(3,100); //buff current mana by 100
			temp.othereff.add(4,10*lvl); //buff mana regen by 10*lv
			temp.dur = (int) 3+ (lvl/4);
			
			temp.save_value = 0;
			
			break;
			
			
			
		} //switch
		
		return temp;
	} //get_effect
} //effect_list class
