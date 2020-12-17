package ninja.bytecode.shuriken.bukkit.bukkit.plugin;

import ninja.bytecode.shuriken.bukkit.api.rift.Rift;
import ninja.bytecode.shuriken.bukkit.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.lib.control.RiftController;

public class CommandRiftVisit extends ShurikenCommand
{
	public CommandRiftVisit()
	{
		super("visit");
		requiresPermission(ShurikenAPIPlugin.perm);
	}

	@Override
	public boolean handle(ShurikenSender sender, String[] args)
	{
		if(!sender.isPlayer())
		{
			sender.sendMessage("You cannot see rifts.");
			return true;
		}

		if(args.length == 0)
		{
			sender.sendMessage("/rift visit <NAME>");

			return true;
		}

		String name = args[0];
		RiftController rc = ShurikenBukkit.getController(RiftController.class);
		Rift rift = rc.getRift(name);
		rift.send(sender.player());

		return true;
	}
}
