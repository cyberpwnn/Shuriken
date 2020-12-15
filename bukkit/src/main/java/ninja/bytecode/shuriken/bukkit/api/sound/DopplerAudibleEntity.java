package ninja.bytecode.shuriken.bukkit.api.sound;

import ninja.bytecode.shuriken.bukkit.api.world.VectorMath;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Doppler audio effect
 *
 * @author cyberpwn
 *
 */
public class DopplerAudibleEntity extends AudibleEntity
{
	public DopplerAudibleEntity(Entity entity, Audible audible, Integer interval)
	{
		super(entity, audible, interval);
	}

	@Override
	protected void onPlay(Player i, Audible audible, Entity entity)
	{
		Vector pv = i.getVelocity();
		Vector ev = entity.getVelocity();
		Double speedDifference = VectorMath.getSpeed(pv.subtract(ev)) * (entity.getLocation().distance(i.getLocation()));
		audible.setPitch((float) ((float) (audible.getPitch() / speedDifference) * 10));
		audible.play(i, entity.getLocation());
		System.out.println("Speed: " + speedDifference + " Pitch: " + audible.getPitch());
	}
}
