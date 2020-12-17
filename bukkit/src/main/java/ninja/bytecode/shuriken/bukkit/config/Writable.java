package ninja.bytecode.shuriken.bukkit.config;

import ninja.bytecode.shuriken.json.JSONObject;

public interface Writable
{
	public void fromJSON(JSONObject j);

	public JSONObject toJSON();
}
