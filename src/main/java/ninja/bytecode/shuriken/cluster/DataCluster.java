package ninja.bytecode.shuriken.cluster;

import com.google.gson.Gson;

import ninja.bytecode.shuriken.collections.GMap;
import ninja.bytecode.shuriken.json.JSONObject;

public class DataCluster extends GMap<String, Object>
{
	private static final long serialVersionUID = -7017325150027317798L;
	
	public DataCluster()
	{
		super();
	}
	
	public static DataCluster fromJSON(JSONObject j)
	{
		return new Gson().fromJson(j.toString(0), DataCluster.class);
	}
	
	public JSONObject toJSON()
	{
		return new JSONObject(new Gson().toJson(this));
	}
	
	public DataCluster crop(String prekey)
	{
		DataCluster cropped = new DataCluster();
		
		for(String i : k())
		{
			if(i.startsWith(prekey))
			{
				String k = i.replaceFirst("\\Q" + prekey + "\\E", "");
				cropped.put(k.startsWith(".") ? k.substring(1) : k, get(i));
			}
		}
		
		return cropped;
	}
	
	public void put(DataCluster cc)
	{
		super.putAll(cc);
	}
	
	public void put(DataCluster cc, String prefix)
	{
		for(String i : cc.k())
		{
			put(prefix + i, cc.get(i));
		}
	}
	
	public DataCluster copy()
	{
		DataCluster cc = new DataCluster();
		cc.putAll(super.copy());
		
		return cc;
	}
	
	public Integer getInt(String key)
	{
		return get(key);
	}
	
	public Long getLong(String key)
	{
		return get(key);
	}
	
	public Float getFloat(String key)
	{
		return get(key);
	}
	
	public Short getShort(String key)
	{
		return get(key);
	}
	
	public Boolean getBoolean(String key)
	{
		return get(key);
	}
	
	public Double getDouble(String key)
	{
		return get(key);
	}
	
	public String getString(String key)
	{
		return get(key);
	}
	
	public Byte getByte(String key)
	{
		return get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key)
	{
		return (T) super.get(key);
	}
}
