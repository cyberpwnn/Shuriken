package ninja.bytecode.shuriken.bukkit.host.command;

import ninja.bytecode.shuriken.bukkit.rift.Rift;
import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.host.control.RiftController;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.bukkit.plugin.ShurikenBukkit;

public class CommandRiftClose extends ShurikenCommand
{
	public CommandRiftClose()
	{
		super("close");
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
			RiftController rc = ShurikenBukkit.getController(RiftController.class);
			Rift rift = rc.getRift(sender.player().getWorld());

			if(rift != null)
			{
				rift.unload();
				rc.getRifts().remove(rift);
				rc.deleteRift(rift.getName());
			}

			return true;
		}

		String name = args[0];
		RiftController rc = ShurikenBukkit.getController(RiftController.class);
		Rift rift = rc.getRift(name);
		rift.unload();
		rc.getRifts().remove(rift);
		rc.deleteRift(rift.getName());
		sender.sendMessage("Colapsed Rift");

		return true;
	}
}
