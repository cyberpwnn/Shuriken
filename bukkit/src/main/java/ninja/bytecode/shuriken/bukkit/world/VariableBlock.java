package ninja.bytecode.shuriken.bukkit.world;


import ninja.bytecode.shuriken.collections.KList;
import org.bukkit.Material;

/**
 * Represents a block that can be multiple blocks (used for variable schematics)
 *
 * @author cyberpwn
 */
public class VariableBlock
{
	private KList<MaterialBlock> blocks;

	public VariableBlock()
	{
		blocks = new KList<MaterialBlock>();
	}

	public VariableBlock(String data)
	{
		this();

		fromString(data);
	}

	/**
	 * Create a variable block
	 *
	 * @param blocks
	 *            blocks
	 */
	public VariableBlock(MaterialBlock... blocks)
	{
		this.blocks = new KList<MaterialBlock>(blocks);
	}

	/**
	 * Add a materialblock into this variable block
	 *
	 * @param material
	 *            the material
	 * @param data
	 *            the data
	 */
	public VariableBlock(Material material, Byte data)
	{
		this(new MaterialBlock(material, data));
	}

	/**
	 * Add a materialblock into this variable block
	 *
	 * @param material
	 *            the material
	 */
	public VariableBlock(Material material)
	{
		this(new MaterialBlock(material));
	}

	/**
	 * Get all variable blocks
	 *
	 * @return the variable blocks
	 */
	public KList<MaterialBlock> getBlocks()
	{
		return blocks;
	}

	public MaterialBlock random()
	{
		return blocks.getRandom();
	}

	/**
	 * Is the given block this block? Does the variable block contain this block
	 *
	 * @param block
	 *            the given block
	 * @return true if it is
	 */
	public boolean is(MaterialBlock block)
	{
		return blocks.contains(block);
	}

	/**
	 * Add a block to the list
	 *
	 * @param block
	 *            the block
	 */
	public void addBlock(MaterialBlock block)
	{
		if(!blocks.contains(block))
		{
			blocks.add(block);
		}
	}

	/**
	 * Remove a block from the list
	 *
	 * @param block
	 *            the block
	 */
	public void removeBlock(MaterialBlock block)
	{
		blocks.remove(block);
	}

	public void fromString(String string)
	{
		blocks.clear();

		if(string.contains(","))
		{
			for(String i : string.split(","))
			{
				try
				{
					blocks.add(W.getMaterialBlock(i));
				}

				catch(Exception e)
				{

				}
			}
		}

		else
		{
			try
			{
				blocks.add(W.getMaterialBlock(string));
			}

			catch(Exception e)
			{

			}
		}
	}

	@Override
	public String toString()
	{
		KList<String> s = new KList<String>();

		for(MaterialBlock i : blocks)
		{
			s.add(i.toString());
		}

		return s.toString(",");
	}
}