package day07.movie.top3;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;




/**
 * 分组
 * 将movieid相同的数据分到一个组
 * @author hasee
 *
 */
public class MyGroup extends WritableComparator{

	
	//构造器，初始化
	public MyGroup() {
		super(MovieBean.class,true);
	}
	
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		MovieBean bean1 = (MovieBean)a;
		MovieBean bean2 = (MovieBean)b;
		return bean1.getMovie().compareTo(bean2.getMovie());
	}
}
