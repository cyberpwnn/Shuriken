package ninja.bytecode.shuriken.bukkit.api.sound;

public abstract class AudioDistortion
{
	public AudioDistortion()
	{
		
	}
	
	public abstract Audible distort(Audible a);
}
