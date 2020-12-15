package ninja.bytecode.shuriken.bukkit.api.nms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.channel.Channel;
import ninja.bytecode.shuriken.collections.KMap;
import org.bukkit.entity.Player;

import ninja.bytecode.shuriken.bukkit.bukkit.plugin.ShurikenAPIPlugin;
import ninja.bytecode.shuriken.bukkit.util.text.D;

public abstract class CatalystPacketListener implements PacketListener
{
	private TinyProtocol protocol;
	protected KMap<String, String> teamCache;
	private Map<Class<?>, List<PacketHandler<?>>> inHandlers = new HashMap<>();
	private Map<Class<?>, List<PacketHandler<?>>> outHandlers = new HashMap<>();
	private Map<String, List<PacketHandler<Object>>> inAbsHandlers = new HashMap<>();
	private Map<String, List<PacketHandler<Object>>> outAbsHandlers = new HashMap<>();
	private List<PacketHandler<?>> inGlobal = new ArrayList<>();
	private List<PacketHandler<?>> outGlobal = new ArrayList<>();

	public CatalystPacketListener()
	{
		teamCache = new KMap<>();
		PacketCache.reset();
	}

	@Override
	public void openListener()
	{
		if(protocol != null)
		{
			throw new RuntimeException("Listener is already open");
		}

		protocol = new TinyProtocol(ShurikenAPIPlugin.p)
		{
			@Override
			public Object onPacketOutAsync(Player reciever, Channel channel, Object packet)
			{
				Object p = packet;

				for(PacketHandler<?> i : outGlobal)
				{
					p = i.onPacket(reciever, p);

					if(p == null)
					{
						break;
					}
				}

				for(String i : outAbsHandlers.keySet())
				{
					if(p.getClass().getSimpleName().equals(i))
					{
						for(PacketHandler<?> j : outAbsHandlers.get(i))
						{
							p = j.onPacket(reciever, p);

							if(p == null)
							{
								break;
							}
						}
					}
				}

				if(p != null && outHandlers.containsKey(packet.getClass()))
				{
					for(PacketHandler<?> i : outHandlers.get(packet.getClass()))
					{
						p = i.onPacket(reciever, p);

						if(p == null)
						{
							break;
						}
					}
				}

				return p;
			}

			@Override
			public Object onPacketInAsync(Player sender, Channel channel, Object packet)
			{
				Object p = packet;

				for(PacketHandler<?> i : inGlobal)
				{
					p = i.onPacket(sender, p);

					if(p == null)
					{
						break;
					}
				}

				for(String i : inAbsHandlers.keySet())
				{
					if(p.getClass().getSimpleName().equals(i))
					{
						for(PacketHandler<?> j : inAbsHandlers.get(i))
						{
							p = j.onPacket(sender, p);

							if(p == null)
							{
								break;
							}
						}
					}
				}

				if(p != null && inHandlers.containsKey(packet.getClass()))
				{
					for(PacketHandler<?> i : inHandlers.get(packet.getClass()))
					{
						p = i.onPacket(sender, p);

						if(p == null)
						{
							break;
						}
					}
				}

				return p;
			}
		};

		onOpened();
		D.as("Mortar NMP").l("Packet Listener Opened");
	}

	@Override
	public void closeListener()
	{
		if(protocol == null)
		{
			throw new RuntimeException("Listener is already closed");
		}

		try
		{
			protocol.close();
		}

		catch(Throwable e)
		{
			// Nobody cares
		}

		inHandlers.clear();
		outHandlers.clear();
		inAbsHandlers.clear();
		outAbsHandlers.clear();
		inGlobal.clear();
		outGlobal.clear();
		protocol = null;
		D.as("Mortar NMP").l("Packet Listener Closed");
	}

	@Override
	public <T> void addOutgoinKListener(Class<? extends T> packetType, PacketHandler<T> handler)
	{
		if(!outHandlers.containsKey(packetType))
		{
			outHandlers.put(packetType, new ArrayList<PacketHandler<?>>());
		}

		outHandlers.get(packetType).add(handler);
	}

	@Override
	public void addGlobalOutgoinKListener(PacketHandler<?> handler)
	{
		outGlobal.add(handler);
	}

	@Override
	public <T> void addIncominKListener(Class<? extends T> packetType, PacketHandler<T> handler)
	{
		if(!inHandlers.containsKey(packetType))
		{
			inHandlers.put(packetType, new ArrayList<PacketHandler<?>>());
		}

		inHandlers.get(packetType).add(handler);
	}

	@Override
	public void addIncominKListener(String packetType, PacketHandler<Object> handler)
	{
		if(!inAbsHandlers.containsKey(packetType))
		{
			inAbsHandlers.put(packetType, new ArrayList<PacketHandler<Object>>());
		}

		inAbsHandlers.get(packetType).add(handler);
	}

	@Override
	public void addGlobalIncominKListener(PacketHandler<?> handler)
	{
		inGlobal.add(handler);
	}

	@Override
	public void removeOutgoingPacketListeners(Class<?> c)
	{
		outHandlers.remove(c);
	}

	@Override
	public void removeOutgoingPacketListeners(String s)
	{
		outAbsHandlers.remove(s);
	}

	@Override
	public void removeOutgoingPacketListeners()
	{
		outHandlers.clear();
	}

	@Override
	public void removeIncomingPacketListeners(Class<?> c)
	{
		inHandlers.remove(c);
	}

	@Override
	public void removeIncomingPacketListeners(String s)
	{
		inAbsHandlers.remove(s);
	}

	@Override
	public void removeIncomingPacketListeners()
	{
		inHandlers.clear();
	}
}
