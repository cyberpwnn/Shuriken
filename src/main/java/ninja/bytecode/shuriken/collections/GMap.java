package ninja.bytecode.shuriken.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ninja.bytecode.shuriken.execution.Queue;
import ninja.bytecode.shuriken.function.Consumer2;
import ninja.bytecode.shuriken.function.Consumer3;

public class GMap<K, V> extends ConcurrentHashMap<K, V>
{
	private static final long serialVersionUID = 7288942695300448163L;

	public GMap()
	{
		super();
	}

	public GMap(GMap<K, V> gMap)
	{
		this();
		put(gMap);
	}

	/**
	 * Puts a value into a map-value-list based on the key such that if GMap<K,
	 * GList<S>> where V is GList<S>
	 *
	 * @param <S>
	 *            the list type in the value type
	 * @param k
	 *            the key to look for
	 * @param vs
	 *            the values to put into the list of the given key
	 * @return the same list (builder)
	 */
	@SuppressWarnings("unchecked")
	public <S> GMap<K, V> putValueList(K k, S... vs)
	{
		try
		{
			GMap<K, GList<S>> s = (GMap<K, GList<S>>) this;

			if(!s.containsKey(k))
			{
				s.put(k, new GList<S>());
			}

			s.get(k).add(vs);
		}

		catch(Throwable e)
		{

		}

		return this;
	}

	/**
	 * Returns a sorted list of keys from this map, based on the sorting order of
	 * the values.
	 *
	 * @return the value-sorted key list
	 */
	public GList<K> sortK()
	{
		GList<K> k = new GList<K>();
		GList<V> v = v();

		Collections.sort(v, new Comparator<V>()
		{
			@Override
			public int compare(V v, V t1)
			{
				return v.toString().compareTo(t1.toString());
			}
		});

		for(V i : v)
		{
			for(K j : k())
			{
				if(get(j).equals(i))
				{
					k.add(j);
				}
			}
		}

		k.dedupe();
		return k;
	}

	/**
	 * Returns a sorted list of keys from this map, based on the sorting order of
	 * the values. Sorting is based on numerical values
	 *
	 * @return the value-sorted key list
	 */
	public GList<K> sortKNumber()
	{
		GList<K> k = new GList<K>();
		GList<V> v = v();

		Collections.sort(v, new Comparator<V>()
		{
			@Override
			public int compare(V v, V t1)
			{
				Number n1 = (Number) v;
				Number n2 = (Number) t1;

				return (int) ((n1.doubleValue() - n2.doubleValue()) * 1000);
			}
		});

		for(V i : v)
		{
			for(K j : k())
			{
				if(get(j).equals(i))
				{
					k.add(j);
				}
			}
		}

		k.dedupe();
		return k;
	}

	/**
	 * Put another map's values into this map
	 *
	 * @param m
	 *            the map to insert
	 * @return this map (builder)
	 */
	public GMap<K, V> put(Map<K, V> m)
	{
		putAll(m);
		return this;
	}

	/**
	 * Return a copy of this map
	 *
	 * @return the copied map
	 */
	public GMap<K, V> copy()
	{
		return new GMap<K, V>(this);
	}

	/**
	 * Loop through each keyvalue set (copy of it) with the map parameter
	 *
	 * @param f
	 *            the function
	 * @return the same gmap
	 */
	public GMap<K, V> rewrite(Consumer3<K, V, GMap<K, V>> f)
	{
		GMap<K, V> m = copy();

		for(K i : m.k())
		{
			f.accept(i, get(i), this);
		}

		return this;
	}

	/**
	 * Loop through each keyvalue set (copy of it)
	 *
	 * @param f
	 *            the function
	 * @return the same gmap
	 */
	public GMap<K, V> each(Consumer2<K, V> f)
	{
		for(K i : k())
		{
			f.accept(i, get(i));
		}

		return this;
	}

	/**
	 * Flip the hashmap and flatten the value list even if there are multiple keys
	 *
	 * @return the flipped and flattened hashmap
	 */
	public GMap<V, K> flipFlatten()
	{
		GMap<V, GList<K>> f = flip();
		GMap<V, K> m = new GMap<>();

		for(V i : f.k())
		{
			m.putNonNull(i, m.isEmpty() ? null : m.get(0));
		}

		return m;
	}

	/**
	 * Flip the hashmap so keys are now list-keys in the value position
	 *
	 * @return the flipped hashmap
	 */
	public GMap<V, GList<K>> flip()
	{
		GMap<V, GList<K>> flipped = new GMap<V, GList<K>>();

		for(K i : keySet())
		{
			if(i == null)
			{
				continue;
			}

			if(!flipped.containsKey(get(i)))
			{
				flipped.put(get(i), new GList<K>());
			}

			flipped.get(get(i)).add(i);
		}

		return flipped;
	}

	/**
	 * Sort values based on the keys sorting order
	 *
	 * @return the values (sorted)
	 */
	public GList<V> sortV()
	{
		GList<V> v = new GList<V>();
		GList<K> k = k();

		Collections.sort(k, new Comparator<K>()
		{
			@Override
			public int compare(K v, K t1)
			{
				return v.toString().compareTo(t1.toString());
			}
		});

		for(K i : k)
		{
			for(V j : v())
			{
				if(get(i).equals(j))
				{
					v.add(j);
				}
			}
		}

		v.dedupe();
		return v;
	}

	/**
	 * Get a copy of this maps keys
	 *
	 * @return the keys
	 */
	public GList<K> k()
	{
		GList<K> k = new GList<K>();
		Enumeration<K> kk = keys();

		while(kk.hasMoreElements())
		{
			K kkk = kk.nextElement();
			k.add(kkk);
		}

		return k;
	}

	/**
	 * Get a copy of this maps values
	 *
	 * @return the values
	 */
	public GList<V> v()
	{
		return new GList<V>(values());
	}

	/**
	 * Still works as it normally should except it returns itself (builder)
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value (single only supported)
	 * @return
	 */
	public GMap<K, V> qput(K key, V value)
	{
		super.put(key, value);
		return this;
	}

	/**
	 * Works just like put, except it wont put anything unless the key and value are
	 * nonnull
	 *
	 * @param key
	 *            the nonnull key
	 * @param value
	 *            the nonnull value
	 * @return the same map
	 */
	public GMap<K, V> putNonNull(K key, V value)
	{
		if(key != null || value != null)
		{
			put(key, value);
		}

		return this;
	}
	
	public V putThen(K key, V valueIfKeyNotPresent)
	{
		if(!containsKey(key))
		{
			put(key, valueIfKeyNotPresent);
		}
		
		return get(key);
	}

	/**
	 * Clear this map and return it
	 *
	 * @return the cleared map
	 */
	public GMap<K, V> qclear()
	{
		super.clear();
		return this;
	}

	/**
	 * Convert this map to keypairs
	 *
	 * @return the keypair list
	 */
	public GList<KeyPair<K, V>> keypair()
	{
		GList<KeyPair<K, V>> g = new GList<>();
		each((k, v) -> g.add(new KeyPair<K, V>(k, v)));
		return g;
	}

	/**
	 * Create a keypair queue
	 *
	 * @return the queue
	 */
	public Queue<KeyPair<K, V>> enqueue()
	{
		return Queue.create(keypair());
	}

	/**
	 * Create a key queue
	 *
	 * @return the queue
	 */
	public Queue<K> enqueueKeys()
	{
		return Queue.create(k());
	}

	/**
	 * Create a value queue
	 *
	 * @return the queue
	 */
	public Queue<V> enqueueValues()
	{
		return Queue.create(v());
	}
}
