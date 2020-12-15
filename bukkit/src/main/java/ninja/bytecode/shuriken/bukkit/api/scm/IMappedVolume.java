package ninja.bytecode.shuriken.bukkit.api.scm;

import ninja.bytecode.shuriken.collections.KMap;
import org.bukkit.Location;
import org.bukkit.util.Vector;



public interface IMappedVolume
{
	public VariableBlock getType(Vector v);

	public VariableBlock getType(Location l);

	public KMap<Vector, VariableBlock> getMapping();

	public KMap<Vector, Location> getRealizedMapping();

	public KMap<Location, Vector> getReverseRealizedMapping();
}
