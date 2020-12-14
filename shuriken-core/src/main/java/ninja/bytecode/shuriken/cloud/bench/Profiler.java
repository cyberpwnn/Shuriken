package ninja.bytecode.shuriken.cloud.bench;

import ninja.bytecode.shuriken.cloud.math.Average;
import ninja.bytecode.shuriken.cloud.math.M;
import ninja.bytecode.shuriken.cloud.collections.KMap;

public class Profiler 
{
	private int averageSize;
	private long staleTimeout;
	private KMap<String, PrecisionStopwatch> activeProfilers;
	private KMap<String, Average> results;
	private KMap<String, Long> stale;
	
	public Profiler(int averageSize, long staleTimeout)
	{
		this.averageSize = averageSize;
		this.staleTimeout = staleTimeout;
		activeProfilers = new KMap<String, PrecisionStopwatch>();
		results = new KMap<String, Average>();
		stale = new KMap<String, Long>();
	}
	
	public Profiler(int averageSize)
	{
		this(averageSize, 60000);
	}
	
	public Profiler()
	{
		this(10);
	}
	
	public Average getResult(String id)
	{
		if(!results.containsKey(id))
		{
			start(id);
			stop(id);
		}
		
		return results.get(id);
	}
	
	public void start(String id)
	{
		if(activeProfilers.containsKey(id))
		{
			stop(id);
		}
		
		activeProfilers.put(id, PrecisionStopwatch.start());
		stale.put(id, M.ms());
	}
	
	public void stop(String id)
	{
		if(!activeProfilers.containsKey(id))
		{
			return;
		}
		
		if(!results.containsKey(id))
		{
			results.put(id, new Average(averageSize));
		}
		
		results.get(id).put(activeProfilers.get(id).getMilliseconds());
		stale.put(id, M.ms());
		clean();
	}

	private void clean() 
	{
		if(staleTimeout <= 0)
		{
			return;
		}
		
		for(String i : stale.k())
		{
			if(activeProfilers.contains(i))
			{
				continue;
			}
			
			if(M.ms() - stale.get(i) > staleTimeout)
			{
				stale.remove(i);
				results.remove(i);
			}
		}
	}
}
