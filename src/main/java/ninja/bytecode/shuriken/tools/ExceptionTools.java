package ninja.bytecode.shuriken.tools;

import java.io.PrintWriter;
import java.io.StringWriter;

import ninja.bytecode.shuriken.collections.GList;

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
	
	public static GList<String> toStrings(Throwable e)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		GList<String> f = new GList<String>(sw.getBuffer().toString().split("\\r?\\n"));
		
		return f;
	}
}
