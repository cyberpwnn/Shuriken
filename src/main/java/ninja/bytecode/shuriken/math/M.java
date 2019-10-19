package ninja.bytecode.shuriken.math;

import java.util.regex.Matcher;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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
	@SuppressWarnings("unchecked")
	public static <T extends Number> T clip(T value, T min, T max)
	{
		return (T) Double.valueOf(Math.min(max.doubleValue(), Math.max(min.doubleValue(), value.doubleValue())));
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
	 * @param numbers
	 *            the numbers
	 * @return the biggest one
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Number> T max(T... doubles)
	{
		double max = Double.MIN_VALUE;

		for(T i : doubles)
		{
			if(i.doubleValue() > max)
			{
				max = i.doubleValue();
			}
		}

		return (T) Double.valueOf(max);
	}
	
	/**
	 * Smallest number
	 *
	 * @param doubles
	 *            the numbers
	 * @return the smallest one
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Number> T min(T... doubles)
	{
		double min = Double.MAX_VALUE;

		for(T i : doubles)
		{
			if(i.doubleValue() < min)
			{
				min = i.doubleValue();
			}
		}

		return (T) Double.valueOf(min);
	}
	
	/**
	 * Evaluates an expression using javascript engine and returns the double
	 * result. This can take variable parameters, so you need to define them.
	 * Parameters are defined as $[0-9]. For example evaluate("4$0/$1", 1, 2);
	 * This makes the expression (4x1)/2 == 2. Keep note that you must use 0-9,
	 * you cannot skip, or start at a number other than 0.
	 * 
	 * @param expression
	 *            the expression with variables
	 * @param args
	 *            the arguments/variables
	 * @return the resulting double value
	 * @throws ScriptException ... gg
	 * @throws IndexOutOfBoundsException
	 *             learn to count
	 */
	public static double evaluate(String expression, Double... args) throws ScriptException, IndexOutOfBoundsException
	{
		for(int i = 0; i < args.length; i++)
		{
			String current = "$" + i;
			
			if(expression.contains(current))
			{
				expression = expression.replaceAll(Matcher.quoteReplacement(current), args[i] + "");
			}
		}
		
		return evaluate(expression);
	}
	
	/**
	 * Evaluates an expression using javascript engine and returns the double
	 * 
	 * @param expression
	 *            the mathimatical expression
	 * @return the double result
	 * @throws ScriptException ... gg
	 */
	public static double evaluate(String expression) throws ScriptException
	{
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine scriptEngine = mgr.getEngineByName("JavaScript");
		
		return Double.valueOf(scriptEngine.eval(expression).toString());
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
	
	public static double percentRange(double percent, double min, double max)
	{
		return min + (percent * (max - min));
	}
}
