package hdfs.test.day03.log;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MoreLog {
	public static void main(String[] args) {
		Logger logger = LogManager.getLogger(MoreLog.class);
		while(true){
			logger.info("开始咯，哈哈哈哈哈，难过了吧，打不过我吧!啦啦啦啦啦啦。。。");
		}
	}
}
