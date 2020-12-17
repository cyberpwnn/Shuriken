package ninja.bytecode.shuriken.random.noise.stream.arithmetic;

import ninja.bytecode.shuriken.collections.functional.Function2;
import ninja.bytecode.shuriken.collections.functional.Function3;
import ninja.bytecode.shuriken.random.noise.stream.BasicStream;
import ninja.bytecode.shuriken.random.noise.stream.ProceduralStream;

public class SubtractingStream<T> extends BasicStream<T>
{
	private final Function3<Double, Double, Double, Double> add;

	public SubtractingStream(ProceduralStream<T> stream, Function3<Double, Double, Double, Double> add)
	{
		super(stream);
		this.add = add;
	}

	public SubtractingStream(ProceduralStream<T> stream, Function2<Double, Double, Double> add)
	{
		this(stream, (x, y, z) -> add.apply(x, z));
	}

	public SubtractingStream(ProceduralStream<T> stream, double add)
	{
		this(stream, (x, y, z) -> add);
	}

	@Override
	public double toDouble(T t)
	{
		return getTypedSource().toDouble(t);
	}

	@Override
	public T fromDouble(double d)
	{
		return getTypedSource().fromDouble(d);
	}

	@Override
	public T get(double x, double z)
	{
		return fromDouble(getTypedSource().getDouble(x, z) - add.apply(x, 0D, z));
	}

	@Override
	public T get(double x, double y, double z)
	{
		return fromDouble(getTypedSource().getDouble(x, y, z) - add.apply(x, y, z));
	}
}
