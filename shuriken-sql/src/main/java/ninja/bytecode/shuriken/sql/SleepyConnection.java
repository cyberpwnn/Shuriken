package ninja.bytecode.shuriken.sql;

import java.sql.Connection;

public interface SleepyConnection extends Connection
{
	public long getTimeSinceLastUsage();
}
