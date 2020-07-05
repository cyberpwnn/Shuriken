package ninja.bytecode.shuriken.function;

@FunctionalInterface
public interface Function3<A, B, C, R>
{
	public R apply(A a, B b, C c);
}
