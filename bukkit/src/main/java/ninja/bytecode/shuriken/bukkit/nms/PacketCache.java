package ninja.bytecode.shuriken.bukkit.api.nms;

import ninja.bytecode.shuriken.collections.KMap;

import java.lang.reflect.InvocationTargetException;

public class PacketCache<T>
{
	private static KMap<Class<?>, PacketCache<?>> cache;
	private final KMap<Long, T> t;
	private final Class<? extends T> tt;

	public PacketCache(Class<? extends T> tt)
	{
		t = new KMap<>();
		this.tt = tt;
	}

	private int size()
	{
		return t.size();
	}

	public T take()
	{
		Thread t = Thread.currentThread();

		if(!this.t.containsKey(t.getId()))
		{
			try
			{
				this.t.put(t.getId(), tt.getConstructor().newInstance());
			}

			catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
			{
				e.printStackTrace();
			}
		}

		return this.t.get(t.getId());
	}

	@SuppressWarnings("unchecked")
	public static <T> T take(Class<? extends T> c)
	{
		if(!cache.containsKey(c))
		{
			cache.put(c, new PacketCache<T>(c));
		}

		return (T) cache.get(c).take();
	}

	public static void reset()
	{
		cache = new KMap<>();
	}

	public static int totalSize()
	{
		int m = 0;

		for(PacketCache<?> i : cache.v())
		{
			m += i.size();
		}

		return m;
	}

	static
	{
		reset();
	}
}
