package ghp.Dungeon_Tactics.menu;

import ghp.Dungeon_Tactics.main.gfx.ImageLoader;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingsMenu {
	
	static ImageLoader loader = new ImageLoader();
	// Variables created.
	private JLabel settingTitleImage;
	private JButton backButton,button520,button720,button920;
	private static JPanel settingsPanel;
	
	// Icons Assigned
	private static ImageIcon res520On = new ImageIcon(loader.load("/resButton520On.png"));
	private static ImageIcon res520Off = new ImageIcon(loader.load("/resButton520Off.png"));
	private static ImageIcon res720On = new ImageIcon(loader.load("/resButton720On.png"));
	private static ImageIcon res720Off = new ImageIcon(loader.load("/resButton720Off.png"));
	private static ImageIcon res920On = new ImageIcon(loader.load("/resButton920On.png"));
	private static ImageIcon res920Off = new ImageIcon(loader.load("/resButton920Off.png"));
	private static ImageIcon backIcon = new ImageIcon(loader.load("/backButton.png"));
	private static ImageIcon settingsImage = new ImageIcon(loader.load("/settingsImage.png"));
	
	
	public SettingsMenu(){
		gui();
	}
	public static JPanel getSettingsPanel (){
		return settingsPanel;
	}
	private void gui() {
		
		settingsPanel= new JPanel(new GridBagLayout());
		settingsPanel.setBackground(Color.BLACK);
		
		// constraints are essentially rules that the components must follow while in our frame.
		GridBagConstraints constraints = new GridBagConstraints();
		
		// Play button is defined below. The constraint gridy is what allows the buttons to be stacked
		// the insets function is what gives the buttons their spacining.
		constraints.insets = new Insets(10,10,10,10);
		constraints.gridx = 0;
		constraints.gridy = 1;
		
		settingTitleImage = new  JLabel();
		settingTitleImage.setIcon(settingsImage);
		settingsPanel.add(settingTitleImage);
		
		button520 = new JButton();
		button520.setIcon(res520On);
		button520.setBorder(null);
		button520.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				button520.setIcon(res520On);
				button720.setIcon(res720Off);
				button920.setIcon(res920Off);
			}
		});
		constraints.gridx = 0;
		constraints.gridy = 2;
		settingsPanel.add(button520,constraints);
		
		button720 = new JButton();
		button720.setIcon(res720Off);
		button720.setBorder(null);
		button720.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				button720.setIcon(res720On);
				button520.setIcon(res520Off);
				button920.setIcon(res920Off);
				
			}
		});
		constraints.gridx = 0;
		constraints.gridy = 3;
		settingsPanel.add(button720,constraints);

		button920 = new JButton();
		button920.setIcon(res920Off);
		button920.setBorder(null);
		button920.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				button920.setIcon(res920On);
				button520.setIcon(res520Off);
				button720.setIcon(res720Off);
			}
		});
		constraints.gridx = 0;
		constraints.gridy = 4;
		settingsPanel.add(button920,constraints);
		
		backButton = new JButton();
		backButton.setIcon(backIcon);
		backButton.setBorder(null);
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenu.panelHandOff(settingsPanel, MainMenu.getMainMenuPanel());
			}
		});
		
		constraints.gridx = 0;
		constraints.gridy = 5;
		settingsPanel.add(backButton,constraints);
		
		settingsPanel.setVisible(true);
		MainMenu.getFrame().add(settingsPanel);
			
	}
}