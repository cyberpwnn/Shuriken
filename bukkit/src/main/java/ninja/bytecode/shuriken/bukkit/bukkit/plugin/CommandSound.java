package ninja.bytecode.shuriken.bukkit.bukkit.plugin;

import ninja.bytecode.shuriken.bukkit.api.sound.Instrument;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarSender;

public class CommandSound extends MortarCommand
{
	public CommandSound()
	{
		super("sound", "inst");
		requiresPermission(MortarAPIPlugin.perm);
	}

	@Override
	public boolean handle(MortarSender sender, String[] args)
	{
		if(args.length == 0)
		{
			for(Instrument i : Instrument.values())
			{
				sender.sendMessage(i.name());
			}
		}

		else
		{
			try
			{
				Instrument.valueOf(args[0].toUpperCase()).play(sender.player());
			}

			catch(Throwable e)
			{
				sender.sendMessage("Not a valid sound");
			}
		}

		return true;
	}

}
