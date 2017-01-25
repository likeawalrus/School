package ghp.Dungeon_Tactics.main.gfx;

import ghp.Dungeon_Tactics.main.Game;

import java.awt.image.BufferedImage;

public class ImageManager {
	
	public BufferedImage player, grassTile, dirtTile, stoneTile,rockTile,wallTile,treeTile,wallDoorTile;
	public BufferedImage grassTDLeftTile,grassTDRightTile,grassTDTopTile,grassTDBottomTile,	grassTDTLCTile,
	grassTDTRCTile,	grassTDBLCTile,	grassTDBRCTile,	grassTWLeftTile,grassTWRightTile,grassTWTopTile,
	grassTWBottomTile,	grassTWTLCTile,	grassTWTRCTile,	grassTWBLCTile,	grassTWBRCTile,
	dirtTGLeftTile,	dirtTGRightTile,dirtTGTopTile,dirtTGBottomTile,	dirtTGTLCTile,dirtTGTRCTile,
	dirtTGBLCTile,dirtTGBRCTile,dirtTWLeftTile,	dirtTWRightTile,dirtTWTopTile,	dirtTWBottomTile,
	dirtTWTLCTile,dirtTWTRCTile,dirtTWBLCTile,dirtTWBRCTile,waterTile,
	waterTDLeftTile,waterTDRightTile,waterTDTopTile,waterTDBottomTile,waterTDTLCTile,waterTDTRCTile,
	waterTDBLCTile,waterTDBRCTile,waterTGLeftTile,waterTGRightTile,waterTGTopTile,waterTGBottomTile,
	waterTGTLCTile,waterTGTRCTile,waterTGBLCTile,waterTGBRCTile,wallGrassTile,wallWaterTile,
	wallDoorGrassTile,wallDoorNoGrassTile,dirt2LRTile,dirt2UDTile,dirt3TopTile,dirt3BottomTile,
	dirt3LeftTile,dirt3RightTile,dirt4ATile, bridgeUDTile, bridgeLRTile,dungeonDirtOneTile,
	dungeonDirtTwoTile,	dungeonDirtThreeTile, dungeonDirtFourTile, dungeonDirtFiveTile,	dungeonDirtSixTile,
	dungeonDirtSevenTile, dungeonWallOneTile, dungeonWallTwoTile, dungeonWallThreeTile,
	dungeonWallFourTile,dungeonBlackTile,dungeonStairsTile, mountainTile, mountainLeftTile,
	mountainRightTile,mountainTopTile,	mountainBottomTile,
	mountainTLCTile,	mountainTRCTile,	mountainBLCTile,	mountainBRCTile;	
	
	
	public ImageManager (SpriteSheet sheet){
		// crop(column, row, pixel width, pixel height)
		//Cole here. These link the spritesheet images to the various tile classes.
		player = sheet.crop(15,0 , Game.TILESIZE, Game.TILESIZE);
		bridgeUDTile = sheet.crop(10,0 , Game.TILESIZE, Game.TILESIZE);
		bridgeLRTile = sheet.crop(10,1 , Game.TILESIZE, Game.TILESIZE);
		
		grassTile = sheet.crop(1,1,Game.TILESIZE,Game.TILESIZE);
		grassTDLeftTile = sheet.crop(3,18,Game.TILESIZE,Game.TILESIZE);
		grassTDRightTile = sheet.crop(5,18,Game.TILESIZE,Game.TILESIZE);
		grassTDTopTile = sheet.crop(4,17,Game.TILESIZE,Game.TILESIZE);
		grassTDBottomTile = sheet.crop(4,19,Game.TILESIZE,Game.TILESIZE);
		grassTDTLCTile = sheet.crop(3,17,Game.TILESIZE,Game.TILESIZE);
		grassTDTRCTile = sheet.crop(5,17,Game.TILESIZE,Game.TILESIZE);
		grassTDBLCTile = sheet.crop(3,19,Game.TILESIZE,Game.TILESIZE);
		grassTDBRCTile = sheet.crop(5,19,Game.TILESIZE,Game.TILESIZE);

		grassTWLeftTile = sheet.crop(12,12,Game.TILESIZE,Game.TILESIZE);
		grassTWRightTile = sheet.crop(14,12,Game.TILESIZE,Game.TILESIZE);
		grassTWTopTile = sheet.crop(13,11,Game.TILESIZE,Game.TILESIZE);
		grassTWBottomTile = sheet.crop(13,13,Game.TILESIZE,Game.TILESIZE);
		grassTWTLCTile = sheet.crop(12,11,Game.TILESIZE,Game.TILESIZE);
		grassTWTRCTile = sheet.crop(14,11,Game.TILESIZE,Game.TILESIZE);
		grassTWBLCTile = sheet.crop(12,13,Game.TILESIZE,Game.TILESIZE);
		grassTWBRCTile = sheet.crop(14,13,Game.TILESIZE,Game.TILESIZE);
		
		dirtTile = sheet.crop(1,18,Game.TILESIZE,Game.TILESIZE);
		dirtTGLeftTile = sheet.crop(0,12,Game.TILESIZE,Game.TILESIZE);
		dirtTGRightTile = sheet.crop(2,12,Game.TILESIZE,Game.TILESIZE);
		dirtTGTopTile = sheet.crop(1,11,Game.TILESIZE,Game.TILESIZE);
		dirtTGBottomTile = sheet.crop(1,13,Game.TILESIZE,Game.TILESIZE);
		dirtTGTLCTile = sheet.crop(0,11,Game.TILESIZE,Game.TILESIZE);
		dirtTGTRCTile = sheet.crop(2,11,Game.TILESIZE,Game.TILESIZE);
		dirtTGBLCTile = sheet.crop(0,13,Game.TILESIZE,Game.TILESIZE);
		dirtTGBRCTile = sheet.crop(2,13,Game.TILESIZE,Game.TILESIZE);

		dirtTWLeftTile = sheet.crop(9,12,Game.TILESIZE,Game.TILESIZE);
		dirtTWRightTile = sheet.crop(11,12,Game.TILESIZE,Game.TILESIZE);
		dirtTWTopTile = sheet.crop(10,11,Game.TILESIZE,Game.TILESIZE);
		dirtTWBottomTile = sheet.crop(10,13,Game.TILESIZE,Game.TILESIZE);
		dirtTWTLCTile = sheet.crop(9,11,Game.TILESIZE,Game.TILESIZE);
		dirtTWTRCTile = sheet.crop(11,11,Game.TILESIZE,Game.TILESIZE);
		dirtTWBLCTile = sheet.crop(9,13,Game.TILESIZE,Game.TILESIZE);
		dirtTWBRCTile = sheet.crop(11,13,Game.TILESIZE,Game.TILESIZE);

		dirt2LRTile = sheet.crop(2,14,Game.TILESIZE,Game.TILESIZE);
		dirt2UDTile = sheet.crop(0,14,Game.TILESIZE,Game.TILESIZE);
		dirt3TopTile = sheet.crop(1,16,Game.TILESIZE,Game.TILESIZE);
		dirt3BottomTile = sheet.crop(1,14,Game.TILESIZE,Game.TILESIZE);
		dirt3LeftTile = sheet.crop(2,15,Game.TILESIZE,Game.TILESIZE);
		dirt3RightTile = sheet.crop(0,15,Game.TILESIZE,Game.TILESIZE);
		dirt4ATile = sheet.crop(1,15,Game.TILESIZE,Game.TILESIZE);
		
		
		waterTile = sheet.crop(9,7,Game.TILESIZE,Game.TILESIZE);
		waterTDLeftTile = sheet.crop(6,12,Game.TILESIZE,Game.TILESIZE);
		waterTDRightTile = sheet.crop(8,12,Game.TILESIZE,Game.TILESIZE);
		waterTDTopTile = sheet.crop(7,11,Game.TILESIZE,Game.TILESIZE);
		waterTDBottomTile = sheet.crop(7,13,Game.TILESIZE,Game.TILESIZE);
		waterTDTLCTile = sheet.crop(6,11,Game.TILESIZE,Game.TILESIZE);
		waterTDTRCTile = sheet.crop(8,11,Game.TILESIZE,Game.TILESIZE);
		waterTDBLCTile = sheet.crop(6,13,Game.TILESIZE,Game.TILESIZE);
		waterTDBRCTile = sheet.crop(8,13,Game.TILESIZE,Game.TILESIZE);

		waterTGLeftTile = sheet.crop(3,12,Game.TILESIZE,Game.TILESIZE);
		waterTGRightTile = sheet.crop(5,12,Game.TILESIZE,Game.TILESIZE);
		waterTGTopTile = sheet.crop(4,11,Game.TILESIZE,Game.TILESIZE);
		waterTGBottomTile = sheet.crop(4,13,Game.TILESIZE,Game.TILESIZE);
		waterTGTLCTile = sheet.crop(3,11,Game.TILESIZE,Game.TILESIZE);
		waterTGTRCTile = sheet.crop(5,11,Game.TILESIZE,Game.TILESIZE);
		waterTGBLCTile = sheet.crop(3,13,Game.TILESIZE,Game.TILESIZE);
		waterTGBRCTile = sheet.crop(5,13,Game.TILESIZE,Game.TILESIZE);


		stoneTile = sheet.crop(7,17,Game.TILESIZE,Game.TILESIZE);
		rockTile = sheet.crop(6,17,Game.TILESIZE,Game.TILESIZE);
		//This set will need to be expanded upon at some point
		wallTile = sheet.crop(4,2,Game.TILESIZE,Game.TILESIZE);
		wallGrassTile = sheet.crop(4,3,Game.TILESIZE,Game.TILESIZE);
		wallWaterTile = sheet.crop(5,4,Game.TILESIZE,Game.TILESIZE); 
		
		treeTile = sheet.crop(4,18,Game.TILESIZE,Game.TILESIZE);
		
		wallDoorGrassTile = sheet.crop(6,3,Game.TILESIZE,Game.TILESIZE);
		wallDoorNoGrassTile = sheet.crop(5,0,Game.TILESIZE,Game.TILESIZE);
		
		dungeonDirtOneTile = sheet.crop(6,17,Game.TILESIZE,Game.TILESIZE);
		dungeonDirtTwoTile = sheet.crop(7,17,Game.TILESIZE,Game.TILESIZE);
		dungeonDirtThreeTile = sheet.crop(8,17,Game.TILESIZE,Game.TILESIZE);
		dungeonDirtFourTile = sheet.crop(6,18,Game.TILESIZE,Game.TILESIZE);
		dungeonDirtFiveTile = sheet.crop(7,18,Game.TILESIZE,Game.TILESIZE);
		dungeonDirtSixTile = sheet.crop(8,18,Game.TILESIZE,Game.TILESIZE);
		dungeonDirtSevenTile = sheet.crop(6,19,Game.TILESIZE,Game.TILESIZE);
		dungeonWallOneTile = sheet.crop(9,17,Game.TILESIZE,Game.TILESIZE);
		dungeonWallTwoTile = sheet.crop(10,17,Game.TILESIZE,Game.TILESIZE);
		dungeonWallThreeTile = sheet.crop(11,17,Game.TILESIZE,Game.TILESIZE);
		dungeonWallFourTile = sheet.crop(9,18,Game.TILESIZE,Game.TILESIZE);
		dungeonBlackTile = sheet.crop(10,18,Game.TILESIZE,Game.TILESIZE);
		dungeonStairsTile = sheet.crop(11,18,Game.TILESIZE,Game.TILESIZE);	

		mountainTile = sheet.crop(1,1,Game.TILESIZE,Game.TILESIZE);
		mountainLeftTile = sheet.crop(0,1,Game.TILESIZE,Game.TILESIZE);
		mountainRightTile = sheet.crop(2,1,Game.TILESIZE,Game.TILESIZE);
		mountainTopTile = sheet.crop(1,0,Game.TILESIZE,Game.TILESIZE);
		mountainBottomTile = sheet.crop(1,2,Game.TILESIZE,Game.TILESIZE);
		mountainTLCTile = sheet.crop(0,0,Game.TILESIZE,Game.TILESIZE);
		mountainTRCTile = sheet.crop(2,0,Game.TILESIZE,Game.TILESIZE);
		mountainBLCTile = sheet.crop(0,2,Game.TILESIZE,Game.TILESIZE);
		mountainBRCTile = sheet.crop(2,2,Game.TILESIZE,Game.TILESIZE);

		
	}

}
