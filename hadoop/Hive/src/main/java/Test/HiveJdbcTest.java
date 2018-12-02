package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HiveJdbcTest {

	public static void main(String[] args) throws Exception {
		Class.forName("org.apache.hive.jdbc.HiveDriver");			//加载驱动
		Connection conn = DriverManager.getConnection( "jdbc:hive2://hadoop01:10000","root","");	//建立连接
		Statement statement = conn.createStatement();	//创建执行SQL语句的对象
		ResultSet set = statement.executeQuery("select * from t_user");
		while(set.next()){
			String str = set.getString(2);
			System.out.println(str);
		}
		set.close();
		statement.close();
		conn.close();
	}
}

