package exam;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;

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
import org.apache.htrace.fasterxml.jackson.databind.ObjectMapper;



public class HotMovie {
	
	public static class MapTask extends Mapper<LongWritable, Text, Text,MovieBean>{
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text,  Text,MovieBean>.Context context)
				throws IOException, InterruptedException {
			ObjectMapper objectMapper = new ObjectMapper();
			MovieBean bean = objectMapper.readValue(value.toString(), MovieBean.class);
			context.write(new Text(bean.getMovie()), bean);
		}
	}
	
	public static class RedeceTask extends Reducer<Text,MovieBean, Text, IntWritable>{
		TreeSet<MovieBean> set = null;
		
		@Override
		protected void setup(Context context)
				throws IOException, InterruptedException {
				set =  new TreeSet<>(new Comparator<MovieBean>() {			
				@Override
				public int compare(MovieBean o1, MovieBean o2) {
					// TODO Auto-generated method stub
					if(o1.getCount()-o2.getCount()==0){
						return o1.getMovie().compareTo(o2.getMovie());
					}else{
						return (int)(o1.getCount()-o2.getCount());
					}
				}
			});
		}
		
		
		
		@Override
		protected void reduce(Text key, Iterable<MovieBean> value,
				Reducer<Text, MovieBean, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			MovieBean bean = new MovieBean();
			int count = 0;
			for (MovieBean movieBean : value) {
				count++;
			}
			bean.setMovie(key.toString());
			bean.setCount(count);
			if(set.size()<20){
				set.add(bean);
			}else{
				MovieBean bean2 = set.first();
				if(bean2.getCount()<bean.getCount()){
					set.remove(bean2);
					set.add(bean);
				}
			}
		}
		
		@Override
		protected void cleanup(Reducer<Text,MovieBean, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			for (MovieBean movieBean : set) {
				context.write(new Text(movieBean.getMovie()), new IntWritable(movieBean.getCount()));
			}
		}
		
			
	}
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setMapperClass(MapTask.class);
		job.setReducerClass(RedeceTask.class);
		job.setJarByClass(HotMovie.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(MovieBean.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("D:\\data\\rating.json"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\movie\\top6"));
		File file = new File("d:\\data\\out\\movie\\top6");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		
		boolean b = job.waitForCompletion(true);
		System.out.println(b?"恭喜你答对了！":"不要放弃，希望就在明天");
	}
}
