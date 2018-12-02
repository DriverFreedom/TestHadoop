package demo_1;

public class NineNine {

	public static void main(String[] args) {
		test(9);
	}
	
	public static void test(int a){
		if(a == 1){
			System.out.println(a+"*"+a+"="+a*a);
		}else{
			test(a-1);
			for(int j = 1;j<=a;j++)
				System.out.print(j+"*"+a+"="+a*j+" ");
			System.out.println();
		}
	}
}

