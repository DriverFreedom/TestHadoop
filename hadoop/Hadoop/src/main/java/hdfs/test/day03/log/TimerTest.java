package hdfs.test.day03.log;

import java.util.Timer;

public class TimerTest {
	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new CollactionTask(), 0, 2*60*1000);
		timer.schedule(new CleanTask(), 0, 24*60*60*1000);
	}
}
