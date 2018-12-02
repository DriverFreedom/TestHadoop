package day07.movie.top3;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

import day07.movie.top1.MovieBean;

/**
 * 进行分区，将想要的数据分发到相同的reduce中
 * @author hasee
 *
 */
public class MyPartition extends Partitioner<MovieBean, NullWritable>{

	/**
	 * numPartitions代表有多少个reduceTask
	 * key 就是map端输出的key
	 * value map端输出的value
	 */
	@Override
	public int getPartition(MovieBean key, NullWritable value, int numPartitions) {
		
		return (key.getMovie().hashCode() & Integer.MAX_VALUE)%numPartitions;
	}
	
	
}
