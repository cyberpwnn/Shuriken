package ninja.bytecode.shuriken.bukkit.bukkit.lace;

import org.bukkit.Chunk;
import org.bukkit.World;



public interface LacedWorld extends Laced
{
	public String getWorldName();

	public World getWorld();

	public LacedChunk getLacedChunk(Chunk c);

	public LacedBlock getBlock(int x, int y, int z);

	public KList<LacedChunk> getChunksLoaded();

	public void commitChunk(MLChunk mlChunk);
}
