package cn.lkp.HBase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.junit.Test;

/**
 * hbase表的增删
 * @author hasee
 *
 */
public class TableAdmin {

	/**
	 * 创建表
	 * @throws Exception 
	 */
	@Test
	public void testCreateTable() throws Exception{
		//创建一个连接
		//Hadoop里边的conf,会加载hdfs-site.xml  core-site.xml。mapred-site.xml   等配置文件 
		//Configuration conf = new Configuration();
		//加载hbash-site.xml和hadoop的文件
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "hadoop01:2181,hadoop02:2181,hadoop03:2181");
		Connection connection = ConnectionFactory.createConnection(conf); 
		//从连接中获取表管理对象
		Admin admin = connection.getAdmin();
		//表描述 表名
		HTableDescriptor table1 = new HTableDescriptor(TableName.valueOf("word"));
		HTableDescriptor table2 = new HTableDescriptor(TableName.valueOf("stat"));
		//列簇
		HColumnDescriptor descriptor1 = new HColumnDescriptor("col");
		HColumnDescriptor descriptor2 = new HColumnDescriptor("ret");
			//descriptor2.setMaxVersions(3);
		//HColumnDescriptor descriptor3 = new HColumnDescriptor("family");
		table1.addFamily(descriptor1);
		table2.addFamily(descriptor2);
		//descriptor.addFamily(descriptor3);	
		//创建表
		admin.createTable(table1);
		admin.createTable(table2);
		
		
		//关闭资源
		admin.close();
		connection.close();
		
	}
	/**
	 * 删除表
	 * @throws Exception 
	 */
	@Test
	public void deleteTable() throws Exception{
		
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "hadoop01:2181,hadoop02:2181,hadoop03:2181");
		Connection connection = ConnectionFactory.createConnection(conf); 
		//从连接中获取表管理对象
		Admin admin = connection.getAdmin();
		admin.disableTable(TableName.valueOf("t_people"));
		admin.deleteTable(TableName.valueOf("t_people"));
		
		
		admin.close();
		connection.close();
	}
	
}

