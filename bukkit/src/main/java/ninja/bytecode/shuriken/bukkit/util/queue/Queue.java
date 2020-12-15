package ninja.bytecode.shuriken.bukkit.util.queue;


import ninja.bytecode.shuriken.collections.KList;

public interface Queue<T>
{
	public void queue(T t);

	public void queue(KList<T> t);

	public boolean hasNext(int amt);

	public boolean hasNext();

	public T next();

	public KList<T> next(int amt);

	public void clear();

	public int size();
}
