package interview;

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

public class Youtube_typeNum {

	public static class mapTask extends Mapper<LongWritable, Text, Text, IntWritable>{
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			String[] split = value.toString().split("\t");
			if(split.length>3){
			Text text = new Text(split[3]);
			context.write(text, new IntWritable(1));
			}
		}
	}
	
	
	public static class reduceTask extends Reducer<Text, IntWritable, Text, IntWritable>{
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
			int count = 0;
			for (IntWritable intWritable : values) {
				count += intWritable.get();
			}
			context.write(key, new IntWritable(count));
		}
	}
	
	public static void main(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration());
		job.setJarByClass(Youtube_typeNum.class);
		job.setMapperClass(mapTask.class);
		job.setReducerClass(reduceTask.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		Path inPath = new Path("D:\\data\\youtube\\4.txt");
		Path outPath = new Path("D:\\data\\youtube\\out2");
		FileInputFormat.setInputPaths(job, inPath);
		FileOutputFormat.setOutputPath(job, outPath);
		File file = new File(outPath.toString());
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		boolean b = job.waitForCompletion(true);
		System.out.println(b?"SUCESS!!!!!!!!!!!!":"FALSE!!!!!!!!!!!");
	}
}

