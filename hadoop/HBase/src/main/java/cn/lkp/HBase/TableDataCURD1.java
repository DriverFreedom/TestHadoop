package cn.lkp.HBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class TableDataCURD1 {
	Table table;
	Connection connection;
	@Before
	public void init() throws Exception{
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "hadoop01:2181,hadoop02:2181,hadoop03:2181");
		connection = ConnectionFactory.createConnection(conf);
		//table = connection.getTable(TableName.valueOf("t_people"));
		table = connection.getTable(TableName.valueOf("word"));
	}
	/**
	 * 添加数据
	 * @throws Exception
	 */
	@Test
	public void testPutDate() throws Exception{
		
		
		/*Put put = new Put(Bytes.toBytes("rk002"));
		put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("name"), Bytes.toBytes("lisi"));
		put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("age"), Bytes.toBytes("18"));
		Put put2 = new Put(Bytes.toBytes("rk004"));
		put2.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("name"), Bytes.toBytes("lisi"));
		put2.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("age"), Bytes.toBytes("18"));
		List<Put> list = new ArrayList<>();
		list.add(put);
		list.add(put2);
		table.put(list);*/
		Put put1 = new Put(Bytes.toBytes("rk001"));
		put1.addColumn(Bytes.toBytes("col"), Bytes.toBytes("line"), Bytes.toBytes("hello world hello java how are you"));
		Put put2 = new Put(Bytes.toBytes("rk002"));
		put2.addColumn(Bytes.toBytes("col"), Bytes.toBytes("line"), Bytes.toBytes("hello world hello java how are you"));
		Put put3 = new Put(Bytes.toBytes("rk003"));
		put3.addColumn(Bytes.toBytes("col"), Bytes.toBytes("line"), Bytes.toBytes("hadoop hello you hbase"));
		List<Put> list = new ArrayList<>();
		list.add(put1);
		list.add(put2);
		list.add(put3);
		table.put(list);
		table.close();
		connection.close();

	}
	/**
	 * 删除数据
	 * @throws Exception
	 */
	@Test
	public void testdeleteDate() throws Exception{
		Delete del= new Delete(Bytes.toBytes("rk002"));
		table.delete(del);
	}
	@After
	public void last() throws Exception{
		table.close();
		connection.close();
	}

}
