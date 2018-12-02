package com.nowcoder;

import java.util.Scanner;

public class kangarooCrossingTheRiver {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int a[] = new int[n];
		for(int i = 0;i<n;i++){
			a[i] = sc.nextInt();
		}
		
		System.out.println(jumpNumber(a,n));
	}

	private static int jumpNumber(int[] a, int n) {
		// TODO Auto-generated method stub
		int res[] = new int[n+1];
		
		res[0] = 0;
		for(int k = 1;k<=n;k++){
			int temp = Integer.MAX_VALUE;
			for(int i =0;i < k;i++){
				if(a[i] + i >=k && res[i]+1<temp){
					res[k] = res[i]+1;
					temp = res[k];
				}
			}
		}
		
		for(int i =1;i<=n;i++){
			if(res[i] == 0)
				res[n] = -1;
		}
		
    	return res[n];
	}

	

}
