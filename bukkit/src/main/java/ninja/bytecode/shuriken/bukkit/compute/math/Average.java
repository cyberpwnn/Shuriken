package ninja.bytecode.shuriken.bukkit.compute.math;

import ninja.bytecode.shuriken.collections.KList;

import java.util.Collections;



public class Average extends RollingAverage
{
	public Average(int size)
	{
		super(size);
	}

	public double getAverage()
	{
		return get();
	}

	public double getMean()
	{
		KList<Double> g = new KList<>(data);
		Collections.sort(g);

		while(g.size() > 2)
		{
			g.pop();
			g.popLast();
		}

		if(g.size() > 1)
		{
			return (g.get(0) + g.get(1)) / 2D;
		}

		return g.get(0);
	}

	public double getMin()
	{
		double v = Double.MAX_VALUE;

		for(double i : data)
		{
			if(i < v)
			{
				v = i;
			}
		}

		return v;
	}

	public double getMax()
	{
		double v = Double.MIN_VALUE;

		for(double i : data)
		{
			if(i > v)
			{
				v = i;
			}
		}

		return v;
	}
}
