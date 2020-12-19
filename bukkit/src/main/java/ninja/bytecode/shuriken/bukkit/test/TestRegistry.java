package ninja.bytecode.shuriken.bukkit.test;

import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.plugin.ShurikenPlugin;
import ninja.bytecode.shuriken.collections.KList;
import ninja.bytecode.shuriken.collections.KMap;
import ninja.bytecode.shuriken.collections.functional.Consumer2;
import ninja.bytecode.shuriken.collections.functional.ConsumerNasty2;
import ninja.bytecode.shuriken.logging.L;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestRegistry {
    private static final KMap<String, ConsumerNasty2<ShurikenSender, String[]>> tests = new KMap<>();

    public static void registerAll(ShurikenPlugin plugin, Class<? extends ShurikenTestSuite> clazz)
    {
        try {
            tests.putAll(getTestRegistries(plugin, clazz));
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            plugin.f("Failed to register test suite " + clazz.getCanonicalName());
            L.ex(e);
        }
    }

    public static void unregisterAll(ShurikenPlugin plugin, Class<? extends ShurikenTestSuite> clazz)
    {
        try {
            tests.removeAll(getTestRegistries(plugin, clazz).k());
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            plugin.f("Failed to unregister test suite " + clazz.getCanonicalName());
            L.ex(e);
        }
    }

    private static KMap<String, ConsumerNasty2<ShurikenSender, String[]>> getTestRegistries(ShurikenPlugin plugin, Class<? extends ShurikenTestSuite> c) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        KMap<String, ConsumerNasty2<ShurikenSender, String[]>> r = new KMap<>();
        ShurikenTestSuite suite = c.getConstructor(ShurikenSender.class, String[].class).newInstance(null, null);

        for(Method i : c.getDeclaredMethods())
        {
            i.setAccessible(true);
            String tag = plugin.getName() + "." + suite.getName() + "." + i.getName();
            r.put(tag, (a, b) -> c.getConstructor(ShurikenSender.class, String[].class)
                    .newInstance(a,
                            KList.from(b)
                                    .qadd(0, i.getName())
                                    .toStringArray()));
        }

        return r;
    }

    public static void addArgs(KList<String> list) {
        list.addAll(tests.k());
    }

    public static void test(ShurikenSender sender, String[] args) throws Throwable {
        for(String i : tests.k())
        {
            if(i.equals(args[0]))
            {
                tests.get(i).accept(sender, KList.from(args).qdelFirst().toStringArray());
            }
        }
    }
}
