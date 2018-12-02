package com.nowcoder;

import java.util.Scanner;

public class LineReverse {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		String[] split = str.split(" ");
		String line = "";
		for (int i = split.length-1;i >= 0; i--) {
			line += split[i]+" ";
		}
		
		System.out.println(line.substring(0, line.length()-1));
		sc.close();
	}

}
