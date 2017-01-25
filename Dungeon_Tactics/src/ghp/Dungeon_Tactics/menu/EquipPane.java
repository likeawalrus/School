package ghp.Dungeon_Tactics.menu;


import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class EquipPane extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Menu menu;
	public JPanel slotselectpane, boxpane;
	public JRadioButton mainhand, offhand, neck, finger1, finger2, head, chest, legs, hands, feet;
	public ButtonGroup group;
	public JComboBox<String> combobox;
	public int currentchar;
	public EquipManager equipmanager;
	String[] string = new String[10];
	
	

	
	
	public void createSlotSelectPane(){
		slotselectpane = new JPanel();
		
		mainhand = new JRadioButton("Main-Hand", true);
		offhand = new JRadioButton("Off-Hand");
		neck = new JRadioButton("Neck");
		finger1 = new JRadioButton("Finger 1");
		finger2 = new JRadioButton("Finger 2");
		head = new JRadioButton("Head");
		chest = new JRadioButton("Chest");
		legs = new JRadioButton("Legs");
		hands = new JRadioButton("Hands");
		feet = new JRadioButton("Feet");
		
		group = new ButtonGroup();
		group.add(mainhand);
		group.add(offhand);
		group.add(neck);
		group.add(finger1);
		group.add(finger2);
		group.add(head);
		group.add(chest);
		group.add(legs);
		group.add(hands);
		group.add(feet);
		
		mainhand.addActionListener(equipmanager);
		offhand.addActionListener(equipmanager);
		neck.addActionListener(equipmanager);
		finger1.addActionListener(equipmanager);
		finger2.addActionListener(equipmanager);
		head.addActionListener(equipmanager);
		chest.addActionListener(equipmanager);
		legs.addActionListener(equipmanager);
		hands.addActionListener(equipmanager);
		feet.addActionListener(equipmanager);
		
		mainhand.setActionCommand("1");
		offhand.setActionCommand("1");
		neck.setActionCommand("1");
		finger1.setActionCommand("1");
		finger2.setActionCommand("1");
		head.setActionCommand("1");
		chest.setActionCommand("1");
		legs.setActionCommand("1");
		hands.setActionCommand("1");
		feet.setActionCommand("1");
		
		
		slotselectpane.setLayout(new BoxLayout(slotselectpane, BoxLayout.Y_AXIS));
		
		slotselectpane.add(mainhand);
		slotselectpane.add(offhand);
		slotselectpane.add(neck);
		slotselectpane.add(finger1);
		slotselectpane.add(finger2);
		slotselectpane.add(head);
		slotselectpane.add(chest);
		slotselectpane.add(legs);
		slotselectpane.add(hands);
		slotselectpane.add(feet);
		
		
	}
	
	public void createBoxPane(){
	
		Dimension filler = new Dimension(150, 200);
		boxpane = new JPanel();
		combobox = new JComboBox<String>();
		combobox.setMaximumRowCount(10);
		combobox.addActionListener(equipmanager);
		combobox.setActionCommand("2");
		boxpane.setLayout(new BoxLayout(boxpane, BoxLayout.Y_AXIS));
		boxpane.add(combobox);
		boxpane.add(new Box.Filler(filler, filler, filler));
	}
	
	public void refresh(int charnum){
		mainhand.setSelected(true);
		currentchar = charnum;
		equipmanager.setMainHand();
	}
	
	public EquipPane(){
		
		equipmanager = new EquipManager(this);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		createSlotSelectPane();
		this.add(slotselectpane);
		createBoxPane();
		this.add(boxpane);
		
	}

}
