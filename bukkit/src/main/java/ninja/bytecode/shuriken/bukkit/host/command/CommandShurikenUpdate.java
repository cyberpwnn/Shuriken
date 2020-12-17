package ninja.bytecode.shuriken.bukkit.host.command;

import ninja.bytecode.shuriken.bukkit.api.sched.J;
import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;

public class CommandShurikenUpdate extends ShurikenCommand
{
	public CommandShurikenUpdate()
	{
		super("update", "checkup");
		requiresPermission(ShurikenAPIPlugin.perm);
	}

	@Override
	public boolean handle(ShurikenSender sender, String[] args)
	{
		J.a(() -> ShurikenAPIPlugin.p.checkForUpdates(args.length > 0 && args[0].equalsIgnoreCase("-f")));

		if(sender.isPlayer())
		{
			sender.sendMessage("Check Console for update information");
		}

		return true;
	}
}
