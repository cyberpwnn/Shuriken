package ninja.bytecode.shuriken.bukkit.tome;

import ninja.bytecode.shuriken.bukkit.sched.J;
import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.collections.KList;

public class CommandReload extends ShurikenCommand
{
	public CommandReload()
	{
		super("reload", "relist", "load", "refresh");
		requiresPermission(ShurikenAPIPlugin.perm);
	}

	@Override
	public boolean handle(ShurikenSender sender, String[] args)
	{
		J.a(() ->
		{
			TomeLibrary.getInstance().reload();
			sender.sendMessage("Loaded " + TomeLibrary.getInstance().getTomes().size() + " Tomes.");
		});

		return true;
	}

	@Override
	public void addTabOptions(ShurikenSender sender, String[] args, KList<String> list) {

	}

	@Override
	protected String getArgsUsage() {
		return "";
	}
}
