package internet;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ThreadMain {

	public static void main(String[] args) throws Exception {
		DatagramSocket s1 = new DatagramSocket();
		DatagramSocket s2 = new DatagramSocket(10086);
		SendThread send = new SendThread(s1);
		ReceiveThread receive = new ReceiveThread(s2);
		Thread t1 = new Thread(send);
		Thread t2 = new Thread(receive);
		t1.start();
		t2.start();
	}
	
	
}
