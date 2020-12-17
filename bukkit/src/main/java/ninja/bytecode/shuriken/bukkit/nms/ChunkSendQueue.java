package ninja.bytecode.shuriken.bukkit.nms;

import ninja.bytecode.shuriken.collections.KList;
import ninja.bytecode.shuriken.collections.KMap;
import ninja.bytecode.shuriken.collections.KSet;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import ninja.bytecode.shuriken.bukkit.sched.A;
import ninja.bytecode.shuriken.bukkit.sched.S;
import ninja.bytecode.shuriken.bukkit.sched.SR;

public class ChunkSendQueue
{
	private KList<Chunk> c = new KList<Chunk>();
	private KMap<Chunk, KSet<Integer>> sections;
	private boolean running;
	private SR s;
	private int interval;
	private int volume;

	public ChunkSendQueue(int interval, int volume)
	{
		this.interval = interval;
		this.volume = volume;
		c = new KList<Chunk>();
		sections = new KMap<Chunk, KSet<Integer>>();
		running = false;
	}

	public boolean hasStuff()
	{
		return c.size() > 0 || sections.size() > 0;
	}

	public void start()
	{
		s = new SR(interval)
		{
			@Override
			public void run()
			{
				if(!sections.isEmpty())
				{
					int l = volume;

					while(l > 0 && !sections.isEmpty())
					{
						Chunk c = sections.k().pop();
						KSet<Integer> s = sections.get(c);

						if(s.isEmpty())
						{
							sections.remove(c);
							continue;
						}

						ShadowChunk sc = NMP.CHUNK.shadow(c);
						for(int i : s)
						{
							sc.modifySection(i);
						}

						PacketBuffer pb = new PacketBuffer().q(sc.flush());

						for(Player i : NMP.CHUNK.nearbyPlayers(c))
						{
							pb.flush(i);
						}

						sections.remove(c);
						l--;
					}
				}

				if(c.isEmpty() || running)
				{
					return;
				}

				int l = volume;
				KList<Chunk> tosend = new KList<Chunk>();

				while(!c.isEmpty() && l > 0)
				{
					l--;
					tosend.add(c.pop());
				}

				running = true;
				new A()
				{
					@Override
					public void run()
					{
						for(Chunk i : tosend)
						{
							new S()
							{
								@Override
								public void run()
								{
									for(Player l : NMP.CHUNK.nearbyPlayers(i))
									{
										NMP.CHUNK.refresh(l, i);
									}
								}
							};
						}

						running = false;
					}
				};
			}
		};
	}

	public void stop()
	{
		s.cancel();
	}

	public boolean isRunning()
	{
		return running;
	}

	public void queue(Chunk c)
	{
		if(!this.c.contains(c))
		{
			this.c.add(c);
		}
	}

	public void queueSection(Chunk c, int section)
	{
		if(!sections.containsKey(c))
		{
			sections.put(c, new KSet<Integer>());
		}

		sections.get(c).add(section);
	}
}
