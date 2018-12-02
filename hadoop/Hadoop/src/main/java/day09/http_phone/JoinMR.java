package day09.http_phone;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class JoinMR {

	public static class MapTask extends Mapper<LongWritable, Text, Text, ProvinceBean>{
		String table = null;
		@Override
		protected void setup(Mapper<LongWritable, Text, Text, ProvinceBean>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			FileSplit fileSplit = (FileSplit)context.getInputSplit();
			table = fileSplit.getPath().getName();
		}
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, ProvinceBean>.Context context)
				throws IOException, InterruptedException {
			if(key.get()!=0){
			ProvinceBean joinBean = new ProvinceBean();
			if(table.startsWith("http")){
				String[] split = value.toString().split("\t");
				int up = Integer.parseInt(split[1].split(" ")[1]);
				int down = Integer.parseInt(split[1].split(" ")[2]);	
				int sum = up+down;
				
				joinBean.set(split[0].substring(0, 3), "null", "null", "null", "null",up,down,sum,"http");
			}else if(table.startsWith("phone")){
					
					String[] split = value.toString().split("\t");
					joinBean.set(split[0], split[1], split[2], split[3], split[4], 0,0,0,"phone");
				
				
			}
			context.write(new Text(joinBean.getPrefix()), joinBean);
			}
		}
		
	}
	
	
	public static class ReduceTask extends Reducer<Text, ProvinceBean, ProvinceBean, NullWritable>{
		@Override
		protected void reduce(Text key, Iterable<ProvinceBean> values,
				Reducer<Text, ProvinceBean, ProvinceBean, NullWritable>.Context context) throws IOException, InterruptedException {
			
			//放user的
			ProvinceBean joinBean = new ProvinceBean();
			ArrayList<ProvinceBean> list = new ArrayList<>();
			//分离数据
			for (ProvinceBean Bean2 : values) {
				String table = Bean2.getTable();
				if("http".equals(table)){
					joinBean.set(Bean2.getPrefix(),Bean2.getPhone(),Bean2.getProvince(),Bean2.getCity(),Bean2.getIsp(),Bean2.getUp_data(),Bean2.getDown_data(),Bean2.getSum(),"http");
				}else{
					ProvinceBean joinBean3 = new ProvinceBean();
					joinBean3.set(Bean2.getPrefix(),Bean2.getPhone(),Bean2.getProvince(),Bean2.getCity(),Bean2.getIsp(),Bean2.getUp_data(),Bean2.getDown_data(),Bean2.getSum(),"http");
					list.add(joinBean3);
				}
			}
			
			//拼接数据
			for (ProvinceBean joinBean2 : list) {
				joinBean2.setUp_data(joinBean.getUp_data());				
				joinBean2.setDown_data(joinBean.getDown_data());
				joinBean2.setSum(joinBean.getUp_data()+joinBean.getDown_data());
				context.write(joinBean2, NullWritable.get());
			}
			
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setMapperClass(MapTask.class);
		job.setReducerClass(ReduceTask.class);
		job.setJarByClass(JoinMR.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(ProvinceBean.class);
		
		job.setOutputKeyClass(ProvinceBean.class);
		job.setOutputValueClass(NullWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("D:\\data\\in\\http"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\http"));
		File file = new File("d:\\data\\out\\http");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		
		boolean b = job.waitForCompletion(true);
		System.out.println(b?"恭喜你答对了！":"不要放弃，希望就在明天");
	}
}
