package cn.lkp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;



public class IO {

	public static void main(String[] args) throws Exception {
		try {
			Configuration conf = new Configuration();
			FileSystem	fs = FileSystem.get(new URI("hdfs://hadoop01:9000"),conf,"root");
			FSDataInputStream inputStream = fs.open(new Path("/ratings.dat"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			Configuration configuration = HBaseConfiguration.create();
			configuration.set("hbase.zookeeper.quorum", "192.168.1.3:2181,192.168.1.4:2181,192.168.1.5:2181");
			Connection connection = ConnectionFactory.createConnection(configuration);
			Table table = connection.getTable(TableName.valueOf("t_movie"));
			
			
			String line = null;
			while((line=reader.readLine())!=null){
				JSONObject parseObject = JSON.parseObject(line);
				String movie_id = parseObject.getString("movie");
				String rate = parseObject.getString("rate");
				String timeStamp = parseObject.getString("timeStamp");
				String uid = parseObject.getString("uid");
				String keyRow = movie_id+uid;
				Put put = new Put(Bytes.toBytes("keyRow"));
				put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("movie_id"),Bytes.toBytes(movie_id));
				put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("rate"),Bytes.toBytes(rate));
				put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("timeStamp"),Bytes.toBytes(timeStamp));
				put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("uid"),Bytes.toBytes(uid));
				List<Put> list = new ArrayList<>();
				list.add(put);
				table.put(list);		
			}
			fs.close();
			reader.close();
			connection.close();
			table.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
}
