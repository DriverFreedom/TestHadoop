package day06.flow;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * bean封装
 * 实现hadoop的序列化机制
 * @author hasee
 *
 */
public class FlowBean implements Writable{
	
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		up = in.readLong();
		down = in.readLong();
		sum = in.readLong();
	}
	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeLong(up);
		out.writeLong(down);
		out.writeLong(sum);
	}
	private long up;
	private long down;
	private long sum;
	public void set(long up,long down){
		this.up = up;
		this.down = down;
		this.sum = up + down;
	}
	public long getUp() {
		return up;
	}
	public void setUp(long up) {
		this.up = up;
	}
	public long getDown() {
		return down;
	}
	public void setDown(long down) {
		this.down = down;
	}
	public long getSum() {
		return sum;
	}
	public void setSum(long sum) {
		this.sum = sum;
	}
	@Override
	public String toString() {
		return up + " " + down +" "+  sum + "";
	}
	
	
}
