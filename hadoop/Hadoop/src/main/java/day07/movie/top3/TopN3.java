package day07.movie.top3;

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





public class TopN3 {
	public static class MapTask extends Mapper<LongWritable, Text, MovieBean,NullWritable >{
		
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, MovieBean, NullWritable>.Context context)
				throws IOException, InterruptedException {
			try {
				//解析json
				ObjectMapper objectMapper = new ObjectMapper();
				MovieBean bean = objectMapper.readValue(value.toString(), MovieBean.class);
				context.write(bean, NullWritable.get());
			} catch (Exception e) {
				// TODO: handle exception
			}
				
		}
	}
	
	
	public static class ReduceTask extends Reducer<MovieBean, NullWritable, MovieBean, NullWritable>{
		
		@Override
		protected void reduce(MovieBean key, Iterable<NullWritable> arg1,
				Reducer<MovieBean, NullWritable, MovieBean, NullWritable>.Context context)
				throws IOException, InterruptedException {
			int num = 0;
			//虽然是空的，但是key能根据迭代进行相应的得到空值的结果
			for (NullWritable nullWritable : arg1) {
				if(num>=20){
					break;
				}else{
					context.write(key, NullWritable.get());
					num++;
				}
			}
		}
		
	}
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setMapperClass(MapTask.class);
		job.setReducerClass(ReduceTask.class);
		job.setJarByClass(TopN3.class);
		/**
		 * 设置reduce的设备数量
		 */
		job.setNumReduceTasks(2);
		/**
		 * 将相同的数据交给同一个reduce处理
		 */
		job.setPartitionerClass(MyPartition.class);
		/**
		 * 将movieid相同的数据分到一个组,这样就可以进行数量控制了。
		 * 否则，每一条数据都视为一个单独的组
		 */
		job.setGroupingComparatorClass(MyGroup.class);
		
		job.setMapOutputKeyClass(MovieBean.class);
		job.setMapOutputValueClass(NullWritable.class);
		
		job.setOutputKeyClass(MovieBean.class);
		job.setOutputValueClass(NullWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("D:\\data\\rating.json"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\movie\\top3"));
		File file = new File("d:\\data\\out\\movie\\top3");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		
		boolean b = job.waitForCompletion(true);
		System.out.println(b?"恭喜你答对了！":"不要放弃，希望就在明天");
	}
}
