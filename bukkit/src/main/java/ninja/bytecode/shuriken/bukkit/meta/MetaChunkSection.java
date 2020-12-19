package ninja.bytecode.shuriken.bukkit.meta;

import ninja.bytecode.shuriken.collections.hunk.Hunk;

import java.util.concurrent.atomic.AtomicBoolean;

public class MetaChunkSection {
    private final Hunk<HeldMetadata> data;

    public MetaChunkSection()
    {
        data = Hunk.newMappedHunkSynced(16, 16, 16);
    }

    public MetaChunkSection write()
    {
        AtomicBoolean hasData = new AtomicBoolean(false);
        data.iterateSync((x,y,z,d) -> {
            if(d == null || d.write() == null)
            {
                data.set(x,y,z,null);
            }

            else
            {
                hasData.set(true);
            }
        });

        if(hasData.get())
        {
            return this;
        }

        return null;
    }

    public HeldMetadata get(String key, int x, int y, int z)
    {
        HeldMetadata m = data.get(x&15 ,y&15 ,z&15);

        if(m == null)
        {
            m = new HeldMetadata();
            data.set(x&15,y&15,z&15, m);
        }

        return m;
    }
}
