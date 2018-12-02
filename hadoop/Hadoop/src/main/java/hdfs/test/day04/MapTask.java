package hdfs.test.day04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class MapTask {

	public static void main(String[] args) throws Exception {
		/**
		 * taskId 标识哪台机器运行
		 * file 统计哪个文件
		 * startOffSet 从哪个位置开始
		 * lenth 读多长
		 */
		
		int taskId = Integer.parseInt(args[0]);
		String file = args[1];
		long startOffSet = Long.parseLong(args[2]);
		long lenth = Long.parseLong(args[3]);
		//连接hdfs
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop01:9000"), new Configuration(),"root");
		FSDataInputStream inputStream = fs.open(new Path(file));
		
		//创建输出文件
		FSDataOutputStream out_tem_1 = fs.create(new Path("/wordcount/tmp/part-m"+taskId+"-1"),true);
		FSDataOutputStream out_tmp_2 = fs.create(new Path("/wordcount/tmp/part-m"+taskId+"-2"),true);
		//定位从哪里读
		inputStream.seek(startOffSet);
		//创建字符缓冲输入流
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		//除了taskId=1的能读第一行，后面的task都需要跳过一行。
		if(taskId != 1){
			reader.readLine();
		}
		//读取并且写入
		long count = 0;
		String line = null;
		while((line = reader.readLine())!=null){
			String[] split = line.split(" ");
			for (String word : split) {
				if(word.hashCode()%2 == 0){
					out_tem_1.write((word+"\t"+1+"\n").getBytes());
				}else{
					out_tmp_2.write((word+"\t"+1+"\n").getBytes());
				}
				//累加每行的数据个数
				count += line.length();
				if(count > lenth){
					break;
				}
			}
		}
		
		reader.close();
		out_tem_1.close();
		out_tmp_2.close();
		fs.close();
			
		
	}

}
