package iOTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class MyBufferedReader {
	
	Reader reader = null;
	public MyBufferedReader(Reader reader) {
		this.reader = reader;
		// TODO Auto-generated constructor stub
	}
	
	public String readLine() throws Exception{
		StringBuilder b = new StringBuilder();
		int ch = 0;
		while((ch = reader.read())!=0){
			if(ch == '\r'){
				continue;
			}
			if(ch=='\n'){
				return b.toString();
			}else{
				b.append((char)ch);
			}
		}
		if(b.length()>0){
			return b.toString();
		}
		
		return null;
	}
	
	public void close(){
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		MyBufferedReader in = new MyBufferedReader(new FileReader(new File("d:/data/line.txt")));
		String line = null;
		while((line = in.readLine())!=null){
			System.out.println(line);
		}
		in.close();
		
	}
}
