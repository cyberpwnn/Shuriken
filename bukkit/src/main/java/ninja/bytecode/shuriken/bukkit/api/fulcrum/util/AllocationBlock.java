package ninja.bytecode.shuriken.bukkit.api.fulcrum.util;

import ninja.bytecode.shuriken.bukkit.bukkit.compatibility.MaterialEnum;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.Fulcrum;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.FulcrumRegistry;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.object.CustomModel;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.resourcepack.ModelType;
import ninja.bytecode.shuriken.bukkit.lang.collection.GList;
import ninja.bytecode.shuriken.bukkit.lang.collection.GMap;
import org.bukkit.Material;

public class AllocationBlock
{
	private final GMap<Material, AllocationUnit> allocationUnits;
	private final GList<Material> allocationOrder;
	private final AllocationStrategy strat;

	public AllocationBlock(AllocationStrategy stat)
	{
		allocationUnits = new GMap<>();
		allocationOrder = new GList<>();
		this.strat = stat;
	}

	public void addDefaultUnits()
	{
		add(MaterialEnum.DIAMOND_HOE.bukkitMaterial(), "diamond_hoe", "items/diamond_hoe", "item/handheld");
		add(MaterialEnum.IRON_HOE.bukkitMaterial(), "iron_hoe", "items/iron_hoe", "item/handheld");
		add(MaterialEnum.STONE_HOE.bukkitMaterial(), "stone_hoe", "items/stone_hoe", "item/handheld");
		add(MaterialEnum.GOLD_HOE.bukkitMaterial(), "golden_hoe", "items/gold_hoe", "item/handheld");
		add(MaterialEnum.WOOD_HOE.bukkitMaterial(), "wooden_hoe", "items/wood_hoe", "item/handheld");
	}

	public void registerAll(FulcrumRegistry registry)
	{
		for(AllocationUnit i : allocationUnits.v())
		{
			if(i.getAllocated() > 0)
			{
				registry.model().register(new CustomModel(i.getModel(), i.generateModel().toString(Fulcrum.minifyJSON ? 0 : 4), ModelType.ITEM));

				if(Fulcrum.generateModelNormals)
				{
					registry.model().register(new CustomModel(i.getModelNormal(), i.generateNormal().toString(Fulcrum.minifyJSON ? 0 : 4), ModelType.ITEM));
				}
			}
		}
	}

	public AllocationUnit getAllocation(Material mat)
	{
		return allocationUnits.get(mat);
	}

	public boolean isAllocated(Material mat)
	{
		return allocationUnits.containsKey(mat);
	}

	public boolean isAllocated(Material mat, short id)
	{
		return getAllocation(mat, id) != null;
	}

	public IAllocation getAllocation(Material mat, short id)
	{
		if(!isAllocated(mat))
		{
			return null;
		}

		return allocationUnits.get(mat).getAllocation(id);
	}

	public int getFreeSpace()
	{
		return getCapacity() - getAllocated();
	}

	public int getCapacity()
	{
		int m = 0;

		for(AllocationUnit i : allocationUnits.v())
		{
			m += i.getCapacity();
		}

		return m;
	}

	public int getAllocated()
	{
		int m = 0;

		for(AllocationUnit i : allocationUnits.v())
		{
			m += i.getAllocated();
		}

		return m;
	}

	public void allocate(IAllocation a)
	{
		nextAvalibleUnit().allocate(a);
	}

	public AllocationUnit nextAvalibleUnit()
	{
		if(strat.equals(AllocationStrategy.SEQUENTIAL))
		{
			for(Material m : allocationOrder)
			{
				AllocationUnit i = allocationUnits.get(m);

				if(i.hasNextID())
				{
					return i;
				}
			}
		}

		else if(strat.equals(AllocationStrategy.CYCLIC))
		{
			GList<AllocationUnit> u = getOrderedAllocationUnits();
			int m = getAllocated();

			for(int c = m; c < m + allocationUnits.size(); c++)
			{
				AllocationUnit i = u.get(c % allocationUnits.size());

				if(i.hasNextID())
				{
					return i;
				}
			}
		}

		throw new RuntimeException("Out of allocation capacity!");
	}

	private GList<AllocationUnit> getOrderedAllocationUnits()
	{
		GList<AllocationUnit> a = new GList<>();

		for(Material i : allocationOrder)
		{
			a.add(allocationUnits.get(i));
		}

		return a;
	}

	public void add(Material material, String model, String texture, String parent)
	{
		allocationUnits.put(material, new AllocationUnit(material, model, texture, parent));
		allocationOrder.add(material);
	}
}
