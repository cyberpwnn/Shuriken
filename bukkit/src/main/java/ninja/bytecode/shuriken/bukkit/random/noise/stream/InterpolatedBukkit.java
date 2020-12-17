package ninja.bytecode.shuriken.bukkit.random.noise.stream;

import ninja.bytecode.shuriken.random.noise.stream.ProceduralStream;
import ninja.bytecode.shuriken.random.noise.stream.interpolation.InterpolatorFactory;
import org.bukkit.block.data.BlockData;

import java.util.function.Function;

public class InterpolatedBukkit
{
	public static final ninja.bytecode.shuriken.random.noise.stream.interpolation.Interpolated<BlockData> BLOCK_DATA = ninja.bytecode.shuriken.random.noise.stream.interpolation.Interpolated.of((t) -> 0D, (t) -> null);
}
