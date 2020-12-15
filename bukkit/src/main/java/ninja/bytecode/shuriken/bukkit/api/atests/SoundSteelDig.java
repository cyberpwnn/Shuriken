package ninja.bytecode.shuriken.bukkit.api.atests;

import ninja.bytecode.shuriken.bukkit.api.fulcrum.object.CustomSound;
import org.bukkit.SoundCategory;

public class SoundSteelDig extends CustomSound
{
	public SoundSteelDig()
	{
		super("steel.dig");
		setCategory(SoundCategory.BLOCKS);
		setDefaultPitch(1.15f);
		setDefaultPitchRandomness(0.175f);
		setDefaultVolume(0.4f);
		setStream(false);
		setSubtitle("Digging steel");
		addSounds("steel$", "/assets/sounds/material/metal/metalbar_break$.ogg", 1, 11);
	}
}
