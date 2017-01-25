package ghp.Dungeon_Tactics.main.tiles;

import java.awt.Graphics;

import ghp.Dungeon_Tactics.main.Game;
import ghp.Dungeon_Tactics.main.gfx.ImageManager;

public class WaterTGRightTile extends Tile{

	public WaterTGRightTile(ImageManager imgMan) {
		// Super calls the class you are constructing 
		super(imgMan);
	}

	
	public void tick() {
		
		
	}

	
	public void render(Graphics brush, int x , int y) {
		brush.drawImage(imgMan.waterTGRightTile, x , y , Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE, null);
		
	}
}
