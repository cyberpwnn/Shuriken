package ninja.bytecode.shuriken.bukkit.fulcrum;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.FulcrumInstance;
import ninja.bytecode.shuriken.bukkit.api.sched.J;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarSender;
import ninja.bytecode.shuriken.bukkit.bukkit.plugin.MortarAPIPlugin;
import ninja.bytecode.shuriken.bukkit.compute.math.Profiler;
import ninja.bytecode.shuriken.bukkit.logic.format.F;

public class CommandFulcrumRecompile extends MortarCommand
{
	public CommandFulcrumRecompile()
	{
		super("rebuild", "recompile", "build", "compile");
		requiresPermission(MortarAPIPlugin.perm);
	}

	@Override
	public boolean handle(MortarSender sender, String[] args)
	{
		Profiler px = new Profiler();
		px.begin();
		sender.sendMessage("Building Fulcrum Pack from Registry Cache...");
		J.a(() ->
		{
			try
			{
				FulcrumInstance.instance.rebuild();
				px.end();
				sender.sendMessage("Build Successful: " + F.time(px.getMilliseconds(), 0));
			}

			catch(NoSuchAlgorithmException | IOException e)
			{
				e.printStackTrace();
				sender.sendMessage("Build Failed: " + e.getMessage());
			}
		});

		return true;
	}
}
