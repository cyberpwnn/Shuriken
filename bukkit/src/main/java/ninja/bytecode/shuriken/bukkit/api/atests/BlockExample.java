package ninja.bytecode.shuriken.bukkit.api.atests;

import ninja.bytecode.shuriken.bukkit.bukkit.compatibility.MaterialEnum;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.object.CustomBlock;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.BlockCollision;
import ninja.bytecode.shuriken.bukkit.api.fulcrum.util.BlockSoundCategory;

public class BlockExample extends CustomBlock
{
	public BlockExample(String id)
	{
		super(id);
		setCollisionMode(BlockCollision.FULL);
		setHardnessLike(MaterialEnum.DIRT.bukkitMaterial());
		setMaxStackSize(64);
		setSound(BlockSoundCategory.BREAK, new SoundSteelBreak());
		setSound(BlockSoundCategory.PLACE, new SoundSteelPlace());
		setSound(BlockSoundCategory.LAND, new SoundSteelLand());
		setSound(BlockSoundCategory.STEP, new SoundSteelStep());
		setSound(BlockSoundCategory.DIG, new SoundSteelDig());
	}
}
