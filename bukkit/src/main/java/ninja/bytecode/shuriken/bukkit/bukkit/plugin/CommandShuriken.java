package ninja.bytecode.shuriken.bukkit.bukkit.plugin;

import ninja.bytecode.shuriken.bukkit.bukkit.command.Command;
import ninja.bytecode.shuriken.bukkit.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.ShurikenSender;

public class CommandShuriken extends ShurikenCommand
{
	@Command
	private CommandShurikenUpdate update;

	@Command
	private CommandSound sound;

	public CommandShuriken()
	{
		super("shuriken", "shurikens", "shur", "shk", "shken", "sken", "sk");
		requiresPermission(ShurikenAPIPlugin.perm);
	}

	@Override
	public boolean handle(ShurikenSender sender, String[] args)
	{
		sender.sendMessage("v" + ShurikenAPIPlugin.p.getDescription().getVersion());
		ShurikenBukkit.checkForUpdates(sender);
		return true;
	}
}
