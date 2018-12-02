package iOTest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 复制图片
 * 通过缓冲字节流复制
 * @author hasee
 *
 */
public class copyImageDemo {
	public static void main(String[] args) throws Exception {
		File file1 = new File("C:\\Users\\hasee\\Pictures\\jpg\\IMG_1454_meitu_1.jpg");
		File file2 = new File("d:/data/image/me.jpg");
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file1));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file2));
		byte[] b = new byte[1024];
		int len = 0 ;
		while((len = in.read(b))!=-1){
			out.write(b,0,len);
			out.flush();
		}
		in.close();
		out.close();
	}
	
}
