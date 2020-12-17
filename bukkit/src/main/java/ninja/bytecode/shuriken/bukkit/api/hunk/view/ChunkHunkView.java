package ninja.bytecode.shuriken.bukkit.api.hunk.view;

import ninja.bytecode.shuriken.collections.hunk.Hunk;
import org.bukkit.Chunk;
import org.bukkit.block.data.BlockData;

public class ChunkHunkView implements Hunk<BlockData>
{
	private final Chunk chunk;

	public ChunkHunkView(Chunk chunk)
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
	public void setRaw(int x, int y, int z, BlockData t)
	{
		if(t == null)
		{
			return;
		}

		chunk.getBlock(x,y,z).setBlockData(t);
	}

	@Override
	public BlockData getRaw(int x, int y, int z)
	{
		return chunk.getBlock(x,y,z).getBlockData();
	}
}
