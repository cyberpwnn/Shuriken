package ninja.bytecode.shuriken.bukkit.meta;

import ninja.bytecode.shuriken.collections.KMap;

public class MetaChunk {
    private final KMap<Integer, MetaChunkSection> sections;

    public MetaChunk()
    {
        sections = new KMap<>();
    }

    public MetaChunk write()
    {
        for(Integer i : sections.k())
        {
            if(sections.get(i).write() == null)
            {
                sections.remove(i);
            }
        }

        if(sections.isEmpty())
        {
            return null;
        }

        return this;
    }

    public MetaChunkSection getSection(int v)
    {
        MetaChunkSection s = sections.get(v);

        if(s == null)
        {
            s = new MetaChunkSection();
            sections.put(v, s);
        }

        return s;
    }

    public HeldMetadata get(String key, int x,int y, int z)
    {
        return getSection(y >> 4).get(key, x, y, z);
    }
}
