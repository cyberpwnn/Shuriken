package ninja.bytecode.shuriken.bukkit.bukkit.plugin;

import ninja.bytecode.shuriken.bukkit.bukkit.command.Command;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarSender;

public class CommandMortar extends MortarCommand
{
	@Command
	private CommandMortarUpdate update;

	@Command
	private CommandSound sound;

	public CommandMortar()
	{
		super("ninja/bytecode/shuriken/bukkit", "mort", "morty", "mortal", "mtr");
		requiresPermission(ShurikenAPIPlugin.perm);
	}

	@Override
	public boolean handle(MortarSender sender, String[] args)
	{
		sender.sendMessage("v" + ShurikenAPIPlugin.p.getDescription().getVersion());
		ShurikenBukkit.checkForUpdates(sender);
		return true;
	}
}
