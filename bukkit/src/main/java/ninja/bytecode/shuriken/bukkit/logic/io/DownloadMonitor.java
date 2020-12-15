package ninja.bytecode.shuriken.bukkit.logic.io;

@FunctionalInterface
public interface DownloadMonitor 
{
	public void onUpdate(DL.DownloadState state, double progress, long elapsed, long estimated, long bps, long iobps, long size, long downloaded, long buffer, double bufferuse);
}
