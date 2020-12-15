package ninja.bytecode.shuriken.bukkit.api.fulcrum.util;

import ninja.bytecode.shuriken.bukkit.compute.math.M;

public class PotentialRange implements Potential
{
	private int min;
	private int max;

	public PotentialRange(int min, int max)
	{
		this.min = min;
		this.max = max;
	}

	@Override
	public int amount()
	{
		return M.rand(min, max);
	}

	public int getMin()
	{
		return min;
	}

	public void setMin(int min)
	{
		this.min = min;
	}

	public int getMax()
	{
		return max;
	}

	public void setMax(int max)
	{
		this.max = max;
	}
}
