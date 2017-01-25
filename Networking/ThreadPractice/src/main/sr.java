//Cole Hannel
//SR project simulates threading on java
//Takes no inputs from user and produces the order in which six processes ran

package main;

import main.sr1;
import main.sr2;
import main.process;

public class sr {
	//The two banks for the multithreading system, monitors
	static sr1 r1 = new sr1(); 
	static sr2 r2 = new sr2();

	public static void main(String args[]) 
	{  //Initialize and tunr the six processes.
		process num1 = new process(1, r1, r2);
		process num2 = new process(2, r1, r2);
		process num3 = new process(3, r1, r2);
		process num4 = new process(4, r1, r2);
		process num5 = new process(5, r1, r2);
		process num6 = new process(6, r1, r2);		
		num1.run();
		num2.run();
		num3.run();
		num4.run();
		num5.run();
		num6.run();

	}
}
