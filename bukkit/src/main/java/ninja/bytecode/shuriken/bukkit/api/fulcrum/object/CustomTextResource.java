package ninja.bytecode.shuriken.bukkit.api.fulcrum.object;

import java.io.IOException;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.FulcrumInstance;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.IResource;
import ninja.bytecode.shuriken.bukkit.logic.io.VIO;

public class CustomTextResource extends CustomResource
{
	public CustomTextResource(String id, String text)
	{
		super(id, FulcrumInstance.instance.getResources().cacheResourceText(id, text));
	}

	public CustomTextResource(String id, IResource resource)
	{
		super(id, resource);
	}

	public void rewrite(String string, String idx)
	{
		try
		{
			updateCacheKey(FulcrumInstance.instance.getResources().cacheResourceText(this.getID(), VIO.readAll(stream()).replaceAll("\\Q" + string + "\\E", idx)));
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}