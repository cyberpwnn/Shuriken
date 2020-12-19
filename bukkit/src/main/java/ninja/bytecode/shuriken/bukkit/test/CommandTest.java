package ninja.bytecode.shuriken.bukkit.test;

import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.bukkit.plugin.ShurikenPlugin;
import ninja.bytecode.shuriken.collections.KList;
import ninja.bytecode.shuriken.logging.L;

public class CommandTest extends ShurikenCommand {
    public CommandTest() {
        super("test");
        requiresPermission(ShurikenAPIPlugin.perm);
    }

    @Override
    public void addTabOptions(ShurikenSender sender, String[] args, KList<String> list) {
        TestRegistry.addArgs(list);
    }

    @Override
    protected String getArgsUsage() {
        return "<test> [args...]";
    }

    @Override
    public boolean handle(ShurikenSender sender, String[] args) {
        try {
            TestRegistry.test(sender, args);
        } catch (Throwable e) {
            L.ex(e);
            sender.sendMessage("Error testing, check the console.");
        }
        return true;
    }
}
