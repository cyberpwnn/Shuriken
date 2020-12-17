package ninja.bytecode.shuriken.bukkit.hunk;

import ninja.bytecode.shuriken.bukkit.hunk.view.BiomeGridHunkView;
import ninja.bytecode.shuriken.bukkit.hunk.view.ChunkBiomeHunkView;
import ninja.bytecode.shuriken.bukkit.hunk.view.ChunkDataHunkView;
import ninja.bytecode.shuriken.bukkit.hunk.view.ChunkHunkView;
import ninja.bytecode.shuriken.collections.hunk.Hunk;
import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.generator.ChunkGenerator.ChunkData;

public class BukkitHunk<T> {
	public static Hunk<Biome> view(BiomeGrid biome) {
		return new BiomeGridHunkView(biome);
	}

	public static Hunk<BlockData> view(ChunkData src) {
		return new ChunkDataHunkView(src);
	}

	public static Hunk<BlockData> viewBlocks(Chunk src) {
		return new ChunkHunkView(src);
	}

	public static Hunk<Biome> viewBiomes(Chunk src) {
		return new ChunkBiomeHunkView(src);
	}
}