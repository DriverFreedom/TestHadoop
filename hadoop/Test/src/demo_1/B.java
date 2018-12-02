package demo_1;

 class A {
	
	static{
		System.out.println("父类的静态代码块");
	}
	{
		System.out.println("父类的构造代码块");
	}
	public A(){
		System.out.println("父类的构造函数");
	
	}
}

public class B extends A{
	static{
		System.out.println("子类的静态代码块");
	}
	{
		System.out.println("子类的构造代码块");
	}
	public B(){
		System.out.println("子类的构造函数");
	
	}
	public static void main(String[] args) {
		String a = "haha";
		String b = "haha";
		System.out.println(a==b);
		
	}
}