package ninja.bytecode.shuriken.bukkit.util.queue;



public class PhantomQueue<T> implements Queue<T>
{
	private KList<T> queue;
	private boolean randomPop;
	private boolean reversePop;

	public PhantomQueue()
	{
		clear();
	}

	public PhantomQueue<T> responsiveMode()
	{
		reversePop = true;
		return this;
	}

	public PhantomQueue<T> randomMode()
	{
		randomPop = true;
		return this;
	}

	@Override
	public void queue(T t)
	{
		queue.add(t);
	}

	@Override
	public void queue(KList<T> t)
	{
		for(T i : t)
		{
			queue(i);
		}
	}

	@Override
	public boolean hasNext(int amt)
	{
		return queue.size() >= amt;
	}

	@Override
	public boolean hasNext()
	{
		return !queue.isEmpty();
	}

	@Override
	public T next()
	{
		return reversePop ? queue.popLast() : randomPop ? queue.popRandom() : queue.pop();
	}

	@Override
	public KList<T> next(int amt)
	{
		KList<T> t = new KList<>();

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

	@Override
	public void clear()
	{
		queue = new KList<T>();
	}

	@Override
	public int size()
	{
		return queue.size();
	}
}
