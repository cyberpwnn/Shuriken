package ninja.bytecode.shuriken.collections.hunk.io;

import ninja.bytecode.shuriken.collections.hunk.Hunk;
import ninja.bytecode.shuriken.execution.Function3;
import ninja.bytecode.shuriken.io.CustomOutputStream;
import ninja.bytecode.shuriken.io.IOAdapter;
import ninja.bytecode.shuriken.io.bytetag.jnbt.ByteArrayTag;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public interface HunkIOAdapter<T> extends IOAdapter<T>
{
    public void write(Hunk<T> t, OutputStream out) throws IOException;

    public Hunk<T> read(Function3<Integer,Integer,Integer,Hunk<T>> factory, InputStream in) throws IOException;

    default void write(Hunk<T> t, File f) throws IOException
    {
        f.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(f);
        GZIPOutputStream gzo = new CustomOutputStream(fos, 6);
        write(t, gzo);
    }

    default Hunk<T> read(Function3<Integer,Integer,Integer,Hunk<T>> factory, File f) throws IOException
    {
        return read(factory, new GZIPInputStream(new FileInputStream(f)));
    }

    default Hunk<T> read(Function3<Integer,Integer,Integer,Hunk<T>> factory, ByteArrayTag f) throws IOException
    {
        return read(factory, new ByteArrayInputStream(f.getValue()));
    }

    default ByteArrayTag writeByteArrayTag(Hunk<T> tHunk, String name) throws IOException
    {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        write(tHunk, boas);
        return new ByteArrayTag(name, boas.toByteArray());
    }
}
