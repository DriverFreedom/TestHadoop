package Zookeeper.demo;

public class testVolatile extends Thread{
	
	private volatile boolean  running = true;
	
	public void setRunning(boolean b){
		running = b;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("run方法开始了");
		while(running){
			
		}
		System.out.println("running is false");
	}
	public static void main(String[] args) throws Exception {
		testVolatile t = new testVolatile();
		t.start();
		Thread.sleep(1000);
		t.setRunning(false);
		System.out.println("设置成false了");
		System.out.println(t.running);
	}

}
