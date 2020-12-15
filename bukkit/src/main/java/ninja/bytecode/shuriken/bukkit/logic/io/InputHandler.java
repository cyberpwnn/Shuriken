package ninja.bytecode.shuriken.bukkit.logic.io;

import java.io.InputStream;

@FunctionalInterface
public interface InputHandler
{
	public void read(InputStream in);
}
