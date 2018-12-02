package iOTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * 从文本数据中读取数据(每一行为一个字符串数据)到集合中，并遍历集合
 * BufferedReader
 * @author hasee
 *
 */
public class copyFileToArrayListDemo {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(new File("d:/data/word2.txt")));
		ArrayList<String> list = new ArrayList<>();
		String line = null;
		while((line=in.readLine())!=null){
			list.add(line);
		}
		in.close();
		for (String string : list) {
			System.out.println(string);
		}
	}
}
