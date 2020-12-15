package ninja.bytecode.shuriken.bukkit.api.atests;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.object.CustomSound;
import org.bukkit.SoundCategory;

public class SoundSteelLand extends CustomSound
{
	public SoundSteelLand()
	{
		super("steel.land");
		setCategory(SoundCategory.BLOCKS);
		setDefaultPitch(1f);
		setDefaultPitchRandomness(0.175f);
		setDefaultVolume(0.8f);
		setStream(false);
		setSubtitle("Lands on Steel");
		addSounds("steel$", "/assets/sounds/material/metal/metalbar_break$.ogg", 1, 11);
	}
}
