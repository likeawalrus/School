package networking;



import java.io.*;
import java.net.*;

public class ClientUDP extends Thread{
	private Thread t;
	private String threadName = "clientthread";
	private int portNumber;
	private String hostName;
	
	public ClientUDP (String name, int pnumb){
			portNumber = pnumb;
			hostName = name;
		}
	
    public void run() {
        String as = "a";
	    System.out.println("client");
        try (
            DatagramSocket dataSocket = new DatagramSocket(4105);
            PrintWriter inptime = new PrintWriter("inptime.txt", "UTF-8");                   	
        ) {
        	InetAddress address = InetAddress.getLocalHost();         	
        	inptime.println(System.currentTimeMillis());        	
        	int x = 1;
        	byte[] buf = new byte[1];
        	buf = as.getBytes();
        	DatagramPacket packet;
	        for(int i = 0; i <= 25; i++){
	        	packet = new DatagramPacket(buf, x, address, portNumber);
	        	for(int j = 0; j < 100; j++){
	        		dataSocket.send(packet);
	        	}
	        	as += as;
	            inptime.println(x);
	            x = x*2;
	            buf = new byte[x];
	            buf = as.getBytes();
	            
	            inptime.println(System.currentTimeMillis());
	        }
	        buf = new byte[3];
        	packet = new DatagramPacket(buf, 3, address, portNumber);	        
    		dataSocket.send(packet);	        
            dataSocket.close();
            
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
