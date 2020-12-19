package ninja.bytecode.shuriken.bukkit.meta;

import ninja.bytecode.shuriken.cache.Cache;
import ninja.bytecode.shuriken.collections.KList;
import ninja.bytecode.shuriken.collections.KMap;
import ninja.bytecode.shuriken.random.RNG;
import ninja.bytecode.shuriken.random.noise.NoiseStyle;
import ninja.bytecode.shuriken.random.noise.stream.ProceduralStream;
import ninja.bytecode.shuriken.random.noise.stream.interpolation.Interpolated;
import org.bukkit.entity.Arrow;

import java.util.List;

public class MetaWorld {
    private KMap<Long, MetaRegion> regions;

    public MetaWorld()
    {
        regions = new KMap<>();


        KList<Arrow> a = new KList<>();




















    }

    public MetaRegion getRegion(int x, int z)
    {
        MetaRegion r = regions.get(Cache.key(x, z));

        if(r == null)
        {
            r = new MetaRegion();
            regions.put(Cache.key(x, z), r);
        }

        return r;
    }

    public HeldMetadata get(String key, int x,int y, int z)
    {
        return getRegion(x >> 5, z >> 5).get(key, x, y, z);
    }
}
