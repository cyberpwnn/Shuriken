package ninja.bytecode.shuriken.bukkit.bukkit.lace;

import ninja.bytecode.shuriken.bukkit.bukkit.plugin.Controller;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;



public class LaceController extends Controller
{
	private KMap<World, LacedWorld> lacedWorld;

	public LaceController()
	{
		setTickRate(20);
	}

	public void write(LacedWorld world)
	{

	}

	public void load(LacedWorld world)
	{

	}

	@EventHandler
	public void on(WorldSaveEvent e)
	{

	}

	@EventHandler
	public void on(WorldLoadEvent e)
	{

	}

	@EventHandler
	public void on(WorldUnloadEvent e)
	{
		lacedWorld.remove(e.getWorld());
	}

	@Override
	public void start()
	{

	}

	@Override
	public void stop()
	{

	}

	@Override
	public void tick()
	{

	}
}
