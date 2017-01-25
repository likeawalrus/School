package ghp.Dungeon_Tactics.main.entities;
//Written By Wesley Leach on 9-16
import java.awt.Graphics;


import ghp.Dungeon_Tactics.main.Game;
import ghp.Dungeon_Tactics.main.gfx.ImageManager;


public class Player  {
	
	private int x,y ,xo,yo,xs,ys;
	private ImageManager imgMan;
	public static int HITPOINTS = 10;
	public static int BaseDamge = 10;	
	public boolean up = false, down = false, leftTurn = false, rightTurn = false;
	
	// Players movement allotment.
	private final int SPEED = 32;
	
	public Player (int x, int y, ImageManager imgMan){
		this.x=x;
		this.y=y;
		//the xo and yo will eventually be handled elsewhere
		xo=320-190;
		yo=320-160;
		xs=0;
		ys=0;
		this.imgMan=imgMan;
	}
	
	public void tick(){
		xs=0;
		ys=0;
		// Player movement is located here.
		if(up){
			ys -=SPEED;
			move (xs,ys);			
			up=false;
		}else if(down){
			ys +=SPEED;
			move (xs,ys);
			down=false;
		}
		if(leftTurn){
			xs -= SPEED;
			move (xs,ys);
			leftTurn=false;
		}else if(rightTurn){
			xs += SPEED;
			move (xs,ys);
			rightTurn=false;
		}
	}
	
	public void move( int xs, int ys){
		System.out.println(xs + " " + ys);
		if(Game.getCombat().getGridDriver().getGrid().collisionWithPlayer(xs/32, ys/32) == false){
	
		if(!collision(xs,0)){
			xo += xs;		
		}
		if(!collision (0,ys)){		
			yo +=ys;
		}
		checkDoor();
		}
	}
	
	// checks if a move is valid
	private boolean collision (int xs, int ys){
		//Check Center
		if(Game.getLevel().getTile((xo +xs +x + (Game.TILESIZE * Game.SCALE -1)/2) / (Game.TILESIZE * Game.SCALE) , (yo+ys + y + (Game.TILESIZE * Game.SCALE -1)/2) / (Game.TILESIZE * Game.SCALE)).isSolid()){
			//System.out.println("collision activated in player");
			return true;
	}
		//if(Game.getCombat().getGridDriver().getGrid().collisionWithPlayer(xs/32, ys/32)){
		//	return true;
		//}

		//check upper/left edges
		//lower/right edges handled in level
		else if(xo +xs +x < 0 || yo+ys + y < 0){
			//System.out.println("collision activated in player");
			return true;
			
		//check door	
		}		
		return false;
	}
	
	private void checkDoor(){
		if(Game.getLevel().getTile((xo  +x + (Game.TILESIZE * Game.SCALE -1)/2) / (Game.TILESIZE * Game.SCALE) , (yo + y + (Game.TILESIZE * Game.SCALE -1)/2) / (Game.TILESIZE * Game.SCALE)).isDoor()){
			int xp = (xo  +x + (Game.TILESIZE * Game.SCALE -1)/2) / (Game.TILESIZE * Game.SCALE);
			int yp = (yo + y + (Game.TILESIZE * Game.SCALE -1)/2) / (Game.TILESIZE * Game.SCALE);
			System.out.println(xp + " " + yp);
			Game.getLevel().newLoadLevel(Game.getLevel().getdoorName(xp, yp));
		}
	}

	
	public void render(Graphics brush){
		/* 
		 * This draws an image to the screen. The first parameter is the actual image that will be drawn
		 * So we crop out a piece of our sprite sheet to be displayed. The x and y coordinate of the image
		 * are the next parameters that we pass in (0,0) is at the top left. The next two parameters have to
		 * do with scaling. They help to ensure that the games scale is maintained. The last parameter is set to
		 * null because that is something called an observer...I have no idea what that is so I set it to null.
		 */
		brush.drawImage(imgMan.player, x , y , Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE,null);
	}
	
	// Get Methods
	public int getXo(){
		return xo;
	}
	public int getYo(){
		return yo;
	}
	public void setXoYo(int newxo, int newyo){
		xo = (newxo*32-222+32*1);
		yo = (newyo*32-192+32*1);
		//System.out.print(xo + " " + yo);		
	}

	public void setXoYoNorm(int newxo, int newyo){
		xo = (newxo);
		yo = (newyo);		
	}
}
