package ninja.bytecode.shuriken.math;

import ninja.bytecode.shuriken.collections.GList;

public class CNG
{
	public static final NoiseInjector ADD = (s, v) -> new double[] {s + v, 1};
	public static final NoiseInjector SUBTRACT = (s, v) -> new double[] {s - v < 0 ? 0 : s - v, -1};
	public static final NoiseInjector MULTIPLY = (s, v) -> new double[] {s * v, 0};
	public static final NoiseInjector MAX = (s, v) -> new double[] {Math.max(s, v), 0};
	public static final NoiseInjector MIN = (s, v) -> new double[] {Math.min(s, v), 0};

	private double freq;
	private double amp;
	private double scale;
	private double fscale;
	private final GList<CNG> children;
	private CNG fracture;
	private SimplexOctaveGenerator generator;
	private final double opacity;
	private NoiseInjector injector;
	private RNG rng;

	public CNG(RNG random, double opacity, int octaves)
	{
		this.rng = random;
		freq = 1;
		amp = 1;
		scale = 1;
		fscale = 1;
		children = new GList<>();
		fracture = null;
		generator = new SimplexOctaveGenerator(random, octaves);
		this.opacity = opacity;
		this.injector = ADD;
	}

	public CNG child(CNG c)
	{
		children.add(c);
		return this;
	}
	
	public RNG nextRNG()
	{
		return getRNG().nextRNG();
	}
	
	public RNG getRNG()
	{
		return rng;
	}

	public CNG fractureWith(CNG c, double scale)
	{
		fracture = c;
		fscale = scale;
		return this;
	}

	public CNG scale(double c)
	{
		scale = c;
		return this;
	}

	public CNG freq(double c)
	{
		freq = c;
		return this;
	}

	public CNG amp(double c)
	{
		amp = c;
		return this;
	}

	public CNG injectWith(NoiseInjector i)
	{
		injector = i;
		return this;
	}

	public double noise(double... dim)
	{
		double f = fracture != null ? (fracture.noise(dim) - 0.5) * fscale : 0D;
		double x = dim.length > 0 ? dim[0] + f : 0D;
		double y = dim.length > 1 ? dim[1] - f : 0D;
		double z = dim.length > 2 ? dim[2] + f : 0D;
		double w = dim.length > 3 ? dim[3] - f: 0D;
		double n = ((generator.noise(x * scale , y * scale, z * scale, w * scale, freq, amp, true) / 2D) + 0.5D) * opacity;
		double m = 1;

		for(CNG i : children)
		{
			double[] r = injector.combine(n, i.noise(dim));
			n = r[0];
			m += r[1];
		}

		return n / m;
	}
}
