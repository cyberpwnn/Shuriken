package ninja.bytecode.shuriken.bukkit.tetris;

import ninja.bytecode.shuriken.bukkit.logic.format.F;
import ninja.bytecode.shuriken.math.CappedAverage;

public class JobMetrics
{
	private CappedAverage time;

	public JobMetrics()
	{
		time = new CappedAverage(32, 4);

		for(int i = 0; i < 32; i++)
		{
			time.put(0.01);
		}
	}

	public void log(double time)
	{
		this.time.put(time);
	}

	public double getEstimatedComputeTime()
	{
		return (time.getAverage());
	}

	public CappedAverage getTime()
	{
		return time;
	}

	@Override
	public String toString()
	{
		return "Estimated: " + F.time(getEstimatedComputeTime(), 2) + " AVG: " + F.time(time.getAverage(), 2);
	}
}
