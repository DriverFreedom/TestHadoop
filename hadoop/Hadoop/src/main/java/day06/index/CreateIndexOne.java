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
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 计算多个文件里字符出现的次数
 * 每个word在各个文件中出现的次数
 * you	a.txt	1,b.txt	1,c.txt	1
 * @author hasee
 *
 */
public class CreateIndexOne {
		
	public static class MapTask extends Mapper<LongWritable, Text, Text, IntWritable>{
		String pathname = null;
		
		@Override
		protected void setup(Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			//获取当前文件名	计算切片
			FileSplit fileSplit = (FileSplit)context.getInputSplit();
			pathname = fileSplit.getPath().getName();
		}
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			
			String[] words = value.toString().split(" ");
			for (String word : words) {
				
				context.write(new Text(word+"-"+pathname), new IntWritable(1));
			}
		}
	}
	
	
	public static class ReduceTask extends Reducer<Text, IntWritable, Text, IntWritable>{
		
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			int count = 0;
			for (IntWritable value : values) {
				count++;
			}
			context.write(key, new IntWritable(count));
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
		job.setJarByClass(CreateIndexOne.class);

		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		
		//设置输入和输出目录
		FileInputFormat.setInputPaths(job, new Path("d:\\data\\in\\index"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\indexOne"));
		//job.wait();
		File file = new File("d:\\data\\out\\indexOne");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		//提交任务
		boolean b = job.waitForCompletion(true);
		
		System.out.println(b?0:1);
	}
}
