package ninja.bytecode.shuriken.plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.zip.ZipException;

public class SideloadedPluginManager implements PluginManager {

	private PluginClassLoader classLoader;
	private File jar;
	private PluginConfig config;
	private Plugin plugin;
	
	@SuppressWarnings("unchecked")
	public SideloadedPluginManager(String name, String pc) throws ZipException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		config = new PluginConfig("internal", pc, name);
		this.classLoader = new PluginClassLoader(new URL[] {jar.toURI().toURL()}, getClassLoader());
		Class<? extends Plugin> pclass = (Class<? extends Plugin>) classLoader.loadClass(config.getMain());
		plugin = pclass.getConstructor().newInstance();
		plugin.setManager(this);
	}
	
	@Override
	public PluginClassLoader getClassLoader() {
		return classLoader;
	}

	@Override
	public Plugin getPlugin() {
		return plugin;
	}

	@Override
	public File getJar() {
		return null;
	}

	@Override
	public void unload()
	{
		if(plugin.isEnabled())
		{
			plugin.disable();
		}
		
		plugin.setManager(null);
		plugin = null;
		try {
			classLoader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		classLoader = null;
	}

	@Override
	public PluginConfig getConfig() {
		return config;
	}
}
