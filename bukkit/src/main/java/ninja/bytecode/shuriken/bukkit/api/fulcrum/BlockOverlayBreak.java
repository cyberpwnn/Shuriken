package ninja.bytecode.shuriken.bukkit.api.fulcrum;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.object.CustomBlock;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.BlockCollision;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.DefaultBlockModel;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.ToolType;

public class BlockOverlayBreak extends CustomBlock
{
	public BlockOverlayBreak(int stage)
	{
		super("break_stage_" + stage);

		if(stage < 0 || stage > 9)
		{
			throw new RuntimeException("Stage must be 0-9");
		}

		setModel(DefaultBlockModel.CUBE_ALL);
		getModel().rewrite("$id", getID());
		setName("Break Stage " + stage);
		setTexture(getID(), "/assets/textures/blocks/destroy_stage_" + stage + ".png");
		setHardness(-1);
		setEffectiveToolType(ToolType.HAND);
		setMinimumToolLevel(Integer.MAX_VALUE);
		setCollisionMode(BlockCollision.NONE);
	}

}
