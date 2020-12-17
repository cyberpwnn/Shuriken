package ninja.bytecode.shuriken.bukkit.api.tome;

import java.io.IOException;

import ninja.bytecode.shuriken.bukkit.sched.J;
import ninja.bytecode.shuriken.bukkit.command.ShurikenCommand;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;


import ninja.bytecode.shuriken.bukkit.util.text.Alphabet;
import ninja.bytecode.shuriken.collections.KList;
import ninja.bytecode.shuriken.collections.KMap;
import ninja.bytecode.shuriken.collections.LKMap;
import org.bukkit.inventory.ItemStack;

public class CommandCatalogue extends ShurikenCommand
{
	public CommandCatalogue()
	{
		super("catalogue", "list", "l", "c");
		requiresPermission(ShurikenAPIPlugin.perm);
	}

	@Override
	public boolean handle(ShurikenSender sender, String[] args)
	{
		J.a(() ->
		{
			KMap<String, Tome> rtomes = TomeLibrary.getInstance().getTomes();
			KList<Tome> tomes = rtomes.sortV();
			LKMap<Alphabet, TomeSection> sections = new LKMap<>();

			//@builder
			Tome tome = new Tome();
			tome.setName("Tome Catalogue");
			tome.setAuthor("Mortar");
			tome.getRoot()
			.add(new TomeMeta("tableOfContents", "true"))
			.add(new TomeMeta("frontPage", "true"));
			//@done

			for(Tome i : tomes)
			{
				if(!sections.containsKey(i.getLetter()))
				{
					sections.put(i.getLetter(), new TomeSection((i.getLetter().getChar() + "").toUpperCase()));
				}

				TomeSection s = sections.get(i.getLetter());
				//@builder
				s.add(new TomeParagraph()
						.add(new TomeFormat()
								.setFormat("italic")
								.setOnClick("run /tome give " + i.getId())
								.add(new TomeHover()
										.add(new TomeParagraph().add(i.getName()))
										.add(new TomeParagraph().add("by " + i.getAuthor()))
										.add(new TomeParagraph().add(" "))
										.add(new TomeParagraph().add("Click to add this tome to your inventory.")))
								.add("\u270E " + i.getName())));
				//@done
			}

			for(Alphabet i : sections.k())
			{
				tome.getRoot().add(sections.get(i));
			}
			ItemStack listing = tome.toItemStack();

			try
			{
				System.out.println(tome.save());
			}

			catch(IOException e)
			{
				e.printStackTrace();
			}


			J.s(() -> sender.player().getInventory().addItem(listing));
		});

		return true;
	}

}
