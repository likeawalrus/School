package ghp.Dungeon_Tactics.grid;

import ghp.Dungeon_Tactics.main.Game;
import ghp.Dungeon_Tactics.main.levels.Level;

public class Grid_Driver {
	
	public static Grid G = new Grid();
	
	public Grid_Driver(){
		// Generate Grid
		
		G.Initialize(20, 20);
		G.SetHero(303, "Skeleton1");
		G.SetHero(910, "Skeleton2");
		G.SetHero(1010, "Frodo");

		
		

		G.SetHero(0, "Sam");
		G.SetHero(1011, "Shelob");
		G.SetHero(1110, "Golem");
		G.setcharfocus("Frodo");

		
		//G.FindChar("Gandalf");
		//G.ClearTile(2, 1);
		//G.FindChar("Elmo");
		//G.ListTargets(303, 3);
		//G.InRange(303, 306, 3);
	}
	public static Grid getGrid(){
		return G;
	}
	
}

