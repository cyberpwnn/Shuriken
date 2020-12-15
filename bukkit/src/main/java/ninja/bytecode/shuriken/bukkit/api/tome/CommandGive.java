package ninja.bytecode.shuriken.bukkit.api.tome;

import ninja.bytecode.shuriken.bukkit.api.sched.J;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarSender;
import ninja.bytecode.shuriken.bukkit.bukkit.plugin.ShurikenAPIPlugin;
import org.bukkit.inventory.ItemStack;

public class CommandGive extends MortarCommand
{
	public CommandGive()
	{
		super("give", "g", "add");
		requiresPermission(ShurikenAPIPlugin.perm);
	}

	@Override
	public boolean handle(MortarSender sender, String[] args)
	{
		if(args.length >= 1)
		{
			String id = args[0];
			boolean reload = args.length >= 2 && (args[1].equalsIgnoreCase("-d") || args[1].equalsIgnoreCase("--disk"));

			J.a(() ->
			{
				if(reload)
				{
					TomeLibrary.getInstance().reload();
				}

				Tome t = TomeLibrary.getInstance().getTomes().get(id);

				if(t != null)
				{
					ItemStack is = t.toItemStack();
					J.s(() -> sender.player().getInventory().addItem(is));
				}

				else
				{
					sender.sendMessage("Cannot find tome " + id + ". Try /tome catalogue.");
				}
			});
		}

		else
		{
			sender.sendMessage("/tome give <id> [-d|--disk]");
		}

		return true;
	}

}
