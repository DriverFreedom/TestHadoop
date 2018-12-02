package hdfs.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

public class homework {
	
	FileSystem fs =null;
	@Before
	public void before() throws IOException, InterruptedException, URISyntaxException{
		Configuration conf = new Configuration();
		fs = FileSystem.get(new URI("hdfs://hadoop01:9000"),conf,"root");
	}
	/**
	 * �������ļ�������hdfs
	 * @throws Exception
	 */
	
	@Test
	public void UpLoad() throws Exception{
		FSDataOutputStream stream = fs.create(new Path("/.txt"));
		FileReader fileReader = new FileReader(new File("D:\\data\\好友.txt"));
		int len = 0;
		while((len=fileReader.read())!=-1){
			stream.write(len);
		}
		stream.close();
		fileReader.close();
	}
	/**
	 * ������好友
	 * @throws Exception
	 */
	@Test
	public void downlod() throws Exception{
		//打开
		FSDataInputStream stream = fs.open(new Path("/好友.txt"));
		FileWriter fw = new FileWriter("d:/好友.txt");
		int len = 0;
		while((len = stream.read())!=-1){
			fw.write(len);
		}
		fw.close();
		stream.close();
	}
}
