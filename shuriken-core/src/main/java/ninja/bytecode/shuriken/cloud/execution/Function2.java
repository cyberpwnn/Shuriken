package ninja.bytecode.shuriken.cloud.execution;

@FunctionalInterface
public interface Function2<A, B, R>
{
	public R apply(A a, B b);
}
