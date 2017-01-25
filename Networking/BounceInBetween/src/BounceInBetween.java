import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
//Cole Hannel
//CS 156 Networking project

public class BounceInBetween {

	public static void main(String args[]){
		//int portNumber = Integer.parseInt(args[0]);
		int portNumber = 4125;//The in-betweens port number
	    System.out.println("server");
	    boolean unfinished = true;
        try (	//making the udp inbetween socket
                DatagramSocket dataSocket = new DatagramSocket(portNumber);
            ) {
        		DatagramPacket packet;
        		byte[] buff = new byte[32768];//has same buffer as receiver
        		while(unfinished){
        			packet = new DatagramPacket(buff, 32768);
            		dataSocket.receive(packet);
            		packet.setPort(4155);//the final receiver
            		//takes packet, changes destination, and sends 
            		//it back out into the wild
            		dataSocket.send(packet);
            		if(packet.getLength() == 3){//closes the in-between when last
            			unfinished = false;		//packet is gotten
            		}
            	}            		
                dataSocket.close();
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
	}
}
