package ninja.bytecode.shuriken.bukkit.fx;

import java.awt.Color;

import ninja.bytecode.shuriken.bukkit.particle.api.ParticleBuilder;
import ninja.bytecode.shuriken.bukkit.particle.api.ParticleEffect;
import ninja.bytecode.shuriken.math.M;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import ninja.bytecode.shuriken.bukkit.sound.Audible;
import ninja.bytecode.shuriken.bukkit.sound.Audio;

public class EffectCauldronAcceptItem implements Effect
{
	private Audible audio;
	private Color c;

	public EffectCauldronAcceptItem()
	{
		audio = new Audio().c(SoundCategory.AMBIENT).vp(1f, 0.1f)
				.setSound(Sound.BLOCK_BREWING_STAND_BREW);
		audio.addChild(((Audio) audio).clone().p(1.5f));
	}

	@Override
	public void play(Location l)
	{
		audio.play(l);
		Color c = Color.WHITE;
		for(int i = 0; i < 12; i++)
		{
			if(M.r(0.25))
			{
				c = Color.MAGENTA.darker().darker();
			}

			if(M.r(0.25))
			{
				c = Color.MAGENTA.brighter().brighter();
			}

			if(M.r(0.15))
			{
				c = Color.BLUE.brighter();
			}


			new ParticleBuilder(ParticleEffect.SPELL_MOB,l.clone().add(0.05, 0, 0.05).add((Math.random() - 0.5) * 0.8, 0.56, (Math.random() - 0.5) * 0.8))
					.setColor(c)
					.display();
		}
	}

	@Override
	public void play(Player p, Location l)
	{
		Color c = Color.WHITE;
		audio.play(p, l);
		for(int i = 0; i < 12; i++)
		{
			if(M.r(0.25))
			{
				c = Color.MAGENTA.darker().darker();
			}

			if(M.r(0.25))
			{
				c = Color.MAGENTA.brighter().brighter();
			}

			if(M.r(0.15))
			{
				c = Color.BLUE.brighter();
			}


			new ParticleBuilder(ParticleEffect.SPELL_MOB,l.clone().add(0.05, 0, 0.05).add((Math.random() - 0.5) * 0.8, 0.56, (Math.random() - 0.5) * 0.8))
					.setColor(c)
					.display(p);
		}
	}
}