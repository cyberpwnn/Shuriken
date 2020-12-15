package ninja.bytecode.shuriken.bukkit.api.fx;

import ninja.bytecode.shuriken.collections.KList;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public interface VisualEffect
{
	public void play(Location l);

	public void play(Location l, double r);

	public void play(Location l, Player p);

	public void play(Location l, KList<Player> p);
}
