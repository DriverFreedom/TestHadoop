package com.nowcoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MaxNumber {

	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		String num = br.readLine();
		StringBuilder sb = new StringBuilder(num);
		int n = Integer.parseInt(br.readLine());
		
		for(int i = 0; i<n;i++){
			sb = help(sb);
		}
		System.out.println(sb);
	}

	private static StringBuilder help(StringBuilder sb) {
		// TODO Auto-generated method stub
		char[] c = sb.toString().toCharArray();
		for(int i = 0;i<sb.length();i++){
			for(int j = i+1;j<sb.length();j++){
				if(c[i]<c[j]){
					String s = sb.substring(0,i)+sb.substring(i+1);
					return new StringBuilder(s);
				}else if(c[i]>c[j]){
					break;
				}
			}
		}
		return new StringBuilder(sb.substring(0,sb.length())) ;
	}

	
}
