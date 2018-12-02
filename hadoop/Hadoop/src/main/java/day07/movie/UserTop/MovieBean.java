package day07.movie.UserTop;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class MovieBean implements WritableComparable<MovieBean>{
	
	private String movie;
	private int rate;
	private String timeStamp;
	private String uid;
	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		this.movie = arg0.readUTF();
		this.rate = arg0.readInt();
		this.timeStamp = arg0.readUTF();
		this.uid = arg0.readUTF();
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
	public int compareTo(MovieBean o) {
		// TODO Auto-generated method stub
		if(o.getUid().compareTo(this.getUid())==0){
			return o.getRate()-this.getRate();
		}else{
			return o.getUid().compareTo(this.getUid());
		}
		
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
	@Override
	public String toString() {
		return "MovieBean [movie=" + movie + ", rate=" + rate + ", timeStamp=" + timeStamp + ", uid=" + uid + "]";
	}
	
	
}
