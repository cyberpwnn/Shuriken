package ninja.bytecode.shuriken.bukkit.api.atests;

import ninja.bytecode.shuriken.bukkit.bukkit.compatibility.MaterialEnum;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.DefaultBlockModel;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.ToolLevel;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.ToolType;

public class BlockExampleCube extends BlockExample
{
	public BlockExampleCube()
	{
		super("example_cube_all");
		setName("Example [Cube All]");
		setModel(DefaultBlockModel.CUBE_ALL);
		getModel().rewrite("$id", getID());
		setTexture(getID(), "/assets/textures/blocks/steel.png");
		setHardnessLike(MaterialEnum.IRON_BLOCK.bukkitMaterial());
		setMinimumToolLevel(ToolLevel.STONE);
		setEffectiveToolType(ToolType.PICKAXE);
	}
}
