package Zookeeper.demo;

import java.awt.List;
import java.util.ArrayList;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * 1.注册
 * 2.遍历servers
 * 3.监控
 * @author hasee
 *
 */
public class DistributedClient {
	
	private static final String connectString= "hadoop01:2181,hadoop02:2181,hadoop03:2181"; 
	private static final int sessionTimeout = 2000;
	ZooKeeper zkClient = null;
	private static final String parentName = "/servers";
	private volatile ArrayList<String> list ;
	public void connect() throws Exception{
		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				try {
					getList();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}
	
	
	public  void getList() throws Exception{
		java.util.List<String> list2 = zkClient.getChildren(parentName, true);
		ArrayList<String> serverList = new ArrayList<>();
		for (String key : list2) {
			byte[] data = zkClient.getData(parentName+"/"+key, false, null);
			serverList.add(new String(data));
			
		}
		list = serverList;
		System.out.println(list);
	}
	
	public void handleServer() throws Exception{
		System.out.println("zkClient is running");
		Thread.sleep(10000000);
	}
	
	public static void main(String[] args) throws Exception {
		DistributedClient client = new DistributedClient();
		client.connect();
		client.getList();
		
		client.handleServer();
		}
}
