package ninja.bytecode.shuriken.bukkit.api.fulcrum.util;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.FulcrumInstance;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.object.CustomItem;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.object.CustomModel;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.object.CustomTexture;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.resourcepack.ModelType;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.resourcepack.TextureType;

public class CustomSkin extends CustomItem
{
	public CustomSkin(String id)
	{
		super(id);
		setMaxStackSize(1);
		setItemToolLevel(0);
		setItemToolType(ToolType.HAND);
	}

	@Override
	public String getAllocatedModel()
	{
		return "inventory/" + model.getModelName();
	}

	public void setModel(DefaultSkinModel model)
	{
		setModel(model.getPath());
	}

	@Override
	public void setModel(String modelResource)
	{
		setModel(new CustomModel(getID(), FulcrumInstance.instance.getResource(modelResource), ModelType.INVENTORY));
	}

	@Override
	public void setModel(IResource modelResource)
	{
		setModel(new CustomModel(getID(), modelResource, ModelType.INVENTORY));
	}

	@Override
	public void setTexture(String name, IResource resource)
	{
		textures.put(name, new CustomTexture(name, resource, TextureType.INVENTORIES));
	}

	@Override
	public void setModel(String name, IResource resource)
	{
		model = new CustomModel(name, resource, ModelType.INVENTORY);
	}
}
