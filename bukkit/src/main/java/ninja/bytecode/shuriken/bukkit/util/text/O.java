package ninja.bytecode.shuriken.bukkit.util.text;



public class O<T> implements Observable<T>
{
	private T t = null;
	private KList<Observer<T>> observers;

	@Override
	public T get()
	{
		return t;
	}

	@Override
	public O<T> set(T t)
	{
		T x = t;
		this.t = t;

		if(observers != null && !observers.isEmpty())
		{
			for(Observer<T> i : observers)
			{
				i.onChanged(x, t);
			}
		}

		return this;
	}

	@Override
	public boolean has()
	{
		return t != null;
	}

	@Override
	public O<T> clearObservers()
	{
		observers.clear();
		return this;
	}

	@Override
	public O<T> observe(Observer<T> t)
	{
		if(observers == null)
		{
			observers = new KList<>();
		}

		observers.add(t);

		return this;
	}
}