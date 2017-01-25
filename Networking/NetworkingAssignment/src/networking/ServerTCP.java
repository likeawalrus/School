package networking;

import java.net.*;
import java.io.*;

public class ServerTCP extends Thread {
	private Thread t;
	private String threadName = "serverthread";
	private int portNumber;
	
	public ServerTCP(int numb){
		portNumber = numb;//the portNumber is gotten from NetCore
	}
	//Runs the server one time. Waits for data from client and then prints information to
	//resuts.txt as it gets it
	public void run() {	
	    System.out.println("server");
        try (//Simple method for starting a socket on port portNumber
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();//Waits for a client to send it something     
                PrintWriter out = new PrintWriter("results.txt", "UTF-8");                   
                BufferedReader in = new BufferedReader(
                		new InputStreamReader(clientSocket.getInputStream()));
        		
            ) {
                String inputLine, oldInput = "a";//For initial comparison of received stuff
                int x = 1;//For results file purposes
                long start = System.currentTimeMillis();
                long chan = 0;
                out.println(chan);
                out.println(1);
	                while ((inputLine = in.readLine()) != null) {
	                    if(!oldInput.equals(inputLine)){
	                    	chan = System.currentTimeMillis() - start;
	    	                out.println(chan);
	    	                out.println(x);
	                    	x=x*2;//Since this is TCP we always know what size the packets will be
	    	                start = start + chan;
	    	                oldInput = inputLine;
	                    }
                }
               	chan = System.currentTimeMillis() - start;
                out.println(chan);	                
                out.close();
                clientSocket.close();
                
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
        
	}
	
	public void start(){
	      if (t == null)
	      {
	         t = new Thread (this, threadName);
	         t.start ();
	      }		
		
	}
	
}
