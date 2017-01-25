package ghp.Dungeon_Tactics.main.levels;

import ghp.Dungeon_Tactics.main.Game;
import ghp.Dungeon_Tactics.main.tiles.BridgeLRTile;
import ghp.Dungeon_Tactics.main.tiles.BridgeUDTile;
import ghp.Dungeon_Tactics.main.tiles.DungeonBlackTile;
import ghp.Dungeon_Tactics.main.tiles.DungeonDirtFiveTile;
import ghp.Dungeon_Tactics.main.tiles.DungeonDirtFourTile;
import ghp.Dungeon_Tactics.main.tiles.DungeonDirtOneTile;
import ghp.Dungeon_Tactics.main.tiles.DungeonDirtSevenTile;
import ghp.Dungeon_Tactics.main.tiles.DungeonDirtSixTile;
import ghp.Dungeon_Tactics.main.tiles.DungeonDirtThreeTile;
import ghp.Dungeon_Tactics.main.tiles.DungeonDirtTwoTile;
import ghp.Dungeon_Tactics.main.tiles.DungeonWallFourTile;
import ghp.Dungeon_Tactics.main.tiles.DungeonWallOneTile;
import ghp.Dungeon_Tactics.main.tiles.DungeonWallThreeTile;
import ghp.Dungeon_Tactics.main.tiles.DungeonWallTwoTile;
import ghp.Dungeon_Tactics.main.tiles.MountainBLCTile;
import ghp.Dungeon_Tactics.main.tiles.MountainBRCTile;
import ghp.Dungeon_Tactics.main.tiles.MountainBottomTile;
import ghp.Dungeon_Tactics.main.tiles.MountainLeftTile;
import ghp.Dungeon_Tactics.main.tiles.MountainRightTile;
import ghp.Dungeon_Tactics.main.tiles.MountainTLCTile;
import ghp.Dungeon_Tactics.main.tiles.MountainTRCTile;
import ghp.Dungeon_Tactics.main.tiles.MountainTile;
import ghp.Dungeon_Tactics.main.tiles.MountainTopTile;
import ghp.Dungeon_Tactics.main.tiles.Tile;
import ghp.Dungeon_Tactics.main.tiles.WallTile;
import ghp.Dungeon_Tactics.main.levels.Grasslevel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.*;

public class Level {
	
	// We will load in images as map files. We will assign tiles by the RGB numbers converted to hex of our tiles
	// Grass is 0,255,0 for example hex 00FF00
	// Stone is 128,128,128      hex 808080
	// 127,116, 63    hex 7F743F
	
	public int[][] tiles;
	public int[][] temptiles;
	public int w, h, watersides, riverrand, maxsize, maxheight, playerx, playery;
	public int mapcounter = 1;
	public int mapnum = 0;
	public Boolean isgrass;
	public String mapname;
	private static Grasslevel grasslevel;
	private static Dungeonlevel dungeonlevel;
	List<String> doorlocx = new ArrayList<String>();
	List<String> doorlocy = new ArrayList<String>();
	List<String> doorlist = new ArrayList<String>();

	
	
	public Level(){
		grasslevel = new Grasslevel();
		dungeonlevel = new Dungeonlevel();
		//w = levelImage.getWidth();
		//h = levelImage.getHeight();
		 try{
			 String strDirectoy ="..\\maps";
			 // Create one directory
			 new File(strDirectoy).mkdir();
		 	}catch (Exception e){//Catch exception if any
		 		System.err.println("Error: " + e.getMessage());
			}
		 	//checks for a mapcounter
			File fmc = new File("..\\maps\\mapcounter.txt");
			if(fmc.exists() && !fmc.isDirectory()) {
			getmapcounter();
			}
			else{
				try {
				    BufferedWriter out = new BufferedWriter(new FileWriter("..\\maps\\mapcounter.txt"));
				    out.write(Integer.toString(1) + "\n");
				    out.close();
				} catch (IOException e) { }
			}
		
		File f = new File("..\\maps\\map0.txt");
		if(f.exists() && !f.isDirectory()) {
		newLoadLevel(0);
		}
		else{
			mapnum=0;
			newLevel(0);
		}
		makeBattlemap();
		
	}
	
