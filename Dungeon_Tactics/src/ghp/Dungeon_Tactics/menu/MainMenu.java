package ghp.Dungeon_Tactics.menu;


import ghp.Dungeon_Tactics.main.Game;
import ghp.Dungeon_Tactics.main.gfx.ImageLoader;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class MainMenu {
	static ImageLoader loader = new ImageLoader();
	private static JPanel panel;
	private static JFrame frame;
	private static JButton playButton;
	private static JButton settingsButton;
	private static JButton quitButton;
	private static JLabel title;
	private static ImageIcon playButtonIcon = new ImageIcon(loader.load("/playButton.png"));
	private static ImageIcon settingButtonIcon = new ImageIcon(loader.load("/settingButton.png"));
	private static ImageIcon quitButtonIcon = new ImageIcon(loader.load("/quitButton.png"));
	private static ImageIcon titleImage = new ImageIcon(loader.load("/titleImage.png"));
		
	
	public MainMenu(){
		gui();
		// When the settings is created it will add itself to the master frame and set its own visibility
		// to false.
	}
	public static JFrame getFrame(){
		return frame;
	}
	public static void panelHandOff (JPanel one, JPanel two){
		one.setVisible(false);
		two.setVisible(true);
	}
	public static JPanel getMainMenuPanel (){
		return panel;
	}
	public void gui(){
		frame = new JFrame("Dungeon Tactics");
		frame.setVisible(true);
		frame.setSize(Game.WIDTH + Game.MENUW,Game.HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		
		
		// Manipulate visablity to compelte menu
		// Write function to turn pixel based movement to grid.
		
		
		panel= new JPanel(new GridBagLayout());
		panel.setBackground(Color.BLACK);
		

		
		// Add components to this panel 
		playButton = new JButton();
		playButton.setIcon(playButtonIcon);
		playButton.setBorder(null);
		
		settingsButton = new JButton();
		settingsButton.setIcon(settingButtonIcon);
		settingsButton.setBorder(null);
		
		quitButton = new JButton();
		quitButton.setIcon(quitButtonIcon);
		quitButton.setBorder(null);
		
		title = new JLabel();
		title.setIcon(titleImage);
		
		// constraints are essentially rules that the components must follow while in our frame.
		GridBagConstraints constraints = new GridBagConstraints();
		
		// Play button is defined below. The constraint gridy is what allows the buttons to be stacked
		// the insets function is what gives the buttons their spacining.
		constraints.insets = new Insets(10,10,10,10);
		constraints.gridx = 0;
		constraints.gridy = 1;
		panel.add(playButton,constraints);
		
		playButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//panel.setVisible(false);
				Game.startGame();
			}
		});
		
		//Settings button is defined below.
		constraints.gridx = 0;
		constraints.gridy = 2;
		panel.add(settingsButton, constraints);
		settingsButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				SettingsMenu settings = new SettingsMenu();
				panel.setVisible(false);
			}
			
		});
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		panel.add(quitButton,constraints);
		quitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		title.setForeground(Color.WHITE);
		Font myFont = new Font("Serif", Font.BOLD, (Game.WIDTH) / Game.TILESIZE + 50);
		title.setFont(myFont);
		panel.add(title);
		
		frame.add(panel);
	}
}