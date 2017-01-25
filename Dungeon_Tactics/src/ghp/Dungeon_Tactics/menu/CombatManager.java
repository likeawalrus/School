package ghp.Dungeon_Tactics.menu;

import ghp.Dungeon_Tactics.main.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class CombatManager implements ActionListener{
	
	private int xstore, ystore, gridstore = 0;
	public CombatMenu combatmenu;
	public CombatManager(CombatMenu combatmenu){
		this.combatmenu = combatmenu;
	}
	
	public void actionPerformed(ActionEvent event) {
		
		int action = Integer.valueOf(event.getActionCommand());
		 
		 switch(action){
		 
		 //move button pressed
		 case 1:
			 //get ready to accept arrow keys to move
			 combatmenu.actpane.setVisible(false);
			 combatmenu.choicepane.setVisible(false);
			 combatmenu.movepane.setVisible(true);
			 combatmenu.game.requestFocus();
 			 xstore = Game.getPlayer().getXo();
			 ystore = Game.getPlayer().getYo();
			 gridstore = Game.getCombat().getGridDriver().getGrid().FindChar("Frodo");
			 break;
		
		//act button pressed
		 case 2:
			 combatmenu.choicepane.setVisible(false);
			 combatmenu.movepane.setVisible(false);
			 combatmenu.actpane.setVisible(true);
			 break;
		
		//end button pressed
		 case 3:
			 Game.combatdriver.spelltargets.clear();
			 combatmenu.move.setVisible(true);
			 combatmenu.act.setVisible(true);
			
			 Game.combatdriver.u.downkeep(Game.combatdriver.cl.char_list.get(combatmenu.charnum));
			 combatmenu.charnum = Game.combatdriver.nextTurn(combatmenu);
			 combatmenu.action.removeActionListener(combatmenu.combatmanager);
			 combatmenu.target.removeActionListener(combatmenu.combatmanager);
			 combatmenu.refresh();
			 //combatmenu.log.setText(combatmenu.log.getText() + "\nIt is now "+ Game.combatdriver.cl.char_list.get(combatmenu.charnum).name +"'s turn.");
			 
			 break;
			 
		//move confirm
		
		//move reset

		

		 case 4:
			 Game.getPlayer().setXoYoNorm(xstore, ystore);
			 int xy = Game.getCombat().getGridDriver().getGrid().FindChar("Frodo");			 			 
			 Game.getCombat().getGridDriver().getGrid().ClearTile(xy/100, xy%100);			 
			 Game.getCombat().getGridDriver().getGrid().SetHero(gridstore, "Frodo");
			 Game.getCombat().getGridDriver().getGrid().setcharfocus("Frodo");			 
			 break;
		
		 case 5:
			 break;
			 
		//cancel move
		 case 6:
			 //reset character to original position
			 //reset number of steps they have
			 combatmenu.actpane.setVisible(false);
			 combatmenu.choicepane.setVisible(true);
			 combatmenu.movepane.setVisible(false);
			 break;
			 
		//confirm action
		 case 7:
			 if(combatmenu.target.getSelectedItem() == null)
				 break;
			 if(combatmenu.action.getSelectedIndex() == 0)
				Game.combatdriver.u.phys_attack(Game.combatdriver.cl.char_list.get(combatmenu.charnum), Game.combatdriver.cl.getc((String) combatmenu.target.getSelectedItem()), 0);
	
				else
				Game.combatdriver.u.spell_att(Game.combatdriver.el, Game.combatdriver.sl, Game.combatdriver.cl.char_list.get(combatmenu.charnum), Game.combatdriver.cl.getc((String) combatmenu.target.getSelectedItem()), Game.combatdriver.sl.gets((String) combatmenu.action.getSelectedItem()));
			 
			 
			 combatmenu.act.setVisible(false);
			 combatmenu.actpane.setVisible(false);
			 combatmenu.choicepane.setVisible(true);
			 combatmenu.movepane.setVisible(false);
			 
			 combatmenu.healthtext.setText(Game.combatdriver.u.disp(Game.combatdriver.cl));
			 combatmenu.log.setText(combatmenu.log.getText() + Game.combatdriver.u.clog);
			 Game.combatdriver.u.clog = "";
			 break;
			 
		//cancel action
		 case 8:
			 combatmenu.actpane.setVisible(false);
			 combatmenu.choicepane.setVisible(true);
			 combatmenu.movepane.setVisible(false);
			 break;
			 
		//action combobox
		 case 9:
				 combatmenu.target.removeAllItems();
				 Vector<String> targets = Game.combatdriver.getTargets(combatmenu.charnum, combatmenu.action.getSelectedIndex());
				 for(int i = 0; i < targets.size(); i++)
					 combatmenu.target.addItem(targets.get(i));
			 
			 break;
			 
			//target combobox
		 case 10:
			 break;
		 }
	}

}