	public void newLevel(int newmapnum){
		int oldmapnum = mapnum;
		if(newmapnum == 0){
			tiles = grasslevel.randLevel();	
		}
		else if(tiles[0][0] != 80 && tiles[49][49] != 80 && tiles[49][0] != 80 && tiles[0][49] != 80){
			tiles = dungeonlevel.randLevel();			
		}
		else{
			tiles = grasslevel.randLevel();
		}
		w = 50;
		h = 50;		
		mapnum = newmapnum;		
		setPlayerLocNew();
		int doornum=0;
		  for(int y = 0 ; y < h; y++){
			  for(int x = 0 ; x < w; x++){
				  if((tiles[x][y]== 57 || tiles[x][y]== 58 || tiles[x][y] == 81) && (playerx != x) && (playery != y)){
					  doorlocx.add(Integer.toString(x));
					  doorlocy.add(Integer.toString(y));
					  doorlist.add(Integer.toString(mapcounter+doornum));
					  doornum +=1;				
				  }
				  //this should always be true once, except in the case of map 0.
				  //And we don't want anyone travelling 'backwards' from map 0 anyways.
				  else if (tiles[x][y]== 57 || tiles[x][y]== 58 || tiles[x][y] == 81){
					  doorlocx.add(Integer.toString(x));
					  doorlocy.add(Integer.toString(y));
					  doorlist.add(Integer.toString(oldmapnum));
					  doornum +=1;
				  }
			  }
		  }
		  mapcounter = mapcounter+doornum+1;
		  setmapcounter();
		  saveLevel();
	}

	public void getmapcounter(){
		try{
		  FileInputStream fstream = new FileInputStream("..\\maps\\mapcounter.txt");
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  //Read File Line By Line
		  mapcounter = Integer.parseInt(br.readLine());
		  br.close();
		} catch (IOException e) { }
	}
	
	public void setmapcounter(){
		try{
		    BufferedWriter out = new BufferedWriter(new FileWriter("..\\maps\\mapcounter.txt", false));
		    //data to be saved would be the width
		    //and height of the map as well as any door properties
		    //for now
		    out.write(Integer.toString(mapcounter) + "\n");
		    out.close();
		    } catch (IOException e) { }
	}	
	
	public void setPlayerLocNew(){
		if(mapnum > 0){
			Random rand = new Random();
			int chance = rand.nextInt(4);
			Boolean playeratdoor = false;
			for(int y = 0 ; y < h; y++){
				for(int x = 0 ; x < w; x++){
					if((tiles[x][y]== 57 || tiles[x][y]== 58 || tiles[x][y] == 81) && chance == 3){
						Game.getPlayer().setXoYo(x,y);
						playeratdoor = true;
						chance +=1;
						playerx=x;
						playery=y;
					}
					else if (tiles[x][y]== 57 || tiles[x][y]== 58 || tiles[x][y] == 81){
						chance +=1;
					}
				}
			}
			if(!playeratdoor){
				setPlayerLocNew();
			}
		}
	}

	public void setPlayerLocOld(int formermapnum){
		int i = 0;
		while(i < doorlist.size()){
			//System.out.println("door list is " + doorlist.get(i) + " currmap is " + formermapnum);
			if(Integer.parseInt(doorlist.get(i)) == formermapnum){
				int x = Integer.parseInt(doorlocx.get(i));
				int y = Integer.parseInt(doorlocy.get(i));
				Game.getPlayer().setXoYo(x,y);
				break;
			}
			i++;
		}
	}

	public void makeBattlemap(){
		Random rand = new Random();
		int xo = Game.getPlayer().getXo();
		int yo = Game.getPlayer().getYo();
		playerx = (xo-32-222/32);
		playery = (yo-32-192/32);
		temptiles = new int[50][50];
		if(tiles[0][0] != 80 && tiles[49][49] != 80 && tiles[49][0] != 80 && tiles[0][49] != 80){
			for(int y = 0; y<50; y++){
				for(int x = 0; x<50; x++){
					temptiles[x][y] = 1;
				}
			}
		}
		else{
			for(int y = 0; y<50; y++){
				for(int x = 0; x<50; x++){
					temptiles[x][y] = 69 + rand.nextInt(6);
				}
			}			
		}
		tiles = temptiles;
		w=20;
		h=20;
	}

	public void removebattlemap(){
		w=50;
		h=50;
		newLoadLevel(mapnum);
		Game.getPlayer().setXoYo(playerx,playery);
	}
	
	public int getLastMap(int mapnum){
		File f = new File("..\\maps\\map"+ mapnum + ".txt");
		//this checks if there is already a map by this number. If there is, checks next one up.
		if(f.exists() && !f.isDirectory()) {
		return getLastMap(mapnum+1);
		}
		else{
			return mapnum;
		}
	}
	
	//map1 loads. Generates doors map2, map3, map4
	//Generate map2, map3, map4 as temptiles.
	
