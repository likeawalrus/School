package ghp.Dungeon_Tactics.grid;


import ghp.Dungeon_Tactics.main.Game;
import ghp.Dungeon_Tactics.main.gfx.ImageManager;

import java.awt.Graphics;
import java.util.Vector;
// Class Grid is a virtual mapping for Dungeon Tactics
// GRID Axis goes from left to right, and top down
//   0 1 2 3 4
// 0 x x x x x
// 1 x x x x x 
// 2 x x x x x
// 3 x x x x x
// 4 x x x x x
public class Grid {
	// Grid dimensions with size x by y
	int global_x;
	int global_y;
	int focusxmod, focusymod;
	private ImageManager imgMan;
	
	// Generate Vector grid
	Tile T;
	Vector<Tile> yGrid;
	Vector< Vector<Tile> > xGrid = new Vector< Vector<Tile> >(global_x);
	// Sets the LocX and LocY to their corresponding locations
	
// -------------------- -------------------- Initializer -------------------- -------------------- 
	// Initializes Grid location values
	public void Initialize(int a, int b){
		System.out.println("Initialized Grid ");
		global_x = a;
		global_y = b;
		// Set all Grid locations with a Tile T
		for(int i = 0; i < a; i++){
			for(int j = 0; j < b; j++){
				T = new Tile();
				yGrid = new Vector<Tile>(global_y);
				
				xGrid.add(yGrid);
				xGrid.get(i).add(T);
				xGrid.get(i).get(j).LocX = i;
				xGrid.get(i).get(j).LocY = j;
			}
		}
	} // End of Initialize()
// -------------------- -------------------- Functions -------------------- --------------------

	// Marker functions used to mark flags TRUE or FALSE
	
	public void SetHero(int HeroPos, String Name){ // Sets the Hero name at tile x,y
		//System.out.println("Running MarkHero()...");
		int y = HeroPos % 100; // Decompress position into x and y variables
		int x = HeroPos-y;
		x = x/100;
		MarkHeroName(x, y, Name); // Sets the CharName = Name
		MarkOccupied(x, y, true); // Marks Occupied to true
		//System.out.println(xGrid.get(x).get(y).CharName + " is now at position " + x + "-" + y);
	}
	public void MarkHeroName(int x, int y, String Name){
		xGrid.get(x).get(y).CharName = Name; // Place character name
	}
	public void MarkOccupied(int x, int y, boolean key){ // 'key' is either TRUE/FALSE
		xGrid.get(x).get(y).Occupied = key; // Marks the flag TRUE/FALSE
	}
	public void MarkVisable(int x, int y, boolean key){ // 'key' is either TRUE/FALSE
		xGrid.get(x).get(y).Visable = key; // Marks the flag TRUE/FALSE
	}
	public void MarkPassable(int x, int y, boolean key){ // 'key' is either TRUE/FALSE
		xGrid.get(x).get(y).Passable = key; // Marks the flag TRUE/FALSE
	}
	public void MarkItem(int x, int y, boolean key){ // 'key' is either TRUE/FALSE
		xGrid.get(x).get(y).Item = key; // Marks the flag TRUE/FALSE
	}
	public void ClearTile(int x, int y){
		//System.out.println("Running ClearTile()...");
		xGrid.get(x).get(y).CharName = "NONE";
		xGrid.get(x).get(y).Occupied = false;
		xGrid.get(x).get(y).Visable = true;
		xGrid.get(x).get(y).Passable = true;
		xGrid.get(x).get(y).Item = false;
	}
	
	// Find location of Character
	// Returns the xy location using a 4digit number; e.g. x=3,y=4 => 0304
	public int FindChar(String Name){
		//System.out.println("Running FindChar()...");
		for(int i = 0; i < global_x; i++){
			for(int j = 0; j < global_y; j++){
				if(xGrid.get(i).get(j).CharName == Name){
					int x_temp = xGrid.get(i).get(j).LocX;
					int y_temp = xGrid.get(i).get(j).LocY;
					int xy = x_temp*100 + y_temp;
					//System.out.println(xGrid.get(i).get(j).CharName + " is at " + String.format("%04d", xy));
					return xy;
				}
			}
		}
		//System.out.println(Name + " not found.");
		return 0; // Deadcode
	} // End of FindChar()
	
	public void MakeBattlemap(){
		Game.getLevel().makeBattlemap();
	}
	public void RemoveBattlemap(){
		Game.getLevel().removebattlemap();
	}
	
