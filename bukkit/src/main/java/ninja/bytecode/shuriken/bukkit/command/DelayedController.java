package ninja.bytecode.shuriken.bukkit.command;

import lombok.AccessLevel;
import lombok.Getter;
import ninja.bytecode.shuriken.bukkit.plugin.Controller;
import ninja.bytecode.shuriken.collections.KList;


public class DelayedController extends Controller
{

	@Getter(AccessLevel.PROTECTED)
	private KList<DelayedCommand> cmds;

	@Override
	public void start()
	{
		this.cmds = new KList<>();
	}

	@Override
	public void stop()
	{
		this.cmds.clear();
	}

	@Override
	public void tick()
	{
		for (DelayedCommand c : cmds.copy()) {
			if (c.canRun()) {
				c.run();
				cmds.remove(c);
			}
		}
	}

	public DelayedCommand register(DelayedCommand cmd)
	{
		this.cmds.add(cmd);
		return cmd;
	}

	public boolean confirm(ShurikenSender sender)
	{
		for (DelayedCommand cmd : cmds) {
			if (!cmd.getSender().player().equals(sender.player())) continue;
			cmd.setCancelled(false);
			try {
				cmd.run();
			} catch (Exception ex) {}
			cmds.remove(cmd);
			return true;
		}
		return false;
	}

	public boolean cancel(ShurikenSender sender)
	{
		for (DelayedCommand cmd : cmds) {
			if (!cmd.getSender().player().equals(sender.player())) continue;
			cmd.setCancelled(true);
			cmd.run();
			cmds.remove(cmd);
			return true;
		}
		return false;
	}
}
