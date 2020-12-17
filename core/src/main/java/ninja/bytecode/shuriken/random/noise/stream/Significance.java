package ninja.bytecode.shuriken.random.noise.stream;

import com.volmit.iris.util.KList;

public interface Significance<T>
{
	public KList<T> getFactorTypes();

	public double getSignificance(T t);

	public T getMostSignificantType();
}
