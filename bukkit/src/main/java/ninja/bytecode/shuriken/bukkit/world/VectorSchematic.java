package ninja.bytecode.shuriken.bukkit.world;



import ninja.bytecode.shuriken.collections.KList;
import ninja.bytecode.shuriken.collections.KMap;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Vector schematics
 *
 * @author cyberpwn
 */
public class VectorSchematic
{
	private final KMap<Vector, VariableBlock> schematic;

	/**
	 * Create a vector schematic
	 */
	public VectorSchematic()
	{
		schematic = new KMap<Vector, VariableBlock>();
	}

	/**
	 * Does the vector schematic's variable blocks contain the given material
	 * block?
	 *
	 * @param mb
	 *            the material block
	 * @return true if it does
	 */
	public boolean contains(MaterialBlock mb)
	{
		for(Vector i : schematic.k())
		{
			if(schematic.get(i).is(mb))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Does this vector schematic contain multiple of the given material blocks
	 *
	 * @param mb
	 *            the materialblock
	 * @return true if it does
	 */
	public boolean containsMultiple(MaterialBlock mb)
	{
		return find(mb).size() > 1;
	}

	/**
	 * Find all vector references that match the given material block
	 *
	 * @param mb
	 *            the materialblock
	 * @return the vector matches
	 */
	public KList<Vector> find(MaterialBlock mb)
	{
		KList<Vector> vectors = new KList<Vector>();

		for(Vector i : schematic.k())
		{
			if(schematic.get(i).is(mb))
			{
				vectors.add(i);
			}
		}

		return vectors;
	}

	/**
	 * Match the location as part of a multiblock structure
	 *
	 * @param location
	 *            the location
	 * @return the mapping or null if no match
	 */
	public KMap<Vector, Location> match(Location location)
	{
		MaterialBlock mb = new MaterialBlock(location);

		for(Vector i : find(mb))
		{
			Vector shift = i;
			Location base = location.clone().subtract(shift);
			KMap<Vector, Location> map = new KMap<Vector, Location>();
			Boolean found = true;

			for(Vector j : schematic.k())
			{
				Location attempt = base.clone().add(j);
				MaterialBlock mbx = new MaterialBlock(attempt);

				if(schematic.get(j).is(mbx))
				{
					map.put(j, attempt);
				}

				else
				{
					found = false;
					break;
				}
			}

			if(found)
			{
				return map;
			}
		}

		return null;
	}

	/**
	 * Get the schematic
	 *
	 * @return the schematic
	 */
	public KMap<Vector, VariableBlock> getSchematic()
	{
		return schematic;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((schematic == null) ? 0 : schematic.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
		{
			return true;
		}
		if(obj == null)
		{
			return false;
		}
		if(getClass() != obj.getClass())
		{
			return false;
		}
		VectorSchematic other = (VectorSchematic) obj;
		if(schematic == null)
		{
			if(other.schematic != null)
			{
				return false;
			}
		}
		else if(!schematic.equals(other.schematic))
		{
			return false;
		}
		return true;
	}

	/**
	 * Clear the schematic
	 */
	public void clear()
	{
		schematic.clear();
	}
}