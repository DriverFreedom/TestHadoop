package day05.firstMR.local;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



/**
 * 本地模式
 * 小数据测试，测试完成之后才改成集群模式进行提交
 * @author root
 *
 */
public class Driver {
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
		job.setJarByClass(Driver.class);

		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		//设置combiner
		job.setCombinerClass(Mycombiner.class);
		
		//设置输入和输出目录
		FileInputFormat.setInputPaths(job, new Path("d:\\data\\word.txt"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\word"));
		//job.wait();
		File file = new File("d:\\data\\out\\word");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		//提交任务
		boolean b = job.waitForCompletion(true);
		
		System.out.println(b?0:1);
		
	}
}