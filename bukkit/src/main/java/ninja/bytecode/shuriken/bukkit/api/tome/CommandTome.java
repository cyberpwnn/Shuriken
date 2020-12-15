package ninja.bytecode.shuriken.bukkit.api.tome;

import ninja.bytecode.shuriken.bukkit.bukkit.command.Command;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarSender;
import ninja.bytecode.shuriken.bukkit.bukkit.plugin.ShurikenAPIPlugin;

public class CommandTome extends MortarCommand
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
	public boolean handle(MortarSender sender, String[] args)
	{
		sender.sendMessage("/tome catalogue");
		sender.sendMessage("/tome reload");
		sender.sendMessage("/tome give <id> [-d|--disk]");

		return true;
	}

}
