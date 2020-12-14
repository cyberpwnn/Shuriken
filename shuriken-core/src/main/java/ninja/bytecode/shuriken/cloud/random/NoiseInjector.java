package ninja.bytecode.shuriken.cloud.random;

@FunctionalInterface
public interface NoiseInjector
{
	public double[] combine(double src, double value);
}
