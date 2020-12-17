package ninja.bytecode.shuriken.bukkit.fx;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ninja.bytecode.shuriken.bukkit.particle.ParticleEffect;

public class ParticleFootstep extends ParticleBase
{
	@Override
	public void play(Location l, double range)
	{
		ParticleEffect.FOOTSTEP.display(0f, 1, l, range);
	}

	@Override
	public void play(Location l, Player p)
	{
		ParticleEffect.FOOTSTEP.display(0f, 1, l, p);
	}
}