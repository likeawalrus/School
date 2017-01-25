package ghp.Dungeon_Tactics.main.tiles;

import java.awt.Graphics;

import ghp.Dungeon_Tactics.main.Game;
import ghp.Dungeon_Tactics.main.gfx.ImageManager;

public class GrassTWLeftTile extends Tile{

	public GrassTWLeftTile(ImageManager imgMan) {
		// Super calls the class you are constructing 
		super(imgMan);
	}

	
	public void tick() {
		
		
	}

	
	public void render(Graphics brush, int x , int y) {
		brush.drawImage(imgMan.grassTWLeftTile, x , y , Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE, null);
		
	}

}
