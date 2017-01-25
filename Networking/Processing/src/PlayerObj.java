class PlayerObj extends GameObj {
  
  public float angle = 0;
  
  void draw() {
    int s = 32;
    int x = width/2, y = height/2;
    triangle(x,y-s,x+s,y+s,x-s,y+s);
  }
}