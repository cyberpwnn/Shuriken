package ninja.bytecode.shuriken.logging;

import java.util.function.Consumer;

import ninja.bytecode.shuriken.collections.GList;
import ninja.bytecode.shuriken.execution.Looper;
import ninja.bytecode.shuriken.execution.Queue;
import ninja.bytecode.shuriken.execution.ShurikenQueue;
import ninja.bytecode.shuriken.format.F;
import ninja.bytecode.shuriken.math.M;
import ninja.bytecode.shuriken.tools.ExceptionTools;

public class L
{
	private static final L l = new L();
	private static String lastTag = "";
	private static Looper looper;
	private static Queue<String> logBuffer;
	public static Consumer<String> consoleConsumer;
	public static GList<Consumer<String>> logConsumers;
	
	public static void i(Object... o)
	{
		l.info(o);
	}
	
	public static void w(Object... o)
	{
		l.warn(o);
	}
	
	public static void v(Object... o)
	{
		l.verbose(o);
	}
	
	public static void f(Object... o)
	{
		l.fatal(o);
	}
	
	protected void info(Object... l)
	{
		log("I", l);
	}
	
	protected void verbose(Object... l)
	{
		log("V", l);
	}
	
	protected void warn(Object... l)
	{
		log("W", l);
	}
	
	protected void fatal(Object... l)
	{
		log("F", l);
	}
	
	protected void exception(Throwable e)
	{
		fatal(ExceptionTools.toString(e));
	}

	public static void ex(Throwable e)
	{
		l.fatal(ExceptionTools.toString(e));
	}
	
	private void log(String tag, Object... l)
	{
		if(l.length == 0)
		{
			return;
		}
		
		String tagger = "[" + F.stampTime(M.ms()) + "]: ";
		
		if(lastTag.equals(tagger))
		{
			tagger = F.repeat(" ", lastTag.length());
		}
		
		else
		{
			lastTag = tagger;
		}
		
		if(l.length == 1)
		{
			logBuffer.queue(tagger + "|" + tag + "/" + Thread.currentThread().getName() + "| " + (l[0] != null ? l[0].toString() : "null"));
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		for(Object i : l)
		{
			sb.append(i == null ? "null" : i.toString());
			sb.append(" ");
		}
		
		logBuffer.queue(tagger + "|" + Thread.currentThread().getName() + "/" + tag + "| " + sb.toString());
	}
	
	static
	{
		logConsumers = new GList<Consumer<String>>();
		logBuffer = new ShurikenQueue<String>();
		consoleConsumer = (s) -> System.out.println(s);
		looper = new Looper() {
			@Override
			protected long loop() {
				return flush() ? 250 : 750;
			}

			private boolean flush() {
				boolean logged = false;
				
				while(logBuffer.hasNext())
				{
					String l = logBuffer.next();
					
					if(consoleConsumer != null)
					{
						consoleConsumer.accept(l);
					}
					
					for(Consumer<String> i : logConsumers)
					{
						i.accept(l);
					}
				}
				
				return logged;
			}
		};
		looper.setPriority(Thread.MIN_PRIORITY);
		looper.setName("Log Manager");
		looper.start();
	}
}
