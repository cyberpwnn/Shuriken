package ninja.bytecode.shuriken.bukkit.plugin;

import java.util.function.Consumer;
import java.util.function.Function;

import ninja.bytecode.shuriken.bukkit.host.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.collections.KList;
import ninja.bytecode.shuriken.collections.KMap;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;




/**
 * Collects events into a buffer and uses consumers to execute them in bursts
 * instead of when it happens.
 *
 * @author cyberpwn
 *
 * @param <T>
 */
public class EventBuffer<T extends Event> implements Listener
{
	private KList<T> buffer;
	private Class<? extends T> eventClass;
	private KList<Consumer<T>> consumers;
	private Function<T, String> sorter;
	private EventPriority priority;
	private boolean ignoreCancelled;

	public EventBuffer(Class<? extends T> eventClass)
	{
		this.eventClass = eventClass;
		buffer = new KList<T>();
		consumers = new KList<Consumer<T>>();
		sorter = x -> "undefined";
		priority = EventPriority.NORMAL;
		ignoreCancelled = true;
	}

	public EventBuffer<T> acceptCancelled()
	{
		this.ignoreCancelled = false;
		return this;
	}

	public EventBuffer<T> priority(EventPriority p)
	{
		this.priority = p;
		return this;
	}

	public EventBuffer<T> engage()
	{
		buffer.clear();
		Bukkit.getPluginManager().registerEvent(eventClass, this, priority, new EventExecutor()
		{
			@SuppressWarnings("unchecked")
			@Override
			public void execute(Listener listener, Event event) throws EventException
			{
				if(event instanceof Cancellable && ((Cancellable) event).isCancelled() && ignoreCancelled)
				{
					return;
				}

				offer((T) event);
			}
		}, ShurikenAPIPlugin.p);

		return this;
	}

	public void disengage()
	{
		HandlerList.unregisterAll(this);
		buffer.clear();
	}

	public EventBuffer(Class<? extends T> eventClass, Consumer<T> consumer)
	{
		this(eventClass);
		consumers.add(consumer);
	}

	public void offer(T f)
	{
		buffer.add(f);
	}

	public void consume(T t)
	{
		for(Consumer<T> i : consumers)
		{
			try
			{
				i.accept(t);
			}

			catch(Throwable e)
			{

			}
		}
	}

	public void consumeTop()
	{
		KMap<String, T> sorted = new KMap<String, T>();
		KList<String> order = new KList<String>();

		for(T i : buffer)
		{
			String s = sorter.apply(i);

			if(order.contains(s))
			{
				order.remove(s);
			}

			order.add(s);
			sorted.put(s, i);
		}

		for(String i : order)
		{
			consume(sorted.get(i));
		}

		buffer.clear();
	}

	public void consumeAll()
	{
		while(hasNext())
		{
			consumeNext();
		}
	}

	public void consumeNext()
	{
		consume(buffer.pop());
	}

	public boolean hasNext()
	{
		return getSize() > 0;
	}

	public int getSize()
	{
		return buffer.size();
	}

	public EventBuffer<T> addConsumer(Consumer<T> t)
	{
		consumers.add(t);
		return this;
	}

	public EventBuffer<T> setSorter(Function<T, String> t)
	{
		sorter = t;
		return this;
	}
}