	public void saveLevel (){
		//this text is redundant and will be fixed
		File f = new File("..\\maps\\map"+ mapnum + ".txt");
		//this checks if there is already a map by this number. If there is, checks next one up.
		if(f.exists() && !f.isDirectory()) {
		//saveLevel();
		}
		else{
			try {
			    BufferedWriter out = new BufferedWriter(new FileWriter("..\\maps\\map"+ mapnum + ".txt"));
			    //data to be saved would be the width
			    //and height of the map as well as any door properties
			    //for now
			    out.write(Integer.toString(w) + "\n");
			    out.write(Integer.toString(h) + "\n");
			    int doornum = 0;
				for(int y = 0 ; y < h; y++){
					for(int x = 0 ; x < w; x++){
						out.write(Integer.toString(tiles[x][y]) + "\n");
						if(tiles[x][y]== 57 || tiles[x][y]== 58 || tiles[x][y] == 81){
							out.write(doorlist.get(doornum) + "\n");
							doornum +=1;
						}
					
					}
				}
			    out.close();
			} catch (IOException e) { }
		}
	}

	public int getdoorName(int x, int y){
		int i = 0;
		//This could be better, but it is safe because we already know we've reached a door if this function is called
		//System.out.print(doorlist.size() + ", x is" + x + ", y is" + y);
		while(i < doorlist.size()){
			if(Integer.parseInt(doorlocx.get(i)) == x	&& Integer.parseInt(doorlocy.get(i)) == y){
			return Integer.parseInt(doorlist.get(i));
			}
			i++;
		}
		return 0;
	}
	
	public void newLoadLevel (int nextmapname){
		  try{//clears old door data for new door data
			  int oldmapname = mapnum;
			  doorlist.clear();
			  doorlocx.clear();
			  doorlocy.clear();
			  File f = new File("..\\maps\\map"+ nextmapname + ".txt");
			  //this checks if there is already a map by this number. If there is, checks next one up.
			  if(!f.exists() && !f.isDirectory()) {
				  newLevel(nextmapname);
			  }
			  else{
				  FileInputStream fstream = new FileInputStream("..\\maps\\map"+ nextmapname + ".txt");
				  DataInputStream in = new DataInputStream(fstream);
				  BufferedReader br = new BufferedReader(new InputStreamReader(in));
				  //Read File Line By Line
				  w = Integer.parseInt(br.readLine());
				  h = Integer.parseInt(br.readLine());
				  mapnum=nextmapname;
				  tiles = new int[w][h];			  
				  for(int y = 0 ; y < h; y++){
					  for(int x = 0 ; x < w; x++){
						  tiles[x][y] = Integer.parseInt(br.readLine());
						  if(tiles[x][y]== 57 || tiles[x][y]== 58 || tiles[x][y] == 81){
							  doorlocx.add(Integer.toString(x));
							  doorlocy.add(Integer.toString(y));
							  doorlist.add(br.readLine());
						  }
					  }
				  }
				  //Close the input stream
				  in.close();
				  setPlayerLocOld(oldmapname);
			  }
			  }catch (Exception e){//Catch exception if any
			    	System.err.println("Error: " + e.getMessage());
			  }
	}
	
	
	public void render(Graphics brush){
		int xo = Game.getPlayer().getXo();
		int yo = Game.getPlayer().getYo();

		// This will allow for dynamic allocation of memory instead of cramming it all into memory at once.
		// Don't ask me how - Wes.
		int x0 =Math.max(xo / (Game.TILESIZE * Game.SCALE),0);
		int y0 =Math.max(yo / (Game.TILESIZE * Game.SCALE),0);
		int x1 =Math.min((xo + Game.WIDTH * Game.SCALE) / (Game.TILESIZE * Game.SCALE)+1 , w);
		int y1 =Math.min((yo + Game.HEIGHT * Game.SCALE) / (Game.TILESIZE * Game.SCALE)+1 , h);	
		//int xtrue = ((xo / (Game.TILESIZE * Game.SCALE) + (xo + Game.WIDTH * Game.SCALE) / (Game.TILESIZE * Game.SCALE)+1))/2;
		//int ytrue = ((yo / (Game.TILESIZE * Game.SCALE)+(yo + Game.HEIGHT * Game.SCALE) / (Game.TILESIZE * Game.SCALE)+1))/2;
		//System.out.println(xo+ " " + yo);
		//System.out.println(xtrue + " " + ytrue);		
		for(int y = y0; y <y1; y++){
			for(int x = x0; x<x1; x++){
				getTile(x,y).render(brush, x * Game.TILESIZE * Game.SCALE - xo, y * Game.TILESIZE * Game.SCALE - yo);
			}
		}
	}
	
