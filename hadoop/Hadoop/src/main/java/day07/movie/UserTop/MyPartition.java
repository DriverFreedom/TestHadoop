package day07.movie.UserTop;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Partitioner;

public class MyPartition extends Partitioner<MovieBean, NullWritable>{

	

	@Override
	public int getPartition(MovieBean arg0, NullWritable arg1, int arg2) {
		// TODO Auto-generated method stub
		return (arg0.getUid().hashCode() & Integer.MAX_VALUE)%arg2;
	}

}
