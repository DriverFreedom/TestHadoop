package atithmetic;

public class ShellSort {

	public static void main(String[] args) {
		int[] a = {1,4,6,2,8,3,87,7};
		shellSort(a);
		for (int i : a) {
			System.out.println(i);
		}
	}
	
	
	public static void shellSort(int[] a){
		int len = a.length;
		int gap = len/2;
		while(gap>0){
			for(int j = gap;j<len;j++){
				int i = j;
				while(i>=gap&&a[i-gap]>a[i]){
					int tmp = a[i];
					a[i] = a[i-gap];
					a[i-gap] = tmp;
					i = i-gap;
				}
			}
			gap = gap/2;
		}
		
		
	}
}
