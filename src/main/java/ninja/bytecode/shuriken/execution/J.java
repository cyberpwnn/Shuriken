package ninja.bytecode.shuriken.execution;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class J
{
	private static final ExecutorService e = Executors.newCachedThreadPool();
	
	public static void a(Runnable a)
	{
		e.submit(a);
	}
	
	public static <T> Future<T> a(Callable<T> a)
	{
		return e.submit(a);
	}
}
