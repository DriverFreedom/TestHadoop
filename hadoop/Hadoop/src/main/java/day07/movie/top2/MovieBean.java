package day07.movie.top2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
/**
 * Writable hadoop的序列化接口
 * @author hasee
 *
 */
public class MovieBean implements Writable{
	
	private String movie;
	private int rate;
	private String timeStamp;
	private String uid;
	
	
	
	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		movie = arg0.readUTF();
		rate = arg0.readInt();
		timeStamp = arg0.readUTF();
		uid = arg0.readUTF();
	}
	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		arg0.writeUTF(movie);
		arg0.writeInt(rate);
		arg0.writeUTF(timeStamp);
		arg0.writeUTF(uid);
		
	}
	
	@Override
	public String toString() {
		return "MovieBean [movie=" + movie + ", rate=" + rate + ", timeStamp=" + timeStamp + ", uid=" + uid + "]";
	}
	public String getMovie() {
		return movie;
	}
	public void setMovie(String movie) {
		this.movie = movie;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public void set(MovieBean movieBean) {
		// TODO Auto-generated method stub
		this.movie = movieBean.getMovie();
		this.rate = movieBean.getRate();
		this.timeStamp = movieBean.getTimeStamp();
		this.uid = movieBean.getUid();
	}
	public void set(String movie,int rate,String timeStamp,String uid){
		this.movie = movie;
		this.rate = rate;
		this.timeStamp = timeStamp;
		this.uid = uid;
	}
	
	
	
}
