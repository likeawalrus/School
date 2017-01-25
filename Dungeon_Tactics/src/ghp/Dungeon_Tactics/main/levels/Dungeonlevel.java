package ghp.Dungeon_Tactics.main.levels;

import java.util.Random;

	public class Dungeonlevel {

		public int[][] tiles;
		public int[][] temptiles;
		public int w, h, watersides, riverrand, maxsize, maxheight;
		public String mapname;
		
		
		public Dungeonlevel() {
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
				//In the beginning, there was absolute darkness.	
				temptiles[x][y]=80;	
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
			//now it is time for dungeon. Years and years of rpg experience tells me that they
			//are somewhat blockish and contain stone, dirt, and monsters.
			for(int y = 2 ; y < (h-2); y++){
				for(int x =2 ; x < (w-2); x++){
					if(rand.nextInt(1000) > 980){	
						temptiles[x][y]=69;
					}
				}
			}			
			//tile 70 is also dirt, but it is being used as a placeholder for the dirt expansion
			for(int z = 0; z < 6; z++){
				for(int y = 1 ; y < (h-1); y++){
					for(int x = 1 ; x < (w-1); x++){	
						if((temptiles[x-1][y]==69 || temptiles[x+1][y]==69 || temptiles[x][y+1]==69 || temptiles[x][y-1]==69) 
								&& (rand.nextInt(1000) > 400)){
							temptiles[x][y]=70;
						}
					}
				} 
				for(int y = 1 ; y < h-1; y++){
					for(int x = 1 ; x < w-1; x++){	
						if(temptiles[x][y]==70){
							temptiles[x][y]=69;
						}
					}
				}
			}
			for(int y = 2 ; y < (h-2); y++){
				for(int x =2 ; x < (w-2); x++){
					if(rand.nextInt(1000) > 990 && temptiles[x][y] == 69 || temptiles[x][y] == 70){	
						temptiles[x][y]=81;
					}
				}
			}				
			for(int y = 1 ; y < (h-1); y++){
				for(int x = 1 ; x < (w-1); x++){	
					if((temptiles[x-1][y]==81 || temptiles[x+1][y]==81 || temptiles[x][y+1]==81 || temptiles[x][y-1]==81 ||
							temptiles[x-1][y-1] == 81 || temptiles[x-1][y+1] == 81
							|| temptiles[x+1][y-1] == 81 || temptiles[x+1][y+1] == 81)){
						temptiles[x][y]=69;
					}
				}
			} 
			
			
			//makes ground randomer
			for(int y = 1 ; y < (h-1); y++){
				for(int x = 1 ; x < (w-1); x++){	
					if((temptiles[x][y] == 69)){
						temptiles[x][y]=69 + rand.nextInt(6);
					}
				}
			} 
			
			
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
