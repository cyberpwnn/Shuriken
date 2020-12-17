package ninja.bytecode.shuriken.random.noise.stream.interpolation;

import ninja.bytecode.shuriken.random.RNG;
import ninja.bytecode.shuriken.random.noise.stream.ProceduralStream;

import java.util.function.Function;

public interface Interpolated<T>
{
	public static final Interpolated<ninja.bytecode.shuriken.random.RNG> RNG = of((t) -> 0D, (t) -> null);
	public static final Interpolated<Double> DOUBLE = of((t) -> t, (t) -> t);
	public static final Interpolated<Integer> INT = of((t) -> Double.valueOf(t), (t) -> t.intValue());
	public static final Interpolated<Long> LONG = of((t) -> Double.valueOf(t), (t) -> t.longValue());

	public double toDouble(T t);

	public T fromDouble(double d);

	default InterpolatorFactory<T> interpolate()
	{
		if(this instanceof ProceduralStream)
		{
			return new InterpolatorFactory<T>((ProceduralStream<T>) this);
		}

		return null;
	}

	public static <T> Interpolated<T> of(Function<T, Double> a, Function<Double, T> b)
	{
		return new Interpolated<T>()
		{
			@Override
			public double toDouble(T t)
			{
				return a.apply(t);
			}

			@Override
			public T fromDouble(double d)
			{
				return b.apply(d);
			}
		};
	}
}
