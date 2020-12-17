package ninja.bytecode.shuriken.bukkit.fx;

import org.bukkit.util.Vector;

public interface DirectionalEffect
{
	public DirectionalEffect setDirection(Vector v);

	public Vector getDirection();
}
