package ghp.Dungeon_Tactics.menu;

import java.awt.Dimension;

import ghp.Dungeon_Tactics.main.Game;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class InventoryPane extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextArea text;
	public JScrollPane scroll;
	
	//This function counts the number of lines in a string
	public int countLines(String str){
		   
		int lines = 1;
	    int pos = 0;
	    while ((pos = str.indexOf("\n", pos) + 1) != 0){
	        lines++;
	    }
	    return lines;
	}

	
	//Refreshes Scroll Box with current Characters Inventory
	public void refresh(int charnum){
		
		//Properly resizes Text area
		text.setRows(countLines(Game.combatdriver.u.inv_out(Game.combatdriver.cl.char_list.get(charnum))));
		
		//Display Inventory from main CombatDriver
		text.setText(Game.combatdriver.u.inv_out(Game.combatdriver.cl.char_list.get(charnum)));
		
		//Set to show top part of text by default
		text.setCaretPosition(0);
		
	}
	
	
	//Constructor: Formats the scroll box
	public InventoryPane(){
		text = new JTextArea();
		text.setColumns(21);
		text.setEditable(false);
		scroll = new JScrollPane(text);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(250, 290));
		this.add(scroll);
		this.setBackground(Menu.COLOR);
		
	}
}
