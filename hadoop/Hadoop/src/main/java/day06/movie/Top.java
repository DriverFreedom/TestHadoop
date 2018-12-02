package day06.movie;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.comparators.ComparableComparator;
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



public class Top {

	
	public static class MapTask extends Mapper<LongWritable, Text, Text, MovieBean>{
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, MovieBean>.Context context)
				throws IOException, InterruptedException {
			try {
				
				ObjectMapper objectMapper = new ObjectMapper();
				MovieBean bean = objectMapper.readValue(value.toString(), MovieBean.class);
				String movie = bean.getMovie();
				context.write(new Text(movie), bean);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	}
	
	
	public static class ReduceTask extends Reducer<Text, MovieBean, MovieBean, NullWritable>{
		
		@Override
		protected void reduce(Text arg0, Iterable<MovieBean> arg1,
				Reducer<Text, MovieBean, MovieBean, NullWritable>.Context context)
				throws IOException, InterruptedException {
			List<MovieBean> list = new ArrayList<>();
			for (MovieBean movieBean : arg1) {
				MovieBean bean = new MovieBean();
				bean.set(movieBean);
				list.add(bean);
			}
			Collections.sort(list, new Comparator<MovieBean>() {

				@Override
				public int compare(MovieBean o1, MovieBean o2) {
					// TODO Auto-generated method stub
					return o2.getRate()-o1.getRate();
				}
			});
			
			
		}
	} 
	
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setMapperClass(MapTask.class);
		job.setReducerClass(ReduceTask.class);
		job.setJarByClass(Top.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(MovieBean.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("D:\\data\\rating.json"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\movie\\top"));
		File file = new File("d:\\data\\out\\movie\\top");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		
		boolean b = job.waitForCompletion(true);
		System.out.println(b?"恭喜你答对了！":"不要放弃，希望就在明天");
	}
}
