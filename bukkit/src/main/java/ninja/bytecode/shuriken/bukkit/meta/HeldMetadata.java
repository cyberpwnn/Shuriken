package ninja.bytecode.shuriken.bukkit.meta;

import ninja.bytecode.shuriken.collections.KList;
import ninja.bytecode.shuriken.collections.KMap;

public class HeldMetadata {

    private KMap<String, WrappedObject> data;

    public HeldMetadata write()
    {
        for(String i : keys())
        {
            if(get(i).getObject() == null)
            {
                remove(i);
            }
        }

        if(data.isEmpty())
        {
            return null;
        }

        return this;
    }

    public KList<String> keys()
    {
        return data.k();
    }

    public KList<WrappedObject> values()
    {
        return data.v();
    }

    public void clear()
    {
        data.clear();
    }

    public WrappedObject get(String key)
    {
        return data.get(key);
    }

    public boolean has(String key)
    {
        return data.containsKey(key);
    }

    public void remove(String key)
    {
        set(key, null);
    }

    public void set(String key, WrappedObject d)
    {
        if(d == null)
        {
            data.remove(key);
            return;
        }

        data.put(key, d);
    }
}
