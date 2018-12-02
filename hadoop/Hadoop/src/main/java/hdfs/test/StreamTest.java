package hdfs.test;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StreamTest {
	FileSystem fs =null;
	@Before
	public void init() throws IOException{
		System.setProperty("HADOOP_USER_NAME", "root");
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://hadoop01:9000");
		fs = FileSystem.get(conf);
	}
	/**
	 * 向文件中插入数据
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void writeFile() throws IllegalArgumentException, IOException{
		FSDataOutputStream outputStream = fs.create(new Path("/writeFile.txt"));
		outputStream.writeDouble(3.1415);
		outputStream.writeUTF("嘿嘿");
		outputStream.write("哈哈".getBytes());
		outputStream.close();
		
	}
	
	/**
	 * 读取文件数据
	 * @throws IllegalArgumentException 
	 * @throws IOException
	 */
	@Test
	public void readFile() throws IllegalArgumentException, IOException{
		FSDataInputStream inputStream = fs.open(new Path("/writeFile.txt"));
		double readDouble = inputStream.readDouble();
		String string = inputStream.readUTF();
		byte[] b = new byte[6];
		int len = 0;
		while((len = inputStream.read(b))!=-1){
			System.out.println(new String(b));
		}
		System.out.println(readDouble);
		
		System.out.println(string);
		
		inputStream.close();
	}
	
	
	@After
	public void after() throws IOException{
		fs.close();
	}
}
