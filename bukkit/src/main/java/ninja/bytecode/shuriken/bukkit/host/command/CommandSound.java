package ninja.bytecode.shuriken.bukkit.bukkit.plugin;

import ninja.bytecode.shuriken.bukkit.api.fx.ParticleRedstone;
import ninja.bytecode.shuriken.bukkit.api.particle.LineParticleManipulator;
import ninja.bytecode.shuriken.bukkit.api.particle.SphereParticleManipulator;
import ninja.bytecode.shuriken.bukkit.api.sound.Audible;
import ninja.bytecode.shuriken.bukkit.api.sound.Audio;
import ninja.bytecode.shuriken.bukkit.api.sound.Instrument;
import ninja.bytecode.shuriken.bukkit.api.sound.MFADistortion;
import ninja.bytecode.shuriken.bukkit.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.ShurikenSender;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;

import java.awt.*;

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

}
