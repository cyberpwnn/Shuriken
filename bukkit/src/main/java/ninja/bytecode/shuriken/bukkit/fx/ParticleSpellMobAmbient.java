package ninja.bytecode.shuriken.bukkit.fx;

import java.awt.Color;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ninja.bytecode.shuriken.bukkit.particle.ParticleEffect;
import ninja.bytecode.shuriken.bukkit.particle.ParticleEffect.ParticleColor;

public class ParticleSpellMobAmbient extends ParticleBase implements ColoredEffect
{
	private Color color;

	public ParticleSpellMobAmbient(Color color)
	{
		this.color = color;
	}

	@Override
	public void play(Location l, double range)
	{
		ParticleColor c = new ParticleEffect.OrdinaryColor(getColor().getRed(), getColor().getGreen(), getColor().getBlue());
		ParticleEffect.SPELL_MOB_AMBIENT.display(c, l, range);
	}

	@Override
	public void play(Location l, Player p)
	{
		ParticleColor c = new ParticleEffect.OrdinaryColor(getColor().getRed(), getColor().getGreen(), getColor().getBlue());
		ParticleEffect.SPELL_MOB_AMBIENT.display(c, l, p);
	}

	@Override
	public ColoredEffect setColor(Color color)
	{
		this.color = color;
		return this;
	}

	@Override
	public Color getColor()
	{
		return color;
	}
}
