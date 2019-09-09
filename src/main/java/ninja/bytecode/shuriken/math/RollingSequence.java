package ninja.bytecode.shuriken.math;

import ninja.bytecode.shuriken.collections.GList;

public class RollingSequence extends Average
{
	private double median;
	private double max;
	private double min;
	private boolean dirtyMedian;

	public RollingSequence(int size)
	{
		super(size);
		median = 0;
		min = 0;
		max = 0;
	}
	
	public double getMin()
	{
		return min;
	}
	
	public double getMax()
	{
		return max;
	}
	
	public double getMedian()
	{
		if(dirtyMedian)
		{
			recalculateMedian();
		}
		
		return median;
	}
	
	private void recalculateMedian()
	{
		median = new GList<Double>().forceAdd(values).sort().middleValue();
		dirtyMedian = false;
	}

	public void resetExtremes()
	{
		max = 0;
		min = 0;
		
		for(double i : values)
		{
			max = M.max(max, i);
			min = M.min(min, i);
		}
	}

	public void put(double i)
	{
		super.put(i);
		dirtyMedian = true;
		max = M.max(max, i);
		min = M.min(min, i);
	}
}
