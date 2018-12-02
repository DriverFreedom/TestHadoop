package hdfs.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.WillClose;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class hdfsDemo {
	FileSystem fs =null;
	@Before
	public void init() throws Exception{
		//连接hdfs
		Configuration conf = new Configuration();
		fs = FileSystem.get(new URI("hdfs://hadoop01:9000"),conf,"root");
		
	}
	/**
	 * 将本地文件上传到hdfs
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testUpLoad() throws Exception{
		fs.copyFromLocalFile(new Path("D:\\data\\http.log"), new Path("/"));
	}
	
	/**
	 * 从hdfs下载文件
	 * @throws Exception 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testDownLoad() throws  Exception{
		fs.copyToLocalFile(new Path("/http.log"), new Path("d:/http"));
	}

	/**
	 * 删除hdfs文件
	 * @throws Exception 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public  void TestDel() throws Exception{
		fs.delete(new Path("/http.log"),true);
	}
	
	
	/**
	 * 创建文件夹
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws Exception 
	 */
	@Test
	public void testMkdir() throws IllegalArgumentException, IOException{
		fs.mkdirs(new Path("/files"));
	}
	/**
	 * 改名字和移动文件
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws Exception
	 */
	@Test
	public void rename() throws IllegalArgumentException, IOException{
		//如果文件夹不存在，移动不会成功，也不会报错。
		//fs.rename(new Path("/test.sh"), new Path("/test2.sh"));
		fs.rename(new Path("/test2.sh"), new Path("/files/test.sh"));
	}
	/**
	 * 查看文件状态
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws Exception
	 */
	@Test
	public void testStatus() throws IllegalArgumentException, IOException{
		/*FileStatus status = fs.getFileStatus(new Path("/files"));
		System.out.println(status);*/
		
		RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
		while(listFiles.hasNext()){
			LocatedFileStatus file = listFiles.next();
			System.out.println(file.getLen());
			System.out.println(file.getBlockSize());
			System.out.println(file.getPath());
			System.out.println(file.getAccessTime());	//最后修改时间 
			System.out.println(file.getReplication());	//副本
			System.out.println("******************");
			//输出每个块的存储位置
			BlockLocation[] blockLocations = file.getBlockLocations();
			for (BlockLocation blockLocation : blockLocations) {
				System.out.println(blockLocation);
			}
			System.out.println("++++++++++++++++++++++++");
		}
	}
	
	/**
	 * 遍历文件夹
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws FileNotFoundException 
	 * @throws Exception
	 */
	@Test
	public void listStatus() throws FileNotFoundException, IllegalArgumentException, IOException{
		FileStatus[] fileStatus = fs.listStatus(new Path("/"));
		for (FileStatus fileStatus2 : fileStatus) {
			if(fileStatus2.isDirectory())
				System.out.println("文件夹");
			if(fileStatus2.isFile())
				System.out.println("文件");
		}
		
		
		
	}
	@After
	public void close() throws Exception{
		fs.close();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
