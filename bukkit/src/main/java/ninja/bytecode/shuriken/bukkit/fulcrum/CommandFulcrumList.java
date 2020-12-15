package ninja.bytecode.shuriken.bukkit.fulcrum;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.FulcrumInstance;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.registry.FCURegistrar;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.registry.Registered;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarSender;
import ninja.bytecode.shuriken.bukkit.bukkit.plugin.MortarAPIPlugin;
import ninja.bytecode.shuriken.bukkit.lang.collection.GMap;

public class CommandFulcrumList extends MortarCommand
{
	public CommandFulcrumList()
	{
		super("list");
		requiresPermission(MortarAPIPlugin.perm);
	}

	@Override
	public boolean handle(MortarSender sender, String[] args)
	{
		GMap<String, FCURegistrar<? extends Registered>> map = FulcrumInstance.instance.getRegistry().getRegistries();

		if(args.length == 0)
		{
			for(String i : map.k())
			{
				sender.sendMessage(i + " (" + map.get(i).getRegistriesByID().size() + " registered)");
			}

			return true;
		}

		String type = args[0].toLowerCase().trim();

		for(String i : map.k())
		{
			if(type.equalsIgnoreCase(i))
			{
				for(String j : map.get(i).getRegistriesByID().k())
				{
					sender.sendMessage(type + ":" + j);
				}
			}
		}

		return true;
	}
}
