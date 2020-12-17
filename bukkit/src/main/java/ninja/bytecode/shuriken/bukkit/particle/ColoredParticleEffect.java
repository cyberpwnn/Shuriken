package ninja.bytecode.shuriken.bukkit.particle;

import ninja.bytecode.shuriken.collections.KList;
import org.bukkit.Color;
import org.bukkit.Location;

public class ColoredParticleEffect implements VisualEffect
{
	private Color color;
	private boolean alpha;

	public ColoredParticleEffect(Color color)
	{
		this.color = color;
		alpha = false;
	}

	@Override
	public KList<VisualEffect> getEffects()
	{
		return new KList<VisualEffect>();
	}

	@Override
	public void play(Location l)
	{
		ParticleEffect.OrdinaryColor oc = new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue());

		if(alpha)
		{
			ParticleEffect.SPELL_MOB_AMBIENT.display(oc, l, 40);
		}

		else
		{
			ParticleEffect.SPELL_MOB.display(oc, l, 40);
		}
	}

	@Override
	public void addEffect(VisualEffect e)
	{

	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public boolean isAlpha()
	{
		return alpha;
	}

	public void setAlpha(boolean alpha)
	{
		this.alpha = alpha;
	}
}