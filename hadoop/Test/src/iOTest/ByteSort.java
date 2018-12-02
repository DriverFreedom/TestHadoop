package iOTest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ByteSort {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(new File("d:/data/byte.txt")));
		BufferedWriter out = new BufferedWriter(new FileWriter(new File("d:/data/byte1.txt")));
		String line = in.readLine();
		in.close();
		char[] c = line.toCharArray();
		Arrays.sort(c);
		
		String str = new String(c);
		out.write(str);
		out.newLine();
		out.flush();
		out.close();
	}
}

