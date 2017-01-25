import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
//Cole Hannel
//CS156 UDP Client(sender)
//Sends data to a server(receiver) until such time as it is out of data
//Tracks time it took to send and the size of stuff sent

public class BounceClient {
	//
	public static void main(String args[]){
		int portNumber = 4125;//To send
		String hostName = "localhost";//Here for the sake of simplicity
        String as = "a";//Base file that's going to get sent
	    System.out.println("client");
	    
        try (
            DatagramSocket dataSocket = new DatagramSocket();//Starts a UDP socket
        		
            PrintWriter inptime = new PrintWriter("inptime.txt", "UTF-8");                   	
        ) {
        	InetAddress address = InetAddress.getLocalHost();         	
        	long start = System.currentTimeMillis();
        	long chan = 0;//tracks change in time rather than time itself
        	inptime.println(0);        	
        	int x = 1;
        	byte[] buf = new byte[1];//needed for UDP protocol, buffer of file going to be sent
        	buf = as.getBytes();//string to byte
        	DatagramPacket packet;        	
	        for(int i = 0; i <= 15; i++){
	        	packet = new DatagramPacket(buf, x, address, portNumber);
	        	for(int j = 0; j < 1000; j++){
	        		dataSocket.send(packet);//Sends 1000 udp packets to get a better feel for how much is lost
	        	}
	        	as += as;//Doubles packet size
	            inptime.println(x);
	            x = x*2;
	            buf = new byte[x];
	            buf = as.getBytes();
	            chan = System.currentTimeMillis() - start;
	            inptime.println(chan);
	            start = start + chan;
	            inptime.println("/n");
	        }
	        buf = new byte[3];//This is sent to close the connection
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
	
}
