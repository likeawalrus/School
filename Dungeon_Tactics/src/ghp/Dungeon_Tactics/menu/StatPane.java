package ghp.Dungeon_Tactics.menu;

import ghp.Dungeon_Tactics.main.Game;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StatPane extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextField points;
	public StatField str, con, dex, intel, faith, agi;
	public JButton confirm;
	public Dimension filler;
	public int currentchar, attrpoints;
	
	//Changes which characters is displayed based on button press
	public void refresh(int charnum){
		currentchar = charnum;
		attrpoints = Game.combatdriver.cl.char_list.get(charnum).attributepoints;
		points.setText("Attribute Points to Spend: " + String.valueOf(attrpoints));
		str.refresh(Game.combatdriver.cl.char_list.get(charnum).str);
		con.refresh(Game.combatdriver.cl.char_list.get(charnum).con);
		dex.refresh(Game.combatdriver.cl.char_list.get(charnum).dex);
		intel.refresh(Game.combatdriver.cl.char_list.get(charnum).intel);
		faith.refresh(Game.combatdriver.cl.char_list.get(charnum).fai);
		agi.refresh(Game.combatdriver.cl.char_list.get(charnum).agi);
	}
	
	
	//When a minus is pressed updates the number of attribute points displayed
	//Returns true if it was successful, false if not
	public Boolean addAttributePoint(){
	
		int max = Game.combatdriver.cl.char_list.get(currentchar).attributepoints;
		
		if(attrpoints + 1 <= max){
			attrpoints++;
			points.setText("Attribute Points to Spend: " + String.valueOf(attrpoints));
			return true;
		}
		return false;
	}
	
	
	//When a plus is pressed updates the number of attribute points displayed
	//Returns true if it was successful, false if not
	public Boolean subAttributePoint(){
		
		if(attrpoints > 0){
			attrpoints--;
			points.setText("Attribute Points to Spend: " + String.valueOf(attrpoints));
			return true;
		}
		return false;
	}
	
	
	//When confirm button is pressed, database stats are matched to values shown on screen
	public void commit(){
		
		//Gotta update basevalues so StatField's knows the minimum stat values
		str.updateBaseValue();
		con.updateBaseValue();
		dex.updateBaseValue();
		intel.updateBaseValue();
		faith.updateBaseValue();
		agi.updateBaseValue();
		
		//Make it so database matches the values shown on screen
		Game.combatdriver.cl.char_list.get(currentchar).attributepoints = attrpoints;
		Game.combatdriver.cl.char_list.get(currentchar).str = str.basevalue;
		Game.combatdriver.cl.char_list.get(currentchar).con = con.basevalue;
		Game.combatdriver.cl.char_list.get(currentchar).dex = dex.basevalue;
		Game.combatdriver.cl.char_list.get(currentchar).intel = intel.basevalue;
		Game.combatdriver.cl.char_list.get(currentchar).fai = faith.basevalue;
		Game.combatdriver.cl.char_list.get(currentchar).agi = agi.basevalue;
	}
	
	
	//Constructor
	public StatPane(){
		//Initializations and Panel Formating-------------------------------------------------------
		filler = new Dimension(180,50);
		currentchar = 0;
		attrpoints = Game.combatdriver.cl.char_list.get(0).attributepoints;
		confirm = new JButton("Confirm ALL changes?");
		confirm.addActionListener(this);	//only the confirm button action is handled by this class
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Menu.COLOR);
		this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		//------------------------------------------------------------------------------------------
		
		
		//Create the StatFields for all the stats, defaults to first character----------------------
		//StatField button actions are handled by StatField class
		str = new StatField(this, "STRENGTH", Game.combatdriver.cl.char_list.get(0).str);
		con = new StatField(this, "CONSTITUTION", Game.combatdriver.cl.char_list.get(0).con);
		dex = new StatField(this, "DEXTERITY", Game.combatdriver.cl.char_list.get(0).dex);
		intel = new StatField(this, "INTELLIGENCE", Game.combatdriver.cl.char_list.get(0).intel);
		faith = new StatField(this, "FAITH", Game.combatdriver.cl.char_list.get(0).fai);
		agi = new StatField(this, "AGILITY", Game.combatdriver.cl.char_list.get(0).agi);
		//------------------------------------------------------------------------------------------
		
		
		//Creates a text box where the available attribute points are displayed---------------------
		points = new JTextField("Attribute Points to Spend: " + String.valueOf(attrpoints));
		points.setBorder(null);
		points.setBackground(Menu.COLOR);
		points.setHorizontalAlignment(JTextField.CENTER);
		points.setEditable(false);
		//------------------------------------------------------------------------------------------
		
		
		//Add all of our beautiful panels to StatPane-----------------------------------------------
		this.add(points);
		this.add(str);
		this.add(con);
		this.add(dex);
		this.add(intel);
		this.add(faith);
		this.add(agi);
		this.add(new Box.Filler(filler, filler, filler));
		this.add(confirm);
	}

	
	//When confirm button is pressed, changes are committed to database
	public void actionPerformed(ActionEvent event){
		commit();
	}
}
