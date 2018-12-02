package ThreadTest;

public class SellTickets_3 implements Runnable{

	private static int tickets = 100;
	//创建锁对象
	private Object obj = new Object();
	private SellTicket_1 s = new SellTicket_1();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			synchronized(s){	//使用任意对象做锁
				if(tickets>0){
					try {
						//Thread.sleep(100);
					} catch (Exception e) {
						// TODO: handle exception
					}
					System.out.println(Thread.currentThread().getName()+"="+tickets--);
				}
			}
		}
	}

	public static void main(String[] args) {
		SellTickets_3 st = new SellTickets_3();
		Thread t1 = new Thread(st,"窗口1");
		Thread t2 = new Thread(st,"NO2");
		Thread t3 = new Thread(st,"No3");
		t1.start();
		t2.start();
		t3.start();
	}
}
