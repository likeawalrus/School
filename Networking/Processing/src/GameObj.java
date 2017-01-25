/*
 * empty
 * An empty canvas, waiting for your game-painting
 */

import java.util.Iterator;
import java.util.LinkedList;

// The time at which the current frame started.
long start_time = -1;
long elapsed_time = 0;

class GameObj {
  
  public boolean alive = true;
  
  // Note: dt is in milliseconds
  void simulate(float dt) {
    
  }
  
  void draw() {
    
  }
}

class PlayerObj extends GameObj {
  
  public float angle = 0;
  
  void draw() {
    int s = 32;
    int x = width/2, y = height/2;
    triangle(x,y-s,x+s,y+s,x-s,y+s);
  }
}

LinkedList<GameObj> entities;
LinkedList<GameObj> new_entities;

PlayerObj player;

// Spawn a new object (i.e., add it to the list of new objects)
void spawn(GameObj o) {
  new_entities.add(o);
}

final color BACKGROUND = #ffffff; // Use any color you like

void setup() {
  size(512,512);       // Use any size you like
  background(BACKGROUND); 
  fill(#000000);       // Default fill color
  frameRate(60);      
  
  entities = new LinkedList<GameObj>();
  new_entities = new LinkedList<GameObj>();
  
  player = new PlayerObj();
  entities.add(player);
}

void draw() {
  background(BACKGROUND);
  
  // Update all the objects, physically, based on the timestep from the previous frame.
  for(GameObj o : entities) {
    o.simulate(elapsed_time / 1000000.0);
  }
  
  // Draw all the objects in their new positions (all one of them)
  for(GameObj o : entities) {
    o.draw();
  }
  
  
  // Purge any dead objects.
  for(Iterator<GameObj> i = entities.iterator(); i.hasNext(); ) {
    if(!i.next().alive)
      i.remove();
  }
  
  // Add new objects, and clear the new_entities list for the next frame
  entities.addAll(new_entities);
  new_entities.clear();
  
  // Compute the duration of this frame, for use in the next.
  elapsed_time = System.nanoTime() - start_time;
  start_time = System.nanoTime();
}

// Event handlers:
// The available event handlers include keyPressed, keyReleased, mousePressed, mouseReleased,
// mouseClicked (press-and-release sequence), mouseMoved, mouseDragged (moved with button pressed),
// and mouseWheel. Implement all that you need; see the reference for details on how they work.

void keyPressed() {
  if(keyCode == LEFT) 
    player.angle -= 1;
  else if(keyCode == RIGHT)
    player.angle += 1;
}  