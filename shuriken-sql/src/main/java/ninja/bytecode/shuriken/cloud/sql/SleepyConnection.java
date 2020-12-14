package ninja.bytecode.shuriken.cloud.sql;

import java.sql.Connection;

public interface SleepyConnection extends Connection
{
	public long getTimeSinceLastUsage();
}
