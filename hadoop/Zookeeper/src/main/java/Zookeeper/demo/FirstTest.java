package Zookeeper.demo;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.annotation.XmlList;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
public class FirstTest {
	private static final String connectString = "hadoop01:2181,hadoop02:2181,hadoop03:2181";
	private int sessionTimeout = 2000;
	ZooKeeper zkClient = null;
	
	@Before
	public void init() throws Exception{
		zkClient = new ZooKeeper(connectString, sessionTimeout, null);
		
	}
	
	/**
	 * data insert
	 * @throws Exception
	 */
	
	@Test
	public void testCreate() throws Exception{
		zkClient.create("/w", "eclipse".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

	}
	
	/**
	 * get child data
	 */
	@Test
	public void getChildren() throws Exception{
		List<String> list = zkClient.getChildren("/", false);
		for (String string : list) {
			System.out.println(string);
		}
	}
	
	/**
	 * the data isExits?
	 */
	@Test
	public void testExits() throws Exception{
		Stat exists = zkClient.exists("/a", false);
		System.out.println(exists);
	}
	
	/**
	 * get data
	 * @throws Exception  
	 */
	@Test
	public  void testGetData() throws  Exception{
		byte[] data = zkClient.getData("/a", false, null);
		System.out.println(new String(data));
		
	}
	
	/**
	 * delete data
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	@Test
	public void deleteData() throws InterruptedException, Exception{
		zkClient.delete("/eclipse0000000002", -1);
	}
	
	/**
	 * update data
	 * @throws Exception 
	 * @throws KeeperException 
	 */
	@Test
	public void setData() throws KeeperException, Exception{
		zkClient.setData("/w", "hello".getBytes(), -1);
	}
	/**
	 * listener
	 * @throws Exception 
	 * @throws KeeperException 
	 */
	@Test
	public void watch() throws KeeperException, Exception{
		List<String> list = zkClient.getChildren("/", true);
		for (String string : list) {
			System.out.println(string);
		}
		Thread.sleep(2*60*1000);
	}
	
	/**
	 * release source
	 * @throws Exception 
	 */
	@After
	public void close() throws Exception{
		zkClient.close();
	}
	
	
	
	
	
	
	
}
