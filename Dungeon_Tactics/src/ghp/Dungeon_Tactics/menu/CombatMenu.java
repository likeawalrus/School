package ghp.Dungeon_Tactics.menu;

import ghp.Dungeon_Tactics.main.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class CombatMenu extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GridBagConstraints gbc;
	public JButton move, act, end, mreset, mconfirm, mback, aconfirm, aback;
	public JPanel healthpane, combatlog, choicepane, movepane, actpane;
	public JTextArea healthtext, log;
	public JTextField steps;
	public JScrollPane scroll;
	public Dimension buttonsize;
	public CombatManager combatmanager;
	public JComboBox<String> action, target;
	public Color color = new Color(241, 211, 154);
	public Game game;
	public int charnum;
	
	public JPanel createHealthPane(){
		JPanel healthpane = new JPanel();
		healthpane.setLayout(new BoxLayout(healthpane, BoxLayout.Y_AXIS));
		healthpane.setBackground(color);
		healthpane.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		//healthpane.setPreferredSize(new Dimension(Game.MENUW, Game.HEIGHT/6));
		
		healthtext = new JTextArea();
		healthtext.setBackground(color);
		healthtext.setEditable(false);
		healthtext.setColumns(23);
		healthtext.setRows(4);
		healthtext.setText(Game.combatdriver.u.disp(Game.combatdriver.cl));
		healthpane.add(healthtext);
	
		return healthpane;
	}
	
	public JPanel createCombatLog(){
		
		
		JPanel combatlog = new JPanel();
		combatlog.setBackground(color);
		log = new JTextArea();
		log.setColumns(21);
		log.setEditable(false);
		log.setText("It is now "+ Game.combatdriver.cl.char_list.get(charnum).name +"'s turn.");
		scroll = new JScrollPane(log);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(250, 175));
		combatlog.add(scroll);
			
		return combatlog;
	}
	
	public JPanel createChoicePane(){
		
		JPanel choicepane = new JPanel(new GridLayout(3,1));
		choicepane.setBackground(color);
		
		move = new JButton("MOVE");
		act = new JButton("ACT");
		end = new JButton("END");
		
		move.addActionListener(combatmanager);
		act.addActionListener(combatmanager);
		end.addActionListener(combatmanager);
		
		move.setActionCommand("1");
		act.setActionCommand("2");
		end.setActionCommand("3");
		
		
		choicepane.add(move);
		choicepane.add(act);
		choicepane.add(end);
		choicepane.setPreferredSize(new Dimension(Game.MENUW, Game.HEIGHT / 3));
		return choicepane;
	}
	
	public JPanel createMovePane(){
		
		JPanel movepane = new JPanel(new GridLayout(2,2));
		movepane.setPreferredSize(new Dimension(Game.MENUW, Game.HEIGHT / 3));
		movepane.setBackground(color);
		steps = new JTextField("\n\nSquares Left: " + String.valueOf(Game.combatdriver.cl.char_list.get(0).movespeed));
		steps.setBackground(color);
		steps.setEditable(false);
		
		mreset = new JButton("Reset?");
		mconfirm = new JButton("Confirm?");
		mback = new JButton("< Back?");
	
		mreset.addActionListener(combatmanager);
		mconfirm.addActionListener(combatmanager);
		mback.addActionListener(combatmanager);
		
		mconfirm.setActionCommand("4");
		mreset.setActionCommand("5");
		mback.setActionCommand("6");
		
		movepane.add(mconfirm);
		movepane.add(mreset);
		movepane.add(mback);
		movepane.add(steps);
		
		return movepane;
	}

	public void refresh(){
		action.removeAllItems();
		for(int i = 0; i < Game.combatdriver.getActions(charnum).size(); i++)
			action.addItem(Game.combatdriver.getActions(charnum).get(i));
		
		target.removeAllItems();
		
		steps.setText("\n\nSquares Left: " + String.valueOf(Game.combatdriver.cl.char_list.get(charnum).movespeed));
		target.addActionListener(combatmanager);
		action.addActionListener(combatmanager);
		healthtext.setText(Game.combatdriver.u.disp(Game.combatdriver.cl));
	}
	
	public JPanel createActPane(){
		
		aconfirm = new JButton("CONFIRM");
		aback = new JButton("< BACK");
		aconfirm.addActionListener(combatmanager);
		aback.addActionListener(combatmanager);
		aconfirm.setActionCommand("7");
		aback.setActionCommand("8");
		
		JPanel actpane = new JPanel();
		actpane.setPreferredSize(new Dimension(Game.MENUW, Game.HEIGHT / 3));
		
		action = new JComboBox<String>();
		action.setMaximumRowCount(10);
		
		target = new JComboBox<String>();
		target.setMaximumRowCount(10);
		
		target.addActionListener(combatmanager);
		action.addActionListener(combatmanager);
		
		action.setActionCommand("9");
		target.setActionCommand("10");
		
		
		for(int i = 0; i < Game.combatdriver.getActions(charnum).size(); i++){
			action.addItem(Game.combatdriver.getActions(charnum).get(i));
		}
		
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(1, 2));
		pane.add(aback);
		pane.add(aconfirm);
		
		actpane.setLayout(new GridLayout(3, 1));
		actpane.add(action);
		actpane.add(target);
		actpane.add(pane);
		
		return actpane;
	}
	
	public CombatMenu(){
		
		charnum = 0;
		
		combatmanager = new CombatManager(this);
		this.setPreferredSize(new Dimension(Game.MENUW, Game.HEIGHT));
		this.setBackground(color);
		
		choicepane = createChoicePane();
		healthpane = createHealthPane();
		combatlog = createCombatLog();
		movepane = createMovePane();
		actpane = createActPane();
		
		gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		gbc.anchor = GridBagConstraints.NORTH;
		
		
		
		gbc.weighty = .5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(healthpane, gbc);
		
		gbc.weighty = .35;
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(choicepane, gbc);
		this.add(movepane, gbc);
		this.add(actpane, gbc);
		movepane.setVisible(false);
		actpane.setVisible(false);
		
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		this.add(combatlog, gbc);
	}
	
}
