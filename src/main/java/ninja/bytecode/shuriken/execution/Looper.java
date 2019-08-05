package ninja.bytecode.shuriken.execution;

public abstract class Looper extends Thread
{
	public void run()
	{
		while(!interrupted())
		{
			try
			{
				Thread.sleep(loop());
			}
			
			catch(Throwable e)
			{
				e.printStackTrace();
			}
		}
	}
	
	protected abstract long loop();
}
