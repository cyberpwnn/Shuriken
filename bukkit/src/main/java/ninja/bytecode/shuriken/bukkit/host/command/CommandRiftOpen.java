package ninja.bytecode.shuriken.bukkit.host.command;

import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.bukkit.plugin.ShurikenBukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World.Environment;

import ninja.bytecode.shuriken.bukkit.api.generator.FlatGenerator;
import ninja.bytecode.shuriken.bukkit.api.rift.Rift;
import ninja.bytecode.shuriken.bukkit.api.rift.RiftException;
import ninja.bytecode.shuriken.bukkit.lib.control.RiftController;

public class CommandRiftOpen extends ShurikenCommand
{
	public CommandRiftOpen()
	{
		super("open");
		requiresPermission(ShurikenAPIPlugin.perm);
	}

	@Override
	public boolean handle(ShurikenSender sender, String[] args)
	{
		if(args.length == 0)
		{
			sender.sendMessage("/rift open <NAME>");
			return true;
		}

		if(!sender.isPlayer())
		{
			sender.sendMessage("You cannot see rifts.");
			return true;
		}

		String name = args[0];
		try
		{
			RiftController rc = ShurikenBukkit.getController(RiftController.class);
			Rift rift = rc.createRift(name);
			rift.setGenerator(FlatGenerator.class);
			rift.setTemporary(true);
			rift.setRandomLightUpdates(false);
			rift.setDifficulty(Difficulty.PEACEFUL);
			rift.setPhysicsThrottle(10);
			rift.setTileTickLimit(0.1);
			rift.setEntityTickLimit(0.1);
			rift.setForcedGameMode(GameMode.CREATIVE);
			rift.setEnvironment(Environment.THE_END);
			rift.setWorldBorderSize(512);
			rift.setWorldBorderWarningDistance(128);
			rift.setWorldBorderEnabled(true);
			rift.load();
			rift.send(sender.player());
		}

		catch(RiftException e)
		{
			sender.sendMessage("Failed. Check the console.");
			e.printStackTrace();
		}

		return true;
	}
}
