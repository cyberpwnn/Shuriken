package ninja.bytecode.shuriken.bukkit.bukkit.plugin;

import ninja.bytecode.shuriken.bukkit.api.rift.Rift;
import ninja.bytecode.shuriken.bukkit.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.lib.control.RiftController;

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
