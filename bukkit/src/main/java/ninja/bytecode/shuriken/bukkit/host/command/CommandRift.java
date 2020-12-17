package ninja.bytecode.shuriken.bukkit.host.command;

import ninja.bytecode.shuriken.bukkit.command.Command;
import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;

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
		sender.sendMessage("/rift list - List all active rifts");
		sender.sendMessage("/rift close [name] - Close the rift you are currently in or the name of the rift.");
		sender.sendMessage("/rift open <name> - Open a new blank rift (temporary)");
		sender.sendMessage("/rift visit <name> - The name of the rift");

		return true;
	}
}
