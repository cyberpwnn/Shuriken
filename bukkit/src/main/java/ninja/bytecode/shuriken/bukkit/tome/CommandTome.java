package ninja.bytecode.shuriken.bukkit.tome;

import ninja.bytecode.shuriken.bukkit.command.Command;
import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.collections.KList;

public class CommandTome extends ShurikenCommand
{
	@Command
	public CommandReload reload;

	@Command
	public CommandGive give;

	@Command
	public CommandCatalogue cat;

	public CommandTome()
	{
		super("tome");
		requiresPermission(ShurikenAPIPlugin.perm);
	}

	@Override
	public boolean handle(ShurikenSender sender, String[] args)
	{
		printHelp(sender);
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
