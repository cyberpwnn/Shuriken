package ninja.bytecode.shuriken.bukkit.api.atests;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.object.CustomSound;
import org.bukkit.SoundCategory;

public class SoundSteelPlace extends CustomSound
{
	public SoundSteelPlace()
	{
		super("steel.place");
		setCategory(SoundCategory.BLOCKS);
		setDefaultPitch(1f);
		setDefaultPitchRandomness(0.175f);
		setDefaultVolume(1f);
		setStream(false);
		setSubtitle("Steel Placed");
		addSounds("steelbreak$", "/assets/sounds/material/metal/metalbar_walk$.ogg", 1, 7);
	}
}
