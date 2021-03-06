package ninja.bytecode.shuriken.bukkit.tome;

import ninja.bytecode.shuriken.bukkit.sched.J;
import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.collections.KList;
import org.bukkit.inventory.ItemStack;

public class CommandGive extends ShurikenCommand
{
	public CommandGive()
	{
		super("give", "g", "add");
		requiresPermission(ShurikenAPIPlugin.perm);
	}

	@Override
	public boolean handle(ShurikenSender sender, String[] args)
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

	@Override
	public void addTabOptions(ShurikenSender sender, String[] args, KList<String> list) {

	}

	@Override
	protected String getArgsUsage() {
		return "<id> [-d|--disk]";
	}
}
