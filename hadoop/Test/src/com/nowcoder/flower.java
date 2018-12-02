package com.nowcoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class flower {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int low = sc.nextInt();
		int height = sc.nextInt();
		List<Integer> list = new ArrayList<>();
		for(int i = low; i<=height;i++){
			int a = i/100;
			int b = i/10%10;
			int c = i%10;
			if(a*a*a+b*b*b+c*c*c == i){
				list.add(i);
			}
		}
		if(list.size()==0){
			System.out.println("no");
		}else{
			for(int i = 0;i<list.size();i++){
				if(i==0){
					System.out.print(list.get(i));
				}else{
					System.out.print(" ");
					System.out.print(list.get(i));
				}
			}
		}
	}

}
