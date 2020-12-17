package ninja.bytecode.shuriken.execution;

@FunctionalInterface
public interface Consumer4<A, B, C, D>
{
	public void accept(A a, B b, C c, D d);
}
