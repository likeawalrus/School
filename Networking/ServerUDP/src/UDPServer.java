import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
//Cole Hannel
//CS156 UDP Server receiver)
//Waits for data, receives it and outputs how much it got and how often it got it
public class UDPServer {
	
	public static void main(String args[]){
		//int portNumber = Integer.parseInt(args[0]);
		int portNumber = 4155;
	    System.out.println("server");
	    boolean unfinished = true;
        try (	//How a UDP socket is made
                DatagramSocket dataSocket = new DatagramSocket(portNumber);
                PrintWriter out = new PrintWriter("results.txt", "UTF-8");                         		
            ) {//UDP requires packets to be premade unlike the TCP thing
        		//which mimicked an I/O stream
        		DatagramPacket packet;
        		byte[] buff = new byte[32768];//Java doesn't seem to let me go bigger
                int x = 1, count = -1;
            	long start = System.currentTimeMillis();
            	long chan = 0;
            	out.println(0);        	
                out.println(1);
        		while(unfinished){
        			packet = new DatagramPacket(buff, 32768);
            		dataSocket.receive(packet);//Receive is like in
            		//it just waits here till it gets something
            		count++;
            		if(x != packet.getLength()){
        	            chan = System.currentTimeMillis() - start;
        	            start = start + chan;//change in time is kept instead of actual time for the sake of convenience
            			out.println(count + " " + chan + " " + packet.getLength());
    	                count = 0;
            			x = packet.getLength();
            			if(packet.getLength() == 3){//If a packet of size 3 is received it closes
            				unfinished = false;
            			}
            		}            		
        		}
                dataSocket.close();
                out.close();
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
        
		
		
	}

}
