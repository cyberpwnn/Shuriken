package ninja.bytecode.shuriken.bukkit.api.sparse;

import java.io.IOException;


import ninja.bytecode.shuriken.bukkit.logic.io.Hasher;
import ninja.bytecode.shuriken.collections.KList;
import ninja.bytecode.shuriken.json.JSONObject;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface SparseProperties
{
	public boolean isModified();

	public KList<String> getKeys();

	public KList<JSONObject> getValues();

	public SparseProperties remove(String key);

	public boolean contains(String key);

	public SparseProperties clear();

	public SparseProperties setRaw(String key, JSONObject data);

	public SparseProperties set(String key, Object data);

	public JSONObject getRaw(String key);

	public <T> T get(String key, Class<? extends T> data);

	public int size();

	public boolean isEmpty();

	public SparseProperties syncronize();

	public JSONObject toJSON();

	public String toCJSON();

	public SparseProperties toItem(ItemStack is);

	public static SparseProperties from(ItemStack is)
	{
		ItemMeta im = is.getItemMeta();

		if(!im.hasLore())
		{
			im.setLore(new KList<>());
		}

		if(!im.getLore().isEmpty() && im.getLore().get(0).startsWith("sparse://"))
		{
			return fromCJSON(im.getLore().get(0).substring(9));
		}

		return fromJSON(new JSONObject());
	}

	public static SparseProperties fromCJSON(String gz)
	{
		try
		{
			return fromStringJSON(Hasher.decompress(gz));
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static SparseProperties fromStringJSON(String j)
	{
		return fromJSON(new JSONObject(j));
	}

	public static SparseProperties fromJSON(JSONObject o)
	{
		return new BSparseProperties(o);
	}
}
