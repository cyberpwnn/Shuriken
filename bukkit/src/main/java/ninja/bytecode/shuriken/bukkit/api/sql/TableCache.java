package ninja.bytecode.shuriken.bukkit.api.sql;


import ninja.bytecode.shuriken.collections.KList;
import ninja.bytecode.shuriken.collections.KMap;

public class TableCache<K, V>
{
	private int limit;
	private KList<K> order;
	private KMap<K, V> map;

	public TableCache(int limit)
	{
		this.limit = limit;
		order = new KList<K>();
		map = new KMap<K, V>();
	}

	public int size()
	{
		return map.size();
	}

	public void clear()
	{
		map.clear();
		order.clear();
	}

	public void invalidate(K k)
	{
		order.remove(k);
		map.remove(k);
	}

	public void put(K k, V v)
	{
		if(!order.contains(k))
		{
			order.add(k);
		}

		map.put(k, v);

		while(order.size() > limit)
		{
			K kf = order.pop();
			map.remove(kf);
		}
	}

	public KList<K> getKeys()
	{
		return map.k().copy();
	}

	public KList<V> getValues()
	{
		return map.v().copy();
	}

	public V get(K k)
	{
		order.remove(k);
		order.add(0, k);
		return map.get(k);
	}

	public boolean has(K k)
	{
		return map.containsKey(k);
	}
}
