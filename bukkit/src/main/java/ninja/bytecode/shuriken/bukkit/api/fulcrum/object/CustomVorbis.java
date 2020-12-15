package ninja.bytecode.shuriken.bukkit.api.fulcrum.object;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.IResource;

public class CustomVorbis extends CustomResource
{
	public CustomVorbis(String id, String cacheKey)
	{
		super(id, cacheKey);
	}

	public CustomVorbis(String id, IResource resource)
	{
		super(id, resource);
	}

	public String toPackPath()
	{
		return "sounds/" + getID() + ".ogg";
	}

	public String toSoundsPath()
	{
		return toSoundsPathJ() + ".ogg";
	}

	public String toSoundsPathJ()
	{
		return getID();
	}
}
