package ninja.bytecode.shuriken.math;

import ninja.bytecode.shuriken.bench.PrecisionStopwatch;
import ninja.bytecode.shuriken.format.Form;

public class Test
{
	public static void main(String[] a)
	{
		SimplexNoise speed = new SimplexNoise(new RNG("same"));
		SimplexNoiseGenerator current = new SimplexNoiseGenerator(new RNG("same"));

		System.out.println("Warming Up...");
		for(int i = 0; i < 300000; i++)
		{
			double x = RNG.r.nextDouble() * Double.MAX_VALUE;
			double y = RNG.r.nextDouble() * Double.MAX_VALUE;
			double z = RNG.r.nextDouble() * Double.MAX_VALUE;
			double aa = speed.noise(x, y, z);
			double bb = current.noise(x, y, z);

			if(aa != bb)
			{
				System.out.println("aa: " + aa + " bb: " + bb);
				System.exit(0);
			}
		}

		PrecisionStopwatch ps = new PrecisionStopwatch();
		ps.begin();
		for(int i = 0; i < 50000; i++)
		{
			double x = RNG.r.nextDouble() * Double.MAX_VALUE;
			double y = RNG.r.nextDouble() * Double.MAX_VALUE;
			double z = RNG.r.nextDouble() * Double.MAX_VALUE;
			speed.noise(x, y, z);
		}
		ps.end();

		PrecisionStopwatch ps2 = new PrecisionStopwatch();
		ps2.begin();
		for(int i = 0; i < 50000; i++)
		{
			double x = RNG.r.nextDouble() * Double.MAX_VALUE;
			double y = RNG.r.nextDouble() * Double.MAX_VALUE;
			double z = RNG.r.nextDouble() * Double.MAX_VALUE;
			current.noise(x, y, z);
		}
		ps2.end();

		System.out.println("Speed: " + Form.duration(ps.getMilliseconds(), 10));
		System.out.println("Currt: " + Form.duration(ps2.getMilliseconds(), 10));
	}
}
