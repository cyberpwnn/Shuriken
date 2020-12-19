package ninja.bytecode.shuriken.bukkit.meta;

import ninja.bytecode.shuriken.cache.Cache;
import ninja.bytecode.shuriken.collections.KMap;

public class MetaRegion
{
    private final KMap<Long, MetaChunk> chunks;

    public MetaRegion()
    {
        chunks = new KMap<>();
    }

    public MetaRegion write()
    {
        for(Long i : chunks.k())
        {
            if(chunks.get(i).write() == null)
            {
                chunks.remove(i);
            }
        }

        if(chunks.isEmpty())
        {
            return null;
        }

        return this;
    }

    public MetaChunk getChunk(int x, int z)
    {
        MetaChunk c = chunks.get(Cache.key(x, z));

        if(c == null)
        {
            c = new MetaChunk();
            chunks.put(Cache.key(x, z), c);
        }

        return c;
    }

    public HeldMetadata get(String key, int x,int y, int z)
    {
        return getChunk((x >> 4) & 31, (z >> 4) & 31).get(key, x, y, z);
    }
}
