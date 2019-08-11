package ninja.bytecode.shuriken.plugin;

public abstract class ShurikenPlugin implements Plugin
{
	private boolean enabled;
	private PluginManager manager;
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public void setManager(PluginManager manager)
	{
		this.manager = manager;
	}

	@Override
	public void enable() {
		if(isEnabled())
		{
			return;
		}
		
		enabled = true;
		onEnable();
	}

	@Override
	public void disable() {
		if(!isEnabled())
		{
			return;
		}
		
		enabled = false;
		onDisable();
	}

	@Override
	public PluginManager getPluginManager() {
		return manager;
	}
}
