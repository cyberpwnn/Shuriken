package ninja.bytecode.shuriken.bukkit.api.fx;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ninja.bytecode.shuriken.bukkit.lang.collection.GList;

public interface VisualEffect
{
	public void play(Location l);

	public void play(Location l, double r);

	public void play(Location l, Player p);

	public void play(Location l, GList<Player> p);
}
