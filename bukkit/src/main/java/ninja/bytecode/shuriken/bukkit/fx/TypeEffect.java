package ninja.bytecode.shuriken.bukkit.fx;

import org.bukkit.Material;

public interface TypeEffect
{
	public TypeEffect setType(Material type);

	public Material getType();
}
