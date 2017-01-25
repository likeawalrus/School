package ghp.Dungeon_Tactics.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuManager implements ActionListener{
	public Menu menu;
	
	//Constructor
	public MenuManager(Menu menu){
		this.menu = menu;
	}
	
	//Changes 'tab' based on button change
	public void actionPerformed(ActionEvent event) {
		
		if (menu.statb.isSelected()){
		
			//Display correct pane for the selected button
			menu.statpane.setVisible(true);
			menu.inventorypane.setVisible(false);
			menu.equippane.setVisible(false);
			
			//Refresh Stat Pane based on which character is selected
			if (menu.char1.isSelected())
				menu.statpane.refresh(0);
			
			if (menu.char2.isSelected())
				menu.statpane.refresh(1);
		
			if (menu.char3.isSelected())
				menu.statpane.refresh(2);
			
			if (menu.char4.isSelected())
				menu.statpane.refresh(3);
		}
		
		
		
		if (menu.inventoryb.isSelected()){
			
			//Display correct pane for the selected button
			menu.statpane.setVisible(false);
			menu.inventorypane.setVisible(true);
			menu.equippane.setVisible(false);
			
			//Refresh Inventory Pane based on which character is selected
			if (menu.char1.isSelected())
				menu.inventorypane.refresh(0);
			
			if (menu.char2.isSelected())
				menu.inventorypane.refresh(1);
			
			if (menu.char3.isSelected())
				menu.inventorypane.refresh(2);
			
			if (menu.char4.isSelected())
				menu.inventorypane.refresh(3);
		}
		
		//Display correct pane for the selected button
		//Equip tab not yet implemented
		if(menu.equipb.isSelected()){
			menu.statpane.setVisible(false);
			menu.inventorypane.setVisible(false);
			menu.equippane.setVisible(true);
			
			if (menu.char1.isSelected())
				menu.equippane.refresh(0);
			
			if (menu.char2.isSelected())
				menu.equippane.refresh(1);
			
			if (menu.char3.isSelected())
				menu.equippane.refresh(2);
			
			if (menu.char4.isSelected())
				menu.equippane.refresh(3);
		}
	}

}
