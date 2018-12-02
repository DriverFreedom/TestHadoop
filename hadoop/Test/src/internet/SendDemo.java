package internet;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SendDemo {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		 // 创建发送端Socket对象
        // DatagramSocket()
        DatagramSocket ds = new DatagramSocket();

        // 创建数据，并把数据打包
        // DatagramPacket(byte[] buf, int length, InetAddress address, int port)
        // 创建数据
        byte[] bys = "hello,udp,我来了".getBytes();
        // 长度
        int length = bys.length;
        // IP地址对象
        InetAddress address = InetAddress.getByName("127.0.0.1");
        // 端口
        int port = 10086;
        DatagramPacket dp = new DatagramPacket(bys, length, address, port);

        // 调用Socket对象的发送方法发送数据包
        // public void send(DatagramPacket p)
        ds.send(dp);

        // 释放资源
        ds.close();
		
	}
}
