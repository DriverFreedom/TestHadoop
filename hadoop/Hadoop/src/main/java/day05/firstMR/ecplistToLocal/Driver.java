package day05.firstMR.ecplistToLocal;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import day05.firstMR.MapTask;
import day05.firstMR.ReduceTask;

/**
 * 从eclipse提交到集群
 * @author root
 *
 */
public class Driver {
	public static void main(String[] args) throws Exception {
		
		//声明使用哪个用户提交的
		System.setProperty("HADOOP_USER_NAME", "root");
		Configuration conf = new Configuration();
		
		//告诉它集群在哪里
		//设置hdfs  提交平台地址  resourcemanager  windows平台提交
		conf.set("fs.defaultFS", "hdfs://hadoop01:9000");
		conf.set("mapreduce.framework.name","yarn" );
		conf.set("yarn.resourcemanager.hostname", "hadoop01");
		conf.set("mapreduce.app-submission.cross-platform", "true");
		
		
		Job job = Job.getInstance(conf, "eclipseToCluster");
		//设置map和Reduce以及提交的jar
		job.setMapperClass(MapTask.class);
		job.setReducerClass(ReduceTask.class);
		job.setJarByClass(Driver.class);
		//job.setJar("C:\\Users\\hasee\\Desktop\\wc.jar");
		//设置输入输出类型
		job.setMapOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		//设置输入和输出目录
		FileInputFormat.addInputPath(job, new Path("/word.txt"));
		FileOutputFormat.setOutputPath(job, new Path("/wordcount/eclipse-out1/"));
		
		//判断文件是否存在
		FileSystem fs = FileSystem.get(conf);
		if(fs.exists(new Path("/wordcount/eclipse-out1/"))){
			fs.delete(new Path("/wordcount/eclipse-out1/"));
		}
		
		//提交任务
		boolean b = job.waitForCompletion(true);
		
		System.out.println(b?0:1);
		
		
	}

}

