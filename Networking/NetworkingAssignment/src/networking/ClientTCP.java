package networking;



import java.io.*;
import java.net.*;

public class ClientTCP extends Thread{
	private Thread t;//Threading for simplicity
	private String threadName = "clientthread";
	private String hostName;
	private int portNumber;
	
	public ClientTCP (String name, int pnumb){
			portNumber = pnumb;//port and host are gotten from NetCore
			hostName = name;
		}
	
    public void run() {
        String as = "a";
	    System.out.println("client");
        try (
            Socket tcpSocket = new Socket(hostName, portNumber);//Creates a TCP socket
            PrintWriter out = new PrintWriter(tcpSocket.getOutputStream(), true);
            PrintWriter inptime = new PrintWriter("inptime.txt", "UTF-8");                   
	
        ) {
        	long start = System.currentTimeMillis();
        	long chan = 0;
        	inptime.println(chan);
        	inptime.println(1);
        	int x = 1;
	        for(int i = 0; i <= 25; i++){//number of times to increase packet size
	        	for(int j = 0; j < 100; j++){
	        		out.println(as);//sends packets to the receiver
	        	}
	        	as += as;//packet size increase
	            chan = System.currentTimeMillis() - start;//now minus a bit ago
	            inptime.println(chan);
	            inptime.println(x);//Change in time is stored, not time because it was getting unwieldly
	            start = start + chan;//The new old
	            x = x*2;//x is used in the file as opposed to the actual string because it was making the text files lag

	        }
            chan = System.currentTimeMillis() - start;//now minus a bit ago
            inptime.println(chan);	        
	        out.close();//Close sockets
            tcpSocket.close();
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
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
