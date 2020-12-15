package ninja.bytecode.shuriken.bukkit.api.scm;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import ninja.bytecode.shuriken.bukkit.lang.collection.GMap;

public interface IMappedVolume
{
	public VariableBlock getType(Vector v);

	public VariableBlock getType(Location l);

	public GMap<Vector, VariableBlock> getMapping();

	public GMap<Vector, Location> getRealizedMapping();

	public GMap<Location, Vector> getReverseRealizedMapping();
}
