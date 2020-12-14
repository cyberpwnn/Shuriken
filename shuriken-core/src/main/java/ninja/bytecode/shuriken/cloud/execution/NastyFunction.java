package ninja.bytecode.shuriken.cloud.execution;

public interface NastyFunction<T, R>
{
	public R run(T t) throws Throwable;
}
