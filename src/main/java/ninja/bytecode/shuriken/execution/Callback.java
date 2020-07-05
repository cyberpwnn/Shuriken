package ninja.bytecode.shuriken.execution;

public interface Callback<T>
{
	public void run(T t);
}
