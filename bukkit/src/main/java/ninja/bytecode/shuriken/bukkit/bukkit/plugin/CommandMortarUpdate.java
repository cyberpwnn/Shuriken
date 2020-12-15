package ninja.bytecode.shuriken.bukkit.bukkit.plugin;

import ninja.bytecode.shuriken.bukkit.api.sched.J;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarSender;

public class CommandMortarUpdate extends MortarCommand
{
	public CommandMortarUpdate()
	{
		super("update", "checkup");
		requiresPermission(MortarAPIPlugin.perm);
	}

	@Override
	public boolean handle(MortarSender sender, String[] args)
	{
		J.a(() -> MortarAPIPlugin.p.checkForUpdates(args.length > 0 && args[0].equalsIgnoreCase("-f")));

		if(sender.isPlayer())
		{
			sender.sendMessage("Check Console for update information");
		}

		return true;
	}
}
