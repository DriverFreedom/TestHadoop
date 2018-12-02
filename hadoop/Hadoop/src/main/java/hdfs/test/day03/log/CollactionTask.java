package hdfs.test.day03.log;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
/**
 * 日志收集步骤：
 * 1、从日志目录查看哪些文件需要上传
 * 2、把需要上传的文件移动到待上传目录
 * 3、上传到hdfs上
 * 4、移动到备份目录
 * @author hasee
 *
 */
public class CollactionTask extends TimerTask{

	@Override
	public void run() {
		try {
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
			String date = format.format(new Date());
			
		// TODO Auto-generated method stub
		//1、查看上传文件
		File logDir = new File("d:/testlog/");
		File[] listFiles = logDir.listFiles(new FilenameFilter() {
			//FileNameFilter 哪些文件是需要获取的
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.startsWith("test.log.");
			}
		});
		//2、将文件移动到待上传目录
		for (File file : listFiles) {
			
				FileUtils.moveFileToDirectory(file, new File("d:/waitUpload"), true);
			}
		
		//3、将待上传的文件逐个上传到hdfs上并移动到备份目录.
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop01:9000"), new Configuration(),"root");
		Path depath = new Path("/log/"+date.substring(0, 10));
		boolean exists = fs.exists(depath);
		//3 判断待上传的目录是否已经存在 不存在则创建一个
		if(!exists){
			fs.mkdirs(depath);
			
		}
		
		//判断备份目录是否存在
		File backDir = new File("d:/backDir/"+date);
		boolean exists2 = backDir.exists();
		if(!exists2){
			backDir.mkdirs();
		}
		
		
		//得到上传的是哪一个服务上的日志文件
		String hostName = InetAddress.getLocalHost().getHostName();
		//4 遍历待上传的目录
		File file = new File("d:/waitUpload");
		File[] list = file.listFiles();
		
		for (File f : list) {
			//上传到hdfs上
			fs.copyFromLocalFile(new Path(f.getPath()), new Path(depath,hostName+"_"+f.getName()+"_"+System.currentTimeMillis()));
			//cp到备份目录
			FileUtils.moveFileToDirectory(f, backDir, true);
		}
		
		
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}


