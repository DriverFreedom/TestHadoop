package com.nowcoder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class math {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			int a = sc.nextInt();
			int b = sc.nextInt();
			double sum = 0;
			double r = a;
			while(b>0){
				sum += r;
				r = Math.sqrt(r);
				b--;
			}
			System.out.printf("%.2f",sum);
			System.out.println();
		}
	}

}
