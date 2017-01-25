package ghp.Dungeon_Tactics.grid;

public class Tile {
	// Variables that a Tile will contain
	int LocX = 0; // x-y tile location
	int LocY = 0;
	String CharName = "NONE"; // Character name
	boolean Occupied = false; // true if tile contains a character
	boolean Visable = true; // true if visable
	boolean Passable = true; // true if passable
	boolean Item = false; // true if this tile has an item(s)
	
}
