//The actual Process class
//Takes one each of SR1 and SR2, then returns them

package main;

class process extends Thread //class name is "process"
{ static sr1 r1; 
  static sr2 r2;
  private int id; 

  public process(int k, sr1 r11, sr2 r22) //constructor 
  { 
	r1 = r11; //I have a strong dislike for warning messages so I changed this from the original
    r2 = r22; //It should functionally be the same
    id = k;
    } 
 
   public void run() 
   { //Fairly straight forward. Acquires one of each resource, and returns it.
	  r1.acquire(id);
	  r2.acquire(id);
	  System.out.println("process " + id + " is working");
	  r1.release(id);
	  r2.release(id);   
    } 
}//class process 
