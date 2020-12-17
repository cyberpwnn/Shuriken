package ninja.bytecode.shuriken.bukkit.nms;

import ninja.bytecode.shuriken.logging.L;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public enum NMSVersion {
    v1_16_R3(Catalyst16.class);

    private static NMSVersion current;
    private final Class<? extends CatalystHost> hostClazz;

    NMSVersion(Class<? extends CatalystHost> hostClazz) {
        this.hostClazz = hostClazz;
    }

    static {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        String version = name.substring(name.lastIndexOf('.') + 1);
        try {
            current = valueOf(version);
        } catch (Throwable e) {
            L.ex(e);
        }
    }

    public CatalystHost createHost() {
        try {
            return hostClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NMSVersion> getAboveInclusive() {
        List<NMSVersion> n = new ArrayList<>();

        for (NMSVersion i : values()) {
            if (i.ordinal() >= ordinal()) {
                n.add(i);
            }
        }

        return n;
    }

    public List<NMSVersion> betweenInclusive(NMSVersion other) {
        List<NMSVersion> n = new ArrayList<>();

        for (NMSVersion i : values()) {
            if (i.ordinal() <= Math.max(other.ordinal(), ordinal()) && i.ordinal() >= Math.min(ordinal(), other.ordinal())) {
                n.add(i);
            }
        }

        return n;
    }

    public List<NMSVersion> getBelowInclusive() {
        List<NMSVersion> n = new ArrayList<>();

        for (NMSVersion i : values()) {
            if (i.ordinal() <= ordinal()) {
                n.add(i);
            }
        }

        return n;
    }

    public static NMSVersion getMinimum() {
        return values()[values().length - 1];
    }

    public static NMSVersion getMaximum() {
        return values()[0];
    }

    public static NMSVersion current() {
        return current;
    }
}
