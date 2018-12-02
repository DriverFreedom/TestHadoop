package day07.movie.UserTop;

import java.io.File;
import java.io.IOException;

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
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.codehaus.jackson.map.ObjectMapper;

public class TopUser {

	public static class MapTask extends Mapper<LongWritable, Text, MovieBean, NullWritable>{
		
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, MovieBean, NullWritable>.Context context)
				throws IOException, InterruptedException {
			ObjectMapper mapper = new ObjectMapper();
			MovieBean bean = mapper.readValue(value.toString(), MovieBean.class);
			context.write(bean, NullWritable.get());
		}
	}
	
	public static class ReduceTask extends Reducer<MovieBean, NullWritable, MovieBean, NullWritable>{
		@Override
		protected void reduce(MovieBean arg0, Iterable<NullWritable> arg1,
				Reducer<MovieBean, NullWritable, MovieBean, NullWritable>.Context arg2)
				throws IOException, InterruptedException {
			int count = 0;
			for (NullWritable nullWritable : arg1) {
				if(count>=20){
					break;
				}else{
					count++;
					arg2.write(arg0, NullWritable.get());
				}
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Configuration conf =  new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setMapperClass(MapTask.class);
		job.setReducerClass(ReduceTask.class);
		
		job.setJarByClass(TopUser.class);
		//设置分组
		job.setGroupingComparatorClass(MyGroup.class);
		
		job.setNumReduceTasks(2);
		//设置分区
		job.setPartitionerClass(MyPartition.class);
		
		job.setMapOutputKeyClass(MovieBean.class);
		job.setMapOutputValueClass(NullWritable.class);
		job.setOutputKeyClass(MovieBean.class);
		job.setOutputValueClass(NullWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("D:\\data\\rating.json"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\movie\\top4"));
		File file = new File("d:\\data\\out\\movie\\top4");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		
		boolean b = job.waitForCompletion(true);
		System.out.println(b?"成功了耶":"失败了,再试一次");
	}
}
