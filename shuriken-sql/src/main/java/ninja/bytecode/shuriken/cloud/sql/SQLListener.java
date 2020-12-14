package ninja.bytecode.shuriken.cloud.sql;

@FunctionalInterface
public interface SQLListener
{
	public void handle(String q);
}
