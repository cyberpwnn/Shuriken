package ninja.bytecode.shuriken.bukkit.particle;

import ninja.bytecode.shuriken.collections.KList;
import org.bukkit.Location;

/**
 * A system effect which contains multiple effects within itself, yet acts like
 * a normal visual effect which could be put into a super-super-effect if needed
 *
 * @author cyberpwn
 *
 */
public class SystemEffect implements VisualEffect
{
	private KList<VisualEffect> effects;

	/**
	 * Create a system effect
	 */
	public SystemEffect()
	{
		effects = new KList<VisualEffect>();
	}

	@Override
	public KList<VisualEffect> getEffects()
	{
		return effects;
	}

	@Override
	public void play(Location l)
	{
		for(VisualEffect i : effects)
		{
			i.play(l);
		}
	}

	@Override
	public void addEffect(VisualEffect e)
	{
		effects.add(e);
	}
}