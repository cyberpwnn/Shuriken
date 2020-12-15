package ninja.bytecode.shuriken.bukkit.api.fulcrum.util;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.FulcrumInstance;
import org.bukkit.Material;

public class BlockHardness
{
	public static double getHardness(Material m)
	{
		return FulcrumInstance.instance.getBlockScraper().getHardness(m);
	}

	public static String getEffectiveTool(Material m)
	{
		return FulcrumInstance.instance.getBlockScraper().getEffectiveTool(m);
	}
}
