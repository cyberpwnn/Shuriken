package ninja.bytecode.shuriken.function;

@FunctionalInterface
public interface Consumer2<A, B>
{
	public void accept(A a, B b);
}