package ninja.bytecode.shuriken.bukkit.api.scm;


import ninja.bytecode.shuriken.collections.KMap;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class MappedSCMVolume implements IMappedVolume
{
	private KMap<Vector, Location> mapping;
	private KMap<Location, Vector> reverseMapping;
	private VectorSchematic schematic;

	public MappedSCMVolume(VectorSchematic schematic, KMap<Vector, Location> mapping)
	{
		this.schematic = schematic;
		this.mapping = mapping;
		reverseMapping = new KMap<Location, Vector>();

		for(Vector i : mapping.k())
		{
			reverseMapping.put(mapping.get(i), i);
		}
	}

	@Override
	public VariableBlock getType(Vector v)
	{
		return schematic.getSchematic().get(v);
	}

	@Override
	public VariableBlock getType(Location l)
	{
		return getType(reverseMapping.get(l));
	}

	@Override
	public KMap<Vector, VariableBlock> getMapping()
	{
		return schematic.getSchematic().copy();
	}

	public VectorSchematic getSchematic()
	{
		return schematic;
	}

	@Override
	public KMap<Vector, Location> getRealizedMapping()
	{
		return mapping.copy();
	}

	@Override
	public KMap<Location, Vector> getReverseRealizedMapping()
	{
		return reverseMapping.copy();
	}
}
