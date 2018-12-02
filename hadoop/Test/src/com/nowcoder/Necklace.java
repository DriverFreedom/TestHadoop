package com.nowcoder;

import java.util.Scanner;

public class Necklace {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			System.out.println(help(sc.nextLine()));
		}
	}
	

	private static int help(String nextLine) {
		// TODO Auto-generated method stub
		char[] c = nextLine.toCharArray();
		for(int i = 5;i < c.length;i++){
			for(int j = 0;j<c.length;j++){
				StringBuilder builder = new StringBuilder();
				for(int k = j;k<i+j;k++){
					builder.append(c[k%c.length]);
				}
				String str = builder.toString();
				if(str.contains("A")&&(str.contains("B")&&str.contains("C")&& str.contains("D") && str.contains("E"))){
					return c.length-str.length();
				}
			}
		}
		return 0;
	}
}
