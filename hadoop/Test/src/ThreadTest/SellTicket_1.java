package ThreadTest;

public class SellTicket_1 extends Thread{

	private static int tickets = 100;
	
	public static void main(String[] args) {
		SellTicket_1 st1 = new SellTicket_1();
		SellTicket_1 st2 = new SellTicket_1();
		SellTicket_1 st3 = new SellTicket_1();
		
		st2.setName("窗口2");
		st3.setName("窗口3");
		st1.setName("窗口1");
		st1.start();
		st2.start();
		st3.start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(tickets>0){
				System.out.println(getName()+"正在出售票"+(tickets--)+"张票");
			}
		}
	}
	
	
}
