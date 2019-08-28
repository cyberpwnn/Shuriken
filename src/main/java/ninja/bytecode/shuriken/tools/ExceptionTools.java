package ninja.bytecode.shuriken.tools;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionTools
{
	public static String toString(Throwable e)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		return sw.getBuffer().toString();
	}
}
