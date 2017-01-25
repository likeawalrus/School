package ghp.Dungeon_Tactics.main.levels;

import java.awt.image.BufferedImage;

import ghp.Dungeon_Tactics.main.Game;
import ghp.Dungeon_Tactics.main.tiles.Tile;
import ghp.Dungeon_Tactics.main.levels.Level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.io.*;


public class Grasslevel {

	public int[][] tiles;
	public int[][] temptiles;
	public int w, h, watersides, riverrand, maxsize, maxheight;
	public String mapname;
	
	
	public Grasslevel() {
	}
	public int[][] randLevel(){
		//Oh, no tears please. It's a waste of good suffering.
		w = 50;
		h = 50;
		Random rand = new Random();
		temptiles = new int[w+2][h+2];
		tiles = new int[w][h];
		w = w+2;
		h = h+2;
		for(int y = 0 ; y < h; y++){
			for(int x = 0 ; x < w; x++){
			//In the beginning, there was grass.	
			temptiles[x][y]=1;	
			}
		}
		//Then there was stone, because why not
		for(int y = 0 ; y < h; y++){
			for(int x = 0 ; x < w; x++){
				if(x == 0 || y == 0 || x == (w-1) || y == (h-1)){	
					temptiles[x][y]=5;
				}
			}
		}
		
		watersides = rand.nextInt(13);
		//the first five if statements create a line of water at the edge of the map.
		//In the first case the map is encircled by water.(Island)
		//Currently, all water types have an equal chance of happening.
		//It would be trivial to change this upon request.
		//watersides 0 is island
		//Watersides 1-4 one side is ocean
		//Watersides 5-8 one side ocean and a river leading into it
		//Watersides 9-12 is no ocean but a river
		//Watersides 13 is dirt
		if(watersides == 0){
			for(int y = 1 ; y < (h-1); y++){
				for(int x = 1 ; x < (w-1); x++){
					if(x == 1 || y == 1 || x == (w-2) || y == (h-2)){	
						temptiles[x][y]=40;
					}
				}
			}
		}
		if(watersides == 1 || watersides == 5){
			for(int y = 1 ; y < (h-1); y++){
				temptiles[1][y]=40;
			}
		}		
		if(watersides == 2 || watersides == 8){
			for(int y = 1 ; y < (h-1); y++){
				temptiles[(w-1)][y]=40;
			}
		}		
		if(watersides == 3 || watersides == 7){
			for(int x = 1 ; x < (w-1); x++){
				temptiles[x][1]=40;
			}
		}
		if(watersides == 4 || watersides == 6){
			for(int x = 1 ; x < (w-1); x++){
				temptiles[x][(h-1)]=40;
			}
		}
		//This creates a river
		//The last else if statement gives a chance for the river to branch/widen
		//But has a low chance of happening
		if(watersides == 5 || watersides == 9){
			riverrand = rand.nextInt(h-5)+3;	
			temptiles[(w-2)][riverrand]=6;
			for(int x = (w-1) ; x > 1; x--){
				for(int y = (w-2) ; y > 1; y--){
					if(temptiles[x][y] == 6){
						riverrand = rand.nextInt(1000);
						if(riverrand > 679)
							temptiles[x-1][y]=6;
						else if((riverrand > 359) && (y >= 1))
							temptiles[x-1][y-1]=6;
						else if((riverrand > 39) && (y < (h-1)))
							temptiles[x-1][y+1]=6;
						else if((y >= 1) && (y < (h-1))){
							temptiles[x-1][y-1]=6;
							temptiles[x-1][y+1]=6;
						}

					}
				}
			}
		}
		if(watersides == 6 || watersides == 10){
			riverrand = rand.nextInt(w-5)+3;	
			temptiles[riverrand][1]=6;
			for(int y = 1 ; y < (h-1); y++){
				for(int x = 2 ; x < w-2; x++){
					if(temptiles[x][y] == 6){
						riverrand = rand.nextInt(1000);
						if(riverrand > 679)
							temptiles[x][y+1]=6;
						else if((riverrand > 359) && (x >= 1))
							temptiles[x-1][y+1]=6;
						else if((riverrand > 39) && (x < (w-1)))
							temptiles[x+1][y+1]=6;
						else if((x >= 1) && (x < (w-1))){
							temptiles[x-1][y+1]=6;
							temptiles[x+1][y+1]=6;
						}

					}
				}
			}
		}
		if(watersides == 7 || watersides == 11){
			riverrand = rand.nextInt(w-5)+3;	
			temptiles[riverrand][(h-2)]=6;
			for(int y = (h-1) ; y > 1; y--){
				for(int x = 2 ; x < w-2; x++){
					if(temptiles[x][y] == 6){
						riverrand = rand.nextInt(1000);
						if(riverrand > 679)
							temptiles[x][y-1]=6;
						else if((riverrand > 359) && (x >= 1))
							temptiles[x-1][y-1]=6;
						else if((riverrand > 39) && (x < (w-1)))
							temptiles[x+1][y-1]=6;
						else if((x >= 1) && (x < (w-1))){
							temptiles[x-1][y-1]=6;
							temptiles[x+1][y-1]=6;
						}
					}
				}
			}
		}
		if(watersides == 8 || watersides == 12){
			riverrand = rand.nextInt(h-5)+3;	
			temptiles[1][riverrand]=6;
			for(int x = 1 ; x < (w-1); x++){
				for(int y = 1 ; y < (h-2); y++){
					if(temptiles[x][y] == 6){
						riverrand = rand.nextInt(1000);
						if(riverrand > 659)
							temptiles[x+1][y]=6;
						else if((riverrand > 339) && (x >= 1))
							temptiles[x+1][y-1]=6;
						else if((riverrand > 19) && (x < (w-1)))
							temptiles[x+1][y+1]=6;
						else if((x >= 1) && (x < (w-1))){
							temptiles[x+1][y-1]=6;
							temptiles[x+1][y+1]=6;
						}

					}
				}
			}
		}
		
		//next we fill in water. One for ocean, one for river.
		//The ocean one goes through a few loops, gradually filling more and more squares
		//Ocean first
		//Eventually this will need to be modified to allow for all ocean sides to be even looking.
		//That statement will make sense if you run the program a few times.
		if(watersides < 9){
			for(int z = 0; z < 7; z++){
				for(int x = 1 ; x < (w-1); x++){
					for(int y = 1 ; y < (h-1); y++){
						if(((temptiles[x][y-1]==40) || (temptiles[x][y+1]==40)) 
						&& (rand.nextInt(1000) > 400) && (temptiles[x][y] == 1)){	
							temptiles[x][y]=40;
							y += 1;
						}
					}
				}
			}
			for(int z = 0; z < 7; z++){
				for(int y = 1 ; y < h-1; y++){
					for(int x = 1 ; x < (w-1); x++){
						if(((temptiles[x-1][y]==40) || (temptiles[x+1][y]==40)) 
						&& (rand.nextInt(1000) > 400) ){	
							temptiles[x][y]=40;
							x += 1;
						}
					}
				}
			}
			for(int z = 0; z < 7; z++){
				for(int y = 1 ; y < (h-1); y++){
					for(int x = 1 ; x < (w-1); x++){
						if((temptiles[x-1][y]==40 && temptiles[x+1][y]==40 && temptiles[x][y+1]==40) || 
			  			   (temptiles[x-1][y]==40 && temptiles[x+1][y]==40 && temptiles[x][y-1]==40) ||
			  			   (temptiles[x+1][y]==40 && temptiles[x][y-1]==40 && temptiles[x][y+1]==40) ||
			  			   (temptiles[x-1][y]==40 && temptiles[x][y+1]==40 && temptiles[x][y-1]==40)){	
							temptiles[x][y]=40;
						}
					}
				}
			}			
		}		
		//Next the river.
		if(watersides == 6 || watersides == 7 || watersides == 10 || watersides == 11){
			for(int y = 1 ; y < (h-1); y++){
				for(int x = 1 ; x < (w-1); x++){
					if(temptiles[x][y] == 6){	
						temptiles[x+1][y]=6;
						temptiles[x-1][y]=6;
						x=x+1;
					}
				}
			}
		}
		else if(watersides == 5 || watersides == 8 || watersides == 9 || watersides == 12){
			for(int x = 1 ; x < (w-1); x++){
				for(int y = 1 ; y < (h-1); y++){
					if(temptiles[x][y] == 6){	
						temptiles[x][y+1]=6;
						temptiles[x][y-1]=6;
						y=y+1;
					}
				}
			}
		}
		for(int y = 1 ; y < h-1; y++){
			for(int x = 1 ; x < w-1; x++){	
				if(temptiles[x][y]==6){
					temptiles[x][y]=40;
				}
			}
		}		
		//Now every square next to water that isn't water becomes dirt
		//We have to do this check once because I am a wizard
		for(int y = 1 ; y < (h-1); y++){
			for(int x = 1 ; x < (w-1); x++){	
				if((temptiles[x-1][y]==40 || temptiles[x+1][y]==40 || temptiles[x][y+1]==40
				|| temptiles[x][y-1]==40 || temptiles[x-1][y-1]==40 || temptiles[x+1][y-1]==40
				|| temptiles[x-1][y+1]==40 || temptiles[x+1][y+1]==40) && temptiles[x][y]!=40){
					temptiles[x][y]=3;
				}
			}
		}
		//Go through more to add more dirt because why not.
		//To prevent previously made dirt from affecting to be made dirt in the same loop
		//stone is being used as a placeholder
		for(int z = 0; z < 4; z++){
			for(int y = 1 ; y < (h-1); y++){
				for(int x = 1 ; x < (w-1); x++){	
					if((temptiles[x-1][y]==3 || temptiles[x+1][y]==3 || temptiles[x][y+1]==3 || temptiles[x][y-1]==3) 
							&& temptiles[x][y]!=40 && (rand.nextInt(1000) > 400)){
						temptiles[x][y]=2;
					}
				}
			} 
			for(int y = 1 ; y < h-1; y++){
				for(int x = 1 ; x < w-1; x++){	
					if(temptiles[x][y]==2){
						temptiles[x][y]=3;
					}
				}
			}
		}
		for(int y = 1 ; y < h-1; y++){
			for(int x = 1 ; x < w-1; x++){	
				if(temptiles[x][y]==1 && (rand.nextInt(1000) > 800)){
					temptiles[x][y]=3;
				}
			}
		}
//This code right here generates those hill structures. The caps are done later.		
		for(int y = 1 ; y < h-1; y++){
			for(int x = 1 ; x < w-1; x++){	
				if((temptiles[x][y]==1 || temptiles[x][y]==3) && (rand.nextInt(1000) > 995) && 
						(temptiles[x][y+1]==1 || temptiles[x][y+1]==3)){
						maxsize = rand.nextInt(3)+1;
						maxheight = rand.nextInt(3)+3;
						for(int randtemp = 0; (randtemp <= maxsize) && (x-randtemp >= 0); randtemp++){
						temptiles[x-randtemp][y]=5;
							for(int z = 1; (z <= maxheight) && (y-z >= 1); z++){
							temptiles[x-randtemp][y-z]=5;
							}						
						}
						maxsize = rand.nextInt(3)+1;
						for(int randtemp = 1; randtemp <= maxsize && (x+randtemp <= (w-1)); randtemp++){
						temptiles[x+randtemp][y]=5;
							for(int z = 1; (z <= maxheight) && (y-z >= 1); z++){
							temptiles[x+randtemp][y-z]=5;
							}												
						}
						temptiles[x][y]=58;						
				}
			}
		}		

		//Some of that dirt is next to three water tiles, which we don't have a sprite for.
		for(int z = 0 ; z < 4; z++){
			for(int y = 1 ; y < h-1; y++){
				for(int x = 1 ; x < w-1; x++){
					if((temptiles[x][y] == 3 && temptiles[x-1][y]==40 
							&& temptiles[x+1][y]==40 && temptiles[x][y-1]==40) ||
							(temptiles[x][y] == 3 && temptiles[x-1][y]==40 
							&& temptiles[x+1][y]==40 && temptiles[x][y+1]==40) ||
							(temptiles[x][y] == 3 && temptiles[x-1][y]==40 
							&& temptiles[x][y+1]==40 && temptiles[x][y-1]==40) ||
							(temptiles[x][y] == 3 && temptiles[x+1][y]==40 
							&& temptiles[x][y+1]==40 && temptiles[x][y-1]==40)){
						temptiles[x][y] = 40;
					}
				}
			}
		}

		//Now to make use of the 'other' 40 something tiles.
		for(int y = 1 ; y < h-1; y++){
			for(int x = 1 ; x < w-1; x++){
				if(temptiles[x][y] == 3){
					if(temptiles[x+1][y]==3 && (temptiles[x-1][y]==3 || temptiles[x-1][y]==60 || temptiles[x-1][y]==65) && temptiles[x][y+1]==1 && temptiles[x][y-1]==1){
						temptiles[x][y] = 60;}
					else if(temptiles[x+1][y]==1 && temptiles[x-1][y]==1 && temptiles[x][y+1]==3 && (temptiles[x][y-1]==3 || temptiles[x][y-1]==61 || temptiles[x][y-1]==63))
						temptiles[x][y] = 61;
					else if(temptiles[x+1][y]==1 && (temptiles[x][y-1]==3 || temptiles[x][y-1]==61) && temptiles[x-1][y]==1 && temptiles[x][y+1]==1)
						temptiles[x][y] = 62;
					else if(temptiles[x+1][y]==1 && temptiles[x][y-1]==1 && temptiles[x-1][y]==1 && temptiles[x][y+1]==3)
						temptiles[x][y] = 63;
					else if(temptiles[x+1][y]==1 && temptiles[x][y-1]==1 && (temptiles[x-1][y]==3 || temptiles[x-1][y]==60) && temptiles[x][y+1]==1)
						temptiles[x][y] = 64;
					else if(temptiles[x+1][y]==3 && temptiles[x][y-1]==1 && temptiles[x-1][y]==1 && temptiles[x][y+1]==1)
						temptiles[x][y] = 65;
					else if(temptiles[x+1][y]==1 && temptiles[x][y-1]==1 && temptiles[x-1][y]==1 && temptiles[x][y+1]==1)
						temptiles[x][y] = 66;				
				}
			}
		}
		
		for(int y = 1 ; y < h-1; y++){
			for(int x = 1 ; x < w-1; x++){
				if(temptiles[x][y] == 3){
					if (temptiles[x-1][y]==40 && temptiles[x][y-1]==40 && temptiles[x+1][y+1]==1)
						temptiles[x][y] = 20;
					else if(temptiles[x+1][y]==40 && temptiles[x][y-1]==40 && temptiles[x-1][y+1]==1)
						temptiles[x][y] = 21;
					else if(temptiles[x-1][y]==40 && temptiles[x][y+1]==40 && temptiles[x+1][y-1]==1)
						temptiles[x][y] = 22;
					else if(temptiles[x+1][y]==40 && temptiles[x][y+1]==40 && temptiles[x-1][y-1]==1)
						temptiles[x][y] = 23;
					else if (temptiles[x-1][y]==1 && temptiles[x][y-1]==1 && temptiles[x+1][y+1]==40)
						temptiles[x][y] = 45;
					else if(temptiles[x+1][y]==1 && temptiles[x][y-1]==1 && temptiles[x-1][y+1]==40)
						temptiles[x][y] = 46;
					else if(temptiles[x-1][y]==1 && temptiles[x][y+1]==1 && temptiles[x+1][y-1]==40)
						temptiles[x][y] = 47;
					else if(temptiles[x+1][y]==1 && temptiles[x][y+1]==1 && temptiles[x-1][y-1]==40)
						temptiles[x][y] = 48;				
					else if (temptiles[x-1][y]==40 && temptiles[x+1][y]==1)
						temptiles[x][y] = 16;
					else if(temptiles[x-1][y]==1 && temptiles[x+1][y]==40)
						temptiles[x][y] = 17;
					else if(temptiles[x][y+1]==1 && temptiles[x][y-1]==40)
						temptiles[x][y] = 18;
					else if(temptiles[x][y+1]==40 && temptiles[x][y-1]==1)
						temptiles[x][y] = 19;				
					else if (temptiles[x-1][y]==40 && temptiles[x][y-1]==40)
						temptiles[x][y] = 36;
					else if(temptiles[x+1][y]==40 && temptiles[x][y-1]==40)
						temptiles[x][y] = 37;
					else if(temptiles[x-1][y]==40 && temptiles[x][y+1]==40)
						temptiles[x][y] = 38;
					else if(temptiles[x+1][y]==40 && temptiles[x][y+1]==40)
						temptiles[x][y] = 39;								
					else if (temptiles[x-1][y]==1 && temptiles[x][y-1]==1)
						temptiles[x][y] = 28;
					else if(temptiles[x+1][y]==1 && temptiles[x][y-1]==1)
						temptiles[x][y] = 29;
					else if(temptiles[x-1][y]==1 && temptiles[x][y+1]==1)
						temptiles[x][y] = 30;
					else if(temptiles[x+1][y]==1 && temptiles[x][y+1]==1)
						temptiles[x][y] = 31;				
					else if (temptiles[x][y]==3 && temptiles[x-1][y]==40)
						temptiles[x][y] = 32;
					else if(temptiles[x+1][y]==40)
						temptiles[x][y] = 33;
					else if(temptiles[x][y-1]==40)
						temptiles[x][y] = 34;
					else if(temptiles[x][y+1]==40)
						temptiles[x][y] = 35;
					else if (temptiles[x-1][y-1]==40)
						temptiles[x][y] = 56;
					else if(temptiles[x+1][y-1]==40)
						temptiles[x][y] = 55;
					else if(temptiles[x-1][y+1]==40)
						temptiles[x][y] = 54;
					else if(temptiles[x+1][y+1]==40)
						temptiles[x][y] = 53;								
					else if (temptiles[x-1][y]==1)
						temptiles[x][y] = 24;
					else if(temptiles[x+1][y]==1)
						temptiles[x][y] = 25;
					else if(temptiles[x][y-1]==1)
						temptiles[x][y] = 26;
					else if(temptiles[x][y+1]==1)
						temptiles[x][y] = 27;				
					else if (temptiles[x-1][y-1]==1)
						temptiles[x][y] = 15;
					else if(temptiles[x+1][y-1]==1)
						temptiles[x][y] = 14;
					else if(temptiles[x-1][y+1]==1)
						temptiles[x][y] = 13;
					else if(temptiles[x+1][y+1]==1)
						temptiles[x][y] = 12;							
				}								
			}
		}
//this mess was easier to do as separate loops as opposed to one giant loop
//It makes the mountain tops less ugly
		for(int y = 1 ; y < h-1; y++){
			for(int x = 1 ; x < w-1; x++){
				if(temptiles[x][y] == 58 && temptiles[x][y+1] == 5){
					temptiles[x][y] = 5;
				}				
			}
		}
		for(int y = 1 ; y < h-1; y++){
			for(int x = 1 ; x < w-1; x++){
				if(temptiles[x][y] == 5 && temptiles[x+1][y] == 5 && (temptiles[x+1][y+1] == 5 || temptiles[x+1][y+1] == 58) && (temptiles[x-1][y+1] == 5 || temptiles[x-1][y+1] == 58)  && (temptiles[x-1][y] == 5 || temptiles[x-1][y] == 82) 
						&& (temptiles[x][y-1] == 5 || temptiles[x][y-1] == 82)
						&& temptiles[x][y+1] == 5 && (temptiles[x][y+2] == 5 || temptiles[x][y+2] == 58)){
					temptiles[x][y] = 82;
				}				
			}
		}
		for(int y = 1 ; y < h-1; y++){
			for(int x = 1 ; x < w-1; x++){
				if(temptiles[x][y] == 5){
					if(temptiles[x-1][y] < 81 && (temptiles[x+1][y] == 82 || temptiles[x+1][y] == 84) && temptiles[x][y+1] == 5 &&
							(temptiles[x][y-1] == 83 || temptiles[x][y-1] == 82 || temptiles[x][y-1] == 5) && 
							(temptiles[x][y+2] == 5 || temptiles[x][y+2] == 82)){
						temptiles[x][y] = 83;
					}
					else if(temptiles[x+1][y] < 82 && (temptiles[x-1][y] == 82 || temptiles[x-1][y] == 83) && temptiles[x][y+1] == 5 &&
							(temptiles[x][y-1] == 84 || temptiles[x][y-1] == 82 || temptiles[x][y-1] == 5) && 
							(temptiles[x][y+2] == 5 || temptiles[x][y+2] == 82)){
						temptiles[x][y] = 84;
					}
					else if(temptiles[x][y-1] < 82 && (temptiles[x-1][y] == 85 || temptiles[x-1][y] == 5) && 
							(temptiles[x+1][y] == 5 || temptiles[x+1][y] == 82) && 
							(temptiles[x][y+1] == 82 ||  temptiles[x][y+1] == 5) && (temptiles[x][y+2] == 5 || temptiles[x][y+2] == 82 || temptiles[x][y+1] == 58)){
						temptiles[x][y] = 85;
					}
					else if(temptiles[x][y+1] < 82 && (temptiles[x][y+1] == 5 || temptiles[x][y+1] == 58) &&(temptiles[x][y-1] == 82 || temptiles[x][y-1] == 85)){
						temptiles[x][y] = 86;
					}
				}
			}
		}
		for(int y = 1 ; y < h-1; y++){
			for(int x = 1 ; x < w-1; x++){
				if(temptiles[x][y] == 5){		
					if(temptiles[x+1][y] == 85 && temptiles[x][y+1] == 83){
						temptiles[x][y] = 87;
					}
					else if((temptiles[x-1][y] == 85 || temptiles[x-1][y] == 82) && temptiles[x][y+1] == 84){
						temptiles[x][y] = 88;
					}
					else if((temptiles[x+1][y] == 86 || temptiles[x-1][y] == 82) && temptiles[x][y-1] == 83){
						temptiles[x][y] = 89;
					}
					else if(temptiles[x-1][y] == 86 && temptiles[x][y-1] == 84){
						temptiles[x][y] = 90;
					}
				}
			}
		}
		
		
		/*		case 82:
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
			return Tile.mountainBRC;*/
		//At the end, the temptiles is reduced to tiles to remove the useless map edge
		for(int y = 1 ; y < h-1; y++){
			for(int x = 1 ; x < w-1; x++){	
				tiles[x-1][y-1] = temptiles[x][y];
			}
		}
		//this is to ward off demons
		w =50;
		h =50;
		return tiles;
	}

}
