package day09.http_phone;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class JoinMR_2 {
	public static class MapTask extends Mapper<LongWritable, Text, Text, Text>{
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] split = value.toString().split(" ");
			context.write(new Text(split[2]), value);
		}
	}
	
	public static class ReduceTask extends Reducer<Text, Text, Text, Text>{
		@Override
		protected void reduce(Text arg0, Iterable<Text> arg1, Reducer<Text, Text, Text, Text>.Context arg2)
				throws IOException, InterruptedException {
			StringBuilder builder = new StringBuilder();
			int up = 0;
			int down = 0;
			int sum = 0;
			for (Text text : arg1) {
				String[] split = text.toString().split(" ");
				up += Integer.parseInt(split[5]);
				down += Integer.parseInt(split[6]);
				sum += Integer.parseInt(split[7]);
			}
			builder.append(up+" "+down+" "+sum);
			arg2.write(arg0, new Text(builder.toString()));
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setMapperClass(MapTask.class);
		job.setReducerClass(ReduceTask.class);
		job.setJarByClass(JoinMR_2.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.setInputPaths(job, new Path("D:\\data\\out\\http"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\http2"));
		File file = new File("d:\\data\\out\\http2");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		
		boolean b = job.waitForCompletion(true);
		System.out.println(b?"恭喜你答对了！":"不要放弃，希望就在明天");
	}
}
