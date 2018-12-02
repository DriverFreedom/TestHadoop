package iOTest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.script.ScriptEngineFactory;

/**
 * 复制多级目录
 *  A:封装数据源File
 *      B:封装目的地File
 *      C:判断该File是文件夹还是文件
 *          a:是文件夹
 *              就在目的地目录下创建该文件夹
 *              获取该File对象下的所有文件或者文件夹File对象
 *              遍历得到每一个File对象
 *              回到C
 *          b:是文件
 *              就复制(字节流)
 * @author hasee
 *
 */
public class CopyFoldsDemo {

	public static void main(String[] args) throws Exception {
		File srcfile = new File("d:/data/in");
		File tofile = new File("d:/data/out");
		//复制文件夹的功能
		copyFolder(srcfile, tofile);
	}
	
	public static void copyFolder(File srcfile,File tofile) throws Exception{
		
		if(srcfile.isDirectory()){
			File newFile = new File(tofile,srcfile.getName());
			newFile.mkdir();
			//获取该file对象下的所有文件或者文件夹file对象
			File[] files = srcfile.listFiles();
			for (File file : files) {
				copyFolder(file,newFile);
			}
			
		}else{
			File newFile = new File(tofile,srcfile.getName());
			copyFile(srcfile,newFile);
		}
		
		
	}

	private static void copyFile(File srcfile, File newFile) throws Exception {
		// TODO Auto-generated method stub
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(srcfile));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(newFile));
		byte[] b = new byte[1024];
		int len = 0;
		while((len = in.read(b))!=-1){
			out.write(b, 0, len);
			out.flush();
		}
		in.close();
		out.close();
	}
}
