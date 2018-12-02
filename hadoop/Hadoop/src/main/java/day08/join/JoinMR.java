package day08.join;



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


/**
 * 合并两张表
 * 读取文件夹下多个文件
 * @author hasee
 *
 */
public class JoinMR {

	public static class MapTask extends Mapper<LongWritable, Text, Text, JoinBean>{
		String table = null;
		@Override
		protected void setup(Mapper<LongWritable, Text, Text, JoinBean>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			FileSplit fileSplit = (FileSplit)context.getInputSplit();
			table = fileSplit.getPath().getName();
		}
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, JoinBean>.Context context)
				throws IOException, InterruptedException {
			String[] split = value.toString().split("::");
			JoinBean joinBean = new JoinBean();
			if(table.startsWith("users")){
				joinBean.set(split[0], split[2], split[1], "null", "null", "user");
			}else if(table.startsWith("ratings")){
				joinBean.set(split[0], "null", "null", split[1], split[2], "rating");
			}
			context.write(new Text(joinBean.getUid()), joinBean);
		}
		
	}
	
	
	public static class ReduceTask extends Reducer<Text, JoinBean, JoinBean, NullWritable>{
		@Override
		protected void reduce(Text key, Iterable<JoinBean> values,
				Reducer<Text, JoinBean, JoinBean, NullWritable>.Context context) throws IOException, InterruptedException {
			//放user的
			JoinBean joinBean = new JoinBean();
			ArrayList<JoinBean> list = new ArrayList<>();
			//分离数据
			for (JoinBean joinBean2 : values) {
				String table = joinBean2.getTable();
				if("user".equals(table)){
					joinBean.set(joinBean2.getUid(), joinBean2.getAge(), joinBean2.getGender(), joinBean2.getMovieId(), joinBean2.getRating(), joinBean2.getTable());
				}else{
					JoinBean joinBean3 = new JoinBean();
					joinBean3.set(joinBean2.getUid(), joinBean2.getAge(), joinBean2.getGender(), joinBean2.getMovieId(), joinBean2.getRating(), joinBean2.getTable());
					list.add(joinBean3);
				}
			}
			
			//拼接数据
			for (JoinBean joinBean2 : list) {
				joinBean2.setAge(joinBean.getAge());
				joinBean2.setGender(joinBean.getGender());
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
		job.setMapOutputValueClass(JoinBean.class);
		
		job.setOutputKeyClass(JoinBean.class);
		job.setOutputValueClass(NullWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("D:\\data\\in\\movie"));
		FileOutputFormat.setOutputPath(job, new Path("d:\\data\\out\\movie\\top1"));
		File file = new File("d:\\data\\out\\movie\\top1");
		if(file.exists()){
			FileUtils.deleteDirectory(file);
		}
		
		boolean b = job.waitForCompletion(true);
		System.out.println(b?"恭喜你答对了！":"不要放弃，希望就在明天");
	}
}
