package ninja.bytecode.shuriken.math;

/**
 * Math
 *
 * @author cyberpwn
 */
public class M
{
	private static final int precision = 128;
	private static final int modulus = 360 * precision;
	private static final float[] sin = new float[modulus];

	/**
	 * Clip doubles between a range and convert to int
	 *
	 * @param value
	 *            the value
	 * @param min
	 *            the min
	 * @param max
	 *            the max
	 * @return the clipped integer
	 */
	public static int iclip(double value, double min, double max)
	{
		return (int) clip(value, min, max);
	}

	/**
	 * Scales B by an external range change so that <br/>
	 * <br/>
	 * BMIN < B < BMAX <br/>
	 * AMIN < RESULT < AMAX <br/>
	 * <br/>
	 * So Given rangeScale(0, 20, 0, 10, 5) -> 10 <br/>
	 * 0 < 5 < 10 <br/>
	 * 0 < ? < 20 <br/>
	 * <br/>
	 * would return 10
	 *
	 * @param amin
	 *            the resulting minimum
	 * @param amax
	 *            the resulting maximum
	 * @param bmin
	 *            the initial minimum
	 * @param bmax
	 *            the initial maximum
	 * @param b
	 *            the initial value
	 * @return the resulting value
	 */
	public static double rangeScale(double amin, double amax, double bmin, double bmax, double b)
	{
		return amin + ((amax - amin) * ((b - bmin) / (bmax - bmin)));
	}

	/**
	 * Clip a value
	 *
	 * @param value
	 *            the value
	 * @param min
	 *            the min
	 * @param max
	 *            the max
	 * @return the clipped value
	 */
	public static double clip(double value, double min, double max)
	{
		return Math.min(max, Math.max(min, value));
	}

	/**
	 * Get true or false based on random percent
	 *
	 * @param d
	 *            between 0 and 1
	 * @return true if true
	 */
	public static boolean r(Double d)
	{
		if(d == null)
		{
			return Math.random() < 0.5;
		}

		return Math.random() < d;
	}

	/**
	 * Get the ticks per second from a time in nanoseconds, the rad can be used for
	 * multiple ticks
	 *
	 * @param ns
	 *            the time in nanoseconds
	 * @param rad
	 *            the radius of the time
	 * @return the ticks per second in double form
	 */
	public static double tps(long ns, int rad)
	{
		return (20.0 * (ns / 50000000.0)) / rad;
	}

	/**
	 * Get the number of ticks from a time in nanoseconds
	 *
	 * @param ns
	 *            the nanoseconds
	 * @return the amount of ticks
	 */
	public static double ticksFromNS(long ns)
	{
		return (ns / 50000000.0);
	}

	/**
	 * Get a random int from to (inclusive)
	 *
	 * @param f
	 *            the from
	 * @param t
	 *            the to
	 * @return the value
	 */
	public static int irand(int f, int t)
	{
		return f + (int) (Math.random() * ((t - f) + 1));
	}

	/**
	 * Get a random float from to (inclusive)
	 *
	 * @param f
	 *            the from
	 * @param t
	 *            the to
	 * @return the value
	 */
	public static float frand(float f, float t)
	{
		return f + (float) (Math.random() * ((t - f) + 1));
	}

	/**
	 * Get a random double from to (inclusive)
	 *
	 * @param f
	 *            the from
	 * @param t
	 *            the to
	 * @return the value
	 */
	public static double drand(double f, double t)
	{
		return f + (Math.random() * ((t - f) + 1));
	}

	/**
	 * Get system Nanoseconds
	 *
	 * @return nanoseconds (current)
	 */
	public static long ns()
	{
		return System.nanoTime();
	}

	/**
	 * Get the current millisecond time
	 *
	 * @return milliseconds
	 */
	public static long ms()
	{
		return System.currentTimeMillis();
	}

	/**
	 * Fast sin function
	 *
	 * @param a
	 *            the number
	 * @return the sin
	 */
	public static float sin(float a)
	{
		return sinLookup((int) (a * precision + 0.5f));
	}

	/**
	 * Fast cos function
	 *
	 * @param a
	 *            the number
	 * @return the cos
	 */
	public static float cos(float a)
	{
		return sinLookup((int) ((a + 90f) * precision + 0.5f));
	}

	/**
	 * Biggest number
	 *
	 * @param ints
	 *            the numbers
	 * @return the biggest one
	 */
	public static int max(int... ints)
	{
		int max = Integer.MIN_VALUE;

		for(int i : ints)
		{
			if(i > max)
			{
				max = i;
			}
		}

		return max;
	}

	/**
	 * Smallest number
	 *
	 * @param ints
	 *            the numbers
	 * @return the smallest one
	 */
	public static int min(int... ints)
	{
		int min = Integer.MAX_VALUE;

		for(int i : ints)
		{
			if(i < min)
			{
				min = i;
			}
		}

		return min;
	}

	/**
	 * is the number "is" within from-to
	 *
	 * @param from
	 *            the lower end
	 * @param to
	 *            the upper end
	 * @param is
	 *            the check
	 * @return true if its within
	 */
	public static boolean within(int from, int to, int is)
	{
		return is >= from && is <= to;
	}

	/**
	 * Get the amount of days past since the epoch time (1970 jan 1 utc)
	 *
	 * @return the epoch days
	 */
	public static long epochDays()
	{
		return epochDays(M.ms());
	}

	/**
	 * Get the amount of days past since the epoch time (1970 jan 1 utc)
	 *
	 * @param ms
	 *            the time in milliseconds
	 * @return the epoch days
	 */
	private static long epochDays(long ms)
	{
		return ms / 1000 / 60 / 60 / 24;
	}

	static
	{
		for(int i = 0; i < sin.length; i++)
		{
			sin[i] = (float) Math.sin((i * Math.PI) / (precision * 180));
		}
	}

	private static float sinLookup(int a)
	{
		return a >= 0 ? sin[a % (modulus)] : -sin[-a % (modulus)];
	}
}
