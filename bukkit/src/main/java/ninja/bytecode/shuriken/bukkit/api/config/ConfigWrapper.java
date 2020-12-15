package ninja.bytecode.shuriken.bukkit.api.config;

import ninja.bytecode.shuriken.collections.KList;

import java.io.File;


public interface ConfigWrapper
{
	public void load(File f) throws Exception;

	public void save(File f) throws Exception;

	public String save();

	public void load(String string) throws Exception;

	public void set(String key, Object o);

	public Object get(String key);

	public KList<String> keys();

	public boolean contains(String key);
}
