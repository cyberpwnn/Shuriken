package ninja.bytecode.shuriken.execution.parallel;

import ninja.bytecode.shuriken.collections.hunk.Hunk;

public interface BurstedHunk<T> extends Hunk<T>
{
	public int getOffsetX();

	public int getOffsetY();

	public int getOffsetZ();
}
