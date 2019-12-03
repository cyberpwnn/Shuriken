package ninja.bytecode.shuriken.math;

import ninja.bytecode.shuriken.collections.GList;

public class RollingSequence extends Average
{
	private double median;
	private double max;
	private double min;
	private boolean dirtyMedian;
	private int dirtyExtremes;
	private boolean precision;

	public RollingSequence(int size)
	{
		super(size);
		median = 0;
		min = 0;
		max = 0;
		setPrecision(false);
	}
	
	public void setPrecision(boolean p)
	{
		this.precision = p;
	}
	
	public boolean isPrecision()
	{
		return precision;
	}
	
	public double getMin()
	{
		if(dirtyExtremes > (isPrecision() ? 0 : values.length))
		{
			resetExtremes();
		}
		
		return min;
	}
	
	public double getMax()
	{
		if(dirtyExtremes > (isPrecision() ? 0 : values.length))
		{
			resetExtremes();
		}
		
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
		
		dirtyExtremes = 0;
	}

	public void put(double i)
	{
		super.put(i);
		dirtyMedian = true;
		dirtyExtremes++;
		max = M.max(max, i);
		min = M.min(min, i);
	}
}
