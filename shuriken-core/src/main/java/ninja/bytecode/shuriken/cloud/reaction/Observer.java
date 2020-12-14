package ninja.bytecode.shuriken.cloud.reaction;

@FunctionalInterface
public interface Observer<T>
{
	public void onChanged(T from, T to);
}
