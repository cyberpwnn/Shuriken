package ninja.bytecode.shuriken.bukkit.host;

import ninja.bytecode.shuriken.bukkit.test.ShurikenTestSuite;
import org.bukkit.command.CommandSender;

public class ShurikenAPITestsAnywhere extends ShurikenTestSuite<CommandSender> {
    public ShurikenAPITestsAnywhere(CommandSender sender, String[] args) {
        super(sender, args);
    }
}
