package ninja.bytecode.shuriken.cloud.execution;

public class Switch
{
	private volatile boolean b;

	/**
	 * Defaulted off
	 */
	public Switch()
	{
		b = false;
	}

	public void flip()
	{
		b = true;
	}

	public boolean isFlipped()
	{
		return b;
	}

	public void reset()
	{
		b = false;
	}
}
