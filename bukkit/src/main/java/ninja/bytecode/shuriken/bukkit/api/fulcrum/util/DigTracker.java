package ninja.bytecode.shuriken.bukkit.api.fulcrum.util;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.BlockStand12;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.ContentAssist;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.FulcrumInstance;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.object.CustomBlock;
import ninja.bytecode.shuriken.bukkit.api.nms.Catalyst;
import ninja.bytecode.shuriken.bukkit.compute.math.M;
import ninja.bytecode.shuriken.bukkit.lang.collection.GList;
import ninja.bytecode.shuriken.bukkit.lang.collection.GMap;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import ninja.bytecode.shuriken.bukkit.api.sched.J;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockBreakAnimation;

public class DigTracker
{
	private GMap<Block, Double> speed;
	private GMap<Block, Double> progress;
	private GMap<Player, Integer> digDelay;
	private GMap<Player, Block> digging;
	private GMap<Block, ArmorStand> breakStands;

	public DigTracker()
	{
		speed = new GMap<>();
		progress = new GMap<>();
		digging = new GMap<>();
		breakStands = new GMap<>();
		digDelay = new GMap<>();
		J.sr(() -> tick(), 0);
	}

	public GList<Player> getDigging(Block b)
	{
		GList<Player> p = digging.flip().get(b);

		return p != null ? p : new GList<>();
	}

	private int getBlockPos(Block b)
	{
		return b.getX() + b.getY() + b.getZ();
	}

	@SuppressWarnings("deprecation")
	public void tick()
	{
		for(Player i : digDelay.k())
		{
			if(digDelay.get(i) < 2)
			{
				digDelay.remove(i);
				continue;
			}

			digDelay.put(i, digDelay.get(i) - 1);
		}

		for(Block i : progress.k())
		{
			Player primaryDigger = null;

			try
			{
				primaryDigger = getDigging(i).get(0);
			}

			catch(Throwable e)
			{
				progress.remove(i);
				speed.remove(i);

				if(breakStands.containsKey(i))
				{
					BlockStand12.allowDelete.add(breakStands.get(i).getEntityId());
					breakStands.get(i).remove();
					breakStands.remove(i);
				}
			}

			if(hasDelay(primaryDigger))
			{
				continue;
			}

			speed.put(i, ToolLevel.getMiningSpeed(primaryDigger, ContentAssist.getBlock(i), primaryDigger.getItemInHand()));
			double digSpeed = speed.containsKey(i) ? speed.get(i) : 1D / 40D;
			progress.put(i, progress.get(i) + digSpeed);
			Player breaker = getDigging(i).get(0);
			if(progress.get(i) >= 1D)
			{
				progress.put(i, 1D);
				Bukkit.getPluginManager().callEvent(new BlockBreakEvent(i, breaker));
				progress.remove(i);
			}

			else
			{
				if(M.interval(4))
				{
					ContentAssist.getBlock(i).playSound(i, BlockSoundCategory.DIG);
				}
			}

			if(progress.containsKey(i))
			{
				int stage = (int) (progress.get(i) * 9.0);
				CustomBlock block = (CustomBlock) FulcrumInstance.instance.getRegistered("break_stage_" + stage);

				if(!breakStands.containsKey(i))
				{
					ArmorStand a = block.placeAt(i);
					breakStands.put(i, a);
				}

				else
				{
					breakStands.get(i).setHelmet(block.toItemStack(1));
				}
			}

			else if(breakStands.containsKey(i))
			{
				BlockStand12.allowDelete.add(breakStands.get(i).getEntityId());
				breakStands.get(i).remove();
				breakStands.remove(i);
			}
		}

		if(progress.isEmpty())
		{
			digging.clear();
			speed.clear();
		}
	}

	public void finishedDigging(Player p, Block b)
	{
		if(p.getGameMode().equals(GameMode.CREATIVE))
		{
			return;
		}

		cancelledDigging(p, b);
	}

	public void cancelledDigging(Player p, Block b)
	{
		if(p.getGameMode().equals(GameMode.CREATIVE))
		{
			return;
		}

		J.s(() ->
		{
			digging.remove(p);

			if(getDigging(b).isEmpty())
			{
				progress.remove(b);
				speed.remove(b);
			}

			if(breakStands.containsKey(b))
			{
				BlockStand12.allowDelete.add(breakStands.get(b).getEntityId());
				breakStands.get(b).remove();
				breakStands.remove(b);
			}

			Catalyst.host.sendViewDistancedPacket(b.getChunk(), new PacketPlayOutBlockBreakAnimation(getBlockPos(b), new BlockPosition(b.getX(), b.getY(), b.getZ()), (int) (-1)));
		});
	}

	public void startedDigging(Player p, Block b, double speed)
	{
		if(p.getGameMode().equals(GameMode.CREATIVE))
		{
			return;
		}

		J.s(() ->
		{
			if(isDigging(p))
			{
				cancelledDigging(p, getDigging(p));
			}

			digging.put(p, b);
			progress.put(b, 0D);
			this.speed.put(b, speed);
		});
	}

	public boolean isDigging(Player p)
	{
		return digging.containsKey(p);
	}

	public Block getDigging(Player p)
	{
		return digging.get(p);
	}

	public boolean hasDelay(Player p)
	{
		return digDelay.containsKey(p);
	}

	public void imposeDelay(Player p, int ticks)
	{
		digDelay.put(p, ticks);
	}
}
