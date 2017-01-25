/*
 * creatures
 * Base Creature class and any subclasses you care to add. In this file, the functions/methods you will need 
 * to modify are:
     Creature.accelerate -- determines how creatures react to each other, in terms of attraction/repulsion.
     Creature.pursue, Creature.flee -- determines the force(s) applied when a creature pursues or flees from
                                       another creature. 
     Note that you may change these in Creature, or subclass Creature and change them in your subclasses. E.g.,
     you can use the same attractive/repulsive forces for all creatures, or change them per subclass (or even
     based on the target creature)
     
     CREATURE_TYPES -- Change this to the number of creature types that exist in your system. The simulation
                       will sometimes randomly spawn a creature of random type, and needs to know how many 
                       such types there are.
                       
     collide(a,b) -- Handles creature-creature collision. You will need to edit this to specify what happens
                     when each pair of creature types collides with each other (e.g., a predator might kill
                     a prey creature on collision).
                     
     creatureByType(t) -- factory function that creates and returns a Creature by its type-index. You will 
                          need to update this as you add additional types of creatures.
 */

// Change this to reflect how many types of creatures you've created (that you want the system to automatically spawn).
final int CREATURE_TYPES = 3;

// --------------------------------------------------------
// Creature spawning probabilities
float[] spawnFreq = {
  13,  // Prey
  1,  // Predator
  2,  // Leader
};

// --------------------------------------------------------
// Creature-creature collision.
// This function is called whenever two creatures collide.
// You will need to fill it in to determine what happens when two creatures collide, for each
// possible pair of creature-types that you care about.
void collide(Creature a, Creature b) {
  if(a.type == PREY && b.type == PREDATOR || a.type == LEADER && b.type == PREDATOR) {
    Predator p = (Predator)b;
    if(a.kill()){
      if(a.type == PREY){
        Prey c = (Prey)a;
        if(c.myLeader != null){
          Leader leading = (Leader)c.myLeader;
          leading.followers -= 1;
        }
      }
      p.health += 180;
    }
    p.target = null;
  }
  else if(a.type == PREY && b.type == PREY){
    
  }
  // The collide loop tests every pair of creatures against each other, so the effects of a 
  // predator A and prey B will be handled automatically, you don't need to test for it. 
}

// ---------------------------------------------------------
// Create a new creature by type. 
// You will have to modify this as you add creature subclasses. If you want to create a creature-type
// that is never spawned by the system, just don't add it to this function. (You'll probably still want
// to give it a unique type value, however.)
Creature creatureByType(int t) {
  switch(t) {
    case PREY: return new Prey();
    case PREDATOR: return new Predator();
    case LEADER: return new Leader();
    default: return null;
  }
}


/* --------------------------------------------------------------------------------------------------------------------
 * Creature
 * Base class for moving, living things
 */
class Creature {
  int type = -1; // Should be set in the constructor to a distinct value for each subclass.
  
  boolean alive = true;
  
  // Display attributes
  int radius = 4; 
  color c = #dddddd; // Light gray. Each type should have a different color
  
  float maxVelocity = 0.1; // px/ms 
  
  float x,y;   // Position
  float vx,vy; // Velocity
  float ax,ay; // Acceleration
  
  void simulate(float dt) {
    x += vx * dt;
    y += vy * dt;
    
    vx += ax * dt;
    vy += ay * dt;
    
    // Limit max velocity
    float l2 = sq(vx) + sq(vy);
    if(l2 > maxVelocity*maxVelocity) {
      l2 = sqrt(l2);
      vx *= maxVelocity / l2;
      vy *= maxVelocity / l2;
    }
    
    // Collide with the edges of the screen
    collide(this, dt);
    
    // Compute acceleration from scratch.
    ax = ay = 0;
    
    accelerate(dt);
  }
  
  // Draw the object
  void draw() {
    fill(c);
    noStroke();
    ellipse(x,y,radius,radius);
  }
    
  boolean kill() {
    alive = false;
    return !alive;
  }
  
  
  void accelerate(float dt) {
    /*
    for(Creature o : creatures) {
      if(o != this) {
        // react to o
      }
    } 
    */
  }
  
  // Add a force to ax,ay to pursue (be attracted to) the target creature
  void pursue(Creature target, float strength) {
    flee(target,-strength);   
  }
  
  // Add a force to ax,ay to flee (be repelled by) the target creature
  void flee(Creature target, float strength) {
    
    // Find the vector pointing away from the target, and normalize it.
    float dx = x - target.x;
    float dy = y - target.y;
    float l = dist2(0,0,dx,dy);
    if(l != 0) {
      l = sqrt(l);
      dx /= l;
      dy /= l;
    }
    
    ax += dx * strength / l;
    ay += dy * strength / l;
  }
}

final int PREY = 0;
class Prey extends Creature {
  // Prey can be invlunerable for a short time at the beginning of their lives.
  float invul = 0;
  boolean targeted = false;
  float inibrate =  6000 + random(-1000, 3000);
  float birthrate = inibrate + random(-4000, -1000);
  float lifespan = 14000 + random(-2000, 1000);
  float health = lifespan;  
  float subbrate = 3500;
  int birthnum = 4;
  Creature myLeader = null;
  Prey() {
    type = PREY;
    c = #dddddd;
    maxVelocity = 0.1; // Normal
  }
  
