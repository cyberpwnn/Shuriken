package ninja.bytecode.shuriken.bukkit.command;

import lombok.Getter;
import lombok.Setter;

public abstract class DelayedRunnable implements Runnable
{
	@Getter
	@Setter
	private DelayedCommand delayedCommand;

	public DelayedRunnable(DelayedCommand c)
	{
		this.delayedCommand = c;
	}

	public final ShurikenSender getSender()
	{
		return this.getDelayedCommand().getSender();
	}
}
