package day05;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FilterPhone {
	public static class MapTask extends Mapper<LongWritable, Text, Text, Text>{
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			//行偏移量等于0的时候跳过
			if(key.get() != 0){
				String[] split = value.toString().split("\t");
				String prefix = split[0];
				String isp = split[4];
				context.write(new Text(prefix), new Text(isp));
			}
			
		}
	}
	
	public static class ReduceTask extends Reducer<Text, Text, Text, Text>{
		@Override
		protected void reduce(Text arg0, Iterable<Text> arg1, Reducer<Text, Text, Text, Text>.Context arg2)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			for (Text text : arg1) {
				arg2.write(arg0, text);
				break;
			}
			
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.setProperty("HADOOP_USER_NAME", "root");

		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf, "phone");
		
		//设置map和reduce，以及提交的jar
		job.setMapperClass(MapTask.class);
		job.setReducerClass(ReduceTask.class);
		job.setJarByClass(FilterPhone.class);
		
		
		//设置输入输出类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		//输入和输出目录
		FileInputFormat.addInputPath(job, new Path("d:\\data\\phone.txt"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\phone"));
		
		//判断文件是否存在
		File file = new File("d:\\data\\out\\phone");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		
		//提交任务
		boolean completion = job.waitForCompletion(true);
		System.out.println(completion?"成功":"失败了！不要放弃哦");
	}
}
