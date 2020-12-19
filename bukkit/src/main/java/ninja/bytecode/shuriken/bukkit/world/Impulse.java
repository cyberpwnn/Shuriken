package ninja.bytecode.shuriken.bukkit.world;


import ninja.bytecode.shuriken.bukkit.sched.J;
import ninja.bytecode.shuriken.bukkit.sched.SR;
import ninja.bytecode.shuriken.collections.KList;
import ninja.bytecode.shuriken.math.M;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.concurrent.atomic.AtomicInteger;

public class Impulse
{
	private double radius;
	private double forceMax;
	private int repeats;
	private boolean pull;
	private double forceMin;
	private KList<Entity> ignore;
	private double damageMin;
	private double damageMax;

	public Impulse(double radius)
	{
		ignore = new KList<Entity>();
		this.radius = radius;
		this.forceMax = 1;
		this.forceMin = 0;
		this.damageMax = 1;
		this.repeats = 0;
		this.damageMin = 0;
		this.pull = false;
	}

	public Impulse echo(int repeats)
	{
		this.repeats = M.iclip((double)repeats, 0D, 1200D);
		return this;
	}

	public Impulse push()
	{
		pull = false;
		return this;
	}

	public Impulse pull()
	{
		pull = true;
		return this;
	}

	public Impulse radius(double radius)
	{
		this.radius = radius;
		return this;
	}

	public Impulse force(double force)
	{
		this.forceMax = force;
		return this;
	}

	public Impulse force(double forceMax, double forceMin)
	{
		this.forceMax = forceMax;
		this.forceMin = forceMin;
		return this;
	}

	public Impulse damage(double damage)
	{
		this.damageMax = damage;
		return this;
	}

	public Impulse damage(double damageMax, double damageMin)
	{
		this.damageMax = damageMax;
		this.damageMin = damageMin;
		return this;
	}

	public void punch(Location at)
	{
		doPunch(at);
		if(repeats > 0)
		{
			AtomicInteger a = new AtomicInteger(0);
			J.sr(() -> {
				double fac =1D - ((1D / (double)repeats) * (double)a.get());
				radius *= fac;
				damageMin = 0;
				damageMax = 0;
				forceMax *= fac;
				forceMin *= fac;
				doPunch(at);
			}, 0, repeats);
		}
	}

	private void doPunch(Location at)
	{
		Area a = new Area(at, radius);

		for(Entity i : a.getNearbyEntities())
		{
			if(ignore.contains(i))
			{
				continue;
			}

			Vector force = VectorMath.direction(at, i.getLocation());

			if(pull)
			{
				force = VectorMath.reverse(force.clone());
			}

			double damage = 0;
			double distance = i.getLocation().distance(at);

			if(forceMin < forceMax)
			{
				force.clone().multiply(((1D - (distance / radius)) * (forceMax - forceMin)) + forceMin);
			}

			if(damageMin < damageMax)
			{
				damage = ((1D - (distance / radius)) * (damageMax - damageMin)) + damageMin;
			}

			try
			{
				if(i instanceof LivingEntity && damage > 0)
				{
					((LivingEntity) i).damage(damage);
				}

				if(i instanceof Player && ((Player)i).getGameMode().equals(GameMode.SPECTATOR))
				{
					continue;
				}i.setVelocity(i.getVelocity().add(force));
			}

			catch(Exception e)
			{

			}
		}
	}

	public Impulse ignore(Entity player)
	{
		ignore.add(player);
		return this;
	}
}
