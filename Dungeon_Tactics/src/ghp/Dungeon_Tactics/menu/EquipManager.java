package ghp.Dungeon_Tactics.menu;

import ghp.Dungeon_Tactics.main.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EquipManager implements ActionListener{

	public EquipPane equippane;
	
	
	public void setMainHand(){
		
		equippane.combobox.removeAllItems();
		equippane.string = Game.combatdriver.u.inv_mainhand(Game.combatdriver.cl.char_list.get(equippane.currentchar));
		
		for(int i = 0; i < 10 ; i++)
			equippane.combobox.addItem(equippane.string[i]);
	}
	
	public void setOffHand(){
			
		equippane.combobox.removeAllItems();
		equippane.string = Game.combatdriver.u.inv_offhand(Game.combatdriver.cl.char_list.get(equippane.currentchar));
			
			for(int i = 0; i < 10; i++)
				equippane.combobox.addItem(equippane.string[i]);
	}
	
	public void setNeck(){
		
		equippane.combobox.removeAllItems();
		equippane.string = Game.combatdriver.u.inv_neck(Game.combatdriver.cl.char_list.get(equippane.currentchar));
		
		for(int i = 0; i < 10 ; i++)
			equippane.combobox.addItem(equippane.string[i]);
	}
	
	public void setFinger1(){
		
		equippane.combobox.removeAllItems();
		equippane.string = Game.combatdriver.u.inv_ring1(Game.combatdriver.cl.char_list.get(equippane.currentchar));
		
		for(int i = 0; i < 10 ; i++)
			equippane.combobox.addItem(equippane.string[i]);
	}
	
	public void setFinger2(){
		
		equippane.combobox.removeAllItems();
		equippane.string = Game.combatdriver.u.inv_ring2(Game.combatdriver.cl.char_list.get(equippane.currentchar));
		
		for(int i = 0; i < 10 ; i++)
			equippane.combobox.addItem(equippane.string[i]);
	}

	public void setHead(){
			
		equippane.combobox.removeAllItems();
		equippane.string = Game.combatdriver.u.inv_head(Game.combatdriver.cl.char_list.get(equippane.currentchar));
			
			for(int i = 0; i < 10 ; i++)
				equippane.combobox.addItem(equippane.string[i]);
		}
	
	public void setChest(){
			
		equippane.combobox.removeAllItems();
		equippane.string = Game.combatdriver.u.inv_chest(Game.combatdriver.cl.char_list.get(equippane.currentchar));
			
			for(int i = 0; i < 10 ; i++)
				equippane.combobox.addItem(equippane.string[i]);
		}

	public void setLegs(){
			
		equippane.combobox.removeAllItems();
		equippane.string = Game.combatdriver.u.inv_legs(Game.combatdriver.cl.char_list.get(equippane.currentchar));
			
			for(int i = 0; i < 10 ; i++)
				equippane.combobox.addItem(equippane.string[i]);
		}

	public void setHands(){
			
		equippane.combobox.removeAllItems();
		equippane.string = Game.combatdriver.u.inv_hands(Game.combatdriver.cl.char_list.get(equippane.currentchar));
			
			for(int i = 0; i < 10 ; i++)
				equippane.combobox.addItem(equippane.string[i]);
		}

	public void setFeet(){
			
		equippane.combobox.removeAllItems();
		equippane.string = Game.combatdriver.u.inv_boots(Game.combatdriver.cl.char_list.get(equippane.currentchar));
			
			for(int i = 0; i < 10 ; i++)
				equippane.combobox.addItem(equippane.string[i]);
		}
	
	public EquipManager(EquipPane equippane){
		
		this.equippane = equippane;
	}
	
	public void update(){
		if(equippane.mainhand.isSelected()){
			setMainHand();
			return;
		}
			
		if(equippane.offhand.isSelected()){
			setOffHand();
			return;
		}
		
		if(equippane.neck.isSelected()){
			setNeck();
			return;
		}
		
		if(equippane.finger1.isSelected()){
			setFinger1();
			return;
		}
		
		if(equippane.finger2.isSelected()){
			setFinger2();
			return;
		}
		
		if(equippane.head.isSelected()){
			setHead();
			return;
		}
		
		if(equippane.chest.isSelected()){
			setChest();
			return;
		}
		
		if(equippane.legs.isSelected()){
			setLegs();
			return;
		}
		
		if(equippane.hands.isSelected()){
			setHands();
			return;
		}
		
		if(equippane.feet.isSelected()){
			setFeet();
			return;
		}
	}
	
	public void actionPerformed(ActionEvent event) {
			
		int action = Integer.valueOf(event.getActionCommand());
		
		switch(action){
			
		case 1:
			
				update();
				break;
			
		case 2:
	
				if(equippane.combobox.getSelectedIndex() > 0 && equippane.combobox.getSelectedItem() != "none"){
					Game.combatdriver.u.menu_equip(equippane, Game.combatdriver.cl);
					update();
				}
				if(equippane.combobox.getSelectedItem() == "none")
					update();
				break;	
		}
	}

}
