package ninja.bytecode.shuriken.bukkit.host.command;

import ninja.bytecode.shuriken.bukkit.command.Command;
import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.collections.KList;

public class CommandShuriken extends ShurikenCommand
{
	@Command
	private CommandSound sound;

	public CommandShuriken()
	{
		super("shuriken", "shurikens", "shur", "shk", "shken", "sken", "sk");
		requiresPermission(ShurikenAPIPlugin.perm);
	}

	@Override
	public boolean handle(ShurikenSender sender, String[] args)
	{
		sender.sendMessage("v" + ShurikenAPIPlugin.p.getDescription().getVersion());
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
