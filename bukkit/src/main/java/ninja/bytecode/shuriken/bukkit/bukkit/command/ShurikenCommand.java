package ninja.bytecode.shuriken.bukkit.bukkit.command;

import ninja.bytecode.shuriken.collections.KList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Represents a pawn command
 *
 * @author cyberpwn
 *
 */
public abstract class ShurikenCommand implements ICommand
{
	private KList<ShurikenCommand> children;
	private KList<String> nodes;
	private KList<String> requiredPermissions;
	private String node;
	private String category;
	private String description;

	/**
	 * Override this with a super constructor as most commands shouldnt change these
	 * parameters
	 *
	 * @param node
	 *            the node (primary node) i.e. volume
	 * @param nodes
	 *            the aliases. i.e. v, vol, bile
	 */
	public ShurikenCommand(String node, String... nodes)
	{
		category = "";
		this.node = node;
		this.nodes = new KList<String>(nodes);
		requiredPermissions = new KList<>();
		children = buildChildren();
		description = "No Description";
	}

	public String getDescription()
	{
		return description;
	}

	protected void setDescription(String description)
	{
		this.description = description;
	}

	protected void requiresPermission(ShurikenPermission node)
	{
		if(node == null)
		{
			return;
		}

		requiresPermission(node.toString());
	}

	protected void requiresPermission(String node)
	{
		if(node == null)
		{
			return;
		}

		requiredPermissions.add(node);
	}

	@Override
	public String getNode()
	{
		return node;
	}

	@Override
	public KList<String> getNodes()
	{
		return nodes;
	}

	@Override
	public KList<String> getAllNodes()
	{
		return getNodes().copy().qadd(getNode());
	}

	@Override
	public void addNode(String node)
	{
		getNodes().add(node);
	}

	public KList<ShurikenCommand> getChildren()
	{
		return children;
	}

	private KList<ShurikenCommand> buildChildren()
	{
		KList<ShurikenCommand> p = new KList<>();

		for(Field i : getClass().getDeclaredFields())
		{
			if(i.isAnnotationPresent(Command.class))
			{
				try
				{
					i.setAccessible(true);
					ShurikenCommand pc = (ShurikenCommand) i.getType().getConstructor().newInstance();
					Command c = i.getAnnotation(Command.class);

					if(!c.value().trim().isEmpty())
					{
						pc.setCategory(c.value().trim());
					}

					else
					{
						pc.setCategory(getCategory());
					}

					p.add(pc);
				}

				catch(IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException | SecurityException e)
				{
					e.printStackTrace();
				}
			}
		}

		return p;
	}

	@Override
	public KList<String> getRequiredPermissions()
	{
		return requiredPermissions;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}
}
