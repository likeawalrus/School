package ghp.Dungeon_Tactics.main.tiles;

import java.awt.Graphics;

import ghp.Dungeon_Tactics.main.Game;
import ghp.Dungeon_Tactics.main.gfx.ImageManager;

/*
 * An abstract class can not be instantiated. That means you can't create new instances of these types of classes.
 * 
 * The purpose of this type class is to serve as a base for making subclasses.
 * 
 * This class in particular will be the basis for all of our other tiles.
 */

public abstract class Tile {
	
	// Protected means only routines in this class can access these variables.
	private int doormaplink;
	protected ImageManager imgMan;
	
	public static Tile bridgeUD = new BridgeUDTile(Game.getImageManager());
	public static Tile bridgeLR = new BridgeLRTile(Game.getImageManager());	
	public static Tile grass = new GrassTile(Game.getImageManager());
	public static Tile grassTDLeft = new GrassTDLeftTile(Game.getImageManager());
	public static Tile grassTDRight = new GrassTDRightTile(Game.getImageManager());
	public static Tile grassTDTop = new GrassTDTopTile(Game.getImageManager());
	public static Tile grassTDBottom = new GrassTDBottomTile(Game.getImageManager());
	public static Tile grassTDTLC = new GrassTDTLCTile(Game.getImageManager());
	public static Tile grassTDTRC = new GrassTDTRCTile(Game.getImageManager());
	public static Tile grassTDBLC = new GrassTDBLCTile(Game.getImageManager());
	public static Tile grassTDBRC = new GrassTDBRCTile(Game.getImageManager());
	public static Tile grassTWLeft = new GrassTWLeftTile(Game.getImageManager());
	public static Tile grassTWRight = new GrassTWRightTile(Game.getImageManager());
	public static Tile grassTWTop = new GrassTWTopTile(Game.getImageManager());
	public static Tile grassTWBottom = new GrassTWBottomTile(Game.getImageManager());
	public static Tile grassTWTLC = new GrassTWTLCTile(Game.getImageManager());
	public static Tile grassTWTRC = new GrassTWTRCTile(Game.getImageManager());
	public static Tile grassTWBLC = new GrassTWBLCTile(Game.getImageManager());
	public static Tile grassTWBRC = new GrassTWBRCTile(Game.getImageManager());	
	
	public static Tile dirt = new DirtTile(Game.getImageManager());
	public static Tile dirtTGLeft = new DirtTGLeftTile(Game.getImageManager());
	public static Tile dirtTGRight = new DirtTGRightTile(Game.getImageManager());
	public static Tile dirtTGTop = new DirtTGTopTile(Game.getImageManager());
	public static Tile dirtTGBottom = new DirtTGBottomTile(Game.getImageManager());
	public static Tile dirtTGTLC = new DirtTGTLCTile(Game.getImageManager());
	public static Tile dirtTGTRC = new DirtTGTRCTile(Game.getImageManager());
	public static Tile dirtTGBLC = new DirtTGBLCTile(Game.getImageManager());
	public static Tile dirtTGBRC = new DirtTGBRCTile(Game.getImageManager());
	public static Tile dirtTWLeft = new DirtTWLeftTile(Game.getImageManager());
	public static Tile dirtTWRight = new DirtTWRightTile(Game.getImageManager());
	public static Tile dirtTWTop = new DirtTWTopTile(Game.getImageManager());
	public static Tile dirtTWBottom = new DirtTWBottomTile(Game.getImageManager());
	public static Tile dirtTWTLC = new DirtTWTLCTile(Game.getImageManager());
	public static Tile dirtTWTRC = new DirtTWTRCTile(Game.getImageManager());
	public static Tile dirtTWBLC = new DirtTWBLCTile(Game.getImageManager());
	public static Tile dirtTWBRC = new DirtTWBRCTile(Game.getImageManager());	
	
	public static Tile dirt2LR = new Dirt2LRTile(Game.getImageManager());
	public static Tile dirt2UD = new Dirt2UDTile(Game.getImageManager());
	public static Tile dirt3Top = new Dirt3TopTile(Game.getImageManager());
	public static Tile dirt3Bottom = new Dirt3BottomTile(Game.getImageManager());
	public static Tile dirt3Left = new Dirt3LeftTile(Game.getImageManager());
	public static Tile dirt3Right = new Dirt3RightTile(Game.getImageManager());
	public static Tile dirt4A = new Dirt4ATile(Game.getImageManager());
	
