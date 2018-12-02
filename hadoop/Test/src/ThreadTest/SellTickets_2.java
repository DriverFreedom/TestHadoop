package ThreadTest;

import java.io.IOException;
import java.nio.CharBuffer;

public class SellTickets_2 implements Runnable{

	private static int tickets = 100;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(tickets>0){
				try {
					Thread.sleep(100);;
				} catch (Exception e) {
					// TODO: handle exception
				}
				System.out.println(Thread.currentThread().getName()+"正在出售第"+tickets--+"张票");
				
			}
		}
	}
	
	public static void main(String[] args) {
		//创建资源对象
		SellTickets_2 st = new SellTickets_2();
		Thread t1 = new Thread(st,"线程1");
		Thread t2 = new Thread(st,"线程1");
		Thread t3 = new Thread(st,"线程1");
		t1.start();
		t2.start();
		t3.start();
		
	}

}
