package Zookeeper.demo;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class DistributedServer {

	private static final String connectString= "hadoop01:2181,hadoop02:2181,hadoop03:2181"; 
	private static final int sessionTimeout = 2000;
	ZooKeeper zkClient = null;
	private static final String parentName = "/servers";
	public void connect() throws Exception{
		zkClient = new ZooKeeper(connectString, sessionTimeout, null);
	}
	
	public void register(String hostName) throws Exception{
		String create = zkClient.create(parentName+"/server", hostName.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(hostName +" is registed in "+create);
		
	}
	
	public void Server(String hostName) throws Exception{
		System.out.println(hostName + "is working");
		Thread.sleep(20000);
	}
	public static void main(String[] args) throws Exception {
		DistributedServer server = new DistributedServer();
		server.connect();
		server.register(args[0]);
		server.Server(args[0]);
	}
}
