package functions;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

public class CommonUtil {
	public static void LogError(Logger log,Exception e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		String errorStr = sw.getBuffer().toString();
		log.error(errorStr);
	}
}
