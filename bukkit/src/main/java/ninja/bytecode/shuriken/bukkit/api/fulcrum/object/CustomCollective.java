package ninja.bytecode.shuriken.bukkit.api.fulcrum.object;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.FulcrumRegistry;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.registry.FCURegisteredObject;
import org.bukkit.Material;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.AllocationUnit;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.IAllocation;

public abstract class CustomCollective extends FCURegisteredObject implements IAllocation
{
	private short allocationID;
	private AllocationUnit allocationUnit;
	public CustomCollective(String id)
	{
		super(id);
	}

	public abstract void registerResources(FulcrumRegistry registry);

	@Override
	public abstract String getAllocatedModel();

	@Override
	public AllocationUnit getAllocationUnit()
	{
		return allocationUnit;
	}

	@Override
	public short getAllocationID()
	{
		return allocationID;
	}

	@Override
	public Material getAllocationMaterial()
	{
		return getAllocationUnit().getMaterial();
	}

	@Override
	public void onAllocated(AllocationUnit unit, short id)
	{
		this.allocationUnit = unit;
		this.allocationID = id;
	}

	@Override
	public boolean isAllocated()
	{
		return getAllocationUnit() != null;
	}
}
