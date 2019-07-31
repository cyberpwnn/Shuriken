package ninja.bytecode.shuriken.queue;

import ninja.bytecode.shuriken.collections.GList;

public class ShurikenQueue<T> implements Queue<T>
{
	private GList<T> queue;
	private boolean randomPop;
	private boolean reversePop;

	public ShurikenQueue()
	{
		clear();
	}

	public ShurikenQueue<T> responsiveMode()
	{
		reversePop = true;
		return this;
	}

	public ShurikenQueue<T> randomMode()
	{
		randomPop = true;
		return this;
	}

	@Override
	public ShurikenQueue<T> queue(T t)
	{
		queue.add(t);
		return this;
	}

	@Override
	public ShurikenQueue<T> queue(GList<T> t)
	{
		queue.add(t);
		return this;
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
	public GList<T> next(int amt)
	{
		GList<T> t = new GList<>();

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
	public ShurikenQueue<T> clear()
	{
		queue = new GList<T>();
		return this;
	}

	@Override
	public int size()
	{
		return queue.size();
	}
}
