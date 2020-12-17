package ninja.bytecode.shuriken.bukkit.host.command;

import ninja.bytecode.shuriken.bukkit.api.rift.Rift;
import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.lib.control.RiftController;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.bukkit.plugin.ShurikenBukkit;

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
}
