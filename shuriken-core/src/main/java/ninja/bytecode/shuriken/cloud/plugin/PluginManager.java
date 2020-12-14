package ninja.bytecode.shuriken.cloud.plugin;

import java.io.File;

public interface PluginManager 
{
	public PluginClassLoader getClassLoader();
	
	public Plugin getPlugin();
	
	public File getJar();
	
	public PluginConfig getConfig();
	
	public void unload();
}
