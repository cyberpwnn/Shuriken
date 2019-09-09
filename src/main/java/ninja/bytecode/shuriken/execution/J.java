package ninja.bytecode.shuriken.execution;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;

import ninja.bytecode.shuriken.logging.L;

public class J
{
	private static int tid = 0;
	private static final ExecutorService e = Executors.newCachedThreadPool(new ThreadFactory()
	{
		@Override
		public Thread newThread(Runnable r)
		{
			tid++;
			Thread t = new Thread(r);
			t.setName("Actuator " + tid);
			t.setPriority(Thread.MIN_PRIORITY);
			t.setUncaughtExceptionHandler((et, e) -> {
				L.f("Exception encountered in " + et.getName());
				L.ex(e);
			});
			
			return t;
		}
	});
	
	public static void a(Runnable a)
	{
		e.submit(a);
	}
	
	public static <T> Future<T> a(Callable<T> a)
	{
		return e.submit(a);
	}
	
	public static boolean attempt(Runnable r)
	{
		try
		{
			r.run();
			return true;
		}
		
		catch(Throwable e)
		{
			
		}
		
		return false;
	}

	public static <T> T attempt(Supplier<T> t, T i)
	{
		try
		{
			return t.get();
		}
		
		catch(Throwable e)
		{
			return i;
		}
	}
}
