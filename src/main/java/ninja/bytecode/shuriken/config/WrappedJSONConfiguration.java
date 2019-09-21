package ninja.bytecode.shuriken.config;

import java.io.File;
import java.util.List;

import ninja.bytecode.shuriken.collections.GList;
import ninja.bytecode.shuriken.io.IO;
import ninja.bytecode.shuriken.json.JSONArray;
import ninja.bytecode.shuriken.json.JSONObject;

public class WrappedJSONConfiguration implements ConfigWrapper
{
	private JSONObject wrapped;

	public WrappedJSONConfiguration()
	{
		wrapped = new JSONObject();
	}

	@Override
	public void load(File f) throws Exception
	{
		load(IO.readAll(f));
	}

	@Override
	public void save(File f) throws Exception
	{
		IO.writeAll(f, wrapped.toString(2));
	}

	@Override
	public String save()
	{
		return wrapped.toString(2);
	}

	@Override
	public void load(String s) throws Exception
	{
		JSONObject j = new JSONObject(s);

		for(String i : j.keySet())
		{
			wrapped.put(i, j.get(i));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void set(String key, Object oo)
	{
		Object o = null;

		if(oo instanceof List)
		{
			o = GList.asStringList((List<?>) oo);
		}

		else
		{
			o = oo;
		}

		if(o instanceof GList)
		{
			try
			{
				o = ((GList<String>) o).toJSONStringArray();
			}

			catch(Throwable e)
			{
				e.printStackTrace();
			}
		}

		if(!key.contains("."))
		{
			wrapped.put(key, o);
		}

		else
		{
			JSONObject cursor = wrapped;
			GList<String> splitkey = new GList<String>(key.split("\\.")).removeLast();

			for(String i : splitkey)
			{
				if(!cursor.has(i) || !(cursor.get(i) instanceof JSONObject))
				{
					cursor.put(i, new JSONObject());
				}

				cursor = cursor.getJSONObject(i);
			}

			cursor.put(key.split("\\.")[key.split("\\.").length - 1], o);
		}
	}

	@Override
	public Object get(String key)
	{
		if(!key.contains("."))
		{
			Object o = wrapped.get(key);

			if(o instanceof JSONArray)
			{
				o = GList.fromJSONAny((JSONArray) o);
			}

			return o;
		}

		else
		{
			JSONObject cursor = wrapped;
			GList<String> splitkey = new GList<String>(key.split("\\.")).removeLast();

			for(String i : splitkey)
			{
				if(!cursor.has(i) || !(cursor.get(i) instanceof JSONObject))
				{
					return null;
				}

				cursor = cursor.getJSONObject(i);
			}

			Object o = cursor.get(key.split("\\.")[key.split("\\.").length - 1]);

			if(o instanceof JSONArray)
			{
				return GList.fromJSONAny((JSONArray) o);
			}

			return o;
		}
	}

	@Override
	public GList<String> keys()
	{
		return keys("", wrapped);
	}

	public GList<String> keys(String prefix, JSONObject start)
	{
		GList<String> keys = new GList<>();

		for(String i : start.keySet())
		{
			if(start.get(i) instanceof JSONObject)
			{
				keys.addAll(keys(i + ".", start.getJSONObject(i)));
			}

			else
			{
				keys.add(prefix + i);
			}
		}

		return keys;
	}

	@Override
	public boolean contains(String key)
	{
		return keys().contains(key);
	}
}