package Test;


import org.apache.hadoop.hive.ql.exec.UDF;

public class getMaxIndex extends UDF{

	public int evaluate(int a,int b,int c){
		return a>b?a>c?1:3:a>c?2:b<c?3:2;
	}
}
