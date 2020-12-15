package ninja.bytecode.shuriken.bukkit.api.fulcrum.util;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.object.CustomBlock;
import org.bukkit.Material;

public interface IAllocation
{
	public String getAllocatedModel();

	public AllocationUnit getAllocationUnit();

	public short getAllocationID();

	public Material getAllocationMaterial();

	public boolean isAllocated();

	public void onAllocated(AllocationUnit unit, short id);

	public CustomBlock block();

	public boolean isBlock();

	public boolean isItem();
}
