package ninja.bytecode.shuriken.bukkit.api.hunk.view;

import ninja.bytecode.shuriken.collections.hunk.Hunk;
import org.bukkit.Chunk;
import org.bukkit.block.Biome;

public class ChunkBiomeHunkView implements Hunk<Biome>
{
	private final Chunk chunk;

	public ChunkBiomeHunkView(Chunk chunk)
	{
		this.chunk = chunk;
	}

	@Override
	public int getWidth()
	{
		return 16;
	}

	@Override
	public int getDepth()
	{
		return 16;
	}

	@Override
	public int getHeight()
	{
		return chunk.getWorld().getMaxHeight();
	}

	@Override
	public void setRaw(int x, int y, int z, Biome t)
	{
		if(t == null)
		{
			return;
		}

		chunk.getBlock(x, y, z).setBiome(t);
	}

	@Override
	public Biome getRaw(int x, int y, int z)
	{
		return chunk.getBlock(x,y,z).getBiome();
	}
}
