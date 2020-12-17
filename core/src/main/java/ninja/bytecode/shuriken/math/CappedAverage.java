package ninja.bytecode.shuriken.bukkit.compute.math;

import ninja.bytecode.shuriken.collections.KList;

import java.util.Collections;



public class CappedAverage extends Average
{
	protected int trim;

	public CappedAverage(int size, int trim)
	{
		super(size);

		if(trim * 2 >= size)
		{
			throw new RuntimeException("Trim cannot be >= half the average size");
		}

		this.trim = trim;
	}

	@Override
	protected double computeAverage()
	{
		double a = 0;

		KList<Double> minmax = new KList<>(data);
		Collections.sort(minmax);

		for(int i = trim; i < data.length - trim; i++)
		{
			a += minmax.get(i);
		}

		return a / ((double) data.length - (double) trim);
	}
}
