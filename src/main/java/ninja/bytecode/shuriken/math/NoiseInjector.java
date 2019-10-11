package ninja.bytecode.shuriken.math;

@FunctionalInterface
public interface NoiseInjector
{
	public double[] combine(double src, double value);
}
