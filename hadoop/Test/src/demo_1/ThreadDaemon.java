package demo_1;

public class ThreadDaemon extends Thread{
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ThreadDaemon daemon1 = new ThreadDaemon();
		ThreadDaemon daemon2 = new ThreadDaemon();
		ThreadDaemon daemon3 = new ThreadDaemon();
		daemon3.setPriority(8);
		
		daemon1.setName("张飞");
		daemon2.setName("关羽");
		//设置守护进程
		daemon1.setDaemon(true);
		daemon2.setDaemon(true);
		daemon1.start();
		daemon2.start();
		daemon3.start();
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 0;i < 10;i++){
			System.out.println(Thread.currentThread().getName()+":"+i);
		}
	}

}
