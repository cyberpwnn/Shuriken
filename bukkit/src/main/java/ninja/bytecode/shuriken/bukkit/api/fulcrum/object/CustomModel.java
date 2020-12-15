package ninja.bytecode.shuriken.bukkit.api.fulcrum.object;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.resourcepack.ModelType;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.resourcepack.PackNode;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.IResource;

public class CustomModel extends CustomTextResource
{
	private ModelType modelType;

	public CustomModel(String id, String text, ModelType modelType)
	{
		super(id, text);
		this.modelType = modelType;
	}

	public CustomModel(String id, IResource resource, ModelType modelType)
	{
		super(id, resource);
		this.modelType = modelType;
	}

	public ModelType getModelType()
	{
		return modelType;
	}

	public void setModelType(ModelType modelType)
	{
		this.modelType = modelType;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((modelType == null) ? 0 : modelType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
		{
			return true;
		}

		if(!super.equals(obj))
		{
			return false;
		}
		if(!(obj instanceof CustomModel))
		{
			return false;
		}
		CustomModel other = (CustomModel) obj;
		if(modelType != other.modelType)
		{
			return false;
		}
		return true;
	}

	@Override
	public String getID()
	{
		return toPackPath();
	}

	public String getModelName()
	{
		return super.getID();
	}

	public String toPackPath()
	{
		if(getModelType() != null)
		{
			return PackNode.model(getModelType(), super.getID() + ".json");
		}

		return null;
	}
}
