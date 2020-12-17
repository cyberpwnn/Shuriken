package ninja.bytecode.shuriken.bukkit.plugin;

import ninja.bytecode.shuriken.bukkit.config.Key;

public class ShurikenConfig
{
	@Key("debug.agressive-logging")
	public static boolean DEBUG_LOGGING = false;

	@Key("updates.update-automatically")
	public static boolean UPDATES = true;
}
