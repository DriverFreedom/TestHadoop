package internet;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressDemo {

	public static void main(String[] args) throws Exception {
		InetAddress address = InetAddress.getByName("hadoop01");
		String name = address.getHostName();
		String ip = address.getHostAddress();
		System.out.println(name+"  "+ip);
	}
}

