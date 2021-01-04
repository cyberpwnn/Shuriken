package ninja.bytecode.shuriken.bukkit.event;

import org.bukkit.event.Cancellable;

public class ShurikenCancellableEvent extends ShurikenEvent implements Cancellable
{
	private boolean cancelled = false;

	@Override
	public boolean isCancelled()
	{
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled)
	{
		this.cancelled = cancelled;
	}
}
