package ninja.bytecode.shuriken.bukkit.api.nms;

import ninja.bytecode.shuriken.collections.KMap;
import ninja.bytecode.shuriken.collections.KSet;
import org.bukkit.Chunk;
import org.bukkit.Location;

public class ChunkTracker
{
	private KMap<Chunk, KSet<Integer>> ig;

	public ChunkTracker()
	{
		ig = new KMap<Chunk, KSet<Integer>>();
	}

	public void hit(Location l)
	{
		if(!ig.containsKey(l.getChunk()))
		{
			ig.put(l.getChunk(), new KSet<Integer>());
		}

		ig.get(l.getChunk()).add(l.getBlockY() >> 4);
	}

	public void flush()
	{
		for(Chunk i : ig.k())
		{
			for(int j : ig.get(i))
			{
				NMP.CHUNK.resend(i.getWorld(), i.getX(), j, i.getZ());
			}
		}

		ig.clear();
	}
}
