package ninja.bytecode.shuriken.collections.functional;

@FunctionalInterface
public interface Consumer2<A, B>
{
	public void accept(A a, B b);
}
