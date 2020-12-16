package ninja.bytecode.shuriken.bukkit.bukkit.plugin;

import ninja.bytecode.shuriken.bukkit.api.rift.Rift;
import ninja.bytecode.shuriken.bukkit.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.lib.control.RiftController;

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
