package ninja.bytecode.shuriken.bukkit.bukkit.plugin;

import ninja.bytecode.shuriken.bukkit.api.rift.Rift;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarSender;
import ninja.bytecode.shuriken.bukkit.lib.control.RiftController;

public class CommandRiftClose extends MortarCommand
{
	public CommandRiftClose()
	{
		super("close");
		requiresPermission(MortarAPIPlugin.perm);
	}

	@Override
	public boolean handle(MortarSender sender, String[] args)
	{
		if(!sender.isPlayer())
		{
			sender.sendMessage("You cannot see rifts.");
			return true;
		}

		if(args.length == 0)
		{
			RiftController rc = Mortar.getController(RiftController.class);
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
		RiftController rc = Mortar.getController(RiftController.class);
		Rift rift = rc.getRift(name);
		rift.unload();
		rc.getRifts().remove(rift);
		rc.deleteRift(rift.getName());
		sender.sendMessage("Colapsed Rift");

		return true;
	}
}
