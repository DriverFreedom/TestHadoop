package cn.lkp.MR;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * wordcount 数据从一个hbase表里面拿出来
 * 输入的表：word 一个列簇col,有一个列line 存放的一句话
 * 输出的表：stat 一个列簇ret,有一个列存放count,word存放在rowkey中
 * @author hasee
 *
 */
public class HbaseMR {
	/**
	 * map需要继承TableMapper
	 * 有两个泛型,都是输出的。输入的已经被定义好了。
	 * @author hasee
	 *
	 */
	public static class MapTask extends TableMapper<Text, IntWritable>{
		//输入的key:rowkey	value:一行封装好的一个result
		private static IntWritable value_one = new IntWritable(1);
		private static Text key_word = new Text();
		@Override
		protected void map(ImmutableBytesWritable key, Result value,
				Mapper<ImmutableBytesWritable, Result, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			//
			byte[] value2 = value.getValue(Bytes.toBytes("col"), Bytes.toBytes("line"));
			String line = new String(value2);
			String[] words = line.split(" ");
			for (String word : words) {
				key_word.set(word);
				context.write(key_word, value_one);
			}
			
		}
	}
	/**
	 * reduceTask需要继承TableReduce
	 * keyin和valuein是maptask的值
	 * keyOut 就是 rowkey
	 * @author hasee
	 *
	 */
	
	public static class ReduceTask extends TableReducer<Text, IntWritable, ImmutableBytesWritable>{
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Reducer<Text, IntWritable, ImmutableBytesWritable, Mutation>.Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable intWritable : values) {
				sum++;
			}
			//写出的结果,封装为一个put
			Put put = new Put(Bytes.toBytes(key.toString()));
			put.addColumn(Bytes.toBytes("ret"), Bytes.toBytes("count"), Bytes.toBytes(sum+""));
			context.write(new ImmutableBytesWritable(Bytes.toBytes(key.toString())), put);
		}
	}
	
	/**
	 * 提交任务
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.1.3:2181,192.168.1.4:2181,192.168.1.5:2181");
 		Job job = Job.getInstance(conf);
 		job.setJarByClass(HbaseMR.class);
 		
 		TableMapReduceUtil.initTableMapperJob("word", new Scan(), MapTask.class, Text.class, IntWritable.class, job);
 		TableMapReduceUtil.initTableReducerJob("stat", ReduceTask.class, job);
 		boolean b = job.waitForCompletion(true);
 		System.out.println(b?"恭喜你，成功了":"不好意思，失败了");
	}

}

