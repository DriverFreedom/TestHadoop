package day06.flow;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

/**
 * 计算各个网址的上传和下载速度
 * @author hasee
 *
 */
public class FolwMR {
		
	public static class MapTask extends Mapper<LongWritable, Text, Text, FlowBean>{
		
		//正则表达式
		public String reg(String url){
			
			Pattern pattern = Pattern.compile("(\\w+\\.)?(\\w+\\.){1}\\w+");
			Matcher matcher = pattern.matcher(url);
			while(matcher.find()){
				String newUrl = matcher.group();
				return newUrl;
			}
			return null;
		}
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, FlowBean>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			try {
				String[] split = value.toString().split("\t")[1].split(" ");
				//String url = split[0];
				//String url = split[0].split("/")[2];
				String url = reg(split[0]);
				long up = Long.parseLong(split[1]);
				long down = Long.parseLong(split[2]);
				FlowBean bean = new FlowBean();
				bean.set(up, down);
				context.write(new Text(url), bean);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
			
		
	}
	public static class ReduceTask extends Reducer<Text, FlowBean, Text, FlowBean>{
		
		@Override
		protected void reduce(Text key, Iterable<FlowBean> values, Reducer<Text, FlowBean, Text, FlowBean>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			long up = 0;
			long down = 0;
			for (FlowBean flowBean : values) {
				
				up += flowBean.getUp();
				down += flowBean.getDown();	
			}
			FlowBean bean = new FlowBean();
			bean.set(up, down);
			context.write(key,bean );
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
		job.setJarByClass(FolwMR.class);

		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(FlowBean.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);
		
		
		//设置输入和输出目录
		FileInputFormat.setInputPaths(job, new Path("d:\\data\\http.log"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out"));
		//job.wait();
		File file = new File("d:\\data\\out");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		//提交任务
		boolean b = job.waitForCompletion(true);
		
		System.out.println(b?0:1);
	}
}

