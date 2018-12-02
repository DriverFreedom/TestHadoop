package iOTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
/**
 * 复制单级目录中所有以.java结尾的数据
 * 复制到另一个目录中，并将.java改为.jad
 * @author hasee
 *
 */
public class CopyFolderDemo {

	public static void main(String[] args) throws Exception {
		File file1 = new File("D:\\data\\image");
		File file2 = new File("D:\\data\\out\\IOtest");
		if(!file2.exists()){
			file2.mkdirs();
		}
		//获取目录下的files数组
		File[] files = file1.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				
				return new File(dir,name).isFile()&&name.endsWith(".java");
			}
		});
		//遍历该数组  并复制
		for (File file : files) {
			String name = file.getName();
			File newFile = new File(file2,name);
			BufferedReader in = new BufferedReader(new FileReader(file));
			BufferedWriter out = new BufferedWriter(new FileWriter(newFile));
			String line = null;
			while((line = in.readLine())!=null){
				out.write(line);
				out.newLine();
				out.flush();
			}
			in.close();
			out.close();
			
		}
		
		//在目的目录下改名字
		File[] files2 = file2.listFiles();
		for (File file : files2) {
			String name = file.getName();
			String newName = name.replace(".java", ".jad");
			File newFile = new File(file2,newName);
			file.renameTo(newFile);
		}
		
		
		
	}
}
