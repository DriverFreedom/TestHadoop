package hdfs.test.day04;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class MapTaskDemo {
	public static void main(String[] args) throws Exception {
		/**
		 * 四个对象
		 */
		int taskId = Integer.parseInt(args[0]);
		String file = args[1];
		long startOffSet = Long.parseLong(args[2]);
		long length = Long.parseLong(args[3]);
		
		//连接hdfs
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop01:90000"),new Configuration(),"root");
		FSDataInputStream inputStream = fs.open(new Path(file));
		//定位从哪里读
		inputStream.seek(startOffSet);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		//输出对象
		FSDataOutputStream out_tem_1 = fs.create(new Path("/wordcount/tmp/part-m"+taskId+"-1"),true);
		FSDataOutputStream out_tem_2 = fs.create(new Path("/wordcount/tmp/part-m"+taskId+"-2"),true);
		
		//除了第一台可以读取第一行
		if(taskId!=1){
			reader.readLine();
		}
		long count =0;
		String line = null;
		while((line=reader.readLine())!=null){
			String[] split = line.split(" ");
			for (String str : split) {
				if(str.hashCode()%2==0){
					out_tem_1.write((str+"\t"+1+"\n").getBytes());
				}else{
					out_tem_2.write((str+"\t"+1+"\n").getBytes());
				}
				count +=line.length();
				if(count>length){
					break;
				}
			}
		}
		reader.close();
		out_tem_1.close();
		out_tem_2.close();
		fs.close();
		
	}
}
