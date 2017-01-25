package ghp.Dungeon_Tactics.main.tiles;

import java.awt.Graphics;

import ghp.Dungeon_Tactics.main.Game;
import ghp.Dungeon_Tactics.main.gfx.ImageManager;

	public class Dirt2LRTile extends Tile{
	
		public Dirt2LRTile(ImageManager imgMan) {
			// Super calls the class you are constructing 
			super(imgMan);
		}
	
		
		public void tick() {
			
			
		}
	
		
		public void render(Graphics brush, int x , int y) {
			brush.drawImage(imgMan.dirt2LRTile, x , y , Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE, null);
			
		}
	
	}

