package ninja.bytecode.shuriken.bukkit.particle;

import ninja.bytecode.shuriken.collections.KList;
import org.bukkit.Location;

/**
 * An effect that can be played
 * @author cyberpwn
 *
 */
public abstract class PhantomEffect implements VisualEffect
{
	@Override
	public KList<VisualEffect> getEffects()
	{
		return new KList<VisualEffect>();
	}

	@Override
	public abstract void play(Location l);

	@Override
	public void addEffect(VisualEffect e)
	{

	}
}