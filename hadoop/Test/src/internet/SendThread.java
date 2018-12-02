package internet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendThread implements Runnable{

	private DatagramSocket socket;
	public SendThread(DatagramSocket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String line = null;
				while((line=reader.readLine())!=null){
					if(line.equals("886")){
						break;
					}
					byte[] bytes = line.getBytes();
					int len = bytes.length;
					DatagramPacket packet = new DatagramPacket(bytes, len,InetAddress.getByName("127.0.0.1"),10086);
					socket.send(packet);
				}
				socket.close();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
