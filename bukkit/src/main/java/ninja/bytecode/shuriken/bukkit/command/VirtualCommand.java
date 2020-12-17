package ninja.bytecode.shuriken.bukkit.command;

import java.lang.reflect.Field;

import ninja.bytecode.shuriken.collections.KList;
import ninja.bytecode.shuriken.collections.KMap;
import org.bukkit.command.CommandSender;

import ninja.bytecode.shuriken.bukkit.sched.J;


import ninja.bytecode.shuriken.bukkit.util.reflection.V;
import ninja.bytecode.shuriken.bukkit.util.text.C;

/**
 * Represents a virtual command. A chain of iterative processing through
 * subcommands.
 *
 * @author cyberpwn
 *
 */
public class VirtualCommand
{
	private ICommand command;
	private String tag;

	private KMap<KList<String>, VirtualCommand> children;

	public VirtualCommand(ICommand command)
	{
		this(command, "");
	}

	public VirtualCommand(ICommand command, String tag)
	{
		this.command = command;
		children = new KMap<KList<String>, VirtualCommand>();
		this.tag = tag;

		for(Field i : command.getClass().getDeclaredFields())
		{
			if(i.isAnnotationPresent(Command.class))
			{
				try
				{
					Command cc = i.getAnnotation(Command.class);
					ICommand cmd = (ICommand) i.getType().getConstructor().newInstance();
					new V(command, true, true).set(i.getName(), cmd);
					children.put(cmd.getAllNodes(), new VirtualCommand(cmd, cc.value().trim().isEmpty() ? tag : cc.value().trim()));
				}

				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public String getTag()
	{
		return tag;
	}

	public ICommand getCommand()
	{
		return command;
	}

	public KMap<KList<String>, VirtualCommand> getChildren()
	{
		return children;
	}

	public boolean hit(CommandSender sender, KList<String> chain)
	{
		return hit(sender, chain, null);
	}

	public boolean hit(CommandSender sender, KList<String> chain, String label)
	{
		ShurikenSender vs = new ShurikenSender(sender);
		vs.setTag(tag);

		if (label != null)
			vs.setCommand(label);

		if(chain.isEmpty())
		{
			if(!checkPermissions(sender, command))
			{
				return true;
			}

			return command.handle(vs, new String[0]);
		}

		String nl = chain.get(0);

		for(KList<String> i : children.k())
		{
			for(String j : i)
			{
				if(j.equalsIgnoreCase(nl))
				{
					vs.setCommand(chain.get(0));
					VirtualCommand cmd = children.get(i);
					KList<String> c = chain.copy();
					c.remove(0);
					if(cmd.hit(sender, c, vs.getCommand()))
					{
						return true;
					}
				}
			}
		}

		if(!checkPermissions(sender, command))
		{
			return true;
		}

		return command.handle(vs, chain.toArray(new String[chain.size()]));
	}

	private boolean checkPermissions(CommandSender sender, ICommand command2)
	{
		boolean failed = false;

		for(String i : command.getRequiredPermissions())
		{
			if(!sender.hasPermission(i))
			{
				failed = true;
				J.s(() -> sender.sendMessage("- " + C.WHITE + i));
			}
		}

		if(failed)
		{
			sender.sendMessage("Insufficient Permissions");
			return false;
		}

		return true;
	}
}
