package demo_1;

public  class runnableTest implements Runnable{
	public static void main(String[] args) {
		runnableTest test = new runnableTest();
		Thread thread1 = new Thread(test,"花");
		Thread thread2 = new Thread(test,"鹏");
		thread1.start();
		thread2.start();
	}

	@Override
	public void run() {
		
		// TODO Auto-generated method stub
		for(int i = 0;i < 10;i++){
			System.out.println(Thread.currentThread().getName()+":"+i);
		}
	}
}
