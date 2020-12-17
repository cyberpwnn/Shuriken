package ninja.bytecode.shuriken.random.noise.stream;


import ninja.bytecode.shuriken.collections.KList;

public interface Significance<T>
{
	public KList<T> getFactorTypes();

	public double getSignificance(T t);

	public T getMostSignificantType();
}
