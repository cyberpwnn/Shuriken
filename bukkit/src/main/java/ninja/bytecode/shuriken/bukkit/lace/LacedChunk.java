package ninja.bytecode.shuriken.bukkit.bukkit.lace;

import ninja.bytecode.shuriken.collections.KList;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;



public interface LacedChunk extends Laced
{
	public LacedWorld getLacedWorld();

	public World getWorld();

	public Chunk getChunk();

	public int getX();

	public int getZ();

	public LacedBlock getBlock(int x, int y, int z);

	public LacedBlock getBlock(Block b);

	public KList<LacedBlock> getBlocks();

	public void commitBlock(MLBlock mlBlock);
}
