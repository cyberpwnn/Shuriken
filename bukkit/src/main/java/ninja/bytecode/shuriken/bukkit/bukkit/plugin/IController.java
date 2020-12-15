package ninja.bytecode.shuriken.bukkit.bukkit.plugin;

import org.bukkit.event.Listener;

import ninja.bytecode.shuriken.bukkit.util.text.Logged;

public interface IController extends Logged, Listener
{
	public String getName();

	public void start();

	public void stop();

	public void tick();

	public int getTickInterval();
}
