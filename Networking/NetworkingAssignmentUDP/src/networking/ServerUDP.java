package networking;

import java.net.*;
import java.io.*;

public class ServerUDP extends Thread {
	private Thread t;
	private String threadName = "serverthread";
	int portNumber;
	
	public ServerUDP(int numb){
		portNumber = numb;
	}
	
	public void run() {	
	    System.out.println("server");
	    boolean unfinished = true;
        try (
                DatagramSocket dataSocket = new DatagramSocket(portNumber);
                PrintWriter out = new PrintWriter("results.txt", "UTF-8");                         		
            ) {
        		DatagramPacket packet;
        		byte[] buff = new byte[4096];
                int x = 0, count = 0;
        		while(unfinished){
        			packet = new DatagramPacket(buff, 4096);
            		dataSocket.receive(packet);
            		count++;
            		if(x != packet.getLength()){
            			out.println(packet.getLength() + " " + count);
    	                out.println(System.currentTimeMillis());
    	                count = 0;
            			x = packet.getLength();
            			if(packet.getLength() == 3){
            				unfinished = false;
            			}
            		}
        		}
                dataSocket.close();
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
	
	public static void main(){
		
	}
	
}
