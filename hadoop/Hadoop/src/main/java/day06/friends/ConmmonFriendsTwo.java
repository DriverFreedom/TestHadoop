package day06.friends;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class ConmmonFriendsTwo {

	public static class MapTask extends Mapper<LongWritable, Text, Text, Text>{
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			
			//FileSplit fileSplit = (FileSplit)context.getInputSplit();
			//String fileName = fileSplit.getPath().getName();
			
			String[] split = value.toString().split("\t");
			String f = split[0];
			String[] users = split[1].split(",");
			Arrays.sort(users);
			//两两组合
			for (int i = 0;i<users.length -1;i++) {
				for(int j = i+1;j<users.length;j++){
					context.write(new Text(users[i]+"-"+users[j]), new Text(f));
				}
			}
		}
	}
	
	
	public static class ReduceTask extends Reducer<Text, Text, Text, Text>{
		@Override
		protected void reduce(Text userPair, Iterable<Text> friends, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
				StringBuilder sb = new StringBuilder();
				for (Text f : friends) {
					sb.append(f.toString()).append(",");
				}
				context.write(userPair, new Text(sb.deleteCharAt(sb.length()-1).toString()));
			
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
		job.setJarByClass(ConmmonFriendsTwo.class);

		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		
		//设置输入和输出目录
		FileInputFormat.setInputPaths(job, new Path("D:\\data\\out\\friendOne\\part-r-00000"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\friendTwo"));
		//job.wait();
		File file = new File("d:\\data\\out\\friendTwo");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		//提交任务
		boolean b = job.waitForCompletion(true);
		
		System.out.println(b?0:1);
	}
}
