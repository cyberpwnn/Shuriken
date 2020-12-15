package ninja.bytecode.shuriken.bukkit.api.world;


import ninja.bytecode.shuriken.collections.KList;
import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 * World utils
 *
 * @author cyberpwn
 */
public class Worlds
{
	/**
	 * Get all worlds
	 *
	 * @return the worlds
	 */
	public static KList<World> getWorlds()
	{
		return new KList<World>(Bukkit.getWorlds());
	}

	/**
	 * Does the world by the given name exist?
	 *
	 * @param world
	 *            the world name
	 * @return true if it does
	 */
	public static boolean hasWorld(String world)
	{
		return getWorld(world) != null;
	}

	/**
	 * Get a world
	 *
	 * @param world
	 *            the world name
	 * @return the world or null
	 */
	public static World getWorld(String world)
	{
		return Bukkit.getWorld(world);
	}
}
