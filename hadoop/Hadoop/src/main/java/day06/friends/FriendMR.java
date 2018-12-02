package day06.friends;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import day06.flow.FlowBean;

/**
 * 求任意两个人的共同好友
 * @author hasee
 *
 */

public class FriendMR {
	
	public static class MapTask extends Mapper<LongWritable, Text, Text, Text>{
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			String[] split1 = value.toString().split(":");
			String user = split1[0];
			String[] friends = split1[1].split(",");
			for (String f : friends) {
				context.write(new Text(f), new Text(user));
			}
		}
		
	}
	
	public static class ReduceTask extends Reducer<Text, Text, Text, Text>{
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			StringBuilder sb = new StringBuilder();
			for (Text user : values) {
				sb.append(user.toString()).append(",");
			}
			context.write(key,new Text(sb.toString()));
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		
		//System.setProperty("HADOOP_USER_NAME", "hasee");
		
		/**
		 * fs.defaultFs的默认值file:///	本地文件系统
		 * mapreduce.framework.name默认值是local
		 */
		Job job = Job.getInstance(conf);

		
		job.setMapperClass(MapTask.class);
		job.setReducerClass(ReduceTask.class);
		job.setJarByClass(FriendMR.class);

		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		
		//设置输入和输出目录
		FileInputFormat.setInputPaths(job, new Path("d:\\data\\friend.txt"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\friendOne"));
		//job.wait();
		File file = new File("d:\\data\\out\\friendOne");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		//提交任务
		boolean b = job.waitForCompletion(true);
		
		System.out.println(b?0:1);
	}
}
