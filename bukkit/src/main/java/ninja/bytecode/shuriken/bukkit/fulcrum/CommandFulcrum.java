package ninja.bytecode.shuriken.bukkit.fulcrum;

import ninja.bytecode.shuriken.bukkit.bukkit.command.Command;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarSender;
import ninja.bytecode.shuriken.bukkit.bukkit.plugin.MortarAPIPlugin;

public class CommandFulcrum extends MortarCommand
{
	@Command
	private CommandFulcrumRecompile recompile;

	@Command
	private CommandFulcrumFlash flash;

	@Command
	private CommandFulcrumGive give;

	@Command
	private CommandFulcrumList list;

	@Command
	private CommandFulcrumSparse sparse;

	public CommandFulcrum()
	{
		super("fulcrum", "fu");
		requiresPermission(MortarAPIPlugin.perm);
	}

	@Override
	public boolean handle(MortarSender sender, String[] args)
	{
		sender.sendMessage("/fu sparse - List sparse entries on item.");
		sender.sendMessage("/fu list - List registries");
		sender.sendMessage("/fu give <type>:<id>");
		sender.sendMessage("/fu compile - Reregister & Compile new Pack");
		sender.sendMessage("/fu flash - Flash Pack");
		return true;
	}
}
