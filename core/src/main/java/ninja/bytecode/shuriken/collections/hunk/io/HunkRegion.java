package ninja.bytecode.shuriken.collections.hunk.io;

import ninja.bytecode.shuriken.collections.KMap;
import ninja.bytecode.shuriken.io.bytetag.jnbt.CompoundTag;
import ninja.bytecode.shuriken.io.bytetag.jnbt.NBTInputStream;
import ninja.bytecode.shuriken.io.bytetag.jnbt.NBTOutputStream;
import ninja.bytecode.shuriken.io.bytetag.jnbt.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class HunkRegion
{
	private final File folder;
	private CompoundTag compound;
	private final int x;
	private final int z;

	public HunkRegion(File folder, int x, int z, CompoundTag compound)
	{
		this.compound = fix(compound);
		this.folder = folder;
		this.x = x;
		this.z = z;
		folder.mkdirs();
	}

	public HunkRegion(File folder, int x, int z)
	{
		this(folder, x, z, new CompoundTag(x + "." + z, new KMap<>()));
		File f = getFile();

		if(f.exists())
		{
			try
			{
				NBTInputStream in = new NBTInputStream(new FileInputStream(f));
				compound = fix((CompoundTag) in.readTag());
				in.close();
			}

			catch(Throwable ignored)
			{

			}
		}
	}

	public CompoundTag getCompound() {
		return compound;
	}

	private CompoundTag fix(CompoundTag readTag)
	{
		Map<String, Tag> v = readTag.getValue();

		if(!(v instanceof KMap))
		{
			return new CompoundTag(readTag.getName(), new KMap<String, Tag>(v));
		}

		return readTag;
	}

	public File getFile()
	{
		return new File(folder, x + "." + z + ".dat");
	}

	public void save() throws IOException
	{
		synchronized(compound)
		{
			File f = getFile();
			FileOutputStream fos = new FileOutputStream(f);
			NBTOutputStream out = new NBTOutputStream(fos);
			out.writeTag(compound);
			out.close();
		}
	}

	public int getX() {
		return x;
	}
	public int getZ() {
		return z;
	}

}
