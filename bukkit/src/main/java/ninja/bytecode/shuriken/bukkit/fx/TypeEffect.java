package ninja.bytecode.shuriken.bukkit.fx;

import ninja.bytecode.shuriken.bukkit.world.BlockType;

public interface TypeEffect
{
	public TypeEffect setType(BlockType type);

	public BlockType getType();
}
