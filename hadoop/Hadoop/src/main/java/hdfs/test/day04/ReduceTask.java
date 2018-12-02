package hdfs.test.day04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec.BR;

public class ReduceTask {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//获取运行机器代号
		int taskId = Integer.parseInt(args[0]);
		//创建map用于存储数据
		Map<String,Integer> map = new HashMap<>();
		//连接hdfs
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop01:9000"),new Configuration(),"root");
		//遍历hdfs文件
		RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/wordcount/tmp/"), true);
		//开始计算
		while(listFiles.hasNext()){
			LocatedFileStatus file = listFiles.next();
			//判断是否是自己需要计算的文件
			if(file.getPath().getName().endsWith("-"+taskId)){
				//创建读文件对象
				FSDataInputStream inputStream = fs.open(file.getPath());
				//缓冲流
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				while ((line = reader.readLine()) != null){
					String[] word = line.split("\t");
					//计算单词的个数
					Integer count = map.getOrDefault(word[0], 0);
					count += Integer.parseInt(word[1]);
					map.put(word[0], count);				
				}
				inputStream.close();
				reader.close();
			}
			
			
			//将结果写入到hdfs
			FSDataOutputStream outputStream = fs.create(new Path("/wordcount/result/part-r-"+taskId));
			Set<Entry<String,Integer>> entrySet = map.entrySet();
			for (Entry<String, Integer> entry : entrySet) {
				outputStream.write((entry.getKey()+"="+entry.getValue()+"\n").getBytes());
			}
			outputStream.close();
			
			
		}
		
		
		fs.close();
		
		
		
		
		
	}

}
