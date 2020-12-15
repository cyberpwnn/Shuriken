package ninja.bytecode.shuriken.bukkit.api.fx;

import java.awt.Color;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ninja.bytecode.shuriken.bukkit.api.particle.ParticleEffect;
import ninja.bytecode.shuriken.bukkit.api.particle.ParticleEffect.ParticleColor;

public class ParticleSpellMob extends ParticleBase implements ColoredEffect
{
	private Color color;

	public ParticleSpellMob(Color color)
	{
		this.color = color;
	}

	@Override
	public void play(Location l, double range)
	{
		ParticleColor c = new ParticleEffect.OrdinaryColor(getColor().getRed(), getColor().getGreen(), getColor().getBlue());
		ParticleEffect.SPELL_MOB.display(c, l, range);
	}

	@Override
	public void play(Location l, Player p)
	{
		ParticleColor c = new ParticleEffect.OrdinaryColor(getColor().getRed(), getColor().getGreen(), getColor().getBlue());
		ParticleEffect.SPELL_MOB.display(c, l, p);
	}

	@Override
	public ParticleSpellMob setColor(Color color)
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
