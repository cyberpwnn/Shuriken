package ninja.bytecode.shuriken.bukkit.api.fulcrum.util;

import ninja.bytecode.shuriken.bukkit.compute.math.M;

public class PotentialChance implements Potential
{
	private double chance;

	public PotentialChance(double chance)
	{
		this.chance = chance;
	}

	@Override
	public int amount()
	{
		return M.r(chance) ? 1 : 0;
	}

	public double getChance()
	{
		return chance;
	}

	public void setChance(double chance)
	{
		this.chance = chance;
	}
}
