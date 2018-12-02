package day07.movie.top1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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


/**
 * 求每部电影的评分前20位
 * @author hasee
 *
 */

public class TopN1 {

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
			ArrayList<MovieBean> list = new ArrayList<>();
			for (MovieBean movieBean : movieBeans) {
				/**
				 * 防止出现都是同一个东西
				 */
				MovieBean bean = new MovieBean();
				bean.set(movieBean);
				list.add(bean);
			}
			//排序
			Collections.sort(list,new Comparator<MovieBean>() {

				@Override
				public int compare(MovieBean o1, MovieBean o2) {
					// TODO Auto-generated method stub
					return o2.getRate()-o1.getRate();	//降序
				}
			});
			
			for(int i = 0;i < Math.min(20, list.size());i++){
				context.write(list.get(i), NullWritable.get());
			}
			context.write(new MovieBean(), NullWritable.get());
		}
	}
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setMapperClass(MapTask.class);
		job.setReducerClass(ReduceTask.class);
		job.setJarByClass(TopN1.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(MovieBean.class);
		
		job.setOutputKeyClass(MovieBean.class);
		job.setOutputValueClass(NullWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("D:\\data\\rating.json"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\movie\\top1"));
		File file = new File("d:\\data\\out\\movie\\top1");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		
		boolean b = job.waitForCompletion(true);
		System.out.println(b?"恭喜你答对了！":"不要放弃，希望就在明天");
	}
	
	
}
