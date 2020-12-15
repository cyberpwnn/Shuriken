package ninja.bytecode.shuriken.bukkit.fulcrum;

import ninja.bytecode.shuriken.bukkit.bukkit.compatibility.MaterialEnum;
import ninja.bytecode.shuriken.bukkit.api.fx.EffectCauldronAcceptRecipe;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.ContentAssist;
import ninja.bytecode.shuriken.bukkit.api.sparse.SparseProperties;
import ninja.bytecode.shuriken.bukkit.api.world.P;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarCommand;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarSender;
import ninja.bytecode.shuriken.bukkit.bukkit.plugin.MortarAPIPlugin;
import org.json.JSONObject;

public class CommandFulcrumSparse extends MortarCommand
{
	public CommandFulcrumSparse()
	{
		super("sparse");
		requiresPermission(MortarAPIPlugin.perm);
	}

	@Override
	public boolean handle(MortarSender sender, String[] args)
	{
		ItemStack is = sender.player().getInventory().getItemInMainHand();
		SparseProperties p = null;

		if(is == null || is.getType().equals(MaterialEnum.AIR.bukkitMaterial()))
		{
			Block b = P.targetBlock(sender.player(), 16).getBlock();
			ArmorStand i = ContentAssist.getArmorStand(b);

			if(i != null)
			{
				is = i.getHelmet();

				if(is == null)
				{
					sender.sendMessage("No data found in target block.");
					return true;
				}

				else
				{
					new EffectCauldronAcceptRecipe().play(b.getLocation().clone().add(0.5, 0.5, 0.5));
				}
			}

			else
			{
				sender.sendMessage("No data found in target block.");
				sender.sendMessage("You can also hold an item to read it's data.");
				return true;
			}
		}

		if(is.getItemMeta() == null)
		{
			sender.sendMessage("No data found in target. (" + is.getType().toString() + ")");
			return true;
		}

		p = SparseProperties.from(is);

		if(args.length == 0)
		{
			for(String i : p.getKeys())
			{
				String v = p.getRaw(i).toString(0);

				if(v.length() > 16)
				{
					v = v.substring(0, 13) + "...";
				}

				sender.sendMessage(i + ": " + v);
			}

			if(p.isEmpty())
			{
				sender.sendMessage("No data found in target.");
			}

			else
			{
				sender.sendMessage(p.getKeys().size() + " entries.");
				sender.sendMessage("Use /fu sparse <entry> for full data.");
			}
		}

		if(args.length == 1)
		{
			String key = args[0];

			if(p.contains(key))
			{
				JSONObject o = p.getRaw(key);
				sender.sendMessage(key + ": \n" + o.toString(2));
			}

			else
			{
				sender.sendMessage("Cannot find key " + key);
			}
		}

		return true;
	}
}
