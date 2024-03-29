package day09.join;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class JoinBean implements WritableComparable<JoinBean>{

	private String uid;
	private String age;
	private String gender;
	private String movieId;
	private String rating;
	private String table;//记录哪一个表中的数据
	
	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		uid = arg0.readUTF();
		age = arg0.readUTF();
		gender = arg0.readUTF();
		movieId = arg0.readUTF();
		rating = arg0.readUTF();
		table = arg0.readUTF();
	}
	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		arg0.writeUTF(uid);
		arg0.writeUTF(age);
		arg0.writeUTF(gender);
		arg0.writeUTF(movieId);
		arg0.writeUTF(rating);
		arg0.writeUTF(table);
	}
	
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	@Override
	public String toString() {
		return "JoinBean [uid=" + uid + ", age=" + age + ", gender=" + gender + ", movieId=" + movieId + ", rating="
				+ rating + ", table=" + table + "]";
	}
	public void set(String uid, String age, String gender, String movieId, String rating, String table) {
		
		this.uid = uid;
		this.age = age;
		this.gender = gender;
		this.movieId = movieId;
		this.rating = rating;
		this.table = table;
	}
	@Override
	public int compareTo(JoinBean o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