	// Lists all targets within a specified range from some coordinate
	public Vector<String> ListTargets(int HeroPos, int range){
		
		Vector<String> s = new Vector<String>();
		int y = HeroPos%100; // Decompress position into x and y variables
		int x = HeroPos-y;
		x = x/100;
		//System.out.println("Running ListTargetsV2()..." + "at " + x + " and " + y);
		for(int i = 0; i < global_x; i++){
			for(int j = 0; j < global_y; j++){
				int AvailMove = range; // Track the # of moves to target
				int xMag = GetMagX(i, HeroPos);
				AvailMove = AvailMove - xMag;
				int yMag = GetMagY(j, HeroPos);
				AvailMove = AvailMove - yMag;
				if(AvailMove >= 0 && xGrid.get(i).get(j).Occupied == true){ // Check for object
					//System.out.println(xGrid.get(i).get(j).CharName); // Then print object name
					s.add(xGrid.get(i).get(j).CharName);
				}
			}
		}
		return s;
	} // End of ListTargets()
	
	// Lists all targets within a specified range from some coordinate
	public boolean InRange(int HeroPos, int TargetPos, int range){
		//System.out.println("Running InRange()...");
		int y2 = TargetPos%100; // Decompress position into x and y variables
		int x2 = TargetPos-y2;
		x2 = x2/100;
		int AvailMove = range; // Track the # of moves to target
		int xMag = GetMagX(x2, HeroPos);
		AvailMove = AvailMove - xMag;
		int yMag = GetMagY(y2, HeroPos);
		AvailMove = AvailMove - yMag;
		if(AvailMove >= 0){
			//System.out.println("Target is in range.");
			return true;
		}
		//System.out.println("Target is not ot in range.");
		return false;
	} // End of InRange()
		
	public int GetMagX(int x, int PosXY){ 
		int y1 = PosXY % 100; // Decompress Pos1;
		int x1 = PosXY-y1;
		x1 = x1/100;
		return Math.abs(x-x1);
	}
	public int GetMagY(int y, int PosXY){ 
		int y1 = PosXY % 100; // Decompress Pos1;
		return Math.abs(y-y1);
	}
	
	public void setcharfocus(String charname){
	int xy, y, x;
	xy = FindChar(charname);
	y = xy%100;
	x=xy/100;
	focusxmod = 190-(x*32);
	focusymod = 160-(y*32);
	}
	
	public void movechar(int xadd, int yadd, String charname){
		int x, y;
		int xy = FindChar(charname);
		y = xy%100;
		x = (xy/100);
		if(((x+(xadd)) >= 0 && (y + yadd) >= 0) && ((x+(xadd)) <= 19 && (y + yadd) <= 19)){
			if(!(xGrid.get(x+xadd).get(y+yadd).Occupied) ){
				System.out.println("movechar activated");
				ClearTile(x,y);		
				xy = xy+(xadd*100)+yadd;
				SetHero(xy, charname);
				setcharfocus("Frodo");
			}
		}
	}
	//returns false if occupied is false. Returns true if true.
	public Boolean collisionWithPlayer(int xadd, int yadd){
		int x, y;
		int xy = FindChar("Frodo");
		y = xy%100;
		x = (xy/100);
		System.out.println(x + " " + xadd + " " + y + " " + yadd);
		if(((x+(xadd)) < 0 || (y + yadd) < 0) || xGrid.get(x+xadd).get(y+yadd).Occupied || 
				((x+(xadd)) > 19 || (y + yadd) > 19)){
			System.out.println("True in collisionwithplayer");
			return true;
		}
		else{
			movechar(xadd, yadd, "Frodo");
			return false;
		}
	}
	
	
	public void drawchars(Graphics brush, ImageManager imgMan){
		this.imgMan=imgMan;		
		int xy, x, y;
		xy = FindChar("Skeleton1");
		y = (xy%100);
		x = (xy/100);
		y = (y)*32 + focusymod;
		x = (x)*32+focusxmod;
		brush.drawImage(imgMan.player, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE,null);
		xy = FindChar("Skeleton2");
		y = (xy%100);
		x = (xy/100);
		y = (y)*32 + focusymod;
		x = (x)*32+focusxmod;
		brush.drawImage(imgMan.player, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE,null);
		xy = FindChar("Frodo");
		y = (xy%100);
		x = (xy/100);
		y = (y)*32 + focusymod;
		x = (x)*32+focusxmod;
		brush.drawImage(imgMan.player, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE,null);
		xy = FindChar("Sam");
		y = (xy%100);
		x = (xy/100);
		y = (y)*32 + focusymod;
		x = (x)*32+focusxmod;
		brush.drawImage(imgMan.player, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE,null);
		xy = FindChar("Shelob");
		y = (xy%100);
		x = (xy/100);
		y = (y)*32 + focusymod;
		x = (x)*32+focusxmod;
		brush.drawImage(imgMan.player, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE,null);
		xy = FindChar("Golem");
		y = (xy%100);
		x = (xy/100);
		y = (y)*32 + focusymod;
		x = (x)*32+focusxmod;
		brush.drawImage(imgMan.player, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE,null);
	}
	
} // End of Grid Class
