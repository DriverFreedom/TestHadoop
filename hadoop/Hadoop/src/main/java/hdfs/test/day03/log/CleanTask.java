package hdfs.test.day03.log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;

public class CleanTask extends TimerTask {
	/**
	 * 清理备份日志
	 * 1.遍历出来所有的日志记录文件夹
	 * 2.把文件夹名字 转化为时间
	 * 3.如果文件夹时间与当前时间 时间差大于24小时，则删除
	 */
	@Override
	public void run() {
		try{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		File file = new File("d:/backDir");
		Date date = new Date();
		File[] files = file.listFiles();
		for (File dir : files) {
			String name = dir.getName();
			Date date2 = format.parse(name);
			if(date.getTime()-date2.getTime() > 24*60*60*1000){
				FileUtils.deleteDirectory(dir);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
