package ghp.Dungeon_Tactics.combat;




import java.util.Vector;

import ghp.Dungeon_Tactics.grid.Grid_Driver;
import ghp.Dungeon_Tactics.main.Game;
import ghp.Dungeon_Tactics.menu.CombatMenu;

import ghp.Dungeon_Tactics.main.levels.Level;

import ghp.Dungeon_Tactics.spells.spell_list;

//import java.util.Vector;

public class CombatDriver {
	
	public effect_list el;
	public database d;
	public spell_list sl;
	public character_list cl;
	public init_manager im;
	public c_util u;

	public static Grid_Driver gd;
	public int cnum;
	public Vector<Integer> spelltargets;
	


	

	
		public CombatDriver()
		{
			//-------initialization-------------------
			gd = new Grid_Driver();
			
			el = new effect_list(); //generates templates as well
			
			d = new database();
			d.generate(); //makes database of all weapons, equipment, armors
			
			sl = new spell_list(); //generates templates as well
			
			cl = new character_list();
			cl.gen_dummies(d, sl, el); //makes 4 characters to test with
			im = new init_manager();
			im.gen_inits(cl); //generate initiative and put them in order
			u = new c_util(); //holds many used functions by other all classes
			
			cnum = 0;
			spelltargets = new Vector<Integer>();
			
			
			//u.eff_update(el, cl.char_list.get(0));
			
			
			//System.out.println("Dungeon Tactics Initialized");
			//----------------------------------------
			
			
			for(int i=0; i<=im.init_list.size()-1; i++)
			{
			System.out.println("init list values " + im.init_list.get(i).cl_id);
			}
			
			
			
			/*
			System.out.println("First character " + cl.char_list.get(0).name);
			System.out.println("His weapon " + cl.char_list.get(0).cwep_list.get(0).name);
			System.out.println("His chest armor " + cl.char_list.get(0).carmor_list.get(1).name);
			System.out.println("His amulet " + cl.char_list.get(0).cequip_list.get(0).name);
			
			System.out.println("Next character " + cl.char_list.get(1).name);
			System.out.println("His ring " + cl.char_list.get(1).cequip_list.get(1).name);
			*/
			
			//cl.char_list.get(0).str = 10;
			//cl.char_list.get(0).dex = 10;
		
			
			//u.phys_attack(cl.char_list.get(0), cl.char_list.get(1), 0); //fight player 0 with player 1 with main hand
			//System.out.println("number of effects on first player " + cl.char_list.get(0).ceff_list.size());
			
			
			
			u.equip_equipment(el, cl.char_list.get(0), d.getequip("power amulet"));
			u.equip_weapon(el, cl.char_list.get(0), d.getweapon("short bow"));
			u.equip_armor(el, cl.char_list.get(0), d.getarmor("mana boots"));
			u.equip_armor(el, cl.char_list.get(1), d.getarmor("iron full plate"));
			
			//System.out.println("Frodo's mana " + cl.char_list.get(0).mana);
			
			
			 
			
			u.turn_manager(el, sl, cl, im);
			
			
			/*
			String[] s = u.inv_offhand(cl.char_list.get(0));
			for(int i = 0; i<=9; i++)
			{
				System.out.println(s[i]);
			}
			*/
			
			/*for(int i=0; i<=8; i++)
			{
			u.phys_attack(cl.char_list.get(0), cl.char_list.get(1), 0);
			}
			*/
			
			//System.out.println("Sam's Hp " + cl.char_list.get(1).hp);
			
			
	    }

		
		public void aicontrol(int cn)
		{
			//while(gd.G.ListTargets(cl.char_list.get(cn).xy, 1).size() == 1 && cl.char_list.get(cn).movespeed > 0) //no one in melee range
			//{
				//move closer
			//}
			
			if(gd.G.ListTargets(cl.char_list.get(cn).xy, 1).size() > 1) //someone in melee range
			{
				u.phys_attack(cl.char_list.get(cn), cl.getc(gd.G.ListTargets(cl.char_list.get(cn).xy, 1).get(1)), 0);
			}
			
			
		}

		public static Grid_Driver getGridDriver(){
			return gd;
		}


		public Vector<String> getActions(int charnum){
			return u.actions(spelltargets, el, sl, cl.char_list.get(charnum), cl, gd.G);
		}
		
		public Vector<String> getTargets(int charnum, int spellnum){
			int range;
			if(spellnum ==0)
			range = Game.combatdriver.cl.char_list.get(charnum).cwep_inv.get(0).range;
			else
			{
				range = Game.combatdriver.sl.gen_spell(el, Game.combatdriver.spelltargets.get(spellnum), Game.combatdriver.cl.char_list.get(charnum).cspells_rank.get(charnum)).range;
			}
			
			return Game.combatdriver.gd.G.ListTargets(Game.combatdriver.cl.char_list.get(charnum).xy, range);
		}
		
		public int nextTurn(CombatMenu c){
			
			
			
			if(cnum == 3){
				cnum++;
				u.upkeep(cl.char_list.get(cnum));
				c.log.setText(c.log.getText() + "\nIt is now "+ cl.char_list.get(cnum).name +"'s turn.");
				aicontrol(cnum);
				c.log.setText(c.log.getText() + u.clog);
				u.clog = "";
				u.downkeep(cl.char_list.get(cnum));
				
				cnum++;
				u.upkeep(cl.char_list.get(cnum));
				
				c.log.setText(c.log.getText() + "\nIt is now "+ cl.char_list.get(cnum).name +"'s turn.");
				aicontrol(cnum);
				c.log.setText(c.log.getText() + u.clog);
				u.downkeep(cl.char_list.get(cnum));
				
				cnum = 0;
				u.upkeep(cl.char_list.get(cnum));
				u.clog = "";
				c.log.setText(c.log.getText() + "\nIt is now "+ cl.char_list.get(cnum).name +"'s turn.");
				c.healthtext.setText(u.disp(cl));
				return cnum;
			}
			else{
				
				cnum++;
				u.upkeep(cl.char_list.get(cnum));
				if(cnum != 4)
					c.log.setText(c.log.getText() + "\nIt is now "+ cl.char_list.get(cnum).name +"'s turn.");
				return cnum;
			}
		}
}
