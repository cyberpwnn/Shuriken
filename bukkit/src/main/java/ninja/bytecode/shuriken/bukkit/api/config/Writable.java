package ninja.bytecode.shuriken.bukkit.api.config;

import org.json.JSONObject;

public interface Writable
{
	public void fromJSON(JSONObject j);

	public JSONObject toJSON();
}
