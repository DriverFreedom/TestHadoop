package day07.movie.UserTop;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class MyGroup extends WritableComparator{

	/**
	 * 初始化 声明一下bean类型
	 */
	public MyGroup() {
		super(MovieBean.class,true);
	}
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		// TODO Auto-generated method stub
		MovieBean b1 = (MovieBean)a;
		MovieBean b2 = (MovieBean)b;
		return b1.getUid().compareTo(b2.getUid());
	}
}
