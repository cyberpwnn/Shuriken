package ninja.bytecode.shuriken.queue;

import ninja.bytecode.shuriken.collections.GList;

public interface Queue<T>
{
	public Queue<T> queue(T t);

	public Queue<T> queue(GList<T> t);

	public boolean hasNext(int amt);

	public boolean hasNext();

	public T next();

	public GList<T> next(int amt);

	public Queue<T> clear();

	public int size();

	public static <T> Queue<T> create(GList<T> t)
	{
		return new ShurikenQueue<T>().queue(t);
	}

	@SuppressWarnings("unchecked")
	public static <T> Queue<T> create(T... t)
	{
		return new ShurikenQueue<T>().queue(new GList<T>().add(t));
	}
}
