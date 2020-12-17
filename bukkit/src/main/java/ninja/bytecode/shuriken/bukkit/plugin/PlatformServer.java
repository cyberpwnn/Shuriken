package ninja.bytecode.shuriken.bukkit.plugin;

import ninja.bytecode.shuriken.math.M;

import java.io.File;


/**
 * Server platform tools
 *
 * @author cyberpwn
 *
 */
public class PlatformServer
{
	/**
	 * Get the time when the server started up (uses server.properties modification
	 * date)
	 *
	 * @return the time when the server fully started up.
	 */
	public static long getStartupTime()
	{
		return new File("server.properties").lastModified();
	}

	/**
	 * Returns the time the server has been online. Reloading does not reset this.
	 *
	 * @return the ACTAUL server uptime
	 */
	public static long getUpTime()
	{
		return M.ms() - getStartupTime();
	}
}
