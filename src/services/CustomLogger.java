package services; /**
 * Created by jubair on 12/17/17.
 * Time 1:01 PM
 */
import org.apache.log4j.Logger;

import java.io.PrintStream;

public class CustomLogger {

	private static final Logger logger = Logger.getLogger(CustomLogger.class);

	public static void tieSystemOutAndErrToLog() {
		System.setOut(createLoggingProxy(System.out));
		System.setErr(createLoggingProxy(System.err));
	}

	public static PrintStream createLoggingProxy(final PrintStream realPrintStream) {
		return new PrintStream(realPrintStream) {
			public void print(final String string) {
				realPrintStream.print(string);
				logger.info(string);
			}
		};
	}
}