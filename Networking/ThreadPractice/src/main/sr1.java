//SR1, one of two monitor classes. SR1 and SR2
//are unctionally identical bu with different resource limits

package main;
class sr1 {
private static int avail1 = 3; //The resource limit

//synchronized method cannot be interrupted 
//only one thread can access at a time 
public synchronized void acquire(int id) 
{ //check if resources, if none then waits until resources have been unlocked via a notify() call
  if(avail1 > 0){
	  System.out.println("process " + id + " acquires SR1");
	  avail1 = avail1 - 1;
  }
  else {
	  while(avail1 == 0){
		try {
			System.out.println("process " + id + " is waiting for SR1");
			wait(); //Gets unlocked by a notify call
			System.out.println("process " + id + " acquires SR1");
			avail1 = avail1 - 1;		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
  }
} 
       
 //synchronized method cannot be interrupted 
 //only one thread can access at a time 
 public synchronized void release(int id) 
 { 
	 //Once a process is done with a resource it releases it back into the wild via notify
	 //There it can be picked up again by some other process in waiting
	 System.out.println("process " + id + " releases SR1");	 
	 avail1 = avail1 + 1;
	 notify();
 }
}