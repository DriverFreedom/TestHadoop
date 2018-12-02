package ThreadTest;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class SellTicket_4 implements Runnable{

	private static int tickets = 100;
	//定义同一把锁
	private Object obj = new Object();
	private SellTicket_1 s = new SellTicket_1();
	
	private int x= 0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
				synchronized (obj) {
					if(tickets>0){
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						// TODO: handle exception
					}
					System.out.println(Thread.currentThread().getName()
							+"正在出售第"+(tickets--)+"张票");
					
				}
			
			//x++;
				}
		}
	}
	private static synchronized void SellTicket() {
		// TODO Auto-generated method stub
		if(tickets>0){
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println(Thread.currentThread().getName()+"正在出售第"
					+(tickets--)+"张票");
		}
	}
	
	public static void main(String[] args) {
		SellTicket_4 sell = new SellTicket_4();
		Thread t1 = new Thread(sell,"窗口1");
		Thread t2 = new Thread(sell,"窗口2");
		Thread t3 = new Thread(sell,"窗口3");
		
		t1.start();
		t2.start();
		t3.start();
	}

}
