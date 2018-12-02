package iOTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


/**
 * 复制文本文件
 * 使用BufferReader和BufferWriter
 * @author hasee
 *
 */
public class copyFileDemo {
	public static void main(String[] args) throws Exception {
		File file1 = new File("d:\\data\\word.txt");
		File file2 = new File("d:\\data\\word2.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file1));
		BufferedWriter writer = new BufferedWriter(new FileWriter(file2));
		String line = null;
		while((line = reader.readLine())!=null){
			writer.write(line);
			writer.newLine();
			writer.flush();
		}
		reader.close();
		writer.close();
		
		/**
		 * char[] c = new char[1024];
		 * int len = 0;
		 * while((len = fr.read(c))!=-1){
		 * 	fw.write(c,0,len);
		 * }
		 */
	}
}
