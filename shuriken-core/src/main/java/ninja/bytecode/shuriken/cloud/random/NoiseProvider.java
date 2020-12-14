package ninja.bytecode.shuriken.cloud.random;
@FunctionalInterface
public interface NoiseProvider
{
	public double noise(double x, double z);
}