package atithmetic;

public class Solution {
	public static void main(String[] args) {
		int [] a = new int[]{4,2,3};
		System.out.println(checkPossibility(a));
	}

	public static boolean checkPossibility(int[] nums) {
		int i = 0,j = nums.length-1;
		while(i < j && nums[i] <= nums[i+1])
			i++;
		while(i < j && nums[j] >= nums[j-1])
			j--;
		int head = i == 0 ? Integer.MIN_VALUE : nums[i-1];
		int next = j == nums.length-1 ? Integer.MAX_VALUE : nums[j+1]; 
		if(j-i <= 1 && (head <= nums[j] || nums[i] <= next)){
			return true;
		}else{
			return false;
		}
		
			
	}
}
