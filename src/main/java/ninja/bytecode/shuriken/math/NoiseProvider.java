package ninja.bytecode.shuriken.math;
@FunctionalInterface
public interface NoiseProvider
{
	public double noise(double x, double z);
}