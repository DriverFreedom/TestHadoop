package interview;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class MovieBean implements WritableComparable<MovieBean>{
	private String vedioId;
	private String category;
	private double rate;
	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		vedioId = arg0.readUTF();
		category = arg0.readUTF();
		rate = arg0.readDouble();
	}
	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		arg0.writeUTF(vedioId);
		arg0.writeUTF(category);
		arg0.writeDouble(rate);
		
	}
	@Override
	public int compareTo(MovieBean o) {
		// TODO Auto-generated method stub
		return this.getVedioId().compareTo(o.getVedioId());
	}
	public String getVedioId() {
		return vedioId;
	}
	public void setVedioId(String vedioId) {
		this.vedioId = vedioId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	@Override
	public String toString() {
		return "MovieBean [vedioId=" + vedioId + ", category=" + category + ", rate=" + rate + "]";
	}
	
	

}
