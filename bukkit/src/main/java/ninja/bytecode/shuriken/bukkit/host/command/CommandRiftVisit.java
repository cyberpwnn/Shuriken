package ninja.bytecode.shuriken.bukkit.host.command;

import ninja.bytecode.shuriken.bukkit.rift.Rift;
import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.host.control.RiftController;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.bukkit.plugin.ShurikenBukkit;
import ninja.bytecode.shuriken.collections.KList;

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


	@Override
	public void addTabOptions(ShurikenSender sender, String[] args, KList<String> list) {

	}

	@Override
	protected String getArgsUsage() {
		return "<rift>";
	}
}
