package ninja.bytecode.shuriken.bukkit.nms;

import ninja.bytecode.shuriken.collections.KMap;
import org.bukkit.Sound;

public class Catalyst {
    public static final CatalystHost host = NMSVersion.current().createHost();
    private static final KMap<String, Sound> soundKeys = createKeys();

    public static Sound getSound(String key)
    {
        return soundKeys.get(key);
    }

    private static KMap<String, Sound> createKeys() {
        KMap<String, Sound> v = new KMap<>();

        for(Sound i : Sound.values())
        {
            v.put(i.getKey().getKey(), i);
        }

        return v;
    }
}
