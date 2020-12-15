package ninja.bytecode.shuriken.bukkit.bukkit.plugin;

import ninja.bytecode.shuriken.bukkit.api.rift.Rift;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarSender;
import ninja.bytecode.shuriken.bukkit.lib.control.RiftController;

public class CommandRiftList extends MortarCommand
{
	public CommandRiftList()
	{
		super("list");
		requiresPermission(MortarAPIPlugin.perm);
	}

	@Override
	public boolean handle(MortarSender sender, String[] args)
	{
		RiftController rc = Mortar.getController(RiftController.class);
		for(Rift i : rc.getRifts())
		{
			sender.sendMessage("- " + i.getName() + " " + (i.isLoaded() ? "[Active]" : "[Hybernating]"));
		}

		sender.sendMessage("There are " + rc.getRifts().size() + " Rifts");

		return true;
	}
}
