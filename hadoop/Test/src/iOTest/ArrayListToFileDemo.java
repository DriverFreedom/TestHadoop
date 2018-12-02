package iOTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 把ArrayList中的字符串数据存储待文件中
 * 字符串数据  所以使用字符流
 * @author hasee
 *
 */
public class ArrayListToFileDemo {

	public static void main(String[] args) throws IOException {
		ArrayList<String> list = new ArrayList();
		list.add("我爱你");
		list.add("你知道吗");
		list.add("你是我的唯一");
		list.add("我要好好守护你");
		
		BufferedWriter out = new BufferedWriter(new FileWriter(new File("d:\\data\\word2.txt")));
		for (String str : list) {
			out.write(str);
			out.newLine();
			out.flush();
		}
		out.close();
	}
}
