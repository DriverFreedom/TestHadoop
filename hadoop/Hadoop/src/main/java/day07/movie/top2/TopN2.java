package day07.movie.top2;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;

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


public class TopN2 {
	
public static class MapTask extends Mapper<LongWritable, Text, Text, MovieBean>{
		
		@Override
		public  void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, MovieBean>.Context context)
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
		protected void reduce(Text movieId, Iterable<MovieBean> movieBeans,
				Reducer<Text, MovieBean, MovieBean, NullWritable>.Context context)
				throws IOException, InterruptedException {
			//使用小根堆   很重要
			TreeSet<MovieBean> tree = new TreeSet<>(new Comparator<MovieBean>() {

				@Override
				public int compare(MovieBean o1, MovieBean o2) {
					if(o1.getRate() - o2.getRate() ==0){
						return o1.getUid().compareTo(o2.getUid());
					}else{
						return  o1.getRate() - o2.getRate();
					}
				
				}
			});
			for (MovieBean movieBean : movieBeans) {
				MovieBean movieBean2 = new MovieBean();
				movieBean2.set(movieBean);
				if(tree.size()<20){
					tree.add(movieBean2);
				}else{
					MovieBean first = tree.first();
					if(first.getRate()<movieBean2.getRate()){
						//做替换
						tree.remove(first);
						tree.add(movieBean2);
					}
					
				}
			}
			
			for (MovieBean movieBean : tree) {
				context.write(movieBean, NullWritable.get());
			}
		
		}
	}
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setMapperClass(MapTask.class);
		job.setReducerClass(ReduceTask.class);
		job.setJarByClass(TopN2.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(MovieBean.class);
		
		job.setOutputKeyClass(MovieBean.class);
		job.setOutputValueClass(NullWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("D:\\data\\rating.json"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\movie\\top2"));
		File file = new File("d:\\data\\out\\movie\\top2");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		
		boolean b = job.waitForCompletion(true);
		System.out.println(b?"恭喜你答对了！":"不要放弃，希望就在明天");
	}
}