	public Tile getTile(int x, int y){
		if ( x >= w || y >= h)
			return Tile.wall;
		switch(tiles[x][y]){
		case 1:
			return Tile.grass;
		case 2:
			return Tile.stone;
		case 3:
			return Tile.dirt;
		case 4:
			return Tile.rock;
		case 5:
			return Tile.wall;
		case 6:
			return Tile.tree;
		//case 7:
			//return Tile.wallDoor;
		case 8:
			return Tile.grassTDLeft;
		case 9:
			return Tile.grassTDRight;
		case 10:
			return Tile.grassTDTop;
		case 11:
			return Tile.grassTDBottom;
		case 12:
			return Tile.grassTDTLC;
		case 13:
			return Tile.grassTDTRC;
		case 14:
			return Tile.grassTDBLC;
		case 15:
			return Tile.grassTDBRC;
		case 16:
			return Tile.grassTWLeft;
		case 17:
			return Tile.grassTWRight;
		case 18:
			return Tile.grassTWTop;
		case 19:
			return Tile.grassTWBottom; 
		case 20:
			return Tile.grassTWTLC; 
		case 21:
			return Tile.grassTWTRC; 
		case 22:
			return Tile.grassTWBLC;  
		case 23:
			return Tile.grassTWBRC; 	
		case 24:
			return Tile.dirtTGLeft; 
		case 25:
			return Tile.dirtTGRight; 
		case 26:
			return Tile.dirtTGTop; 
		case 27:
			return Tile.dirtTGBottom; 
		case 28:
			return Tile.dirtTGTLC; 
		case 29:
			return Tile.dirtTGTRC; 
		case 30:
			return Tile.dirtTGBLC; 
		case 31:
			return Tile.dirtTGBRC; 
		case 32:
			return Tile.dirtTWLeft;
		case 33:
			return Tile.dirtTWRight; 
		case 34:
			return Tile.dirtTWTop; 
		case 35:
			return Tile.dirtTWBottom; 
		case 36:
			return Tile.dirtTWTLC; 
		case 37:
			return Tile.dirtTWTRC; 
		case 38:
			return Tile.dirtTWBLC; 
		case 39:
			return Tile.dirtTWBRC; 
		case 40:
			return Tile.water; 	
		case 41:
			return Tile.waterTGLeft; 
		case 42:
			return Tile.waterTGRight;
		case 43:
			return Tile.waterTGTop; 
		case 44:
			return Tile.waterTGBottom; 
		case 45:
			return Tile.waterTGTLC; 
		case 46:
			return Tile.waterTGTRC; 
		case 47:
			return Tile.waterTGBLC; 
		case 48:
			return Tile.waterTGBRC; 
		case 49:
			return Tile.waterTDLeft; 
		case 50:
			return Tile.waterTDRight; 
		case 51:
			return Tile.waterTDTop; 
		case 52:
			return Tile.waterTDBottom; 
		case 53:
			return Tile.waterTDTLC;
		case 54:
			return Tile.waterTDTRC;
		case 55:
			return Tile.waterTDBLC;
		case 56:
			return Tile.waterTDBRC;
		case 57:
			return Tile.wallDoorGrass;
		case 58:
			return Tile.wallDoorNoGrass; 
		case 59:
			return Tile.wallGrass;
		case 60:
			return Tile.dirt2LR;
		case 61:
			return Tile.dirt2UD;
		case 62:	
			return Tile.dirt3Top;
		case 63:	
			return Tile.dirt3Bottom;
		case 64:	
			return Tile.dirt3Left;
		case 65:	
			return Tile.dirt3Right;
		case 66:	
			return Tile.dirt4A;
		case 67:	
			return Tile.bridgeLR;
		case 68:	
			return Tile.bridgeUD;
 		case 69:
 			return Tile.dungeonDirtOne;
		case 70:
			return Tile.dungeonDirtTwo;
		case 71:	
			return Tile.dungeonDirtThree;
		case 72:	
			return Tile.dungeonDirtFour;
		case 73:	
			return Tile.dungeonDirtFive;
		case 74:	
			return Tile.dungeonDirtSix;
		case 75:	
			return Tile.dungeonDirtSeven;
		case 76:	
			return Tile.dungeonWallOne;
		case 77:	
			return Tile.dungeonWallTwo;
		case 78:	
			return Tile.dungeonWallThree;
		case 79:	
			return Tile.dungeonWallFour;
		case 80:	
			return Tile.dungeonBlack;
		case 81:
			return Tile.dungeonStairs;
		case 82:
			return Tile.mountain;
		case 83:	
			return Tile.mountainLeft;
		case 84:	
			return Tile.mountainRight;
		case 85:
			return Tile.mountainTop;
		case 86:	
			return Tile.mountainBottom;
		case 87:
			return Tile.mountainTLC;
		case 88:	
			return Tile.mountainTRC;
		case 89:	
			return Tile.mountainBLC;
		case 90:	
			return Tile.mountainBRC;		
			
		default:
			return Tile.grass;
		}
		
	}
}
