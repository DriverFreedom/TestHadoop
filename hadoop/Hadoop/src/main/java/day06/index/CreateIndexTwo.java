package day06.index;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class CreateIndexTwo {
	
	
	public static class MapTask extends Mapper<LongWritable, Text, Text, Text>{
		
		Text outKey = new Text();
		Text outValue = new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			String[] split = value.toString().split("-");
			String word = split[0];
			String nameNum = split[1];
			outKey.set(word);
			outValue.set(nameNum);
			context.write(outKey,outValue);
		}
	}
	
	
	public static class ReduceTask extends Reducer<Text, Text, Text, Text>{
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			StringBuilder builder = new StringBuilder();
			boolean flag = true;
			for (Text text : values) {
				if(flag){
					builder.append(text.toString());
					flag = false;
				}else{
					builder.append(",");
					builder.append(text.toString());
				}
				
			}
			//context.write(key, new Text(builder.deleteCharAt(builder.length()-1).toString()));
			context.write(key, new Text(builder.toString()));
			
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
		job.setJarByClass(CreateIndexTwo.class);

		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		
		//设置输入和输出目录
		FileInputFormat.setInputPaths(job, new Path("d:\\data\\out\\indexOne"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\indexTwo"));
		//job.wait();
		File file = new File("d:\\data\\out\\indexTwo");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		//提交任务
		boolean b = job.waitForCompletion(true);
		
		System.out.println(b?0:1);
	}
}
