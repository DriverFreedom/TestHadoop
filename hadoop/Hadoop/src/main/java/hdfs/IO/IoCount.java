package hdfs.IO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class IoCount {
	public static void main(String[] args) throws Exception {
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop01:9000"), new Configuration(), "root");
		FSDataInputStream inputStream = fs.open(new Path("/word.txt"));
		
		//记住这个
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		List<String> lists = new ArrayList<>();
		String line = null;
		while((line=reader.readLine())!=null){
			
			String[] str = line.split(" ");
			for (String string : str) {
				if( string.length()!=0)
					lists.add(string);
			}
		}
		Map<String,Integer> map = new HashMap<>();
		for (String str : lists) {
			Integer count = map.getOrDefault(str, 0);
			map.put(str, count+1);
		}
		//排序
		mapSort(map);
		FSDataOutputStream outputStream = fs.create(new Path("/count.txt"));
		Set<Entry<String,Integer>> entrySet = map.entrySet();
		for (Entry<String, Integer> entry : entrySet) {
			outputStream.writeUTF(entry.getKey()+":"+entry.getValue()+"\n");
		}
		inputStream.close();
		outputStream.close();
		fs.close();
		
	}
	
	public static  void mapSort(Map<String,Integer> map){
		//Set<Entry<String,Integer>> entrySet = map.entrySet();
		System.out.println(map.size());
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>();
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return o2.getValue()-o1.getValue();
			}
		});
		for (Entry<String, Integer> entry : list) {
			System.out.println(entry.getKey()+":"+entry.getValue()+"\n");
			
		}
	}
}
