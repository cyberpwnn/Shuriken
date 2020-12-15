package ninja.bytecode.shuriken.bukkit.bukkit.plugin;

import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarSender;
import ninja.bytecode.shuriken.bukkit.logic.format.F;

public class CommandClearConsole extends MortarCommand
{
	public CommandClearConsole()
	{
		super("cls");
		requiresPermission(ShurikenAPIPlugin.perm);
		setDescription("Clears the console");
	}

	@Override
	public boolean handle(MortarSender sender, String[] args)
	{
		if(sender.isPlayer())
		{
			return false;
		}

		sender.sendMessage(F.repeat("\n ", 80));
		sender.sendMessage("Poof");
		return true;
	}
}
