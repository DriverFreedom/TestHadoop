package day09.http_phone;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class provinceData {

	public static class MapTask extends Mapper<LongWritable, Text, Text, ProvinceBean>{
		Map<String,String> map = new HashMap<>();
		@Override
		protected void setup(Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			Configuration conf = context.getConfiguration();
			//String fileName = conf.get("fileName");
			FileSystem fs = FileSystem.get(conf);
			FSDataInputStream inputStream = fs.open(new Path("/http.log"));
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while((line = br.readLine())!=null){
				String[] split = line.split("\t");
				map.put(split[0].substring(0,7),line);
			}
		}
		
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, Text, ProvinceBean>.Context context)
				throws IOException, InterruptedException {
			try {
				
					ProvinceBean bean = new ProvinceBean();
					String[] phone = value.toString().split("\t");
					String http = map.get(phone[1]);
					String[] http_spl = http.split("\t");
					//System.out.println(http_spl[1].split(" ")[1]);
					int up = Integer.parseInt(http_spl[1].split(" ")[1]);
					int down = Integer.parseInt(http_spl[1].split(" ")[2]);	
					int sum = up+down;
					bean.set(phone[0], phone[1], phone[2], phone[3], phone[4],up,down,sum,"");
					context.write(new Text(bean.getCity()),bean);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		
			
		}
		
	}
	
	public static class ReduceTask extends Reducer<Text, ProvinceBean, Text, Text>{
		@Override
		protected void reduce(Text arg0, Iterable<ProvinceBean> arg1,
				Reducer<Text, ProvinceBean, Text, Text>.Context arg2) throws IOException, InterruptedException {
			String  str = null;
			int up = 0;
			int down = 0;
			int sum = 0;
			for (ProvinceBean bean : arg1) {
				up += bean.getUp_data();
				down += bean.getDown_data();
			}
			sum += up+down;
			str=up+" "+down+" "+sum;
			arg2.write(arg0, new Text(str));
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://hadoop01:9000");
		System.setProperty("HADOOP_USER_NAME", "root");	
		
		Job job = Job.getInstance(conf);
		job.setMapperClass(MapTask.class);
		job.setReducerClass(ReduceTask.class);
		job.setJarByClass(provinceData.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(ProvinceBean.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		//设置输入和输出目录
		FileInputFormat.addInputPath(job, new Path("/phone.txt"));
		FileOutputFormat.setOutputPath(job, new Path("/out/http"));
		//查看目录是否存在
		FileSystem fs = FileSystem.get(conf);
		if(fs.exists(new Path("/out/http"))){
			fs.delete(new Path("/out/http"), true);
		}
		
		boolean b = job.waitForCompletion(true);
		System.out.println(b?"恭喜你答对了！":"不要放弃，希望就在明天");
	}
}
