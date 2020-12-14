package ninja.bytecode.shuriken.cloud.config;

import ninja.bytecode.shuriken.cloud.json.JSONObject;

public interface Writable
{
	public void fromJSON(JSONObject j);

	public JSONObject toJSON();
}
