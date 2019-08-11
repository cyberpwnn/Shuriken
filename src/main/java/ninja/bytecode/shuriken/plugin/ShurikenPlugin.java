package ninja.bytecode.shuriken.plugin;

import ninja.bytecode.shuriken.logging.L;

public abstract class ShurikenPlugin extends L implements Plugin
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
		info(getPluginManager().getConfig().getName() + " Enabled");
	}

	@Override
	public void disable() {
		if(!isEnabled())
		{
			return;
		}
		
		enabled = false;
		onDisable();
		info(getPluginManager().getConfig().getName() + " Disabled");
	}

	@Override
	public PluginManager getPluginManager() {
		return manager;
	}
}