	public static Tile water = new WaterTile(Game.getImageManager());	
	public static Tile waterTGLeft = new WaterTGLeftTile(Game.getImageManager());
	public static Tile waterTGRight = new WaterTGRightTile(Game.getImageManager());
	public static Tile waterTGTop = new WaterTGTopTile(Game.getImageManager());
	public static Tile waterTGBottom = new WaterTGBottomTile(Game.getImageManager());
	public static Tile waterTGTLC = new WaterTGTLCTile(Game.getImageManager());
	public static Tile waterTGTRC = new WaterTGTRCTile(Game.getImageManager());
	public static Tile waterTGBLC = new WaterTGBLCTile(Game.getImageManager());
	public static Tile waterTGBRC = new WaterTGBRCTile(Game.getImageManager());
	public static Tile waterTDLeft = new WaterTDLeftTile(Game.getImageManager());
	public static Tile waterTDRight = new WaterTDRightTile(Game.getImageManager());
	public static Tile waterTDTop = new WaterTDTopTile(Game.getImageManager());
	public static Tile waterTDBottom = new WaterTDBottomTile(Game.getImageManager());
	public static Tile waterTDTLC = new WaterTDTLCTile(Game.getImageManager());
	public static Tile waterTDTRC = new WaterTDTRCTile(Game.getImageManager());
	public static Tile waterTDBLC = new WaterTDBLCTile(Game.getImageManager());
	public static Tile waterTDBRC = new WaterTDBRCTile(Game.getImageManager());

	public static Tile rock = new RockTile(Game.getImageManager());
	public static Tile stone = new StoneTile(Game.getImageManager());
	
	public static Tile wallDoorGrass = new WallDoorGrassTile(Game.getImageManager());
	public static Tile wallDoorNoGrass = new WallDoorNoGrassTile(Game.getImageManager());
	
	
	public static Tile wall = new WallTile(Game.getImageManager());
	public static Tile wallGrass = new WallGrassTile(Game.getImageManager());	
	public static Tile wallWater = new WallWaterTile(Game.getImageManager());
	
	public static Tile tree = new TreeTile(Game.getImageManager());
	
	public static Tile dungeonDirtOne = new DungeonDirtOneTile(Game.getImageManager());
	public static Tile dungeonDirtTwo = new DungeonDirtTwoTile(Game.getImageManager());
	public static Tile dungeonDirtThree = new DungeonDirtThreeTile(Game.getImageManager());
	public static Tile dungeonDirtFour = new DungeonDirtFourTile(Game.getImageManager());
	public static Tile dungeonDirtFive = new DungeonDirtFiveTile(Game.getImageManager());
	public static Tile dungeonDirtSix = new DungeonDirtSixTile(Game.getImageManager());
	public static Tile dungeonDirtSeven = new DungeonDirtSevenTile(Game.getImageManager());
	public static Tile dungeonWallOne = new DungeonWallOneTile(Game.getImageManager());
	public static Tile dungeonWallTwo = new DungeonWallTwoTile(Game.getImageManager());
	public static Tile dungeonWallThree = new DungeonWallThreeTile(Game.getImageManager());
	public static Tile dungeonWallFour = new DungeonWallFourTile(Game.getImageManager());
	public static Tile dungeonBlack = new DungeonBlackTile(Game.getImageManager());
	public static Tile dungeonStairs = new DungeonStairsTile(Game.getImageManager());
	
	public static Tile mountain = new MountainTile(Game.getImageManager());
	public static Tile mountainLeft = new MountainLeftTile(Game.getImageManager());
	public static Tile mountainRight = new MountainRightTile(Game.getImageManager());
	public static Tile mountainTop = new MountainTopTile(Game.getImageManager());
	public static Tile mountainBottom = new MountainBottomTile(Game.getImageManager());
	public static Tile mountainTLC = new MountainTLCTile(Game.getImageManager());
	public static Tile mountainTRC = new MountainTRCTile(Game.getImageManager());
	public static Tile mountainBLC = new MountainBLCTile(Game.getImageManager());
	public static Tile mountainBRC = new MountainBRCTile(Game.getImageManager());

	
	public Tile (ImageManager imgMan){
		this.imgMan = imgMan;
	}
	
	public abstract void tick();
	public abstract void render(Graphics brush, int x , int y);
	public boolean isSolid(){
		return false;
	}
	public boolean isDoor(){
		return false;
	}	
}
