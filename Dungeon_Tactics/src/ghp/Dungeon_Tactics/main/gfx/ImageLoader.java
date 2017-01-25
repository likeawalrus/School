package ghp.Dungeon_Tactics.main.gfx;
//Written By Wesley Leach on 9-16
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	// Path is the file path of the image we wish to load.
	public BufferedImage load (String path){

		// The try and catch is simply a way to insulate ourselves from unexpected errors.
		// If you need one the eclipse IDE will help you create it, just hoover the mouse over the error and follow
		// its suggestion.
		
		try {
			// This line returns the image that is located at the path variable to the object that called
			// buffered image.
			return ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
