package ninja.bytecode.shuriken;

import ninja.bytecode.shuriken.bench.Profiler;
import ninja.bytecode.shuriken.execution.ChronoLatch;
import ninja.bytecode.shuriken.execution.QueueExecutor;

public class Shuriken
{
	public static final Profiler profiler = new Profiler();
	
	public static void main(String[] a)
	{
		ChronoLatch c = new ChronoLatch(250);
		QueueExecutor q = new QueueExecutor();
		q.start();
		int e = 0;
		
		while(true)
		{
			q.queue().queue(() -> {});
			e++;
			
			if(c.flip())
			{
				System.out.println("MS: " + q.getRunTime() + " E: " + e);
			}
		}
	}
}
