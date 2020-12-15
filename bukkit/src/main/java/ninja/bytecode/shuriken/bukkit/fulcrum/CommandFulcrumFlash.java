package ninja.bytecode.shuriken.bukkit.fulcrum;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.FulcrumInstance;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.ResourcePackUtil;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarSender;
import ninja.bytecode.shuriken.bukkit.bukkit.plugin.MortarAPIPlugin;

public class CommandFulcrumFlash extends MortarCommand
{
	public CommandFulcrumFlash()
	{
		super("flash");
		requiresPermission(MortarAPIPlugin.perm);
	}

	@Override
	public boolean handle(MortarSender sender, String[] args)
	{
		new ResourcePackUtil().sendResourcePackWeb(sender.player(), FulcrumInstance.packName + ".zip");

		return true;
	}
}
