package ninja.bytecode.shuriken.bukkit.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.collections.KSet;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import ninja.bytecode.shuriken.bukkit.util.text.C;
import ninja.bytecode.shuriken.bukkit.util.text.TXT;

public class ShurikenBukkit
{
	public static boolean STARTUP_LOAD = false;

	public static boolean isMainThread()
	{
		return Bukkit.isPrimaryThread();
	}

	public static File getJar(Class<?> src)
	{
		try
		{
			return new File(new File(src.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath());
		}

		catch(URISyntaxException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static KSet<Class<?>> getClassesInPackage(String p, Class<?> src)
	{
		JarScannerSpecial s = new JarScannerSpecial(getJar(src), p);

		try
		{
			s.scan();
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}

		return s.getClasses();
	}

	public static String tag(String s)
	{
		return TXT.makeTag(C.BLUE, C.DARK_GRAY, C.GRAY, s);
	}

	@SuppressWarnings("unchecked")
	public static <T extends IController> T getController(Class<? extends T> t, Plugin p)
	{
		return (T) ((ShurikenPlugin) p).getController(t);
	}

	public static <T extends IController> T getController(Class<? extends T> t)
	{
		return getController(t, ShurikenAPIPlugin.p);
	}

	public static World getDefaultWorld()
	{
		for(World i : Bukkit.getWorlds())
		{
			if(i.getName().equals("world"))
			{
				return i;
			}
		}

		for(World i : Bukkit.getWorlds())
		{
			if(i.getEnvironment().equals(Environment.NORMAL))
			{
				return i;
			}
		}

		return Bukkit.getWorlds().get(0);
	}

	public static void callEvent(Event e)
	{
		Bukkit.getServer().getPluginManager().callEvent(e);
	}
}
