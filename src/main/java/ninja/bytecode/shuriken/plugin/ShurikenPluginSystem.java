package ninja.bytecode.shuriken.plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import ninja.bytecode.shuriken.collections.GList;

public class ShurikenPluginSystem implements PluginSystem {
	private GList<PluginManager> managers;
	
	@Override
	public GList<PluginManager> getManagers() {
		return managers.copy();
	}

	@Override
	public PluginManager load(File p) throws PluginException {
		try {
			PluginManager pm = new ShurikenPluginManager(p);
			managers.add(pm);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | IOException e) {
			throw new PluginException(e);
		}
		
		return null;
	}

	@Override
	public void loadAll(File folder) throws PluginException {
		for(File i : folder.listFiles())
		{
			if(i.isFile() && i.getName().endsWith(".jar"))
			{
				load(i);
			}
		}
	}

	@Override
	public void disableAll() {
		for(PluginManager i : getManagers())
		{
			i.getPlugin().disable();
		}
	}

	@Override
	public void unloadAll() {
		disableAll();
		
		for(PluginManager i : getManagers())
		{
			i.unload();
		}
		
		managers.clear();
	}

	@Override
	public PluginManager getPlugin(String name) {
		for(PluginManager i : getManagers())
		{
			if(i.getConfig().getName().equals(name))
			{
				return i;
			}
		}
		
		return null;
	}

}
