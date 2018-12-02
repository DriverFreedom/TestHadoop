package internet;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Receive_2 {

	public static void main(String[] args) throws Exception {
		DatagramSocket socket = new DatagramSocket(10086);
		while(true){
			byte[] bytes = new byte[1024];
			int len = bytes.length;
			DatagramPacket packet = new DatagramPacket(bytes,len);
			socket.receive(packet);
			String ip = packet.getAddress().getHostAddress();
			System.out.println(ip+":");
			byte[] data = packet.getData();
			String str = new String(data, 0, data.length);
			System.out.println(str);
		}
		
	}
}
