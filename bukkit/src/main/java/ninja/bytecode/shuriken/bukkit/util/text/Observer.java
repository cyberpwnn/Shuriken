package ninja.bytecode.shuriken.bukkit.util.text;

@FunctionalInterface
public interface Observer<T>
{
	public void onChanged(T from, T to);
}
