package ninja.bytecode.shuriken.bukkit.fx;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ninja.bytecode.shuriken.bukkit.api.particle.ParticleEffect;

public class ParticleSnowball extends ParticleBase
{
	@Override
	public void play(Location l, double range)
	{
		ParticleEffect.SNOWBALL.display(0f, 1, l, range);
	}

	@Override
	public void play(Location l, Player p)
	{
		ParticleEffect.SNOWBALL.display(0f, 1, l, p);
	}
}