package ninja.bytecode.shuriken.cloud.execution;

@FunctionalInterface
public interface Consumer3<A, B, C>
{
	public void accept(A a, B b, C c);
}
