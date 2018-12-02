package exam;

import java.io.File;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Hdfs {

	public static void main(String[] args) throws Exception {
		FileSystem fs = FileSystem.get(new URI("hdfs://hadoop01:9000"),new Configuration(),"root");
		File file = new File("d:/logs");
		File[] files = file.listFiles();
		Pattern pattern = Pattern.compile("\\d+$");
		for (File file2 : files) {
			String name = file2.getName();
			Matcher matcher = pattern.matcher(name);
			if(matcher.find()){
				fs.copyFromLocalFile(new Path(file2.getPath()), new Path("/logs"));
			}
		}
		fs.close();
	}
}
