package ninja.bytecode.shuriken.sql;

@FunctionalInterface
public interface SQLListener
{
	public void handle(String q);
}
