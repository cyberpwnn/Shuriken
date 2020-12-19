package ninja.bytecode.shuriken.bukkit.host.command;

import ninja.bytecode.shuriken.bukkit.command.Command;
import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.collections.KList;

public class CommandRift extends ShurikenCommand
{
	@Command
	private CommandRiftOpen open;
	
	@Command
	private CommandRiftClose close;
	
	@Command
	private CommandRiftList list;
	
	@Command
	private CommandRiftVisit visit;

	public CommandRift()
	{
		super("rift");
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
