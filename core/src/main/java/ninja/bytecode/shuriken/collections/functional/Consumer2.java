package ninja.bytecode.shuriken.execution;

@FunctionalInterface
public interface Consumer2<A, B>
{
	public void accept(A a, B b);
}
