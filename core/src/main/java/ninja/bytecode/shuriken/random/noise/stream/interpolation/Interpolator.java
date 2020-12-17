package ninja.bytecode.shuriken.random.noise.stream.interpolation;


import ninja.bytecode.shuriken.random.noise.stream.ProceduralStream;

public interface Interpolator<T>
{
	@SuppressWarnings("unchecked")
	default InterpolatorFactory<T> into()
	{
		if(this instanceof ProceduralStream)
		{
			return new InterpolatorFactory<T>((ProceduralStream<T>) this);
		}

		return null;
	}
}
