package ninja.bytecode.shuriken.bukkit.api.atests;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.object.CustomInventory;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.InventorySkinType;

public class InventoryExampleChest extends CustomInventory
{
	public InventoryExampleChest()
	{
		super("example_chest");
		setName("Example Chest");
		setSkinType(InventorySkinType.W9_H3);
		setSkinTexture("/assets/textures/inventories/chest_3/layout.png");
	}
}
