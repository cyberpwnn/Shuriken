package ninja.bytecode.shuriken.bukkit.api.fx;

import ninja.bytecode.shuriken.bukkit.api.world.BlockType;

public interface TypeEffect
{
	public TypeEffect setType(BlockType type);

	public BlockType getType();
}
