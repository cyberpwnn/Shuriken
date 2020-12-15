package ninja.bytecode.shuriken.bukkit.logic.queue;



public class GQueue<T> implements Queue<T>
{
	private KList<T> queue;
	private boolean randomPop;
	private boolean reversePop;

	public GQueue()
	{
		clear();
	}

	public GQueue<T> responsiveMode()
	{
		reversePop = true;
		return this;
	}

	public GQueue<T> randomMode()
	{
		randomPop = true;
		return this;
	}

	public void queue(T t)
	{
		queue.add(t);
	}

	public void queue(KList<T> t)
	{
		for(T i : t)
		{
			queue(i);
		}
	}

	public boolean hasNext(int amt)
	{
		return queue.size() >= amt;
	}

	public boolean hasNext()
	{
		return !queue.isEmpty();
	}

	public T next()
	{
		return reversePop ? queue.popLast() : randomPop ? queue.popRandom() : queue.pop();
	}

	public KList<T> next(int amt)
	{
		KList<T> t = new KList<T>();

		for(int i = 0; i < amt; i++)
		{
			if(!hasNext())
			{
				break;
			}

			t.add(next());
		}

		return t;
	}

	public void clear()
	{
		queue = new KList<T>();
	}

	public int size()
	{
		return queue.size();
	}
}
