package ninja.bytecode.shuriken.bukkit.api.atests;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.object.CustomSound;
import org.bukkit.SoundCategory;

public class SoundSteelStep extends CustomSound
{
	public SoundSteelStep()
	{
		super("steel.step");
		setCategory(SoundCategory.BLOCKS);
		setDefaultPitch(1.15f);
		setDefaultPitchRandomness(0.175f);
		setDefaultVolume(0.3f);
		setStream(false);
		setSubtitle("Walking on Steel");
		addSounds("steel$", "/assets/sounds/material/metal/metalbar_break$.ogg", 1, 11);
	}
}
