package iOTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * 键盘录入5个学生信息(姓名,语文成绩,数学成绩,英语成绩),按照总分从高到低存入文本文件
 * @author hasee
 *
 */
public class StudentDemo {
	public static void main(String[] args) throws Exception {
		TreeSet<Student> set = new TreeSet<Student>(new Comparator<Student>() {

			@Override
			public int compare(Student o1, Student o2) {
				// TODO Auto-generated method stub
				int num1 = o2.getSum()-o1.getSum();
				int num2 = num1==0?o2.getChinese()-o1.getChinese():num1;
				int num3 = num2==0?o2.getMath()-o1.getMath():num2;
				int num4 = num3==0?o2.getEnglish()-o1.getEnglish():num3;
				int num5 = num4==0?o2.getName().compareTo(o1.getName()):num4;
				return num5;
			}
		});
		
		//输入五个学生的信息
		for(int i =0;i<5;i++){
			Scanner sc = new Scanner(System.in);
			System.out.println("请输入第"+i+"个学生的姓名");
			String name = sc.nextLine();
			System.out.println("请输入第"+i+"个学生的语文成绩");
			int chinese = sc.nextInt();
			System.out.println("请输入第"+i+"个学生的数学成绩");
			int math = sc.nextInt();	
			System.out.println("请输入第"+i+"个学生的英语成绩");
			int english = sc.nextInt();
			Student stu = new Student(name, chinese, math, english);
			set.add(stu);
		}
		//存入文本文件
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("d:/data/student.txt")));
		writer.write("学生信息如下:");
		writer.newLine();
		writer.flush();
		writer.write("姓名	语文	数学	英语	总分");
		writer.newLine();
		writer.flush();
		for (Student student : set) {
			writer.write(student.toString());
			writer.newLine();
			writer.newLine();
		}
		writer.close();
		System.out.println("学生信息存储完毕");
	}
}
