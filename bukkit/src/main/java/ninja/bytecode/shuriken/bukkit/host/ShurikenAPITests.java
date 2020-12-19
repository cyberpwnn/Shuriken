package ninja.bytecode.shuriken.bukkit.host;

import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.test.ShurikenTestSuite;
import org.bukkit.command.CommandSender;

public class ShurikenAPITests extends ShurikenTestSuite {
    public ShurikenAPITests(ShurikenSender sender, String[] args) {
        super(sender, args);
    }

    @Override
    public String getName() {
        return "api";
    }
}
