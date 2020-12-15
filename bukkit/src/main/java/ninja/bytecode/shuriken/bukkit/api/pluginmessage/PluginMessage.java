package ninja.bytecode.shuriken.bukkit.api.pluginmessage;

import ninja.bytecode.shuriken.bukkit.bukkit.plugin.MortarAPIPlugin;

import ninja.bytecode.shuriken.collections.KList;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class PluginMessage
{
	private final KList<String> data;

	public PluginMessage(String... strings)
	{
		data = new KList<String>();
		add(strings);
	}

	public PluginMessage add(String... strings)
	{
		data.add(strings);
		return this;
	}

	@Override
	public String toString()
	{
		return data.toString();
	}

	public PluginMessage send(Player... ps)
	{
		ByteArrayDataOutput out = ByteStreams.newDataOutput();

		for(String i : data)
		{
			out.writeUTF(i);
		}

		for(Player i : ps)
		{
			i.sendPluginMessage(MortarAPIPlugin.p, "BungeeCord", out.toByteArray());
		}

		return this;
	}
}
