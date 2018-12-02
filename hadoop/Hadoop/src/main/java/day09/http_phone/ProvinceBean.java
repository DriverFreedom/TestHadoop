package day09.http_phone;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class ProvinceBean implements WritableComparable<ProvinceBean>{
	private String prefix;
	private String phone;
	private String province;
	private String city;
	private String isp;
	private int up_data;
	private int down_data;
	private int sum;
	private String table;
	
	
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		prefix = arg0.readUTF();
		phone = arg0.readUTF();
		province = arg0.readUTF();
		city = arg0.readUTF();
		isp = arg0.readUTF();
		up_data = arg0.readInt();
		down_data = arg0.readInt();
		sum = arg0.readInt();
		table = arg0.readUTF();
		
	}
	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		arg0.writeUTF(prefix);
		arg0.writeUTF(phone);
		arg0.writeUTF(province);
		arg0.writeUTF(city);
		arg0.writeUTF(isp);
		arg0.writeInt(up_data);
		arg0.writeInt(down_data);
		arg0.writeInt(sum);
		arg0.writeUTF(table);
	}
	@Override
	public int compareTo(ProvinceBean o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIsp() {
		return isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}
	public int getUp_data() {
		return up_data;
	}
	public void setUp_data(int up_data) {
		this.up_data = up_data;
	}
	public int getDown_data() {
		return down_data;
	}
	public void setDown_data(int down_data) {
		this.down_data = down_data;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	public ProvinceBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return  prefix  + " "+phone +" "+province +" "+ city
				+ " "+isp  +" "+ up_data +" "+ down_data  +" "+ sum ;
	}
	public  void set(String prefix, String phone, String province, String city, String isp, int up_data,
			int down_data, int sum,String table) {
		
		this.prefix = prefix;
		this.phone = phone;
		this.province = province;
		this.city = city;
		this.isp = isp;
		this.up_data = up_data;
		this.down_data = down_data;
		this.sum = sum;
		this.table = table;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	
	
	
	
	
}
