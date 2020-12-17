package ninja.bytecode.shuriken.bukkit.bukkit.plugin.commands;

import lombok.Getter;
import lombok.Setter;
import ninja.bytecode.shuriken.bukkit.bukkit.command.ShurikenSender;

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
