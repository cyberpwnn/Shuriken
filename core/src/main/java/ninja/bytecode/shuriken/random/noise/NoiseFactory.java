package ninja.bytecode.shuriken.random.noise;

@FunctionalInterface
public interface NoiseFactory 
{
	NoiseGenerator create(long seed);
}
