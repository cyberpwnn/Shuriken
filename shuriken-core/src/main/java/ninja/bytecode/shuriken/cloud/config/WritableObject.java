package ninja.bytecode.shuriken.cloud.config;

import ninja.bytecode.shuriken.cloud.json.JSONObject;

public abstract class WritableObject implements Writable
{
	public abstract void toJSON(JSONObject o);

	@Override
	public abstract void fromJSON(JSONObject j);

	@Override
	public JSONObject toJSON()
	{
		JSONObject j = new JSONObject();
		toJSON(j);
		return j;
	}

}
