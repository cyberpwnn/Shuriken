package ninja.bytecode.shuriken.bukkit.api.fx;

import org.bukkit.util.Vector;

public interface DirectionalEffect
{
	public DirectionalEffect setDirection(Vector v);

	public Vector getDirection();
}
