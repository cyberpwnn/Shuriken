package ninja.bytecode.shuriken.execution;

public interface NastyFuture<R>
{
	public R run() throws Throwable;
}
