package ninja.bytecode.shuriken.bukkit.host.command;

import ninja.bytecode.shuriken.bukkit.rift.Rift;
import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.host.control.RiftController;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.bukkit.plugin.ShurikenBukkit;
import ninja.bytecode.shuriken.collections.KList;

public class CommandRiftList extends ShurikenCommand
{
	public CommandRiftList()
	{
		super("list");
		requiresPermission(ShurikenAPIPlugin.perm);
	}

	@Override
	public boolean handle(ShurikenSender sender, String[] args)
	{
		RiftController rc = ShurikenBukkit.getController(RiftController.class);
		for(Rift i : rc.getRifts())
		{
			sender.sendMessage("- " + i.getName() + " " + (i.isLoaded() ? "[Active]" : "[Hybernating]"));
		}

		sender.sendMessage("There are " + rc.getRifts().size() + " Rifts");

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
