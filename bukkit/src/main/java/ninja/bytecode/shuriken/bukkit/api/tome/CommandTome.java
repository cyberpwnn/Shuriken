package ninja.bytecode.shuriken.bukkit.api.tome;

import ninja.bytecode.shuriken.bukkit.bukkit.command.Command;
import ninja.bytecode.shuriken.bukkit.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.bukkit.plugin.ShurikenAPIPlugin;

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
		sender.sendMessage("/tome catalogue");
		sender.sendMessage("/tome reload");
		sender.sendMessage("/tome give <id> [-d|--disk]");

		return true;
	}

}
