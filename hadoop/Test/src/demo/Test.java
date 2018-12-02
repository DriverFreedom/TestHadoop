package demo;

import java.util.Stack;

public class Test{
	
	
	 public static void main(String[] args) {
		 ListNode l1 = new  ListNode(1);
		 l1.next = new ListNode(2);
		 l1.next.next = new ListNode(4);
		 
		 ListNode l2 = new ListNode(1);
		 l2.next = new ListNode(3);
		 l2.next.next = new ListNode(4);
		 
		 ListNode l = mergeTwoLists(l1,l2);
		 while(l.next != null){
			 System.out.println(l.val);
			 l = l.next;
		 }
	 }
	 
	 public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		 if(l1 == null ) return l2;
		 if(l2 == null ) return l1;
		 ListNode l3 = null;
		 if(l1.val<l2.val){
			 l3 = l1;
			 l3.next = mergeTwoLists(l1.next, l2);
		 }else{
			 l3 = l2;
			 l3.next = mergeTwoLists(l1, l2.next);
		 }
	        return l3;
	   }
	 
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}