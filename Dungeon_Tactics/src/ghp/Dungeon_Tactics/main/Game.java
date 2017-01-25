
package ghp.Dungeon_Tactics.main;
// Written By Wesley Leach on 9-16
import ghp.Dungeon_Tactics.combat.CombatDriver;
import ghp.Dungeon_Tactics.main.entities.Player;
import ghp.Dungeon_Tactics.main.gfx.ImageLoader;
import ghp.Dungeon_Tactics.main.gfx.ImageManager;
import ghp.Dungeon_Tactics.main.gfx.SpriteSheet;
import ghp.Dungeon_Tactics.main.levels.Level;
import ghp.Dungeon_Tactics.menu.CombatMenu;
import ghp.Dungeon_Tactics.menu.MainMenu;
import ghp.Dungeon_Tactics.menu.Menu;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;






import javax.swing.JFrame;



public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	// Public variable declariation area
	public boolean running = false;
	public static final int WIDTH = 400, HEIGHT = 400, SCALE = 1,TILESIZE = 32,SPRITE_BORDER =1, MENUW = 260;
	// A thread will help the game multitask to minimize lag. 
	// For more info : http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html
	public static CombatDriver combatdriver = new CombatDriver();
	public Thread gameThread;
	private BufferedImage spriteSheet;
	private  static Player player;
	private  static Level level;
	private static Menu menu = new Menu();
	private static CombatMenu combatmenu = new CombatMenu();
	
	
	
	
	private static ImageManager imgMan;
	
	// This will initialize everything that needs to be set up
	// before the game begins.
	public void init(){
		ImageLoader loader = new ImageLoader();
		spriteSheet = loader.load("/32BitSpriteSheet.png");
		
		SpriteSheet spriteSheetHelper = new SpriteSheet(spriteSheet);
		
		imgMan = new ImageManager(spriteSheetHelper);
		
		player = new Player(WIDTH  * SCALE / 2 -10, HEIGHT * SCALE / 2 -40,imgMan);
		
		// Testing
		//BufferedImage limage = loader.load("/level1.png");
		level = new Level();
		//combatdriver = new CombatDriver();
		//menu = new Menu();
		
		
		// This adds an event listener that will wait and "listen" for our keyboard input.
		this.addKeyListener(new KeyManager());
		
	}
	
	
	// Synchronized key word helps to stop thread interference and memory inconsistencies. Since the thread allows for multi-
	// tasking sometimes the memory can be given inconsistent results. Using a synchronized method helps eliminate those
	//problems. For more info go here : http://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
	public synchronized void start(){
		//if the game is already running this conditional will escape this function.
		if (running) return;
		running = true;
		// The This keyword points to the current object that is being handled. I don't know what the current object is here.
		// I think it is passing the name game thread that is being created into the default constructor. Someone feel free,
		// to do a write up here. Also screw multi-line comments. I COMMIT TO NOTHING.
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	public synchronized void stop(){
		if(!running)return;
		// OK so the gameThread.join joins a bunch of threads. More importantly it waits for each thread to end and then joins them.
		// The reason you have to surround it in a try / catch is because it can throw an error sometimes. If you guys want to start
		// an error logging system just change the information after the TODO block.
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// Updates all the game components( Updates all the variables, x and y cords, etc)
	// This also serves to balance the game by limiting the number of updates per minute regardless of users pc clock speed.
	public void tick(){
		player.tick();
		
	}
	
	// Draws elements on to the screen.
	public void render(){
		// BufferStragety is basically how many buffers do we want?
		// This is this instance is referring to this class.
		BufferStrategy bufferStrat = this.getBufferStrategy();
		
		if( bufferStrat == null){
			//Creates our buffer strategy with three buffers if we don't already have a strategy implemented.
			// Note, probably a bad idea to go over 5 buffers.
			createBufferStrategy(3);
			return;
		}
		
		//This object will allow us to paint other objects onto the screen.
		Graphics paintBrush = bufferStrat.getDrawGraphics();
		
		// Code after this point will be painted onto the screen
		paintBrush.fillRect( 0,0, WIDTH * SCALE , HEIGHT * SCALE);
		
		level.render(paintBrush);
		player.render(paintBrush);
		combatdriver.gd.G.drawchars(paintBrush, imgMan);
		
		
		// Painting to screen ends here.
		
		//Cleans up after the paint brush.
		paintBrush.dispose();
		bufferStrat.show();
		
	}
	
	
	// Every runnable program needs a public run function, because this is what will be running at start.
	// This is where our game-loop is located.
	public void run(){
		init();
		long lastTime = System.nanoTime(); // Gets current time in nano seconds
		final double amountOfTicks = 60D; // D verifies that it is a double.
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		
		while(running){
			long now = System.nanoTime(); // Gets current time in nano seconds.
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1 ){
				tick();
				delta--;
			}
			render();
		}
		stop();
		
	}
	
	public static void main (String[] args){
		MainMenu mainMenu = new MainMenu();
	}
	
	// Get methods for other classes
	public static Level getLevel(){
		return level;
	}
	
	public static Player getPlayer(){
		return player;
	}
	public static CombatDriver getCombat(){
		return combatdriver;
	}

	
	public static ImageManager getImageManager(){
		return imgMan;
	}
	

	public static void startGame() {
		// TODO Auto-generated method stub
		Game game = new Game();
		game.setFocusable(true);
		Game.combatmenu.game = game;
		
		//game.init();
		// Sets game Dimensions
		game.setPreferredSize(new Dimension(WIDTH * SCALE,HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE,HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE,HEIGHT * SCALE));
		
		// WINDOW FOR THE GAME TO RUN IN BELOW
		
		// Establishes a frame or window for our game to run in.
		//Sets the size of our game windows
		//MainMenu.getFrame().setSize(WIDTH * SCALE + MENUW + 10, HEIGHT *SCALE);
		// Sets the behavior of the X button on the top right.
		MainMenu.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//disallows resizeable drag function in OS.
		MainMenu.getFrame().setResizable(false);
		MainMenu.getMainMenuPanel().setVisible(false);
		//add these 2 windows to the frame
		MainMenu.getFrame().setLayout(new FlowLayout(FlowLayout.LEFT));
		MainMenu.getFrame().add(game);
		MainMenu.getFrame().add(Game.combatmenu);
		MainMenu.getFrame().pack();
		MainMenu.getFrame().setVisible(true);
		
		// Starts the game
		game.start();
		
	}
	
	
	
}