  void simulate(float dt) {
    super.simulate(dt);
    birthrate -= (dt);
    health -= (dt/2);
    invul = max(invul - dt, 0);
    if(health < 0) {
      if(kill()) {
        //considering plant/fungi
      }
    }
      if(0 > birthrate) {        
        for(int i = 0; i < birthnum; i++){
          Prey c = new Prey(); 
          c.x = x;
          c.y = y;
          c.vx = -vx + random(-0.1,0.1);
          c.vy = -vy + random(-0.1,0.1);
          c.maxVelocity = maxVelocity + random(-0.05, 0.05);
          c.inibrate = min(2800, inibrate + random(-500, 500));
          c.birthrate = c.inibrate;
          c.birthnum = max(0, birthnum + int(random(-1, 1)));
          c.subbrate = min(1800, subbrate + random(-100, 100));
          c.lifespan = lifespan + random(-500, 500);
          c.health = c.lifespan;
          spawn(c);
        }
        birthrate = subbrate;
      }
  }
  
  boolean kill() {
    if(invul > 0)
      return false;
    else {
      alive = false;
      return !alive;
    }
  }   
  
  void accelerate(float dt) {
    
    for(Creature o : creatures) {
      if(o != this) {
        if(o.type == PREY)// && dist2(x,y,o.x,o.y) < 1) 
          continue;//flee(o,0.0001); // very slight repulsion
        else if(o.type == LEADER && myLeader == null && dist2(x,y,o.x,o.y) < 64*64){
          Leader b = (Leader)o;
          if(b.followers < 20){
            myLeader = b;
            b.followers += 1;
            pursue(o,0.2); // Strong attraction
          }
        }
        else if(o.type == LEADER && o == myLeader)
          pursue(o,0.11);
        else if(dist2(x,y,o.x,o.y) < 32*32)
          flee(o,0.1); // Strong repulsion
      }
    } 
    
  }
}

final int PREDATOR = 1;
class Predator extends Creature {
  Prey target = null;
  float inibrate =  5000 + random(-1000, 2000);
  float birthrate = inibrate;
  float health = 3000 + random(-2000, 1000);
  float subbrate = 2500;
  float inilifespan =  16000 + random(-3000, 3000);
  float lifespan = inilifespan;
  int birthnum = 1;
  Predator() {
    type = PREDATOR;
    c = #bb2222;
    maxVelocity = 0.3; // Faster
//    health = 4500; // Add some variation to lifespan
  }  
  
  void simulate(float dt) {
    super.simulate(dt);
    
    health -= (dt);
    birthrate -= (dt);
    lifespan -= (dt/2);
    if(health < 0 || lifespan < 0) {
      if(kill()) {
        // Spawn some new prey upon death
        /*for(int i = 0; i < 15; i++) {
          Prey c = new Prey();
          c.x = x + random(-30,30);
          c.y = y + random(-30,30);
          c.vx = vx + random(-0.1,0.1);
          c.vy = vy + random(-0.1,0.1);
          c.maxVelocity = 0.3;
          c.invul = 100;
          spawn(c);
        }*/
      }
    }      
      // Give birth to a child?
      if(0 > birthrate) {        
        for(int i = 0; i < birthnum; i++){
          Predator c = new Predator(); 
          c.x = x;
          c.y = y;
          c.vx = -vx + random(-0.1,0.1);
          c.vy = -vy + random(-0.1,0.1);
          c.maxVelocity = maxVelocity + random(-0.1, 0.1);          
          c.inibrate = min(3000, inibrate + random(-500, 500));
          c.birthrate = c.inibrate;
          c.birthnum = max(1, birthnum + int(random(-1, 1)));
          c.subbrate = min(2000, subbrate + random(-500, 500));
          c.health = min(1500, health + random(-1000, 0));
          c.inilifespan = inilifespan + random(-1000, 1000);
          c.lifespan = c.inilifespan;
          spawn(c);
          health -= 100;
        }
        birthrate = subbrate;
      }
  }
  
  void accelerate(float dt) {    
    for(Creature o : creatures) {
      if(o != this) {
        if(target != null && target.alive == true){
          pursue(o,0.1); // Strong attraction          
        }/*
        else if(o.type == PREY && dist2(x,y,o.x,o.y) < 16*16){
          Prey pr = (Prey)o;
          if(!pr.targeted){
            target = (Prey)o;
            pr.targeted = true;
          }
          pursue(o,0.1); // Strong attraction

        }*/
        else if(o.type == PREY && dist2(x,y,o.x,o.y) < 32*32)
          pursue(o, 0.5);        
        else if(o.type == PREY)
          pursue(o, 0.1);
        else
          if(o.type == PREDATOR && dist2(x,y,o.x,o.y) < 64*64) 
            flee(o,0.2); // Slight repulsion, if too close
      }
    }    
  }
}

final int LEADER = 2;
class Leader extends Creature {
  // Prey can be invlunerable for a short time at the beginning of their lives.
  float invul = 0;
  int followers = 0;
  Leader() {
    type = LEADER;
    c = #dddddd;
    maxVelocity = 0.3; // Slightly faster than normal
  }
  
  void simulate(float dt) {
    super.simulate(dt);
    
    invul = max(invul - dt, 0);
  }
  
  boolean kill() {
    if(invul > 0)
      return false;
    else {
      alive = false;
      return !alive;
    }
  }   
  
  void accelerate(float dt) {
    
    for(Creature o : creatures) {
      if(o != this) {
        if(o.type == PREY) 
          continue; // ignore
        else if(o.type == PREDATOR && dist2(x,y,o.x,o.y) < 96*96)
          flee(o,0.2); // Strong repulsion
      }
    } 
    
  }
}
  