package ninja.bytecode.shuriken.bukkit.api.atests;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.object.CustomInventory;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.InventorySkinType;

public class InventoryExampleChestDouble extends CustomInventory
{
	public InventoryExampleChestDouble()
	{
		super("example_chest_double");
		setName("Example Double Chest");
		setSkinType(InventorySkinType.W9_H6);
		setSkinTexture("/assets/textures/inventories/chest_6/layout.png");
	}
}
