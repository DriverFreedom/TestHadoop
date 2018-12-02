package demo_1;

import java.util.Date;

public class ThreadStop implements Runnable {
	public static void main(String[] args) {
		ThreadStop stop1 = new ThreadStop();
		Thread t = new Thread(stop1);
		t.start();
		try {
			Thread.sleep(1000);
			t.interrupt();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("开始执行"+new Date());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("线程被终止了");
		}
		System.out.println("继续执行"+new Date());
	}

}
