package day05.firstMR;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Driver {
	public static void main(String[] args) throws Exception {
		//什么都没有配置 怎么知道我的yarn集群在哪里？怎么知道hdfs集群？
		//通过加装配置文件
		Configuration conf = new Configuration();
		
		/*conf.set("fs.defaultFS", "hdfs://hadoop01:9000");				
		conf.set("mapreduce.framework.name", "yarn");					//提交到哪里
		conf.set("yarn.resourcemanager.hostname", "hadoop01");			//resourcemanager在哪里
		
		conf.set("mapreduce.app-submission.cross-platform", "true");*/	//windows 提交任务到linux上需要设置的参数
		Job job = Job.getInstance(conf);
		//声明设置job的map和Reduce是哪一个，并且设置哪一个做任务提交
		job.setMapperClass(MapTask.class);
		job.setReducerClass(ReduceTask.class);
		job.setJarByClass(Driver.class);
		//job.setJar(jar);
		//设置输出类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		//设置输入和输出目录
		FileInputFormat.addInputPath(job, new Path("/word.txt"));
		FileOutputFormat.setOutputPath(job, new Path("/wordcount/results"));
		//查看目录是否存在
		FileSystem fs = FileSystem.get(conf);
		if(fs.exists(new Path("/wordcount/results"))){
			fs.delete(new Path("/wordcount/results"), true);
		}
		//提交之后会监控运行状态
		boolean b = job.waitForCompletion(true);
		System.out.println(b?"程序执行完毕！！！":"程序出bug了！！！！");
	}
}

