package ninja.bytecode.shuriken.bukkit.meta;

import com.google.gson.Gson;
import lombok.Data;
import ninja.bytecode.shuriken.bukkit.world.P;
import ninja.bytecode.shuriken.json.JSONObject;

@Data
public class WrappedObject {
    private Object object;
    private final Class<?> type;

    public WrappedObject(Class<?> type, Object object)
    {
        this.type = type;
        this.object = object;
    }

    public WrappedObject(Class<?> type)
    {
        this(type, null);
    }

    public static WrappedObject of(String json) throws ClassNotFoundException {
        JSONObject o = new JSONObject(json);
        Class<?> c = Class.forName(o.getString("t"));
        Object obj = !o.has("o") ? null : new Gson().fromJson(o.get("o").toString(), c);
        return new WrappedObject(c, obj);
    }

    public String toJson() {
        if (object == null)
        {
            JSONObject o = new JSONObject();
            o.put("t", type.getCanonicalName());
            return o.toString(0);
        }

        String json = new Gson().toJson(object);
        JSONObject o = new JSONObject("{\"o\": " + json + "}");
        o.put("t", type.getCanonicalName());
        return o.toString(0);
    }
}
