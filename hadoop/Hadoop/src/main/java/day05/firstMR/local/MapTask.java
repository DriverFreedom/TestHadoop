package day05.firstMR.local;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


/**
 * 四个参数
 * KEYIN 输入数据的key 行偏移量
 * VALURIN 输入的value,每一行数据的类型
 * KEYOUT 输出的key类型
 * VALUEOUT 输出的value类型
 * 
 * 序列化
 * java的序列化：存储全类名，每一个数据的类型都会存储	效率不高
 * hadoop自己的序列化
 * Long		LongWritable
 * Integer	IntWritable
 * String	Text
 * float	FloatWritable
 * double	DoubleWritable
 * null		NullWritable
 * @author hasee
 *
 */

/**
 * map 阶段: 每一行的数据进行切分，输出数据
 * @author hasee
 *
 */
public class MapTask extends Mapper<LongWritable, Text, Text, IntWritable>{
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		//value 每行的数据
		String[] split = value.toString().split(" ");
		for (String word : split) {
			context.write(new Text(word), new IntWritable(1));
		}
		//super.map(key, value, context);
	}
}
