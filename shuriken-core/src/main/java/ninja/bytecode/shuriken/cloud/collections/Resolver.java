package ninja.bytecode.shuriken.cloud.collections;

@FunctionalInterface
public interface Resolver<K, V>
{
	public V resolve(K k);
}
