//SR2, one of two monitor classes. SR1 and SR2
//are functionally identical but with different resource limits

package main;

class sr2 {
	private static int avail2 = 2; //The resource limit of SR2

	//synchronized method cannot be interrupted 
	//only one thread can access at a time 
	public synchronized void acquire(int id) 
	{ 		//If a resource is available, take it, if not, wait until one is available
		  if(avail2 > 0){
			  System.out.println("process " + id + " acquires SR2");
			  avail2 = avail2 - 1;
		  }
		  else{
			  while(avail2 == 0){
				try {
					System.out.println("process " + id + " is waiting for SR2");
					wait();
					System.out.println("process " + id + " acquires SR2");
					avail2 = avail2 - 1;
					
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
		//Here a resourcce previousl acquired b a process is released
		 avail2 = avail2 + 1;
		 System.out.println("process " + id + " releases SR2");
		 notify();    
	} 

}
