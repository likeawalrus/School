package ghp.Dungeon_Tactics.menu;



import ghp.Dungeon_Tactics.main.Game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;






import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Menu extends JPanel{
	/*
	 * Isiah Hernandez
	 * Menu is the Over-Arching JPanel that will be added to the Games Display Frame
	 * This class handles only the formatting of the Tab select and Character Select
	 * Button Interactions are handled by MenuManager class
	 */
	private static final long serialVersionUID = 1L;
	public JPanel  selectpane, charpane, scrollpane;
	public JTextArea text;
	public JScrollPane scroll;
	public Font menufont, charfont;
	public JRadioButton statb, inventoryb, equipb, char1, char2, char3, char4;
	public ButtonGroup group1, group2;
	public MenuManager menumanager;
	public StatPane statpane;
	public InventoryPane inventorypane;
	public GridBagConstraints gbc;
	public EquipPane equippane;
	public static Color COLOR = new Color(241, 211, 154);
	
	
	//Creates the 3 main Select buttons --> Consider making own class
	public void createSelectPane(){
		
		//Button Formatting-----------------------------------------------
		statb = new JRadioButton("Stats", true);
		inventoryb = new JRadioButton("Inventory");
		equipb = new JRadioButton("Equipped");
		
		statb.setBackground(COLOR);
		inventoryb.setBackground(COLOR);
		equipb.setBackground(COLOR);
		
		statb.setFont(menufont);
		inventoryb.setFont(menufont);
		equipb.setFont(menufont);
		
		group1 = new ButtonGroup();
		group1.add(equipb);
		group1.add(inventoryb);
		group1.add(statb);
		//----------------------------------------------------------------
		
		//Button Actions are handled by MenuManager Class
		statb.addActionListener(menumanager);
		inventoryb.addActionListener(menumanager);
		equipb.addActionListener(menumanager);
		
		//selectpane is what is added to Menu Panel.
		//will consider making new class
		selectpane = new JPanel();
		selectpane.setBackground(COLOR);
		selectpane.add(statb);
		selectpane.add(inventoryb);
		selectpane.add(equipb);
	}

	//Creates the 4 Character Select Buttons --> Consider making own class
	public void createCharPane(){
		
		//Get character names from main CombatDriver
		char1 = new JRadioButton(Game.combatdriver.cl.char_list.get(0).name, true);
		char2 = new JRadioButton(Game.combatdriver.cl.char_list.get(1).name);
		char3 = new JRadioButton(Game.combatdriver.cl.char_list.get(2).name);
		char4 = new JRadioButton(Game.combatdriver.cl.char_list.get(3).name);
		
		//Button Formatting-----------------------------------------------
		char1.setBackground(COLOR);
		char2.setBackground(COLOR);
		char3.setBackground(COLOR);
		char4.setBackground(COLOR);
		
		char1.setFont(charfont);
		char2.setFont(charfont);
		char3.setFont(charfont);
		char4.setFont(charfont);		
		
		group2 = new ButtonGroup();
		group2.add(char1);
		group2.add(char2);
		group2.add(char3);
		group2.add(char4);
		//-----------------------------------------------------------------
		
		//Button Actions are handled by MenuManager Class
		char1.addActionListener(menumanager);
		char2.addActionListener(menumanager);
		char3.addActionListener(menumanager);
		char4.addActionListener(menumanager);
		
		//charpane is what is added to Menu Panel.
		//will consider making new class
		charpane = new JPanel(new GridLayout(2,2));
		charpane.setBorder(BorderFactory.createEmptyBorder(10,0,5,0));
		charpane.setBackground(COLOR);
		charpane.add(char1);
		charpane.add(char2);
		charpane.add(char3);
		charpane.add(char4);
	}
	
	//Constructor is only called once --> Serves as set up
	public Menu(){
		
		//Initializations and Formatting of Menu Panel-------------------------
		this.setPreferredSize(new Dimension(Game.MENUW, Game.HEIGHT));
		this.setBackground(COLOR);
		menufont = new Font("Tahoma", 0, 15);
		charfont = new Font("Tahoma", 0, 12);
		gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		gbc.anchor = GridBagConstraints.NORTH;
		menumanager = new MenuManager(this); //menumanager will handle the 7 main select buttons
		//---------------------------------------------------------------------
		
		
		//Creation of the 4 main panels--------------------------------------------
		createSelectPane();
		
		createCharPane();
		
		statpane = new StatPane();
		
		inventorypane = new InventoryPane();
		
		equippane = new EquipPane();
		//-------------------------------------------------------------------------	
		
		
		//Add panels to Menu, they will be aligned one on top of the other
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(selectpane, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(charpane, gbc);
		
		//statpane, inventorypane, and eventually equippane will all occupy the same area
		//only one will be visible at a time
		//visibility is handled by MenuManager class
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		this.add(statpane, gbc);
		this.add(inventorypane, gbc);
		this.add(equippane, gbc);
		inventorypane.setVisible(false);	//By default statpane is visible at start of game	
		equippane.setVisible(false);
	}

	//Getting panel to add to play field.
	public Component getPanel() {
		return statpane;
	}
}
