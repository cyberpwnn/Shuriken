package ninja.bytecode.shuriken.bukkit.api.tome;

import ninja.bytecode.shuriken.bukkit.api.sched.J;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarSender;
import ninja.bytecode.shuriken.bukkit.bukkit.plugin.MortarAPIPlugin;

public class CommandReload extends MortarCommand
{
	public CommandReload()
	{
		super("reload", "relist", "load", "refresh");
		requiresPermission(MortarAPIPlugin.perm);
	}

	@Override
	public boolean handle(MortarSender sender, String[] args)
	{
		J.a(() ->
		{
			TomeLibrary.getInstance().reload();
			sender.sendMessage("Loaded " + TomeLibrary.getInstance().getTomes().size() + " Tomes.");
		});

		return true;
	}

}
