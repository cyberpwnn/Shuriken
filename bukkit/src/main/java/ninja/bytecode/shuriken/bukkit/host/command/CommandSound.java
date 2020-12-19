package ninja.bytecode.shuriken.bukkit.host.command;

import ninja.bytecode.shuriken.bukkit.sound.Instrument;
import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.collections.KList;

public class CommandSound extends ShurikenCommand
{
	public CommandSound()
	{
		super("sound", "inst");
		requiresPermission(ShurikenAPIPlugin.perm);
	}

	@Override
	public boolean handle(ShurikenSender sender, String[] args)
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

	@Override
	public void addTabOptions(ShurikenSender sender, String[] args, KList<String> list) {
		if(args.length == 0)
		{
			list.addAll(new KList<Instrument>(Instrument.values()).convert(Enum::name));
		}
	}

	@Override
	protected String getArgsUsage() {
		return "[sound]";
	}
}
