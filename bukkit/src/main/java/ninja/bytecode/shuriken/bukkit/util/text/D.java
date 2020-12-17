package ninja.bytecode.shuriken.bukkit.util.text;

import java.io.Serializable;

import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.bukkit.plugin.ShurikenConfig;
import ninja.bytecode.shuriken.collections.KList;
import ninja.bytecode.shuriken.collections.KMap;


public class D implements Serializable, Logged
{
	private static final long serialVersionUID = 1L;
	private String tag;
	public static final D d = new D("Mortar");
	private static final KMap<String, D> dm = new KMap<>();

	public D(D d, String e)
	{
		this(d.tag + " > " + e);
	}

	public D(String t)
	{
		tag = t;
	}

	private void log(String f, Object... s)
	{
		KList<Object> m = new KList<Object>(s);
		String msg = m.toString(", ");
		String c = C.getLastColors(msg);

		if(f.equals("INFO"))
		{
			c = C.WHITE.toString();
		}

		if(f.equals("WARN"))
		{
			c = C.YELLOW.toString();
		}

		if(f.equals("FATAL"))
		{
			c = C.RED.toString();
		}

		if(f.equals("VERBOSE"))
		{
			c = C.GRAY.toString();
		}

		ShurikenAPIPlugin.log(c + "|" + f.toUpperCase() + "| " + C.LIGHT_PURPLE + tag + C.WHITE + " " + c + msg + c);
	}

	public static D as(Object o)
	{
		return as(o.getClass().getSimpleName());
	}

	public static D as(String tag)
	{
		if(!dm.containsKey(tag))
		{
			dm.put(tag, new D(tag));
		}

		return dm.get(tag);
	}

	@Override
	public void l(Object... s)
	{
		log("INFO", s);
	}

	@Override
	public void v(Object... s)
	{
		if(!ShurikenConfig.DEBUG_LOGGING)
		{
			return;
		}

		log("VERBOSE", s);
	}

	@Override
	public void w(Object... s)
	{
		log("WARN", s);
	}

	@Override
	public void f(Object... s)
	{
		log("FATAL", s);
	}

	public static void ll(Object... s)
	{
		d.log("INFO", s);
	}

	public static void vv(Object... s)
	{
		d.log("VERBOSE", s);
	}

	public static void ww(Object... s)
	{
		d.log("WARN", s);
	}

	public static void ff(Object... s)
	{
		d.log("FATAL", s);
	}
}
