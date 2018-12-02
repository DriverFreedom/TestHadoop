package day05.line;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class LineMR {
	
	public static class MapTask extends Mapper<LongWritable, Text, Text, IntWritable>{
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			try{
			String[] split = value.toString().split(",");
			int start = Integer.parseInt(split[0]);
			int end = Integer.parseInt(split[1]);
			for(int i = start;i <= end;i++){
				context.write(new Text(i+""), new IntWritable(1));
			}
			}catch(Exception e){
				
			}
			
		}
	}
	
	public static class ReduceTask extends Reducer<Text, IntWritable, Text, IntWritable>{
		@Override
		protected void reduce(Text arg0, Iterable<IntWritable> arg1,
				Reducer<Text, IntWritable, Text, IntWritable>.Context arg2) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			int count = 0;
			for (IntWritable intWritable : arg1) {
				count++;
			}
			arg2.write(arg0, new IntWritable(count));
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		
		
		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf, "line");
		
		//设置map和reduce，以及提交的jar
		job.setMapperClass(MapTask.class);
		job.setReducerClass(ReduceTask.class);
		job.setJarByClass(LineMR.class);
		
		
		//设置输入输出类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		//输入和输出目录
		FileInputFormat.addInputPath(job, new Path("d:\\data\\line.txt"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\line"));
		
		//判断文件是否存在
		FileSystem fs = FileSystem.get(conf);
		if(fs.exists(new Path("d:\\data\\out\\line"))){
			fs.delete(new Path("d:\\data\\out\\line"), true);
		}
		
		//提交任务
		boolean completion = job.waitForCompletion(true);
		System.out.println(completion?"成功":"失败了！不要放弃哦");
	}
}
