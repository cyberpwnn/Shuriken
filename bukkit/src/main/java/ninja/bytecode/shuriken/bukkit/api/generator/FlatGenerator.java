package ninja.bytecode.shuriken.bukkit.api.generator;

import java.util.Random;

import ninja.bytecode.shuriken.bukkit.bukkit.compatibility.MaterialEnum;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class FlatGenerator extends ChunkGenerator
{
	@Override
	public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
		ChunkData c = createChunkData(world);

		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < 16; j++)
			{
				c.setBlock(i, 0, j, MaterialEnum.BEDROCK.bukkitMaterial());

				if(x % 2 == 0 ^ z % 2 == 0)
				{
					c.setBlock(i, 1, j, MaterialEnum.PURPUR_BLOCK.bukkitMaterial());
				}

				else
				{
					c.setBlock(i, 1, j, MaterialEnum.END_BRICKS.bukkitMaterial());
				}
			}
		}

		return c;
	}
}
