package ghp.Dungeon_Tactics.main.gfx;
import ghp.Dungeon_Tactics.main.Game;

//Written By Wesley Leach on 9-16
import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	// sheet refers to spritesheet.png once it is loaded in. 
	private BufferedImage sheet;
	
	public SpriteSheet(BufferedImage sheet){
		this.sheet = sheet;
	}
	
	public BufferedImage crop (int col, int row, int width,int height){
		// This will create a sub image at the location specified in pixels.
		// col and row and multiplied by 32 because that is the size of our individual spites.
		return sheet.getSubimage(col*Game.TILESIZE +(col*Game.SPRITE_BORDER), row*Game.TILESIZE +(row*Game.SPRITE_BORDER), width, height);
	}

}
