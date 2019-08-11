package ninja.bytecode.shuriken.plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.zip.ZipException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import ninja.bytecode.shuriken.io.VIO;

public class JarPluginManager implements PluginManager {

	private PluginClassLoader classLoader;
	private File jar;
	private PluginConfig config;
	private Plugin plugin;
	
	@SuppressWarnings("unchecked")
	public JarPluginManager(File jar) throws ZipException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		VIO.readEntry(jar, "plugin.json", (in) -> {
			try 
			{
				config = new Gson().fromJson(VIO.readAll(in), PluginConfig.class);
			} 
			
			catch (JsonSyntaxException | IOException e)
			{
				e.printStackTrace();
			}
		});
		this.jar = jar;
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
		return jar;
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
