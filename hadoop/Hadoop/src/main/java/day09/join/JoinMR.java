package day09.join;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;




/**
 * 合并两张表
 * 读取文件夹下多个文件
 * setup的时候读小表，使用hdfs的api进行读取
 * map端读大表，进行合并
 * 传参
 * @author hasee
 *
 */
public class JoinMR {

	public static class MapTask extends Mapper<LongWritable, Text, JoinBean, NullWritable>{
		String table = null;
		//存放小表数据
		Map<String,String> map = new HashMap<>();
		@Override
		protected void setup(Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			Configuration conf = context.getConfiguration();
			//String fileName = conf.get("fileName");
			FileSystem fs = FileSystem.get(conf);
			FSDataInputStream inputStream = fs.open(new Path("/users.dat"));
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while((line = br.readLine())!=null){
				String[] split = line.split("::");
				map.put(split[0],line);
			}
		}
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] split = value.toString().split("::");
			JoinBean joinBean = new JoinBean();
			String[] line = map.get(split[0]).split("::");
			//1::F::1::10::48067
			joinBean.set(split[0], line[2], line[1], split[1], split[2], "null");
			context.write(joinBean, NullWritable.get());
		}
		
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		//什么都没有配置 怎么知道我的yarn集群在哪里？怎么知道hdfs集群？
				//通过加装配置文件
				Configuration conf = new Configuration();				
				conf.set("fs.defaultFS", "hdfs://hadoop01:9000");
				System.setProperty("HADOOP_USER_NAME", "root");	
				
				Job job = Job.getInstance(conf);
				
				job.setMapperClass(MapTask.class);
				
				job.setJarByClass(JoinMR.class);
				//job.setJar(jar);
				//设置输出类型
				
				job.setOutputKeyClass(JoinBean.class);
				job.setOutputValueClass(NullWritable.class);
				
				//设置输入和输出目录
				FileInputFormat.addInputPath(job, new Path("/ratings.dat"));
				FileOutputFormat.setOutputPath(job, new Path("/out/join"));
				//查看目录是否存在
				FileSystem fs = FileSystem.get(conf);
				if(fs.exists(new Path("/out/join"))){
					fs.delete(new Path("/out/join"), true);
				}
				//提交之后会监控运行状态
				boolean b = job.waitForCompletion(true);
				System.out.println(b?"程序执行完毕！！！":"程序出bug了！！！！");
		
	}
}
