package ninja.bytecode.shuriken.cloud.execution;

public interface NastyFuture<R>
{
	public R run() throws Throwable;
}
