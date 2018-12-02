package day06.movie;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.codehaus.jackson.map.ObjectMapper;

import day05.FilterPhone.ReduceTask;

/**
 * 求电影的平均分
 */
public class Avg {
	
	public static class MapTask extends Mapper<LongWritable, Text, Text, IntWritable>{
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			ObjectMapper objectMapper = new ObjectMapper();
			MovieBean bean = objectMapper.readValue(value.toString(), MovieBean.class);
			String movie = bean.getMovie();
			int rate = bean.getRate();
			context.write(new Text(movie), new IntWritable(rate));
		}
	}
	
	public static class RedeceTask extends Reducer<Text, IntWritable, Text, FloatWritable>{
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Reducer<Text, IntWritable, Text, FloatWritable>.Context context) throws IOException, InterruptedException {
			int count = 0;
			int i = 0;
			for (IntWritable value : values) {
				//count += Integer.getInteger(value.toString());
				count += value.get();
				i++;
			}
			context.write(key, new FloatWritable(count*1.0f/i));
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setMapperClass(MapTask.class);
		job.setReducerClass(RedeceTask.class);
		job.setJarByClass(Avg.class);
		
		//设置map输出类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		//设置redece输出类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FloatWritable.class);
		
		
		//设置文件地址
		
		FileInputFormat.setInputPaths(job,new Path("D:\\data\\rating.json"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\movie"));
		
		File file = new File("d:\\data\\out\\movie");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		
		boolean b = job.waitForCompletion(true);
		System.out.println(b?"恭喜你答对了！":"不要放弃，希望就在明天");
	}
}
