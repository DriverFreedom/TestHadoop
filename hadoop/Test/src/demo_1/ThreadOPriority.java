package demo_1;

public class ThreadOPriority extends Thread{
	public ThreadOPriority(String name){
		super(name);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ThreadOPriority t1 = new ThreadOPriority("李渊");
		ThreadOPriority t2 = new ThreadOPriority("李世民");
		ThreadOPriority t3 = new ThreadOPriority("李元霸");
		//t1.setPriority(1);
		//t2.setPriority(10);
		t1.start();
		try {
			//t1.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t2.start();
		t3.start();
		//System.out.println(t1.getPriority());
		//System.out.println(t2.getPriority());
		//System.out.println(t3.getPriority());
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 0;i < 10;i++){
			System.out.println(Thread.currentThread().getName()+":"+i);
			try {
				//Thread.sleep(10);
				Thread.yield();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
