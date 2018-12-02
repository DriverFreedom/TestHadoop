package hdfs.test.day04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

public class ReduceTaskDemo {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int taskId = Integer.parseInt(args[0]);
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop01:9000"),new Configuration(),"root");
		RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/wordcount/tmp/"), true);
		
		Map<String,Integer> map = new HashMap<>();
		FSDataOutputStream outputStream = fs.create(new Path("/wordcount/results/part-r-"+taskId));
		while(listFiles.hasNext()){
			LocatedFileStatus file = listFiles.next();
			if(file.getPath().getName().endsWith("-"+taskId)){
				FSDataInputStream inputStream = fs.open(file.getPath());
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				while((line=reader.readLine())!=null){
					String[] split = line.split("\t");
					Integer count = map.getOrDefault(split[0], 0);
					count += Integer.parseInt(split[1]);
					map.put(line, count);
				}
				inputStream.close();
				reader.close();
			}
		}
		fs.close();
		
		
	}

}
