package com.nowcoder;

import java.util.Scanner;

public class TreeTall {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		if(n<3){
			System.out.println(n);
		}else{
			int max = 0;
			int[] height = new int [n];
            int[] binary = new int[n];
            height[0] = 1;
			for(int i = 0;i < n-1; i++){
				int a = sc.nextInt();
				int b = sc.nextInt();
				binary[a] +=1;
				if(binary[b] < 3){
					height[b] = height[a]+1;
				}
				max = max>height[b]?max:height[b];
				
			}
			System.out.println(max);
		}
		
	}

}
