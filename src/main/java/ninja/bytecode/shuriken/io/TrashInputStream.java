package ninja.bytecode.shuriken.io;

import java.io.IOException;
import java.io.InputStream;

public class TrashInputStream extends InputStream
{
	@Override
	public int read() throws IOException
	{
		return (int) (Math.random() * 255);
	}
}
