package ninja.bytecode.shuriken.cloud.execution;

public interface Callback<T>
{
	public void run(T t);
}
