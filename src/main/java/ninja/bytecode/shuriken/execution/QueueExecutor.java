package ninja.bytecode.shuriken.execution;

import ninja.bytecode.shuriken.Shuriken;

public class QueueExecutor extends Looper
{
	private Queue<Runnable> queue;
	
	public QueueExecutor()
	{
		queue = new ShurikenQueue<Runnable>();
		Shuriken.profiler.start("executor-" + getId());
		Shuriken.profiler.stop("executor-" + getId());
	}
	
	public Queue<Runnable> queue()
	{
		return queue;
	}
	
	@Override
	protected long loop()
	{
		Shuriken.profiler.start("executor-" + getId());
		
		while(queue.hasNext())
		{
			try
			{
				queue.next().run();
			}
			
			catch(Throwable e)
			{
				e.printStackTrace();
			}
		}
		
		Shuriken.profiler.stop("executor-" + getId());
		
		return Math.max(50, (long) getRunTime());
	}

	public double getRunTime() {
		return Shuriken.profiler.getResult("executor-" + getId()).getAverage();
	}
}
