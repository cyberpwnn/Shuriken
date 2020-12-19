package ninja.bytecode.shuriken.bukkit.host.command;

import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.logic.format.F;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.collections.KList;

public class CommandClearConsole extends ShurikenCommand
{
	public CommandClearConsole()
	{
		super("cls");
		requiresPermission(ShurikenAPIPlugin.perm);
		setDescription("Clears the console");
	}

	@Override
	public boolean handle(ShurikenSender sender, String[] args)
	{
		if(sender.isPlayer())
		{
			return false;
		}

		sender.sendMessage(F.repeat("\n ", 80));
		sender.sendMessage("Poof");
		return true;
	}

	@Override
	public void addTabOptions(ShurikenSender sender, String[] args, KList<String> list) {

	}

	@Override
	protected String getArgsUsage() {
		return null;
	}
}
