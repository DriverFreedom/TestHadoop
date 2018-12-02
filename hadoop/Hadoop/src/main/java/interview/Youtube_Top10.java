package interview;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;





public class Youtube_Top10 {
	public static class mapTask extends Mapper<LongWritable,Text,Text,MovieBean>{
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, MovieBean>.Context context)
				throws IOException, InterruptedException {
			String[] split = value.toString().split("\t");
			String movie = split[0];
			if(split.length>6){
			MovieBean bean = new MovieBean();
			bean.setVedioId(movie);
			bean.setCategory(split[3]);
			bean.setRate(Double.parseDouble(split[6]));
			context.write(new Text(movie),bean);
			System.out.println();
			}
		}
		
	}
	
	
	public static class reduceTask extends Reducer<Text, MovieBean, Text, DoubleWritable>{
			TreeSet<MovieBean> treeSet = null;
		
		@Override
		protected void setup(Context context)
				throws IOException, InterruptedException {
				treeSet =  new TreeSet<>(new Comparator<MovieBean>() {			
				@Override
				public int compare(MovieBean o1, MovieBean o2) {
					// TODO Auto-generated method stub
					return o1.getRate() < o2.getRate()?1:o1.getRate() == o2.getRate()?0:-1;
				}
			});
		}
		protected void reduce(Text key, Iterable<MovieBean> moviebeans,
				Reducer<Text, MovieBean, Text, DoubleWritable>.Context context) throws IOException, InterruptedException {
				
				MovieBean bean = new MovieBean();
				for (MovieBean movieBean2 : moviebeans) {
					bean.setVedioId(movieBean2.getVedioId());
					bean.setCategory(movieBean2.getCategory());
					bean.setRate(movieBean2.getRate());
				}
				
				if(treeSet.size() < 10){
					treeSet.add(bean);
				}else{
					MovieBean bean2 = (MovieBean) treeSet.first();
					if(bean2.getRate()<bean.getRate()){
						treeSet.remove(bean2);
						treeSet.add(bean);
					}
					
				}
			
			
		}
		
		@Override
		protected void cleanup(Reducer<Text, MovieBean, Text, DoubleWritable>.Context context)
			throws IOException, InterruptedException {
			for (MovieBean movieBean : treeSet) {
				context.write(new Text(movieBean.getVedioId()), new DoubleWritable(movieBean.getRate()));
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration());
		job.setJarByClass(Youtube_Top10.class);
		job.setMapperClass(mapTask.class);
		job.setReducerClass(reduceTask.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(MovieBean.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		Path inputPath = new Path("D:\\data\\youtube\\4.txt");
		Path outputPath = new Path("D:\\data\\youtube\\out");
		FileInputFormat.setInputPaths(job,inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		File file = new File(outputPath.toString());
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		boolean b = job.waitForCompletion(true);
		System.out.println(b?"sucess":"失败了");
	}
}
