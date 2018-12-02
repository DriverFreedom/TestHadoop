package ThreadTest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lock_1 implements Runnable{
	private int tickets = 100;
	private Lock lock = new ReentrantLock();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				lock.lock();
				if(tickets>0){
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						// TODO: handle exception
					}
					System.out.println(Thread.currentThread().getName()+( tickets--));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}finally{
				lock.unlock();
			}
		}
	}
	
	
	public static void main(String[] args) {
		Lock_1 lock = new Lock_1();
		Thread t1 = new Thread(lock,"one");
		Thread t2 = new Thread(lock,"two");
		Thread t3 = new Thread(lock,"three");
		t1.start();
		t2.start();
		t3.start();
	}	

}
