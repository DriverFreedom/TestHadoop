package cn.lkp.HBase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.MultipleColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TableDataCURD2 {
	private Table table;
	
	@Before
	public void init() throws Exception{
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.1.3:2181,192.168.1.4:2181,192.168.1.5:2181");
		Connection connection = ConnectionFactory.createConnection(conf); 
		
		table = connection.getTable(TableName.valueOf("t_people"));
	}
	/**
	 * 单行查询
	 * @throws Exception 
	 * @throws Exception 
	 */
	@Test
	public void testQuery() throws Exception{
		Get get = new Get(Bytes.toBytes("rk001"));
		get.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("name"));
		Result result = table.get(get);
		byte[] value = result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("name"));
		System.out.println(Bytes.toString(value));
		byte[] value2 = result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("age"));
		System.out.println(Bytes.toString(value2));
	}
	
	/**
	 * 多行查询
	 * @throws Exception 
	 */
	@Test
	public void teseScanDate() throws Exception{
		Scan scan = new Scan();
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			System.out.println(Bytes.toString(result.getRow()));
			byte[] bs = result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("name"));
			System.out.println(Bytes.toString(bs));
			byte[] bs2 = result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("age"));
			System.out.println(new String(bs2));
			System.out.println("-------------------------------------------------");
		}
	}
	
	/**
	 * 列值过滤器
	 * @throws Exception 
	 */
	@Test
	public void scanData() throws Exception{
		Scan scan = new Scan();
		//列值过滤	参数:列簇名 列名  比较符  值
		SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("base_info"), Bytes.toBytes("name"), CompareOp.EQUAL, Bytes.toBytes("zhangsan"));
		scan.setFilter(filter);
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			System.out.println(Bytes.toString(result.getRow()));
			byte[] bs = result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("name"));
			System.out.println(Bytes.toString(bs));
			byte[] bs2 = result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("age"));
			System.out.println(new String(bs2));
		}
	}
	/**
	 * 列名前置过滤器
	 * @throws Exception 
	 * @throws Exception
	 */
	@Test
	public void testScanByColumn() throws Exception{
		Scan scan = new Scan();
		//列值过滤	参数:列簇名 列名  比较符  值
		ColumnPrefixFilter filter = new ColumnPrefixFilter(Bytes.toBytes("name"));
		scan.setFilter(filter);
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			System.out.println(Bytes.toString(result.getRow()));
			byte[] bs = result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("name"));
			System.out.println(Bytes.toString(bs));
			//byte[] bs2 = result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("age"));
			//System.out.println(new String(bs2));
		}
	}
	/**
	 * 多个列名前置过滤器
	 * @throws Exception 
	 */
	@Test
	public void MultipleColumnPrefixFilter() throws Exception{
		Scan scan = new Scan();
		//列值过滤	参数:列簇名 列名  比较符  值
		byte[][] bytes = new byte[][]{Bytes.toBytes("name"),Bytes.toBytes("age")};
		MultipleColumnPrefixFilter filter = new org.apache.hadoop.hbase.filter.MultipleColumnPrefixFilter(bytes);
		scan.setFilter(filter);
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			System.out.println(Bytes.toString(result.getRow()));
			byte[] bs = result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("name"));
			System.out.println(Bytes.toString(bs));
			byte[] bs2 = result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("age"));
			System.out.println(new String(bs2));
		}
	}
	
	/**
	 * rowkey过滤器
	 * @throws Exception 
	 * @throws Exception
	 */
	@Test
	public void testRowKey() throws Exception{
		Scan scan = new Scan();
		//列值过滤	参数:列簇名 列名  比较符  值
		RowFilter filter = new RowFilter(CompareOp.NOT_EQUAL, new RegexStringComparator("^1234"));
		scan.setFilter(filter);
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			System.out.println(Bytes.toString(result.getRow()));
			byte[] bs = result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("name"));
			System.out.println(Bytes.toString(bs));
			byte[] bs2 = result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("age"));
			System.out.println(new String(bs2));
		}
	}
	
	@After
	public void last() throws Exception{
		table.close();
	}
}
