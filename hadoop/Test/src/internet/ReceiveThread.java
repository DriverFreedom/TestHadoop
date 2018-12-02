package internet;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiveThread implements Runnable{
	private DatagramSocket socket;
	public ReceiveThread(DatagramSocket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
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
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
