package ninja.bytecode.shuriken.bukkit.fx;

import ninja.bytecode.shuriken.bukkit.compatibility.SoundEnum;
import ninja.bytecode.shuriken.math.M;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import ninja.bytecode.shuriken.bukkit.sound.Audible;
import ninja.bytecode.shuriken.bukkit.sound.Audio;

public class EffectCauldronBubble implements Effect
{
	private VisualEffect effect;
	private VisualEffect effect2;
	private Audible audio;
	private Audible audio2;

	public EffectCauldronBubble()
	{
		audio = new Audio().c(SoundCategory.AMBIENT).vp(0.7f, 0.1f).setSound(SoundEnum.ENTITY_BOAT_PADDLE_WATER.bukkitSound());
		audio2 = new Audio().c(SoundCategory.AMBIENT).vp(0.3f, 0.1f).setSound(SoundEnum.ITEM_BUCKET_EMPTY_LAVA.bukkitSound());
		effect = new ParticleWaterWake().setDirection(new Vector(0, 0.1, 0));
		effect2 = new ParticleWaterSplash().setDirection(new Vector(0, 0.1, 0));
	}

	@Override
	public void play(Location l)
	{
		if(M.r(0.03))
		{
			audio2.play(l);
		}

		if(M.r(0.09))
		{
			audio.play(l);
		}

		for(int i = 0; i < 3; i++)
		{
			if(M.r(0.6))
			{
				effect.play(l.clone().add(0.05, 0, 0.05).add((Math.random() - 0.5) * 0.8, 0.56, (Math.random() - 0.5) * 0.8));
			}
		}

		if(M.r(0.3))
		{
			effect2.play(l.clone().add(0.05, 0, 0.05).add((Math.random() - 0.5) * 0.8, 0.56, (Math.random() - 0.5) * 0.8));
		}
	}

	@Override
	public void play(Player p, Location l)
	{
		audio.play(p, l);
		effect.play(l.clone().add(Math.random() / 2, 0, Math.random() / 2), p);
	}

}