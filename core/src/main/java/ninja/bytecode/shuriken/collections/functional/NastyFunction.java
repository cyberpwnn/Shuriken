package ninja.bytecode.shuriken.execution;

public interface NastyFunction<T, R>
{
	public R run(T t) throws Throwable;
}
