package ghp.Dungeon_Tactics.menu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StatField extends JPanel implements ActionListener{
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JButton addbutton, subbutton;
	public JTextField text, num;
	public StatPane statpane;
	public int basevalue;
	
	//Updates the display value to the passed-in integer
	public void refresh(int value){
		num.setText(String.valueOf(value));
	}
	
	//When changes are committed base stat value is updated
	//basevalue serves as a minimum value for a given stat
	public void updateBaseValue(){
		basevalue = Integer.valueOf(num.getText());
	}
	
	//Constructor
	public StatField(StatPane statpane, String stat, int basevalue){
		
		//Initializations and Panel Formatting----------------------
		this.statpane = statpane;
		this.basevalue = basevalue;
		this.setBackground(Menu.COLOR);
		Dimension buttonsize = new Dimension(41, 20);
		//----------------------------------------------------------
		
		
		//Create the 'plus' and 'minus' buttons---------------------
		addbutton = new JButton("+");
		addbutton.setPreferredSize(buttonsize);
		addbutton.addActionListener(this);
		addbutton.setActionCommand("1");

		subbutton = new JButton("-");
		subbutton.setPreferredSize(buttonsize);
		subbutton.addActionListener(this);
		subbutton.setActionCommand("2");
		//----------------------------------------------------------
		
		
		//Create the 2 text boxes-----------------------------------
		//Box for stat name
		text = new JTextField(10);
		text.setText(stat + ':');
		text.setEditable(false);
		text.setBackground(Menu.COLOR);
		text.setBorder(null);
		text.setHorizontalAlignment(JTextField.RIGHT);
		
		//Box for stat value
		num = new JTextField(3);
		num.setBorder(null);
		num.setHorizontalAlignment(JTextField.CENTER);
		num.setText(String.valueOf(basevalue));
		num.setEditable(false);
		//-----------------------------------------------------------
		
		
		//Add components to panel------------------------------------
		this.add(subbutton);
		this.add(addbutton);
		this.add(text);
		this.add(num);
	}

	//When a 'plus' or 'minus' is pressed
	public void actionPerformed(ActionEvent event){
		 int action = Integer.valueOf(event.getActionCommand());
		 
		 switch(action){	//Decides whether 'plus' or 'minus' was pressed
		 
		 //Plus------------------------------------------------------------------------------------
		 case 1:
			 //Can only add if there are available attribute points
			 if(statpane.subAttributePoint())	
				 refresh(Integer.valueOf(num.getText()) + 1);	//update display, but not database
			 		break;
			 		
			 		
		//Minus------------------------------------------------------------------------------------ 		
		 case 2: 
			 //Can only subtract if changes have been made previously
			 if(Integer.valueOf(num.getText()) > basevalue && statpane.addAttributePoint())
				 refresh(Integer.valueOf(num.getText()) - 1);	//update display, but not database
		       		break;
		 }
	}
	
}
