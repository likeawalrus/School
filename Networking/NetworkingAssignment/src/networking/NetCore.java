//Cole Hannel
//CSCI 156 Program to test TCP method

package networking;


import networking.ClientTCP;
import networking.ServerTCP;
import java.io.*;

public class NetCore {
	
	public static void main(String[] args) throws IOException {
		//This just starts the two threads for the TCP program
		ServerTCP x = new ServerTCP(4101);
		ClientTCP y = new ClientTCP("localhost", 4101);
		x.start();
		y.start();
		
	}

}
