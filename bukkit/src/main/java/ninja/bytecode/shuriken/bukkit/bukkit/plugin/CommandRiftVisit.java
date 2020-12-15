package ninja.bytecode.shuriken.bukkit.bukkit.plugin;

import ninja.bytecode.shuriken.bukkit.api.rift.Rift;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarSender;
import ninja.bytecode.shuriken.bukkit.lib.control.RiftController;

public class CommandRiftVisit extends MortarCommand
{
	public CommandRiftVisit()
	{
		super("visit");
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
			sender.sendMessage("/rift visit <NAME>");

			return true;
		}

		String name = args[0];
		RiftController rc = Mortar.getController(RiftController.class);
		Rift rift = rc.getRift(name);
		rift.send(sender.player());

		return true;
	}
}
