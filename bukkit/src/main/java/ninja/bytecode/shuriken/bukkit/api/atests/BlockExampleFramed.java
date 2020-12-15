package ninja.bytecode.shuriken.bukkit.api.atests;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.DefaultBlockModel;

public class BlockExampleFramed extends BlockExample
{
	public BlockExampleFramed()
	{
		super("example_cube_framed");
		setName("Example [Cube Framed]");
		setModel(DefaultBlockModel.CUBE_FRAMED);
		getModel().rewrite("$id", getID());
		setTexture(getID() + "_outside", "/assets/textures/blocks/steel.png");
		setTexture(getID() + "_inside", "/assets/textures/blocks/quartz.png");
	}
}
