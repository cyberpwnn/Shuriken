package ninja.bytecode.shuriken.bukkit.api.particle;
import ninja.bytecode.shuriken.bukkit.lang.collection.GList;
import org.bukkit.Location;

/**
 * An effect that can be played
 * @author cyberpwn
 *
 */
public abstract class PhantomEffect implements VisualEffect
{
	@Override
	public GList<VisualEffect> getEffects()
	{
		return new GList<VisualEffect>();
	}

	@Override
	public abstract void play(Location l);

	@Override
	public void addEffect(VisualEffect e)
	{

	}
}