package ninja.bytecode.shuriken.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class GList<T> extends ArrayList<T> implements List<T>
{
	private static final long serialVersionUID = -2892550695744823337L;

	/**
	 * Rewrite this list by checking each value and changing the value (or not).
	 * Return null to remove the element in the function
	 *
	 * @param t
	 *            the function
	 * @return the same list (not a copy)
	 */
	public GList<T> rewrite(Function<T, T> t)
	{
		GList<T> m = copy();
		clear();

		for(T i : m)
		{
			addNonNull(t.apply(i));
		}

		return this;
	}

	/**
	 * To array
	 *
	 * @return the array
	 */
	@SuppressWarnings("unchecked")
	public T[] array()
	{
		return (T[]) toArray();
	}

	/**
	 * Return a copy of this list
	 *
	 * @return the copy
	 */
	public GList<T> copy()
	{
		return new GList<T>().add(this);
	}

	/**
	 * Shuffle the list
	 *
	 * @return the same list
	 */
	public GList<T> shuffle()
	{
		Collections.shuffle(this);
		return this;
	}

	/**
	 * Sort the list (based on toString comparison)
	 *
	 * @return the same list
	 */
	public GList<T> sort()
	{
		Collections.sort(this, (a, b) -> a.toString().compareTo(b.toString()));
		return this;
	}

	/**
	 * Reverse this list
	 *
	 * @return the same list
	 */
	public GList<T> reverse()
	{
		Collections.reverse(this);
		return this;
	}

	@Override
	public String toString()
	{
		return "[" + toString(", ") + "]";
	}

	/**
	 * Tostring with a seperator for each item in the list
	 *
	 * @param split
	 *            the seperator
	 * @return the string representing this object
	 */
	public String toString(String split)
	{
		StringBuilder b = new StringBuilder();

		for(String i : toStringList())
		{
			b.append(split + i);
		}

		return b.toString().substring(split.length());
	}

	/**
	 * Invoke tostring on each value in the list into a string list
	 *
	 * @return the string list
	 */
	public GList<String> toStringList()
	{
		return convert((t) -> t.toString());
	}

	/**
	 * Convert a list into another list type. Such as GList<Integer> to
	 * GList<String>. list.convert((i) -> "" + i);
	 *
	 * @param <V>
	 * @param converter
	 * @return
	 */
	public <V> GList<V> convert(Function<T, V> converter)
	{
		GList<V> v = new GList<V>();
		forEach((t) -> v.addNonNull(converter.apply(t)));
		return v;
	}

	/**
	 * Adds T to the list, ignores if null
	 *
	 * @param t
	 *            the value to add
	 * @return the same list
	 */
	public GList<T> addNonNull(T t)
	{
		if(t != null)
		{
			super.add(t);
		}

		return this;
	}

	/**
	 * Swaps the values of index a and b. For example "hello", "world", "!" swap(1,
	 * 2) would change the list to "hello", "!", "world"
	 *
	 * @param a
	 *            the first index
	 * @param b
	 *            the second index
	 * @return the same list (builder), not a copy
	 */
	public GList<T> swapIndexes(int a, int b)
	{
		T aa = remove(a);
		T bb = get(b);
		add(a, bb);
		remove(b);
		add(b, aa);

		return this;
	}

	/**
	 * Remove a number of elements from the list
	 *
	 * @param t
	 *            the elements
	 * @return this list
	 */
	@SuppressWarnings("unchecked")
	public GList<T> remove(T... t)
	{
		for(T i : t)
		{
			super.remove(i);
		}

		return this;
	}

	/**
	 * Add another glist's contents to this one (addall builder)
	 *
	 * @param t
	 *            the list
	 * @return the same list
	 */
	public GList<T> add(GList<T> t)
	{
		super.addAll(t);
		return this;
	}

	/**
	 * Add a number of values to this list
	 *
	 * @param t
	 *            the list
	 * @return this list
	 */
	@SuppressWarnings("unchecked")
	public GList<T> add(T... t)
	{
		for(T i : t)
		{
			super.add(i);
		}

		return this;
	}

	/**
	 * Check if this list has an index at the given index
	 *
	 * @param index
	 *            the given index
	 * @return true if size > index
	 */
	public boolean hasIndex(int index)
	{
		return size() > index;
	}

	/**
	 * Get the last index of this list (size - 1)
	 *
	 * @return the last index of this list
	 */
	public int last()
	{
		return size() - 1;
	}
}
